/* 
* Name: Angelo Burke 
* Date: Dec 7, 2025 
* Assignment: Week 4 Bank Account Management Application Project 
*/ 
  
import java.io.*; 
import java.util.*;   
public class DatabaseHelper { 
    private static final String DB_FILE = "bank.txt"; 
    public static void addAccount(BankAccount account) { 
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DB_FILE, true))) { 
            bw.write(convertAccountToLine(account)); 
            bw.newLine(); 
        } catch (IOException e) { 
            System.out.println("Error adding account: " + e.getMessage()); 
        } 
    } 
  
    public static List<BankAccount> getAllAccounts() { 
        List<BankAccount> accounts = new ArrayList<>(); 
        try (BufferedReader br = new BufferedReader(new FileReader(DB_FILE))) { 
            String line; 
            while ((line = br.readLine()) != null) { 
                BankAccount acc = convertLineToAccount(line); 
                if (acc != null) accounts.add(acc); 
            } 
        } catch (IOException e) { 
            System.out.println("Error reading accounts: " + e.getMessage()); 
        } 
        return accounts; 
    } 
  
    public static void updateDatabase(List<BankAccount> accounts) { 
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DB_FILE))) { 
            for (BankAccount acc : accounts) { 
                bw.write(convertAccountToLine(acc)); 
                bw.newLine(); 
            } 
        } catch (IOException e) { 
            System.out.println("Error updating database: " + e.getMessage()); 
        } 
    } 
  
    public static void deleteAccount(String accountNumber) { 
        List<BankAccount> accounts = getAllAccounts(); 
        accounts.removeIf(acc -> acc.accountNumber.equals(accountNumber)); 
        updateDatabase(accounts); 
    } 
  
    // Convert Account -> Line 
    private static String convertAccountToLine(BankAccount acc) { 
        if (acc instanceof CheckingAccount) { 
            CheckingAccount ca = (CheckingAccount) acc; 
            return "CHECKING|" + acc.accountNumber + "|" + acc.owner.getFullName() + "|" + 
                   acc.getBalance() + "|" + ca.getOverdraftFee(); 
        } else { 
            SavingsAccount sa = (SavingsAccount) acc; 
            return "SAVINGS|" + acc.accountNumber + "|" + acc.owner.getFullName() + "|" + 
                   acc.getBalance() + "|" + sa.getInterestRate(); 
        } 
    } 
  
    // Convert Line -> Account 
    private static BankAccount convertLineToAccount(String line) { 
        try { 
            String[] parts = line.split("\\|"); 
            String type = parts[0]; 
            String accountNum = parts[1]; 
            String fullName = parts[2]; 
            double balance = Double.parseDouble(parts[3]); 
            double extraValue = Double.parseDouble(parts[4]); 
            String[] nameParts = fullName.split(" "); 
            Customer c = new Customer(nameParts[0], nameParts.length > 1 ? nameParts[1] : ""); 
            if (type.equals("CHECKING")) { 
                return new CheckingAccount(c, accountNum, balance, extraValue); 
            } else { 
                return new SavingsAccount(c, accountNum, balance, extraValue); 
            } 
        } catch (Exception e) { 
            System.out.println("Error converting line: " + line); 
            return null; 
        } 
    } 
}  