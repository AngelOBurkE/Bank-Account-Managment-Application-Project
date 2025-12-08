/*  
* Name: Angelo Burke  
* Date: Dec 8, 2025   
* Assignment: Week 3 Bank Account Management Application Project.  
*/  

import java.util.*;   
public class BankSystem { 
    public static void main(String[] args) { 
        System.out.println("==============================================="); 
        System.out.println("       Project Week 4 - Bank Application"); 
        System.out.println("       Displaying Current Account Records"); 
        System.out.println("       Author: Angelo Burke"); 
        System.out.println("===============================================\n"); 
        List<BankAccount> accounts = DatabaseHelper.getAllAccounts(); 
        if (accounts.isEmpty()) { 
            System.out.println("No accounts found in database."); 
            return; 
        } 
  
        System.out.println("--- ACCOUNT INFORMATION ---"); 
        for (BankAccount acc : accounts) { 
            System.out.println(acc.toString()); 
            System.out.println("Account Type: " + acc.getAccountType() + "\n"); 
        } 
  
        System.out.println("============================================================"); 
        System.out.println("                        ACCOUNT TABLE"); 
        System.out.println("============================================================"); 
        System.out.printf("%-15s %-20s %-15s\n", "Account", "Owner", "Balance"); 
        System.out.println("------------------------------------------------------------"); 
        for (BankAccount acc : accounts) { 
            System.out.printf("%-15s %-20s $%-15.2f\n", 
                    acc.getAccountType(), 
                    acc.getOwner().getFullName(), 
                    acc.getBalance()); 
        } 
        System.out.println("============================================================"); 
    } 
} 