package managers;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import data.Encryption;
import main.Start;

public class Managers
{
	// Instance variables for a manager
	private String username = "";
	private String firstName = "";
	private String lastName = "";
	private String password = "";

	public Managers(String username, String password, String firstName, String lastName)
	{ 
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public static void saveData(String fileName) throws IOException 
	{
		OutputStream outStr = new FileOutputStream(fileName);
		try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) 
		{
			for (int i = 0; i < Start.managerList.size(); i++)
			{
				try 
				{
					// Instance of the cipher object. -5 is the key.
					Encryption cipher = new Encryption(-5); 
					cipher.encrypt(Start.managerList.get(i).managerAccountsToString(), outStr);
				}
				catch (IOException e)
				{
					System.out.println("[ERROR] : " + e);
				}
			}			
		} 
	}

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
	
	public String managerAccountsToString()
	{
		return username + ";" + password + ";" + firstName + ";" +lastName;
	}
}