/*
* Name: Angelo Burke
* Date Nov 21, 2025
* Assignment: Week 1 Bank Account Management Application Project
*/

import java.util.Scanner; 
public class App { 
    public static void main(String[] args) {  
        Scanner input = new Scanner(System.in); 
        System.out.println("=================================================="); 
        System.out.println("        Project - Bank Application"); 
        System.out.println("        Author: Angelo Burke"); 
        System.out.println("==================================================\n"); 
        // Welcome message 
        System.out.println("Welcome to the Bank Account Management Application!"); 
        System.out.println("You can check your balance, deposit money, withdraw money,"); 
        System.out.println("update your personal information, and view account details.\n"); 
        // Composition: Creating Customer for both accounts 
        Customer cust = new Customer("Angelo", "Burke"); 
        // Inheritance: Creating both types of accounts 
        SavingsAccount sa = new SavingsAccount(cust, "SA-1001", 5000.00, 0.02); 
        CheckingAccount ca = new CheckingAccount(cust, "CA-2001", 1250.00, 35.00); 
        BankAccount activeAccount = sa;   
        boolean running = true; 
        while (running) { 
            System.out.println("\n========= MENU ========="); 
            System.out.println("1. Switch Account (Savings / Checking)"); 
            System.out.println("2. Check Balance"); 
            System.out.println("3. Deposit Money"); 
            System.out.println("4. Withdraw Money"); 
            System.out.println("5. Update Customer Name"); 
            System.out.println("6. Display Account Table"); 
            System.out.println("7. Exit Application"); 
            System.out.println("============================="); 
            System.out.print("Enter option: "); 
            int choice = input.nextInt(); 
            input.nextLine();
            switch (choice) { 
                case 1: 
                    System.out.println("\nWhich account do you want to use?"); 
                    System.out.println("1. Savings"); 
                    System.out.println("2. Checking"); 
                    System.out.print("Enter option: "); 
                    int acctChoice = input.nextInt(); 
                    input.nextLine(); 
                    if (acctChoice == 1) { 
                        activeAccount = sa; 
                        System.out.println("Now using Savings Account."); 
                    } else { 
                        activeAccount = ca; 
                        System.out.println("Now using Checking Account."); 
                    } 
                    break; 
  
                case 2:  // Check balance 
                    System.out.println("\nYour current balance is: $" + activeAccount.getBalance()); 
                    break; 
  
                case 3:  // Deposit 
                    System.out.print("\nEnter amount to deposit: $"); 
                    double dep = input.nextDouble(); 
                    input.nextLine(); 
                    activeAccount.deposit(dep); 
                    System.out.println("Deposit successful!"); 
                    break; 
  
                case 4:  // Withdraw 
                    System.out.print("\nEnter amount to withdraw: $"); 
                    double w = input.nextDouble(); 
                    input.nextLine(); 
                    activeAccount.withdraw(w); 
                    System.out.println("Withdrawal completed!"); 
                    break; 
  
                case 5:  // Update name 
                    System.out.print("\nEnter NEW first name: "); 
                    String newF = input.nextLine();   
                    System.out.print("Enter NEW last name: "); 
                    String newL = input.nextLine(); 
                    cust.setFirstName(newF); 
                    cust.setLastName(newL); 
                    System.out.println("Name updated successfully!"); 
                    break; 
  
                case 6:  // Display account table 
                    System.out.println("\n============================================================"); 
                    System.out.println("                      ACCOUNT TABLE"); 
                    System.out.println("============================================================"); 
                    System.out.printf("%-15s %-20s %-15s\n", "Account", "Owner", "Balance"); 
                    System.out.println("------------------------------------------------------------"); 
                    System.out.printf("%-15s %-20s $%-15.2f\n",  
                                      "Savings", cust.getFullName(), sa.getBalance()); 
                    System.out.printf("%-15s %-20s $%-15.2f\n",  
                                      "Checking", cust.getFullName(), ca.getBalance()); 
                    System.out.println("============================================================"); 
                    break; 
  
                case 7:  // Exit 
                    System.out.println("\nHave a good day."); 
                    running = false; 
                    break; 
                default: 
                    System.out.println("Invalid option. Try again."); 
            } 
  
            if (running) { 
                System.out.print("\nWould you like to do another action? (y/n): "); 
                String again = input.nextLine().toLowerCase(); 
                if (!again.equals("y")) { 
                    running = false; 
                } 
            } 
        }   
        input.close(); 
    } 
} 