package data;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption 
{
	private int key;
	
	public Encryption(int givenKey) 
	{
		key = givenKey;
	}

	public void encrypt(String s, OutputStream outStr) throws IOException 
	{
		boolean done = false;
		while(!done) 
		{
			for (int i = 0; i < s.length(); i++)
			{
				char c = s.charAt(i);
				byte b = (byte) c; // convert to byte
				byte w = (byte) (b + key); // encryption process
				// write
				outStr.write(w);
			}
			done = true;
			// Add new line
			outStr.write(System.getProperty("line.separator").getBytes());
			outStr.flush();
		}		
	}
	
	public String decrypt(String s) throws IOException 
	{
		String plainText = "";
		boolean done = false;
		while(!done) 
		{
			for (int i = 0; i < s.length(); i++)
			{
				char c = s.charAt(i);
				byte b = (byte) c; // convert to byte
				byte w = (byte) (b - key); // decryption process
				char p = (char) w; // convert back to char
				plainText += p; // add to the string
			}
			done = true;
		}	
		// Return decrypted string
		return plainText;
	}
	
    public static String hash(String password) 
    {
		try
		{	
			//Static getInstance method is called with hashing SHA
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			
			// digest() method called to calculate message digest of input
			// and return array of byte
			byte[] messageDigest = md.digest(password.getBytes());
			
			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);
			
			// Convert message digest into hex value
			String hashtext = no.toString(16);
			
			while (hashtext.length() < 32 ) 
			{
				hashtext = "0" + hashtext;
			}
			
			return hashtext;	
		}
		
		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) 
		{
			System.out.println("Exception thrown" + "for incorrect algortihm: " + e);
			return null;
		}
	}
}