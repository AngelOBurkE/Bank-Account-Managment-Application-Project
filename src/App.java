/* 
* Name: Angelo Burke  
* Date: Dec 8, 2025  
* Assignment: Week 1 Bank Account Management Application Project 
* Week 4 Updated again
*/ 

import java.util.*;   
public class App { 
    public static void main(String[] args) { 
        Scanner input = new Scanner(System.in); 
        System.out.println("==============================================="); 
        System.out.println("         Project Week 4 - Bank Application"); 
        System.out.println("         MAIN MENU"); 
        System.out.println("         Author: Angelo Burke"); 
        System.out.println("===============================================\n"); 
        System.out.println("Welcome to Angelo's bank account management system!"); 
  
        while (true) { 
            System.out.println("\nChoose an option:"); 
            System.out.println("1. Add New Account"); 
            System.out.println("2. View All Accounts"); 
            System.out.println("3. Update Account (Deposit/Withdraw)"); 
            System.out.println("4. Delete Account"); 
            System.out.println("5. Exit"); 
            System.out.print("Enter choice: "); 
            int choice = input.nextInt(); 
            input.nextLine(); 
            switch (choice) { 
                case 1: 
                    addAccountMenu(input); 
                    break; 
                case 2: 
                    BankSystem.main(null); 
                    break; 
                case 3: 
                    updateMenu(input); 
                    break; 
                case 4: 
                    deleteMenu(input); 
                    break; 
                case 5: 
                    System.out.println("Goodbye!"); 
                    return; 
                default: 
                    System.out.println("Invalid choice.\n"); 
            } 
        } 
    } 
  
    private static void addAccountMenu(Scanner input) { 
        System.out.print("First Name: "); 
        String fn = input.nextLine(); 
        System.out.print("Last Name: "); 
        String ln = input.nextLine(); 
        Customer c = new Customer(fn, ln); 
        System.out.print("Account Type (1=Savings, 2=Checking): "); 
        int type = input.nextInt(); 
        input.nextLine(); 
        System.out.print("Account Number: "); 
        String accNum = input.nextLine(); 
        System.out.print("Starting Balance: "); 
        double balance = input.nextDouble(); 
        if (type == 1) { 
            System.out.print("Interest Rate (e.g. 0.02): "); 
            double rate = input.nextDouble(); 
            BankAccount sa = new SavingsAccount(c, accNum, balance, rate); 
            DatabaseHelper.addAccount(sa); 
        } else { 
            System.out.print("Overdraft Fee: "); 
            double fee = input.nextDouble(); 
            BankAccount ca = new CheckingAccount(c, accNum, balance, fee); 
            DatabaseHelper.addAccount(ca); 
        } 
        System.out.println("Account successfully added!"); 
    } 

    private static void updateMenu(Scanner input) { 
        List<BankAccount> accounts = DatabaseHelper.getAllAccounts(); 
        if (accounts.isEmpty()) { 
            System.out.println("No accounts available."); 
            return; 
        } 
  
        System.out.print("Enter account number to update: "); 
        String accNum = input.nextLine(); 
        BankAccount acc = accounts.stream() 
                .filter(a -> a.getAccountNumber().equals(accNum)) 
                .findFirst() 
                .orElse(null); 
        if (acc == null) { 
            System.out.println("Account not found."); 
            return; 
        } 
  
        System.out.println("1. Deposit"); 
        System.out.println("2. Withdraw"); 
        int option = input.nextInt(); 
        System.out.print("Amount: "); 
        double amt = input.nextDouble(); 
        if (option == 1) acc.deposit(amt); 
        else acc.withdraw(amt); 

       // Update the DB 
        DatabaseHelper.deleteAccount(acc.getAccountNumber()); 
        DatabaseHelper.addAccount(acc);   
        System.out.println("Update successful."); 
    } 

    private static void deleteMenu(Scanner input) { 
        System.out.print("Enter account number to delete: "); 
        String accNum = input.nextLine(); 
        DatabaseHelper.deleteAccount(accNum); 
        System.out.println("Account deleted (if it existed)."); 
    } 
} 