package employees;

import java.io.FileWriter;
import java.util.ArrayList;
import data.Encryption;
import main.Start;

public class EmployeeView 
{
	// Arraylist of strings for failed login logs
	public static ArrayList<String> employeeLogs = new ArrayList<String>();
	
	// Variables used throughout class
	private static String username = "";
	private static String firstName = ""; 
	private static String lastName = "";
	
	public static void employeeLogin() throws Exception 
	{		
		boolean login = false;
		String password = "";
		int loopCounter = 0;
        int totalAttempts = 3;
	
		System.out.println("Welcome Employee");
		// Loop until login is successful
		while (login == false || totalAttempts == 0) 
		{
			System.out.println("Please enter your username:");
			username = Start.scan.next();
			
			System.out.println("Please enter your password:");
			password = Start.scan.next();
			// Hash the password for comparison as passwords are stored as hash values
			password = Encryption.hash(password);
			
			for (int i = 0; i < Start.employeeList.size(); i++)
            {
                if (username.equals(Start.employeeList.get(i).getUsername()) && password.equals((Start.employeeList.get(i).getPassword()))) 
                {
                	// If login is successful then store the user's name
                	firstName = Start.employeeList.get(i).getFirstName();
                    lastName = Start.employeeList.get(i).getLastName();
                	System.out.println("Access Granted");
                    login = true;
                    // Go to employee menu
                    employeeMenu();
                    break;
                }
            } 
			if(login == true)
            {
            	// Logged in successfully
            }	
			else 
			{
				System.out.println("Incorrect Username/Password");
				System.out.println("Please try again");
				// Set up the logs of the attempted login from the user if it is incorrect
				loopCounter++;
	            if (loopCounter == 1) 
	            {
	            	// For each attempt send the data to 'logAttempt' for formatting and then store it in the arraylist
	                employeeLogs.add(Start.logAttempt(username, password));
	            } 
	            else if (loopCounter == 2) 
	            {
	            	employeeLogs.add(Start.logAttempt(username, password));
	            } 
	            else if (loopCounter == 3) 
	            {
	            	employeeLogs.add(Start.logAttempt(username, password));
	            } 
	            else if (loopCounter == 4) 
	            {
	            	employeeLogs.add(Start.logAttempt(username, password));
	            } 
	            else if (loopCounter == 5) 
	            {
	            	employeeLogs.add(Start.logAttempt(username, password));
	            }
	            totalAttempts--;
			}
			// When remaining attempts reaches 0 the user will be locked out
            if (totalAttempts == 0) 
            {
            	System.out.println("[WARNING! YOUR USERNAME AND PASSWORDS HAVE BEEN LOGGED FOR SECURITY PURPOSES]");
            	System.out.println("ACCOUNT LOCKED, NUMBER OF ATTEMPTS EXCEEDED");
                // Reset remaining attempts
            	totalAttempts = 3;
                // Call lock out method
                Start.lock();
                
                // Write the arraylist of logs to a file
                FileWriter writer = new FileWriter("employeeLogsFile.txt"); 
                for(String str: employeeLogs) 
                {
					writer.write(str);
					// Add new line
					writer.write(System.getProperty("line.separator"));
					writer.flush();
                }
                // Close file writer
                writer.close();
	        }   
		}
	}
	    
	private static void employeeMenu() throws Exception 
	{
		String choice = "";
		
		do
		{
			// Display main menu for the employees, loops until they decide to quit
			System.out.println("-- Employee Area --");
			System.out.println("1 - View Calender");
			System.out.println("2 - View Past Appointments");
			System.out.println("3 - Refer Customer to Manager");
			System.out.println("Q - Quit");
			System.out.print("Pick : ");
			choice = Start.scan.next().toUpperCase();
			// Call corresponding method for the user input
			switch (choice) 
			{
				case "1" :
				{
					upcomingAppointments();
					choice = "";
					break;
				}	
				case "2" :
				{
					pastAppointments();
					choice = "";
					break;
				}
				case "3" : 
				{
					referCustomer();
					choice = "";
					break;
				}
				case "Q":
				{
					// Exit back to the main menu of the bank
					System.out.println("Goodbye " +firstName + " " + lastName);
					break;
				}
				default :
				{
					// Keep prompting the user for a valid input by looping over the menu
					System.out.println("[ERROR] The input you have entered is invalid.");
		        	employeeMenu();
				}
			}
		} while (!choice.equals("Q"));
	}
	
	private static void upcomingAppointments() throws Exception 
	{
        System.out.println("Upcoming Appointments for " + firstName + " " + lastName + " :" );
        
        int numberOfAppointments = 0;
        
		for (int i = 0; i < Start.appointmentList.size(); i++)
        {
			// Search through the appointments list and find any place where the employee names match. if they do print that appointment when outcome is pending
			if (firstName.equals(Start.appointmentList.get(i).getEmployeeFirstName()) && lastName.equals(Start.appointmentList.get(i).getEmployeeLastName()) && Start.appointmentList.get(i).getOutcome().equals("Pending"))
			{
				Start.appointmentList.get(i).printAppointments();
				numberOfAppointments++;
			}
        }
		// In the event that no appointments are found
		if (numberOfAppointments==0)
		{
			System.out.println("No upcoming appointments.");
		}
		// Return user back to menu
		Start.returnToMenu();
	}
 
	private static void pastAppointments() throws Exception 
	{
		System.out.println("Past Appointments:" );
	    int numberOfAppointments = 0;
	        
		for (int i = 0; i < Start.appointmentList.size(); i++)
	    {
			// Search through the appointments list and find any place where the employee names match. if they do print that appointment when outcome is not pending
	    	if (firstName.equals(Start.appointmentList.get(i).getEmployeeFirstName()) && lastName.equals(Start.appointmentList.get(i).getEmployeeLastName()) && !(Start.appointmentList.get(i).getOutcome().equals("Pending")))
	    	{
	    		Start.appointmentList.get(i).printAppointments();
	    		numberOfAppointments++;
	    	}			
	    }    
		// In the event of no appointments
		if (numberOfAppointments == 0)
		{
			System.out.println("No past appointments.");
		}
		// Return user back to menu
		Start.returnToMenu();
	}
 
	private static void referCustomer() throws Exception 
	{
		// Yes or no question is validated by the yesOrNo method
		System.out.println("Would you like to refer a customer to the branch manager? YES or NO");
	    boolean refer = main.Start.yesOrNo();
	    // If return is true display next set of options otherwise return to menu
	    if (refer == true) 
	    {
	    	System.out.println("You are now refferring a customer to the branch manager, please enter the following information:");
	        
	        int numberOfAppointments = 0;
	        int referralIndex = 0;
	        int accountNumber = 0;
	        String date = "";
	         
	        // Ask for accountNumber and validate it using numberValidation method
	        System.out.println("Customer's Account Number: ");
	        accountNumber = Start.numberValidation();
	        System.out.println("Date of Appointment: ");
	        date = Start.scan.next();
	  	    
	        for (int i = 0; i < Start.appointmentList.size(); i++)
	        {
	        	// Search through the appointments list for the appointment specified by the user, checking account number, date and employee names
	        	if (accountNumber == (Start.appointmentList.get(i).getAccountNumber()) && date.equals(Start.appointmentList.get(i).getDate()) && firstName.equals(Start.appointmentList.get(i).getEmployeeFirstName()) && lastName.equals(Start.appointmentList.get(i).getEmployeeLastName()))
	        	{
	        		numberOfAppointments++;
	        		// Log index of matching appointment
	  	    		referralIndex = i;
	        	}
	        }
	        // In the event of no matches
	        if (numberOfAppointments==0)
	        {
	        	System.out.println("No previous appointments.");
	        }
	        else
	        {
	        	// If a match has been found then ask for the managers name
	        	System.out.println("Manager's First Name: ");
	        	String managersName = Start.scan.next();
	        	
	        	// Set the managers name
	        	Start.appointmentList.get(referralIndex).setManagerName(managersName);
	        }
	    }  
	    // Return to menu
	    Start.returnToMenu();
	}	
}