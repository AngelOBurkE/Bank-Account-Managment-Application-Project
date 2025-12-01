/* 
* Name: Angelo Burke 
* Date: Nov 30, 2025 
* Assignment: Week 3 Bank Account Management Application Project 
*/ 

public abstract class BankAccount implements Transactions { 
    // Protected variables accessible by subclasses (inheritance) 
    protected Customer owner;   
    protected String accountNumber;   
    protected double balance;   
    public BankAccount(Customer owner, String accountNumber, double balance) {   
        this.owner = owner;   
        this.accountNumber = accountNumber;   
        this.balance = balance;   
    }  

    public BankAccount(Customer owner, String accountNumber) { 
        this(owner, accountNumber, 0.0); 
    } 
 
    public abstract String getAccountType(); 

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