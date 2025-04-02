package com.rswg3_5.techretailpro.models;

public class Admin extends User {
    private String adminEmail;
    private String adminPassword;
    
    public Admin() {
    }

    public Admin(String adminEmail, String adminPassword, String username) {
        super(username);
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @Override
    public String toString() {
        return "Admin{" + "adminEmail=" + adminEmail + ", adminPassword=" + adminPassword + '}';
    }
    
    @Override
    public void login() {
        
    }
    
    @Override
    public void displayRole() {
        
    }
    
    public void addProduct() {
        
    }
    
    public void deleteProduct() {
        
    }
    
    public void updateProduct() {
        
    }
    
    public void listCustomerAccount() {
        
    }
    
    public void viewCustomerAccount() {
        
    }
    
    public void viewCustomerOrderHistory() {
        
    }
    
    public void deleteCustomerAccount() {
        
    }
    
    public void updateCustomerAccount() {
        
    }
}
