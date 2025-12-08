/* 

* Name: Angelo Burke 

* Date: Dec 7, 2025 

* Assignment: Week 4 Bank Account Management Application Project 

* 

* Purpose: 

* - Uses SQLite (bank.db) to persist account records. 

* - Exposes simple CRUD methods used by App.java and BankSystem.java. 

* 

* Notes: 

* - Requires sqlite-jdbc driver on classpath (sqlite-jdbc.jar). 

* - Keeps API compatible with previous text-file helper. 

*/ 

  

import java.sql.*; 

import java.util.ArrayList; 

import java.util.List; 

  

public class DatabaseHelper { 

  

    private static final String DB_FILE = "bank.db"; 

    private static final String DB_URL = "jdbc:sqlite:" + DB_FILE; 

  

    // Ensure the table exists; called lazily before operations 

    private static void ensureTables() { 

        try (Connection conn = DriverManager.getConnection(DB_URL); 

             Statement stmt = conn.createStatement()) { 

  

            String create = "CREATE TABLE IF NOT EXISTS accounts (" + 

                    "account_number TEXT PRIMARY KEY," + 

                    "owner_first TEXT NOT NULL," + 

                    "owner_last TEXT," + 

                    "type TEXT NOT NULL," +          // "CHECKING" or "SAVINGS" 

                    "balance REAL NOT NULL DEFAULT 0.0," + 

                    "overdraft_fee REAL," +          // nullable for savings 

                    "interest_rate REAL" +           // nullable for checking 

                    ");"; 

            stmt.execute(create); 

  

        } catch (SQLException e) { 

            System.out.println("DB error (ensureTables): " + e.getMessage()); 

        } 

    } 

  

    // CREATE: add an account to the DB 

    public static void addAccount(BankAccount account) { 

        ensureTables(); 

        String sql = "INSERT OR REPLACE INTO accounts (account_number, owner_first, owner_last, type, balance, overdraft_fee, interest_rate) " + 

                "VALUES (?, ?, ?, ?, ?, ?, ?);"; 

  

        try (Connection conn = DriverManager.getConnection(DB_URL); 

             PreparedStatement ps = conn.prepareStatement(sql)) { 

  

            ps.setString(1, account.accountNumber); 

  

            // owner full name -> split to first/last 

            String[] nameParts = account.owner.getFullName().split(" "); 

            String first = nameParts.length > 0 ? nameParts[0] : ""; 

            String last = nameParts.length > 1 ? nameParts[1] : ""; 

  

            ps.setString(2, first); 

            ps.setString(3, last); 

  

            if (account instanceof CheckingAccount) { 

                CheckingAccount ca = (CheckingAccount) account; 

                ps.setString(4, "CHECKING"); 

                ps.setDouble(5, ca.getBalance()); 

                ps.setDouble(6, ca.getOverdraftFee()); 

                ps.setNull(7, Types.REAL); 

            } else if (account instanceof SavingsAccount) { 

                SavingsAccount sa = (SavingsAccount) account; 

                ps.setString(4, "SAVINGS"); 

                ps.setDouble(5, sa.getBalance()); 

                ps.setNull(6, Types.REAL); 

                ps.setDouble(7, sa.getInterestRate()); 

            } else { 

                // generic bank account (fallback) 

                ps.setString(4, "UNKNOWN"); 

                ps.setDouble(5, account.getBalance()); 

                ps.setNull(6, Types.REAL); 

                ps.setNull(7, Types.REAL); 

            } 

  

            ps.executeUpdate(); 

        } catch (SQLException e) { 

            System.out.println("DB error (addAccount): " + e.getMessage()); 

        } 

    } 

  

    // READ: return all accounts as objects (reconstructing correct subclass) 

    public static List<BankAccount> getAllAccounts() { 

        ensureTables(); 

        List<BankAccount> list = new ArrayList<>(); 

        String sql = "SELECT account_number, owner_first, owner_last, type, balance, overdraft_fee, interest_rate FROM accounts ORDER BY account_number;"; 

  

        try (Connection conn = DriverManager.getConnection(DB_URL); 

             Statement stmt = conn.createStatement(); 

             ResultSet rs = stmt.executeQuery(sql)) { 

  

            while (rs.next()) { 

                String accNum = rs.getString("account_number"); 

                String first = rs.getString("owner_first"); 

                String last = rs.getString("owner_last"); 

                String type = rs.getString("type"); 

                double balance = rs.getDouble("balance"); 

                double overd = rs.getObject("overdraft_fee") == null ? 0.0 : rs.getDouble("overdraft_fee"); 

                double ir = rs.getObject("interest_rate") == null ? 0.0 : rs.getDouble("interest_rate"); 

  

                Customer owner = new Customer(first, last); 

                if ("CHECKING".equalsIgnoreCase(type)) { 

                    CheckingAccount ca = new CheckingAccount(owner, accNum, balance, overd); 

                    list.add(ca); 

                } else if ("SAVINGS".equalsIgnoreCase(type)) { 

                    SavingsAccount sa = new SavingsAccount(owner, accNum, balance, ir); 

                    list.add(sa); 

                } else { 

                    // fallback - treat as savings 

                    SavingsAccount sa = new SavingsAccount(owner, accNum, balance, ir); 

                    list.add(sa); 

                } 

            } 

  

        } catch (SQLException e) { 

            System.out.println("DB error (getAllAccounts): " + e.getMessage()); 

        } 

  

        return list; 

    } 

  

    // UPDATE: replace DB contents with provided list (simple approach, deletes all and reinserts) 

    // This mirrors the previous updateDatabase(List<BankAccount>) behavior 

    public static void updateDatabase(List<BankAccount> accounts) { 

        ensureTables(); 

        String deleteAll = "DELETE FROM accounts;"; 

        try (Connection conn = DriverManager.getConnection(DB_URL); 

             Statement stmt = conn.createStatement()) { 

  

            conn.setAutoCommit(false); 

            stmt.execute(deleteAll); 

  

            String insert = "INSERT INTO accounts (account_number, owner_first, owner_last, type, balance, overdraft_fee, interest_rate) VALUES (?, ?, ?, ?, ?, ?, ?);"; 

            try (PreparedStatement ps = conn.prepareStatement(insert)) { 

                for (BankAccount acc : accounts) { 

                    ps.setString(1, acc.accountNumber); 

                    String[] nameParts = acc.owner.getFullName().split(" "); 

                    String first = nameParts.length > 0 ? nameParts[0] : ""; 

                    String last = nameParts.length > 1 ? nameParts[1] : ""; 

                    ps.setString(2, first); 

                    ps.setString(3, last); 

  

                    if (acc instanceof CheckingAccount) { 

                        CheckingAccount ca = (CheckingAccount) acc; 

                        ps.setString(4, "CHECKING"); 

                        ps.setDouble(5, ca.getBalance()); 

                        ps.setDouble(6, ca.getOverdraftFee()); 

                        ps.setNull(7, Types.REAL); 

                    } else if (acc instanceof SavingsAccount) { 

                        SavingsAccount sa = (SavingsAccount) acc; 

                        ps.setString(4, "SAVINGS"); 

                        ps.setDouble(5, sa.getBalance()); 

                        ps.setNull(6, Types.REAL); 

                        ps.setDouble(7, sa.getInterestRate()); 

                    } else { 

                        ps.setString(4, "UNKNOWN"); 

                        ps.setDouble(5, acc.getBalance()); 

                        ps.setNull(6, Types.REAL); 

                        ps.setNull(7, Types.REAL); 

                    } 

  

                    ps.executeUpdate(); 

                } 

            } 

  

            conn.commit(); 

            conn.setAutoCommit(true); 

  

        } catch (SQLException e) { 

            System.out.println("DB error (updateDatabase): " + e.getMessage()); 

        } 

    } 

  

    // DELETE: delete account by account number 

    public static void deleteAccount(String accountNumber) { 

        ensureTables(); 

        String sql = "DELETE FROM accounts WHERE account_number = ?;"; 

        try (Connection conn = DriverManager.getConnection(DB_URL); 

             PreparedStatement ps = conn.prepareStatement(sql)) { 

  

            ps.setString(1, accountNumber); 

            ps.executeUpdate(); 

  

        } catch (SQLException e) { 

            System.out.println("DB error (deleteAccount): " + e.getMessage()); 

        } 

    } 

} 