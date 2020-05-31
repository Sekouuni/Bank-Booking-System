package data;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import main.Start;

public class Appointments 
{
	// Instance variables for an appointment
	private int accountNumber = 0;
	private String customerFirstName = "";
	private String customerLastName = "";
	private String date = "";
	private String service = "";
	private String employeeFirstName = "";
	private String employeeLastName = "";
	private String managerFirstName = "";
	private String outcome = "";
  
    public Appointments(int accountNumber, String customerFirstName, String customerLastName, String date, String service, String employeeFirstName, String employeeLastName, String managerFirstName, String outcome)
    { 
        this.accountNumber = accountNumber;
        this.customerFirstName =customerFirstName;
        this.customerLastName = customerLastName;
        this.date = date;
        this.service = service;
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.managerFirstName = managerFirstName;
        this.outcome = outcome; 
    }
    
    public static void saveData(String fileName) throws IOException 
	{
		OutputStream outStr = new FileOutputStream(fileName);
		try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) 
		{
			for (int i = 0; i < Start.appointmentList.size(); i++)
			{
				try 
				{
					// Instance of the cipher object. -9 is the key.
					Encryption cipher = new Encryption(-9); 
					cipher.encrypt(Start.appointmentList.get(i).arrayToString(), outStr);
				}
				catch (IOException e)
				{
					System.out.println("[ERROR] : " + e);
				}
			}			
		} 
	}
    
    // Declare setters/getters for required fields
    public String arrayToString()
	{
		return accountNumber + ";" + customerFirstName + ";" + customerLastName + ";" + date + ";" + service + ";" + employeeFirstName + ";" + employeeLastName + ";" + managerFirstName + ";" + outcome;
	}
    
    public void printAppointments() 
    {
    	System.out.printf("%d - %s %s on %s regarding %s. %n", accountNumber, customerFirstName, customerLastName, date, service);
    }
    
    public void setManagerName(String managerFirstName)
    {
    	this.managerFirstName = managerFirstName;
    }
    
    public void setOutcome(String outcome)
    {
    	this.outcome = outcome;
    }
    
    public int getAccountNumber() 
    {
    	return accountNumber;
    }
    
   public String getCustomerFirstName() 
   { 
	   return customerFirstName; 
   }
   
   public String getCustomerLastName() 
   {
	   return customerLastName; 
   }
   
   public String getDate() 
   {
	   return date;   
   }
   
   public String getService() 
   {
	   return service;
   }
   
   public String getEmployeeFirstName() 
   {
	   return employeeFirstName; 
   }
   
   public String getEmployeeLastName() 
   {	   
	   return employeeLastName;
   }
   
   public String getManagerFirstName()
   {
	   return managerFirstName;
   }
   
   public String getOutcome() 
   {  
	   return outcome;   
   }   
}