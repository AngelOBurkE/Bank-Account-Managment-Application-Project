/*
* Name: Angelo Burke
* Date Nov 21, 2025
* Assignment: Week 1 Bank Account Management Application Project, now impliments Transactions.
*/

public class BankAccount implements Transactions {  // Interface implemented here   
    protected Customer owner;  
    protected String accountNumber; 
    protected double balance; 
    public BankAccount(Customer owner, String accountNumber, double balance) { 
        this.owner = owner; 
        this.accountNumber = accountNumber; 
        this.balance = balance; 
    } 
  
    @Override 
    public void deposit(double amount) { 
        balance += amount; 
    } 
  
    @Override 
    public void withdraw(double amount) { 
        balance -= amount; 
    } 
  
    public double getBalance() { 
        return balance; 
    } 
  
    public String toString() { 
        return "Account Number: " + accountNumber + 
               "\nOwner: " + owner.getFullName() + 
               "\nBalance: $" + balance; 
    } 
}  