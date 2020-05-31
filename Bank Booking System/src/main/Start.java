package main;

import customers.Customers;
import customers.Transactions;
import data.Appointments;
import data.Encryption;
import employees.Employees;
import managers.Managers;
//import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Start 
{
	// Global declaration of all the arraylists
	public static ArrayList<Customers> customerList = new ArrayList<Customers>();
	public static ArrayList<Employees> employeeList = new ArrayList<Employees>();
	public static ArrayList<Managers> managerList = new ArrayList<Managers>();
	public static ArrayList<Appointments> appointmentList = new ArrayList<Appointments>();
	
	// Global declaration for input scanner
	public static Scanner scan = new Scanner(System.in);
	
	// Set up a scanner for a file reader
	private static Scanner read = new Scanner(System.in);
	
	public static void main(String[] args) throws Exception
	{
		// Start of program.
		
		// Must load accounts first in order to create the object to then append to it.
		// Customers
		loadCustomerAccounts("customerAccounts.txt");
		loadCustomerData("customerData.txt");
		// Must load accounts first in order to create the object to then append to it.
		// Employees
		loadEmployeeAccounts("employeeAccounts.txt");
		loadEmployeeData("employeeData.txt");
		// Managers
		loadManagerAccounts("managerAccounts.txt");
		// Appointments
		loadAppointments("Appointments.txt");
		// Transactions
		loadTransactions("customerTransactions.txt");
		
		// Close file reader as all files have been read.
		read.close();
		
		// Go to main login menu
		loginMenu();
		// User has logged out
		
		// Close input scanner as there are no more inputs.
		scan.close();
		
		// Save all of the files
		Customers.saveData("customerAccounts.txt", 1);
		Customers.saveData("customerData.txt", 2);
		Customers.saveData("customerTransactions.txt", 3);
		Employees.saveData("employeeAccounts.txt", 1);
		Employees.saveData("employeeData.txt", 2);
		Managers.saveData("managerAccounts.txt");
		Appointments.saveData("appointments.txt");
		
		System.exit(0);
		// End of program.
     }
	
	public static void loginMenu() throws Exception
	{
		String choice = "";
		
		// Start a repeating main menu that loops until the Quit option is chosen.
		do
		{		
			System.out.println("-- Welcome to Liverpool Bank --");
			System.out.println("-- MAIN MENU --");
			System.out.println("1 - Customer Login");
			System.out.println("2 - Employee Login");
			System.out.println("3 - Manager Login");
			System.out.println("Q - Quit");
			System.out.print("Pick : ");
			
			choice = scan.next().toUpperCase();
			
			switch (choice) 
			{
				case "1" : 
				{
					// Load customer interface
					customers.CustomerView.custLogin();
					choice = "";
					break;
				}
				case "2" : 
				{
					// Load employee interface
					employees.EmployeeView.employeeLogin();
					choice = "";
					break;
				}
				case "3" : 
				{
					// Load manager interface
					managers.ManagerView.managerLogin();
					choice = "";
					break;
				}   
				case "Q" :
				{
					// Exit switch statement and then the loop
					break;
				}
				default :
				{
					// If the input is not one of the 4 options an error is shown and the user will be asked again
					System.out.println("[ERROR] invalid input");
				}
			}
         } while (!choice.equalsIgnoreCase("Q"));

		System.out.println("Goodbye!");
	}
	
	private static void loadCustomerAccounts(String fileName) throws Exception 
 	{
 		// Setup scanner for file reader with the given parameter
		read = new Scanner(new FileReader(fileName));
		while (read.hasNext()) 
		{
			// Read entire line of the file
			String encrypted = read.nextLine();
			// New instance of the cipher object where the key is -12
			Encryption cipher = new Encryption(-12);
			// Call decryption method then split the line by semi colon.
			String [] strs = cipher.decrypt(encrypted).split(";");
			
			// Create new customer objects
			Customers customer = new Customers(Integer.valueOf(strs[0]), strs[1], strs[2], strs[3]);
	    	customerList.add(customer);
		}
 	}
 	
 	private static void loadCustomerData(String fileName) throws Exception 
 	{
 		// Setup scanner for file reader with the given parameter
 		read = new Scanner(new FileReader(fileName));
 		while (read.hasNextLine()) 
 		{
			// Read entire line of the file
			String encrypted = read.nextLine();
			// New instance of the cipher object where the key is -12
			Encryption cipher = new Encryption(-12);
			// Call decryption method then split the line by semi colon.
			String [] strs = cipher.decrypt(encrypted).split(";");
 			
 			for (int i = 0; i < customerList.size(); i++) 
 	 		{
 				/* Search through the arraylist to find where the customer numbers match ( prevously stored and the one just read in ) 
 				 * if they match then call the append method and pass the data from the file.
 				 */
 	 			if (Integer.valueOf(strs[3]) == customerList.get(i).getCustomerNumber())
 	 			{
 	 				customerList.get(i).appendPersonalData(Integer.valueOf(strs[0]), strs[1], strs[2], Integer.valueOf(strs[3]), Integer.valueOf(strs[4]), strs[5], strs[6], Double.valueOf(strs[7]), strs[8], strs[9]);
 	 			}
 	 		}
 		}
 	}
 	
 	private static void loadEmployeeAccounts(String fileName) throws Exception 
	{
 		// Setup scanner for file reader with the given parameter
 		read = new Scanner(new FileReader(fileName));
		while (read.hasNext()) 
		{
			// Read entire line of the file
			String encrypted = read.nextLine();
			// New instance of the cipher object where the key is -7
			Encryption cipher = new Encryption(-7);
			// Call decryption method then split the line by semi colon.
			String [] strs = cipher.decrypt(encrypted).split(";");

			// Create new employee objects
			Employees employee = new Employees(strs[0], strs[1], strs[2], strs[3]);
	    	employeeList.add(employee);   
		}
	}
 	
 	private static void loadEmployeeData(String fileName) throws Exception 
 	{
 		// Setup scanner for file reader with the given parameter
 		read = new Scanner(new FileReader(fileName));
 		while (read.hasNextLine()) 
 		{
			// Read entire line of the file
			String encrypted = read.nextLine();
			// New instance of the cipher object where the key is -7
			Encryption cipher = new Encryption(-7);
			// Call decryption method then split the line by semi colon.
			String [] strs = cipher.decrypt(encrypted).split(";");
			
 			for (int i = 0; i < employeeList.size(); i++)
 	 		{
 				/* Search through the arraylist to find where the usernames match ( prevously stored and the one just read in ) 
 				 * if they match then call the append method and pass the data from the file.
 				 */
 				if ((strs[0]).equals(employeeList.get(i).getUsername()))
 	 			{
 	 				employeeList.get(i).appendEmployeeData(strs[0], strs[1], strs[2], Integer.valueOf(strs[3]), strs[4], strs[5], strs[6], strs[7], strs[8]);
 	 			}
 	 		}
 		}
 	}
 	
 	private static void loadManagerAccounts(String fileName) throws Exception 
	{
 		// Setup scanner for file reader with the given parameter
 		read = new Scanner(new FileReader(fileName));
		while (read.hasNext()) 
		{
			// Read entire line of the file
			String encrypted = read.nextLine();
			// New instance of the cipher object where the key is -5
			Encryption cipher = new Encryption(-5);
			// Call decryption method then split the line by semi colon.
			String [] strs = cipher.decrypt(encrypted).split(";");
			
			Managers manager = new Managers(strs[0], strs[1], strs[2], strs[3]);
	    	managerList.add(manager);
		}
	}
 	
 	private static void loadAppointments(String fileName) throws Exception
    {
 		// Setup scanner for file reader with the given parameter
 		read = new Scanner(new FileReader(fileName));
 		while(read.hasNext())
		{
			// Read entire line of the file
			String encrypted = read.nextLine();
			// New instance of the cipher object where the key is -9
			Encryption cipher = new Encryption(-9);
			// Call decryption method then split the line by semi colon.
			String [] strs = cipher.decrypt(encrypted).split(";");
			  
 			Appointments appointment = new Appointments(Integer.valueOf(strs[0]), strs[1], strs[2], strs[3], strs[4], strs[5], strs[6], strs[7], strs[8]);
 			appointmentList.add(appointment);
		}
    }
 	
 	private static void loadTransactions(String fileName) throws Exception
	{
 		// Setup scanner for file reader with the given parameter
 		read = new Scanner(new FileReader(fileName));
	   	while(read.hasNext())
	   	{
			// Read entire line of the file
			String encrypted = read.nextLine();
			// New instance of the cipher object where the key is -12
			Encryption cipher = new Encryption(-12);
			// Call decryption method then split the line by semi colon
			String [] strs = cipher.decrypt(encrypted).split(";");
 			
			// Create new transaction object
	   		Transactions transaction = new Transactions(Integer.valueOf(strs[0]), Integer.valueOf(strs[1]), strs[2], strs[3], Double.valueOf(strs[4]));
	   		
	   		// For each customer in the arraylist check if the have any transactions
	   		for (int i = 0;  i < customerList.size(); i++)
			{
				if ((customerList.get(i).getAccountNumber()) == transaction.getAccountNumber())
				{
					// If they do add it to their per object transaction list
					customerList.get(i).setTransactionList(transaction);
				}
			}
	   	}
	}
 	
 	public static void lock() 
    {
        try 
        {
            // Pause the program for 9000 msec Or 9 seconds
            Thread.sleep(9000);
        } 
        catch (InterruptedException e)
        {
            // Output any errors
        	e.printStackTrace();
        }
    }
 	
 	public static String logAttempt(String input1, String input2) 
    {
        // Format the two input parameters and return the string
 		String stringLog = "The username logged is: " + input1 + " The password logged is: "+ input2;
        return stringLog;
    }
 	
 	public static void returnToMenu() throws Exception
    {
         String choice = "";
         
         /* Pause the program to give the user time to read the on screen info and when they are ready 
          * they should enter any character and the program will continue
          */
         System.out.println();
         System.out.println("Enter any character to return to menu.");
         choice = Start.scan.next();
         // If the user enters any value continue
         if (choice != "")
         {
        	 // Continue
         }
         else
         {
        	 System.out.println("[ERROR]");
         }
    }

 	public static Integer numberValidation()
    {
       int number = 0;
       while (!scan.hasNextInt())
       {
    	   // Continue to loop until the user enters an integer
    	   System.out.println("[ERROR] Invalid input please enter a number.");
    	   scan.next();
       }
       number = scan.nextInt();
       // Return the integer
       return number;
    }
 	
 	public static double doubleValidation()
    {
       double input = 0.0;
       while (!scan.hasNextDouble())
       {
    	   // Continue to loop until the user enters a double
    	   System.out.println("[ERROR] Invalid input please enter a double (0.00).");
    	   scan.next();
       }
       input = scan.nextDouble();
       // Return the double
       return input;
    }
 	
 	public static Boolean yesOrNo()
    {
        // Yes or No question method
        // Reads input from user and compares using if statement and returns a value back to the method it is called from
 		String yOrN = "";
        yOrN = scan.next();
        while (yOrN != "")
        {
            if (yOrN.equalsIgnoreCase("yes"))
            {
                return true;
            }
            else if (yOrN.equalsIgnoreCase("no"))
            {
                return false;
            }
            else
            {
                //This will continue to prompt the user for a valid input.
                System.out.println("[ERROR] Invalid input. Please enter (Yes/No)");
                yOrN = scan.nextLine();
            }
        }
        yOrN = "";
        return null;
    }
}