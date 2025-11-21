/*
* Name: Angelo Burke
* Date Nov 21, 2025
* Assignment: Week 1 Bank Account Management Application Project
*/

public class Customer { 
    private String firstName; 
    private String lastName; 
    public Customer(String firstName, String lastName) { 
        this.firstName = firstName; 
        this.lastName = lastName; 
    } 
    public String getFullName() { 
        return firstName + " " + lastName; 
    } 
    public void setFirstName(String n) { this.firstName = n; } 
    public void setLastName(String n) { this.lastName = n; } 
} 