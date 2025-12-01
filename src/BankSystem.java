/* 
* Name: Angelo Burke 
* Date: Nov 30, 2025 
* Assignment: Week 3 Bank Account Management Application Project 
*/ 

import java.util.Scanner; 
public class BankSystem { 
    public static void main(String[] args) { 
        Scanner input = new Scanner(System.in); 
         System.out.println("=================================================="); 
        System.out.println("        Project Week 3 - Bank Application"); 
        System.out.println("        Author: Angelo Burke"); 
        System.out.println("==================================================\n"); 
        System.out.println("Welcome to the Week 3 Bank Account Application"); 
        System.out.println("This version demonstrates abstraction, constructors, and access specifiers.\n"); 
  
        Customer cust1 = new Customer("Angelo", "Burke"); 
        Customer cust2 = new Customer("Sarah", "Miller");
        BankAccount sa = new SavingsAccount(cust1, "SA-1001", 5000.0, 0.02); 
        BankAccount ca = new CheckingAccount(cust2, "CA-2001", 1250.0, 35.0); 
        BankAccount activeAccount = sa;   
   
        System.out.println("\n--- CURRENT ACCOUNT INFORMATION ---"); 
        System.out.println("Active Account Type: " + activeAccount.getAccountType()); 
        System.out.println("Active Account Balance: $" + activeAccount.getBalance()); 
  
        // Display accounts 
        System.out.println("\n--- ACCOUNT INFORMATION ---"); 
        System.out.println(sa.toString()); 
        System.out.println("Account Type: " + sa.getAccountType() + "\n"); 
        System.out.println(ca.toString()); 
        System.out.println("Account Type: " + ca.getAccountType() + "\n"); 
  
        // Table output 
        System.out.println("\n============================================================"); 
        System.out.println("                      ACCOUNT TABLE"); 
        System.out.println("============================================================"); 
        System.out.printf("%-15s %-20s %-15s\n", "Account", "Owner", "Balance"); 
        System.out.println("------------------------------------------------------------"); 
        System.out.printf("%-15s %-20s $%-15.2f\n", sa.getAccountType(), cust1.getFullName(), sa.getBalance()); 
        System.out.printf("%-15s %-20s $%-15.2f\n", ca.getAccountType(), cust2.getFullName(), ca.getBalance()); 
        System.out.println("============================================================");   
        input.close(); 
    } 
} 