/*
* Name: Angelo Burke
* Date Nov 15, 2025
* Assignment: Week 1 Bank Account Management Application Project
*/

public class SavingsAccount extends BankAccount { 
    private double interestRate; 
    public SavingsAccount(Customer owner, String accountNumber, double balance, double interestRate) { 
        super(owner, accountNumber, balance);
        this.interestRate = interestRate; 
    } 
    public void applyInterest() { 
        balance += balance * interestRate; 
    } 
  
    @Override 
    public String toString() { 
        return "\n--- Savings Account ---\n" + 
               super.toString() + 
               "\nInterest Rate: " + (interestRate * 100) + "%"; 
    } 
}  