/* 
* Name: Angelo Burke 
* Date: Dec 8, 2025 
* Assignment: Week 4 Bank Account Management Application Project.
*/ 

import java.sql.*; 
import java.util.*;   
public class DatabaseHelper { 
    private static final String DB_URL = "jdbc:sqlite:bank.db"; 
    static { 
        // Create table if not exists 
        try (Connection conn = DriverManager.getConnection(DB_URL); 
             Statement stmt = conn.createStatement()) { 
             String sql = "CREATE TABLE IF NOT EXISTS accounts (" + 
                    "accountNumber TEXT PRIMARY KEY," + 
                    "ownerFirstName TEXT," + 
                    "ownerLastName TEXT," + 
                    "balance REAL," + 
                    "accountType TEXT," + 
                    "extraValue REAL" + // interestRate or overdraftFee 
                    ")"; 
            stmt.execute(sql); 
        } catch (SQLException e) { 
            System.out.println("Error initializing database: " + e.getMessage()); 
        } 
    } 
  
    public static void addAccount(BankAccount acc) { 
        String sql = "INSERT INTO accounts(accountNumber, ownerFirstName, ownerLastName, balance, accountType, extraValue) VALUES(?,?,?,?,?,?)"; 
        try (Connection conn = DriverManager.getConnection(DB_URL); 
            PreparedStatement pstmt = conn.prepareStatement(sql)) { 
            pstmt.setString(1, acc.getAccountNumber()); 
            pstmt.setString(2, acc.getOwner().getFullName().split(" ")[0]); 
            pstmt.setString(3, acc.getOwner().getFullName().split(" ").length > 1 ? acc.getOwner().getFullName().split(" ")[1] : ""); 
            pstmt.setDouble(4, acc.getBalance()); 
            pstmt.setString(5, acc.getAccountType()); 
            if (acc instanceof CheckingAccount) { 
                pstmt.setDouble(6, ((CheckingAccount) acc).getOverdraftFee()); 
            } else { 
                pstmt.setDouble(6, ((SavingsAccount) acc).getInterestRate()); 
            } 
            pstmt.executeUpdate(); 
        } catch (SQLException e) { 
            System.out.println("Error adding account: " + e.getMessage()); 
        } 
    } 
  
    public static List<BankAccount> getAllAccounts() { 
        List<BankAccount> accounts = new ArrayList<>(); 
        String sql = "SELECT * FROM accounts"; 
        try (Connection conn = DriverManager.getConnection(DB_URL); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) { 
            while (rs.next()) { 
                Customer c = new Customer(rs.getString("ownerFirstName"), rs.getString("ownerLastName")); 
                String accNum = rs.getString("accountNumber"); 
                double balance = rs.getDouble("balance"); 
                double extra = rs.getDouble("extraValue"); 
                String type = rs.getString("accountType"); 
                if (type.equals("Checking Account")) { 
                    accounts.add(new CheckingAccount(c, accNum, balance, extra)); 
                } else { 
                    accounts.add(new SavingsAccount(c, accNum, balance, extra)); 
                } 
            } 
        } catch (SQLException e) { 
            System.out.println("Error reading accounts: " + e.getMessage()); 
        } 
        return accounts; 
    } 
  
    public static void deleteAccount(String accNum) { 
        String sql = "DELETE FROM accounts WHERE accountNumber=?"; 
        try (Connection conn = DriverManager.getConnection(DB_URL); 
            PreparedStatement pstmt = conn.prepareStatement(sql)) { 
            pstmt.setString(1, accNum); 
            pstmt.executeUpdate(); 
        } catch (SQLException e) { 
            System.out.println("Error deleting account: " + e.getMessage()); 
        } 
    } 
} 
