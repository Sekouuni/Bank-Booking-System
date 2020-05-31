package customers;

import java.util.Random;
import java.io.FileWriter;
import java.util.ArrayList;
import main.Start;
import data.Appointments;
import data.Encryption;

public class CustomerView
{
	// Arraylist of strings for failed login logs
    private static ArrayList<String> customerLogs = new ArrayList<String>();
    
    // Variables used throughout class
    private static int userID = 0;
    private static String firstName = "";
    private static String lastName = "";
    
    public static void custLogin() throws Exception 
    {    
        boolean login = false;
        String password = "";
        int loopCounter = 0;
        int totalAttempts = 3;
                
        System.out.println("Welcome Customer");
        // Loop until login is successful
        while (login == false || totalAttempts == 0) 
        {
            System.out.println("Please enter your login number (Customer Number):");
            // Validate the input is a number
            userID = Start.numberValidation();
            
            System.out.println("Please enter your password:");
            password = Start.scan.next();
            // Hash the password for comparison as passwords are stored as hash values
            password = Encryption.hash(password);
            
            for (int i = 0; i < Start.customerList.size(); i++)
            {
                if (userID == (Start.customerList.get(i).getCustomerNumber()) && password.equals((Start.customerList.get(i).getPassword()))) 
                {
                	// If login is successful then store the user's name
                	firstName = Start.customerList.get(i).getFirstName();
                    lastName = Start.customerList.get(i).getLastName();
                	System.out.println("Access Granted");
                    login = true;
                    // Go to customer menu
                    customerMenu();
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
	            	customerLogs.add(Start.logAttempt(String.valueOf(userID), password));
	            } 
	            else if (loopCounter == 2) 
	            {
	            	customerLogs.add(Start.logAttempt(String.valueOf(userID), password));
	            } 
	            else if (loopCounter == 3) 
	            {
	            	customerLogs.add(Start.logAttempt(String.valueOf(userID), password));
	            } 
	            else if (loopCounter == 4) 
	            {
	            	customerLogs.add(Start.logAttempt(String.valueOf(userID), password));
	            } 
	            else if (loopCounter == 5) 
	            {
	            	customerLogs.add(Start.logAttempt(String.valueOf(userID), password));
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
                FileWriter writer = new FileWriter("customerLogsFile.txt"); 
                for (String str: customerLogs) 
                {
					writer.write(str);
					// Add a new line
					writer.write(System.getProperty("line.separator"));
					writer.flush();
                }
                // Close file writer
                writer.close();
	        }   
        }
    }
    
    private static void customerMenu() throws Exception
    {
        String choice = "";
        do
        {
        	// Display main menu for the customers loops until they decide to quit
			System.out.println("--Customer Menu--");
			System.out.println("1 - View Your Account Balance");
			System.out.println("2 - List Your Most Recent Transactions");
			System.out.println("3 - Add/Change Your Personal Details");
			System.out.println("4 - Request an appointment in your local branch");
			System.out.println("Q - Quit");
			choice = Start.scan.next().toUpperCase();
			// Call corresponding method for the user input
	        switch(choice)
	        {
		        case "1" :
		        {
		        	accountBalance();
		            choice = "";
		            break;
		        }
		        case "2" :
		        {
		        	listTransactions();
		        	choice = "";
		            break;
		        }
		        case "3" :
		        {
		            changeDetails();
		            choice = "";
		            break;
		        }
		        case "4" :
		        {
		            requestAppointment();
		            choice = "";
		            break;
		        }
		        case "Q" :
		        {
		            // Exit back to the main menu of the bank
		        	System.out.println("Goodbye " + firstName + " " + lastName);
		        	break;
		        }
		        default:
		        {
		        	// Keep prompting the user for a valid input by looping over the menu
		        	System.out.println("[ERROR] The input you have entered is invalid.");
		        	customerMenu();
		        }
	        }
        } while (!choice.equalsIgnoreCase("Q"));
    }
    
    private static void accountBalance() throws Exception
    {       
        for (int i = 0; i < Start.customerList.size(); i ++)
        {
        	// Find the user in the arraylist and print out their current balance
            if (userID == (Start.customerList.get(i).getCustomerNumber()))
            {
                System.out.printf("Your current account balance is: £%.2f%n", Start.customerList.get(i).getCurrentBalance());
            }
        }
        // Return user back to menu
        Start.returnToMenu();
    }
	
    private static void listTransactions() throws Exception
    {
    	int accountNumber = 0;
	    int numberOfTransactions = 0;
	    int accountIndex = -1;
	    
    	System.out.println("-- Recent Transactions --");

    	for (int i = 0; i < Start.customerList.size(); i++)
		{
			// Find the index and account number of the user currently logged in
    		if (userID == (Start.customerList.get(i).getCustomerNumber()))
	    	{
	    		accountNumber = Start.customerList.get(i).getAccountNumber();
	    		accountIndex = i;
	    		break;
	    	}	
		}
	    
    	// Search through the transactions list for the user and print out and transactions
		for (int i = 0; i < Start.customerList.get(accountIndex).transactionList.size(); i++)
	    {
			if (accountNumber == (Start.customerList.get(accountIndex).transactionList.get(i).getAccountNumber()))
            {
				Start.customerList.get(accountIndex).transactionList.get(i).printTransactions();
				numberOfTransactions++;
            }
	    } 
		// In the event of no recent transactions
		if (numberOfTransactions == 0 && accountIndex > -1)
		{
			System.out.println("No recent transactions.");
		}
		// Return user back to menu
		Start.returnToMenu();
    }
    
    private static void changeDetails() throws Exception
    {
        // Yes or no question is validated by the yesOrNo method
    	System.out.println("Would you like to change your details? (Yes or No)");
        boolean change = main.Start.yesOrNo();
        // If return is true display next set of options otherwise return to menu
        if (change == true)
        {
        	String choice = "";
        	int accountIndex = -1;
            int accountNumber = 0;
            int houseNumber = 0;
            String addressL1 = "";
            String addressL2 = "";
            String newPassword = "";
            // Change details menu
            System.out.println("-- Change Details --");
            System.out.println("1 - Change Address");
            System.out.println("2 - Change Password");
            System.out.println("Q - Quit to Menu");
            
            choice = Start.scan.next().toUpperCase();
            // Loop the options until user decides to quit
            while (!choice.equals("Q"))
            {
				switch (choice)
				{
				    case "1" :
				    {
					    // For changing address get the account index
				    	for (int i = 0; i < Start.customerList.size(); i++)
					    {    
					        if (accountNumber == Start.customerList.get(i).getAccountNumber())
					        {
					            accountIndex = i;
					        }
					    }  
				    	
				    	// Ask for new address and validate the house number using numberValidation method
					    System.out.println("Please enter your new address: ");
					    houseNumber = Start.numberValidation();
						addressL1 = Start.scan.next();
						addressL2 = Start.scan.next();
						
						// Set the new address of the user using the account index
						Start.customerList.get(accountIndex).setHouseNumber(houseNumber);
						Start.customerList.get(accountIndex).setAddressL1(addressL1);
						Start.customerList.get(accountIndex).setAddressL2(addressL2);
						
						// Display back to the user what their new address is so they know it is correct
						System.out.println("Address successfully updated to : " + houseNumber  + " " +  addressL1 + " " + addressL2);
					    break;
					}
					case "2" :
					{
						// For changing password get the account index
						for (int i = 0; i < Start.customerList.size(); i++)
					    {
							if (accountNumber == Start.customerList.get(i).getAccountNumber())
							{
								accountIndex = i;
							}
					    }
					     
						// Repeat the question until the password passes the validity checks
						boolean valid = false;
						while (valid == false)
						{
					    	System.out.println("Please enter your new Password (Must be 8 of more characters and contain at least 1 special character e.g. £ $ * ^ % : ");
							newPassword = Start.scan.next();
							
							// Check to make sure the new password is 8 or more characters
							for (int i = 7; i >= newPassword.length();)
							{
							   System.out.println("Invalid Password: Password is too short!");
							   break;
							}
							  
							// Check to make sure that the password contains at least one of the outlined special characters
							if (newPassword.contains("$") || newPassword.contains("%") || newPassword.contains("*") || newPassword.contains("^") || newPassword.contains("£") || newPassword.contains("&") && newPassword.length() > 7)
							{
							    // If these checks are passed ask user to enter password again to avoid mistakes
								System.out.println("Valid password, please retype the password again to confirm.");
							    String validPassword = Start.scan.next();
							
							    // Check if the double entered passwords match
								if (validPassword.equals(newPassword))
								{
								   System.out.println("Password successfully updated."); 
								   // Hash password and store the new password 
								   newPassword = Encryption.hash(newPassword);
								   Start.customerList.get(accountIndex).setPassword(newPassword);
								   valid = true;
								}
								else if (!validPassword.equals(newPassword))
								{
									// If passwords don't match it will fail and the user will have to start again
									System.out.println("[ERROR] Passwords Do Not Match!");
								}
							}
							else
							{
							    newPassword = Encryption.hash(newPassword);
							    // Another check to make sure the user isn't trying to set the same password they already have
							    if (newPassword.equals(Start.customerList.get(accountIndex).getPassword()))
							    {
							        System.out.println("Invalid Password: Cannot use the same password!");
							    }
							}
					    }
                          choice = "";
					}
                 }
             }
            // Return user back to menu
            Start.returnToMenu();
         }
         else if (change == false)
         {
        	 // Return user back to menu
        	 Start.returnToMenu();
         }
    }
    
    private static void requestAppointment() throws Exception 
    {
    	int accountNumber = 0;
    	String date = "";
    	String context = "";
     	String employeeFirstName = "";
     	String employeeLastName = "";
     	
    	System.out.println("Please Enter the Following Information To Book Your Appointment.");
    	System.out.println("Please enter your account number :");
    	// Validate the input is a number
    	accountNumber = Start.numberValidation();
    	
    	System.out.println("Please enter a date when want your appointment (Format - DD/MM/YYYY): ");
    	date = Start.scan.next();
    	
     	System.out.println("What is the appointment about? (Loan, Mortgage, Overdraft): ");
     	context = Start.scan.next();
     	
     	/* Depending on what service the appointment is regarding search through the employees list
     	 * and find an employee that specialises in that area
     	 * if a specialist can not be found then randomly assign an employee to the appointment
     	 */
     	if (context.equalsIgnoreCase("loan"))
     	{
     		// Find specialist in loans
     		for (int i = 0; i < Start.employeeList.size(); i++)
     		{
     			if ((Start.employeeList.get(i).getSpecialisation()).equalsIgnoreCase(context))
     			{
     				employeeFirstName = Start.employeeList.get(i).getFirstName();
     				employeeLastName = Start.employeeList.get(i).getLastName();
     				break;
     			}
     		}
     	}
     	else if (context.equalsIgnoreCase("mortgage"))
     	{
     		// Find specialist in mortgages
     		for (int i = 0; i < Start.employeeList.size(); i++)
     		{
     			if ((Start.employeeList.get(i).getSpecialisation()).equalsIgnoreCase(context))
     			{
     				employeeFirstName = Start.employeeList.get(i).getFirstName();
     				employeeLastName = Start.employeeList.get(i).getLastName();
     				break;
     			}
     		}
     	}
     	else if (context.equalsIgnoreCase("overdraft"))
     	{
     		// Find specialist in overdrafts
     		for(int i = 0; i < Start.employeeList.size(); i++)
     		{
     			if ((Start.employeeList.get(i).getSpecialisation()).equalsIgnoreCase(context))
     			{
     				employeeFirstName = Start.employeeList.get(i).getFirstName();
     				employeeLastName = Start.employeeList.get(i).getLastName();
     				break;
     			}
     		}
     	}
     	else
     	{
     		// Randomly assign an employee by generating a number between 0 and the size of the arraylist
     		Random num = new Random();
     		int n = num.nextInt(Start.employeeList.size());
     		employeeFirstName = Start.employeeList.get(n).getFirstName();
     		employeeLastName = Start.employeeList.get(n).getLastName();
     	}
     	
     	// Create new appoinment object and add it to the arraylist
     	// No manager is set at this time as the employees send them off to managers for processing and the outcome is set as pending
        Appointments appointment = new Appointments(accountNumber, firstName, lastName, date, context, employeeFirstName, employeeLastName, "", "Pending");
		Start.appointmentList.add(appointment);
		// Return user back to menu
		Start.returnToMenu();
     }
}