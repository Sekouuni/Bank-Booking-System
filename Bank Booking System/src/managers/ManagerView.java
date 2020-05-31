package managers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import customers.Customers;
import data.Encryption;
import employees.Employees;
import main.Start;

public class ManagerView 
{
	// Arraylist of strings for failed login logs
	public static ArrayList<String> managerLogs = new ArrayList<String>();
	
	// Variables used throughout class
	private static String username = "";
	private static String firstName = "";
    private static String lastName = "";

	public static void managerLogin() throws Exception 
	{
		boolean login = false;
		String password = "";
		int loopCounter = 0;
		int totalAttempts = 2;

		System.out.println("Welcome Manager");
		// Loop until login is successful 
		while (login == false || totalAttempts == 0) 
		{
			System.out.println("Please Enter Your Username:");
			username = Start.scan.next();

			System.out.println("Please Enter Your Password:");
			password = Start.scan.next();
			// Hash the password for comparison as passwords are stored as hash values
			password = Encryption.hash(password);

			for (int i = 0; i < Start.managerList.size(); i++)
            {
                if (username.equals(Start.managerList.get(i).getUsername()) && password.equals((Start.managerList.get(i).getPassword()))) 
                {
                	// If login is successful then store the user's name
                	firstName = Start.managerList.get(i).getFirstName();
                    lastName = Start.managerList.get(i).getLastName();
                	System.out.println("Access Granted");
                    login = true;
                    // Go to manager menu
                    managerMenu();
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
				System.out.println("Please Try Again");
				// Set up the logs of the attempted login from the user if it is incorrect
				loopCounter++;
	            System.out.println(totalAttempts);
	            if (loopCounter == 1) 
	            {
	            	// For each attempt send the data to 'logAttempt' for formatting and then store it in the arraylist
	            	managerLogs.add(Start.logAttempt(username, password));
	            } 
	            else if (loopCounter == 2) 
	            {
	            	managerLogs.add(Start.logAttempt(username, password));
	            } 
	            else if (loopCounter == 3) 
	            {
	            	managerLogs.add(Start.logAttempt(username, password));
	            } 
	            else if (loopCounter == 4) 
	            {
	            	managerLogs.add(Start.logAttempt(username, password));
	            } 
	            else if (loopCounter == 5) 
	            {
	            	managerLogs.add(Start.logAttempt(username, password));
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
				FileWriter writer = new FileWriter("managerLogsFile.txt"); 
                for(String str: managerLogs) 
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
	
	private static void managerMenu() throws Exception 
	{
		String choice = "";

		do 
		{
			// Display main menu for the managers, loops until they decide to quit
			System.out.println("-- Manager's Area --");
			System.out.println("1 - View Upcoming Employee Appointments");
			System.out.println("2 - View Customer Accounts");
			System.out.println("3 - View Employee Accounts");
			System.out.println("4 - View Outstanding Applications");
			System.out.println("Q - Logout");
			System.out.print("Pick : ");
			choice = Start.scan.next().toUpperCase();
			// Call corresponding method for the user input
			switch (choice) 
			{
				case "1": 
				{
					viewUpcomingAppointments();
					choice = "";
					break;
				}
				case "2": 
				{
					viewCustomerAccounts();
					choice = "";
					break;
				}
				case "3": 
				{
					viewEmployeeAccounts();
					choice = "";
					break;
				}
				case "4": 
				{
					viewOutstandingApplications();
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
		        	managerMenu();
				}
			}
		} while (!choice.equalsIgnoreCase("Q"));
	}
	
	private static void viewUpcomingAppointments() throws Exception 
	{
		System.out.println("--View Employee's Upcoming Appointments--");

		int numberOfAppointments = 0;   
		for (int i = 0; i < Start.employeeList.size(); i++) 
		{
			// Loop over employee list to find each employee and print their name
			numberOfAppointments = 0; 
			System.out.println(Start.employeeList.get(i).getFirstName() + " " + Start.employeeList.get(i).getLastName());
			
			for (int j = 0; j < Start.appointmentList.size(); j++) 
			{
				// For each employee search through the appointments list to find any appointments that they have and print them
				if (Start.employeeList.get(i).getFirstName().equals(Start.appointmentList.get(j).getEmployeeFirstName()) && Start.employeeList.get(i).getLastName().equals(Start.appointmentList.get(j).getEmployeeLastName()) && Start.appointmentList.get(j).getOutcome().equals("Pending"))
				{
					 Start.appointmentList.get(j).printAppointments();
					 numberOfAppointments ++;
				}
			}
			// In the event of no appointments
			if (numberOfAppointments == 0)
			{
				System.out.println("No Upcoming Appointments.");
			}
		}
		// Return to menu
		Start.returnToMenu();
	}

	private static void viewCustomerAccounts() throws Exception 
	{
		System.out.println("--Customer Accounts--");

		int numberOfCustomers = 0;

		for (int i = 0; i < Start.customerList.size(); i++) 
		{
			// Loop over the customers list and print out a summary of their data
			Start.customerList.get(i).printCustomerData();
			numberOfCustomers++;
		}
		// If no customers are found
		if (numberOfCustomers == 0) 
		{
			System.out.println("No Customers Available.");
		}

		String choice = "";
		
		do
		{
			// Loop over the sub menu until they decide to return to menu
			System.out.println("Would you like to add or remove Customer Accounts?");
			System.out.println("1 - Add Customer Account");
			System.out.println("2 - Remove Customer Account");
			System.out.println("Q - Return to Main Menu");
			System.out.print("Pick : ");
			choice = Start.scan.next().toUpperCase();
			// Call corresponding method for the user input
			switch(choice)
			{
				case "1" :
				{
					addCustomer();
					choice = "";
					break;
				}

				case "2" :
				{
					removeCustomer();
					choice = "";
					break;
				}
				case "Q":
				{
					// Exit back to the managers menu
					break;
				}
				default :
				{
					// Keep prompting the user for a valid input by looping over the menu
					System.out.println("[ERROR] The input you have entered is invalid.");
		        	managerMenu();
				}
			}
		} while (!choice.equalsIgnoreCase("Q"));
	}

	private static void removeCustomer() throws IOException 
	{
		int accountIndex = -1;
		int accountNumber = 0;
		
		// Ask for accountNumber and validate it using numberValidation method
		System.out.println("Please Enter The Account Number Of The Customer You Would Like To Remove :");
		accountNumber = Start.numberValidation();

		for (int i = 0; i < Start.customerList.size(); i++) 
		{
			// Find the customer by their account number
			if (accountNumber == Start.customerList.get(i).getAccountNumber()) 
			{
				// Log account index
				accountIndex = i;
			}
		}
		if (accountIndex > -1)
		{
			// Remove from the arraylist
			Start.customerList.remove(accountIndex);
		}
		else
		{
			// In the event that the customer wasn't found or removal failed
			System.out.println("No customer was found. No customers where removed.");
		}
	}

	private static void addCustomer() throws Exception 
	{
		int accountNumber = 0;
		String firstName;
		String lastName;
		int customerNumber;
		int houseNumber = 0;
		String addressL1 = "";
		String addressL2 = "";
		double currentBalance = 0.0;
		String service1 = "";
		String service2 = "";
		String password = "";
		int accountIndex = -1;
		
		// Ask for accountNumber and validate it using numberValidation method
		System.out.println("Please Enter The Account Number Of The Customer You Would Like To Add :");
		accountNumber = Start.numberValidation();
		
		System.out.println("Please Enter the Following Information About the Customer.");
		System.out.println("First Name :");
		firstName = Start.scan.next();
		
		System.out.println("Last Name :");
		lastName = Start.scan.next();
		
		// Ask for customer number and validate it using numberValidation method
		System.out.println("Customer Number :");
		customerNumber = Start.numberValidation();
		
		// Ask for house number and validate it using numberValidation method
		System.out.println("House Number :");
		houseNumber = Start.numberValidation();
		
		System.out.println("Address Line 1 :");
		addressL1 = Start.scan.next();
		
		System.out.println("Address Line 2 :");
		addressL2 = Start.scan.next();
		
		// Ask for account balance and validate it using doubleValidation method
		System.out.println("Starting Balance :");
		currentBalance = Start.doubleValidation();
		
		System.out.println("Service 1 :");
		service1 = Start.scan.next();
		
		System.out.println("Service 2 :");
		service2 = Start.scan.next();
		
		System.out.println("Initial Password :");
		password = Start.scan.next();
		// Hash the password for comparison as passwords are stored as hash values
		password = Encryption.hash(password);
		
		// Create a new customer object and add it to the arraylist
		Customers customer = new Customers(customerNumber, password, firstName, lastName);
		Start.customerList.add(customer);
		
		for (int i = 0; i < Start.customerList.size(); i++) 
		{
			if (customerNumber == Start.customerList.get(i).getCustomerNumber()) 
			{
				// Log the index of the new object
				accountIndex = i;
			}
		}	
		// Call the append method and add the new data to the new object
		Start.customerList.get(accountIndex).appendPersonalData(accountNumber, firstName, lastName, customerNumber, houseNumber, addressL1, addressL2, currentBalance, service1, service2);
		// Return to menu
        Start.returnToMenu();
	}

	private static void viewEmployeeAccounts() throws Exception 
	{
		System.out.println("--Employee Accounts--");

		int numberOfEmployees = 0;

		for (int i = 0; i < Start.employeeList.size(); i++) 
		{
			// For each employee in that employee list print out a summary of their data
			Start.employeeList.get(i).printEmployeeData();
			numberOfEmployees++;
		}
		// In the event of no employees found
		if (numberOfEmployees == 0) 
		{
			System.out.println("No Employee Accounts Available.");
		}

		String choice = "";

		do 
		{
			// Loop over the sub menu until they decide to return to menu
			System.out.println("Would you like to add or remove an Employee Account?");
			System.out.println("1 - Add Employee Account");
			System.out.println("2 - Remove Employee Account");
			System.out.println("Q - Return to Main Menu");
			choice = Start.scan.next().toUpperCase();
			// Call corresponding method for the user input
			switch (choice)
			{
				case "1" :
				{
					addEmployee();
					choice = "";
					break;
				}
				case "2" : 
				{
					removeEmployee();
					choice = "";
					break;
				}
				case "Q" :
				{
					// Exit back to the managers menu
					break;
				}
				default :
				{
					// Keep prompting the user for a valid input by looping over the menu
					System.out.println("[ERROR] The input you have entered is invalid.");
		        	managerMenu();
				}
			}
		} while (!choice.equalsIgnoreCase("Q"));
	}

	private static void removeEmployee() throws IOException
	{
		int accountIndex = -1;
		String username = "";
		
		System.out.println("Please Enter The Account Username Of The Employee You Would Like To Remove :");
		username = Start.scan.next();

		for (int i = 0; i < Start.employeeList.size(); i++) 
		{
			// Find the employee by the username
			if (username.equals(Start.employeeList.get(i).getUsername())) 
			{
				// Log account index
				accountIndex = i;
			}
		}
		if (accountIndex > -1)
		{
			// Remove from arraylist
			Start.employeeList.remove(accountIndex);
		}
		else
		{
			// In the event that the customer wasn't found or removal failed
			System.out.println("No employee was found. No employees where removed.");
		}
	}

	private static void addEmployee() throws Exception
	{
		String username = "";
		String firstName = "";
		String lastName = "";
		int houseNumber = 0;
		String addressL1 = "";
		String addressL2 = "";
		String workingHours = "";
		String workingDays = "";
		String specialisation = "";
		String password = "";
		int accountIndex = -1;
		
		System.out.println("Please Enter The Username Of The Employee You Would Like To Add :");
		username = Start.scan.next();
		
		System.out.println("Please Enter the Following Information About the Employee.");
		System.out.println("First Name :");
		firstName = Start.scan.next();
		
		System.out.println("Last Name :");
		lastName = Start.scan.next();
		
		// Ask for house number and validate it using numberValidation method
		System.out.println("House Number :");
		houseNumber = Start.numberValidation();
		
		System.out.println("Address Line 1 :");
		addressL1 = Start.scan.next();
		
		System.out.println("Address Line 2 :");
		addressL2 = Start.scan.next();
		
		System.out.println("Working Hours :");
		workingHours = Start.scan.next();
		
		System.out.println("Working Days :");
		workingDays = Start.scan.next();
		
		System.out.println("Specialisation :");
		specialisation = Start.scan.next();
		
		System.out.println("Initial Password :");
		password = Start.scan.next();
		// Hash the password for comparison as passwords are stored as hash values
		password = Encryption.hash(password);
		
		// Create a new employee object and add to arraylist
		Employees employee = new Employees(username, password, firstName, lastName);
		Start.employeeList.add(employee);
		
		for (int i = 0; i < Start.employeeList.size(); i++) 
		{
			if (username == Start.employeeList.get(i).getUsername()) 
			{
				// Log account index
				accountIndex = i;
			}
		}	
		// Call the append method and add the new data to the new object
		Start.employeeList.get(accountIndex).appendEmployeeData(username, firstName, lastName, houseNumber, addressL1, addressL2, workingHours, workingDays, specialisation);
        // Return to menu
        Start.returnToMenu();
	}

	private static void viewOutstandingApplications() throws Exception 
	{
		System.out.println("-- Outstanding Applications --");
		
		int numberOfAppointments = 0;
		
		for (int i = 0; i < Start.appointmentList.size(); i++) 
		{
			// For each application in the list that is outstanding print that appointment
			if (firstName.equals(Start.appointmentList.get(i).getManagerFirstName()) && Start.appointmentList.get(i).getOutcome().equals("Pending")) 
			{
				Start.appointmentList.get(i).printAppointments();
				numberOfAppointments++;
			} 
		}
		// In the event that no appointments are outstanding
		if (numberOfAppointments == 0) 
		{
			System.out.println("No Referred Appointments Available.");
		}
		else
		{
			// Yes or no question is validated by the yesOrNo method
			System.out.println("Would you like to process any of the applications? (Yes OR No)");
			boolean process = Start.yesOrNo();
			// If return is true call process method otherwise continue
			if (process == true)
			{
				processApplications();
			}
		}
		// Return to menu
		Start.returnToMenu();
	}

	private static void processApplications() throws Exception
	{
		int numberOfReferrals = 0;
		int referralIndex = 0;
		int accountNumber = 0;
		
		// Ask for account number and validate it using numberValidation method
		System.out.println("Please Enter The Account Number Of The Customer You'd Like To Process :");
		accountNumber = Start.numberValidation();
		
		for (int i = 0; i < Start.appointmentList.size(); i++)
		{
			// Loop over appointments list and find any appointments for the specified customer that are pending
			if (accountNumber  == Start.appointmentList.get(i).getAccountNumber() && Start.appointmentList.get(i).getOutcome().equals("Pending"))
        	{
        		numberOfReferrals++;
  	    		referralIndex = i;
        	}
		}
        // In the event that no referrals are found  
        if (numberOfReferrals == 0)
        {
        	System.out.println("No Applications Exist Under This Account Number.");
        }
        else
        {
        	// Ask for the outcome of application
        	System.out.println("Is this application accepted or denied? : ");
	        String outcome = Start.scan.next();
	        // Set outcome of application
	        Start.appointmentList.get(referralIndex).setOutcome(outcome);
        }
        // Return to menu
		Start.returnToMenu();
	}    
}