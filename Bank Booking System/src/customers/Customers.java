package customers;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import data.Encryption;
import main.Start;

public class Customers 
{	
	// Global declaration of transactions list non static as there is one list for each customer object 
	public ArrayList<Transactions> transactionList = new ArrayList<Transactions>();
	
	// Instance variables for a customer
	private String firstName = "";
	private String lastName = "";
	private String password = "";
	private int accountNumber = 0;
	private int customerNumber = 0;
    private int houseNumber = 0;
    private String addressL1 = "";
    private String addressL2 = "";
    private double currentBalance = 0.00;
    private String service1 = "";
    private String service2 = "";
    
    public Customers(int customerNumber, String password, String firstName, String lastName)
    { 
        this.customerNumber = customerNumber;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public void appendPersonalData(int accountNumber, String firstName, String lastName, int customerNumber, int houseNumber, String addressL1, String addressL2, double currentBalance, String service1, String service2)
    {
        this.accountNumber = accountNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerNumber = customerNumber;
        this.houseNumber = houseNumber;
        this.addressL1 = addressL1;
        this.addressL2 = addressL2;
        this.currentBalance = currentBalance;
        this.service1 = service1;
        this.service2 = service2;
    }
    
    public void setTransactionList(Transactions t)
    {
    	transactionList.add(t);
    }
    
    public static void saveData (String fileName, int n) throws IOException 
    {       
    	OutputStream outStr = new FileOutputStream(fileName);
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) 
        {
        	for (int i = 0; i < Start.customerList.size(); i++)
            {
        		try 
                {
        			// Instance of the cipher object. -12 is the key.
                    Encryption cipher = new Encryption(-12); 
                    if (n == 1)
                    {
                    	cipher.encrypt(Start.customerList.get(i).customerAccountsToString(), outStr);
                    }
                    else if (n == 2) 
                    {
                    	cipher.encrypt(Start.customerList.get(i).customerDataToString(), outStr);
                    }
                    else if (n == 3)
                    {
                    	for (int j = 0; j < Start.customerList.get(i).transactionList.size(); j++)
                    	{
                    		cipher.encrypt(Start.customerList.get(i).transactionList.get(j).transactionsToString(), outStr);
                    	}
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
    public int getCustomerNumber() 
    {
        return customerNumber;
    }
    
    public String getFirstName() 
    {
    	return firstName;
	}
    
    public String getLastName() 
    {
    	return lastName;
	}
    
    public String getPassword()
    {
        return password;
    }
    
    public int getAccountNumber()
    {
    	return accountNumber;
    }
    
    public double getCurrentBalance() 
    {
    	return currentBalance;
	}
    
    public void setAccountNumber(int accountNumber)
    {
    	this.accountNumber = accountNumber;
    }
    
    public void setFirstName(String firstName)
    {
    	this.firstName = firstName;
    }
    
    public void setLastName(String lastName)
    {
    	this.lastName = lastName;
    }
    
    public void setCustomerNumber(int customerNumber)
    {
    	this.customerNumber = customerNumber;
    }

	public void setPassword(String password) 
	{
		this.password = password;
	}
    
    public void setHouseNumber(int houseNumber)
    {
    	this.houseNumber = houseNumber;
    }
    
    public void setAddressL1(String addressL1)
    {
    	this.addressL1 = addressL1;
    }
    
    public void setAddressL2(String addressL2)
    {
    	this.addressL2 = addressL2;
    }
    
    public void setCurrentBalance(int currentBalance)
    {
    	this.currentBalance = currentBalance;
    }
    
    public void setService1(String service1)
    {
    	this.service1 = service1;
    }
    
    public void setService2(String service2)
    {
    	this.service2 = service2;
    }
    
    public String customerAccountsToString() 
    {
        return customerNumber + ";" + password + ";" + firstName + ";" + lastName;
    }
    
    public String customerDataToString() 
    {
        return accountNumber + ";" + firstName + ";" + lastName + ";" + customerNumber + ";" + houseNumber + ";" +  addressL1 + ";" + addressL2 + ";" + currentBalance + ";" + service1 + ";" + service2;
    }
    
	public void printCustomers() 
	{
		System.out.printf("%s , %s , %s , %s.%n", firstName, lastName, addressL1, addressL2);
	}

	public void printCustomerData() 
    {
    	System.out.printf("Account Number - %d. Name - %s %s. Customer Number - %d. Address - %d %s %s. Current Balance - £%.2f. Services - %s %s. %n", accountNumber, firstName, lastName, customerNumber, houseNumber, addressL1, addressL2, currentBalance, service1, service2);
    }
}