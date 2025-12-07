/*  
* Name: Angelo Burke  
* Date: Nov 30, 2025  
* Assignment: Week 3 Bank Account Management Application Project  
*/  
  
public class SavingsAccount extends BankAccount {      
    private double interestRate; 
    public SavingsAccount(Customer owner, String accountNumber, double balance, double interestRate) {    
        super(owner, accountNumber, balance);    
        this.interestRate = interestRate;    
    }  
   
    public SavingsAccount(Customer owner, String accountNumber, double interestRate) {  
        super(owner, accountNumber);  
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
   
    @Override  
    public String getAccountType() {  
        return "Savings Account";  
    }  
}   