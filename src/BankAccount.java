/*
* Name: Angelo Burke
* Date Nov 15, 2025
* Assignment: Week 1 Bank Account Management Application Project
*/

public class BankAccount { 
    protected Customer owner;    
    protected String accountNumber; 
    protected double balance;   
    public BankAccount(Customer owner, String accountNumber, double balance) { 
        this.owner = owner; 
        this.accountNumber = accountNumber; 
        this.balance = balance; 
    } 
    public double getBalance() { 
        return balance; 
    } 
    public void deposit(double amount) { 
        balance += amount; 
    } 
    public void withdraw(double amount) { 
        balance -= amount; 
    } 
    public String toString() { 
        return "Account Number: " + accountNumber + 
               "\nOwner: " + owner.getFullName() + 
               "\nBalance: $" + balance; 
    } 
} 