package employees;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import data.Encryption;
import main.Start;

public class Employees 
{
	// Instance variables for an employee
	private String username = "";
	private String firstName = "";
	private String lastName = "";
	private String password = "";
	private int houseNumber = 0;
	private String addressL1 = "";
	private String addressL2 = "";
	private String workingHours = "";
	private String workingDays = "";
	private String specialisation = "";
	
	public Employees(String username, String password, String firstName, String lastName)
	{ 
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public void appendEmployeeData(String username, String firstName, String lastName, int houseNumber, String addressL1, String addressL2, String workingHours, String workingDays, String specialisation)
	{
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.houseNumber = houseNumber;
		this.addressL1 = addressL1;
		this.addressL2 = addressL2;
		this.workingHours = workingHours;
		this.workingDays = workingDays;
		this.specialisation = specialisation;
	}
	
	public static void saveData(String fileName, int n) throws IOException 
	{
		OutputStream outStr = new FileOutputStream(fileName);
		try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) 
		{
			for (int i = 0; i < Start.employeeList.size(); i++)
			{
				try 
				{
					// Instance of the cipher object. -7 is the key.
					Encryption cipher = new Encryption(-7); 
					 if (n == 1)
	                    {
						 cipher.encrypt(Start.employeeList.get(i).employeeAccountsToString(), outStr);
	                    }
	                    else if (n == 2) 
	                    {
	                    	cipher.encrypt(Start.employeeList.get(i).employeeDataToString(), outStr);
	                    }
	                    else
	                    {
	                    	System.out.println("[ERROR] Invalid input parameter.");
	                    }
				}
				catch (IOException e)
				{
					System.out.println("[ERROR] : " + e);
				}
			}			
		} 
	}
	
	// Declare setters/getters for required fields
	public String getUsername() 
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public String getSpecialisation()
	{
		return specialisation;
	}
	
	public String employeeAccountsToString()
	{
		return username + ";" + password + ";" + firstName + ";" +lastName;
	}
	
	public String employeeDataToString() 
	{
		return username + ";" + firstName + ";" + lastName + ";" + houseNumber + ";" + addressL1 + ";" + addressL2 + ";" + workingHours + ";" + workingDays + ";" + specialisation; 
	}
	
	public void printEmployees() 
	{
		System.out.printf("%s , %s , %s , %s.%n", firstName, lastName, addressL1, addressL2);	
	}
	
	public void printEmployeeData() 
    {
    	System.out.printf("Username - %s. Name - %s %s.  Address - %d %s %s. Working Hours - %s. Working Days - %s. Specialisation - %s. %n", username, firstName, lastName, houseNumber, addressL1, addressL2, workingHours, workingDays, specialisation);
    }
}