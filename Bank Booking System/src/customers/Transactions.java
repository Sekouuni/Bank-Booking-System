package customers;

public class Transactions 
{	
	// Instance variables for a transaction
	private int accountNumber = 0;
	private int transactionNumber = 0;
	private String date;
	private String product;
	private double amount;
	
	public Transactions(int accountNumber, int transactionNumber, String date, String product, double amount)
	{
		this.accountNumber = accountNumber;
		this.transactionNumber = transactionNumber;
		this.date = date;
		this.product = product;
		this.amount = amount;
	}

	public void printTransactions()
	{
		System.out.printf("%d %s %s £%.2f. %n", transactionNumber, date, product, amount);
	}
	
    public String transactionsToString()
    {
    	return accountNumber + ";" + transactionNumber + ";" + date + ";" + product + ";" + amount;
    }
    
	public int getAccountNumber() 
	{
		return accountNumber;
	}
}
