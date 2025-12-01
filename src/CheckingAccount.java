/* 
* Name: Angelo Burke 
* Date: Nov 30, 2025 
* Assignment: Week 3 Bank Account Management Application Project 
*/ 

public class CheckingAccount extends BankAccount {     
    private double overdraftFee;
    public CheckingAccount(Customer owner, String accountNumber, double balance, double overdraftFee) {   
        super(owner, accountNumber, balance);
        this.overdraftFee = overdraftFee;   
    } 
  
    public CheckingAccount(Customer owner, String accountNumber, double overdraftFee) { 
        super(owner, accountNumber); 
        this.overdraftFee = overdraftFee; 
    } 
  
    @Override   
    public void withdraw(double amount) {   
        super.withdraw(amount);   
        if (balance < 0) {   
            balance -= overdraftFee;   
        }   
    }   
  
    @Override   
    public String toString() {   
        return "\n--- Checking Account ---\n" +   
               super.toString() +   
               "\nOverdraft Fee: $" + overdraftFee;   
    }   
  
    @Override 
    public String getAccountType() { 
        return "Checking Account"; 
    } 
}  