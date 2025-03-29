package com.rswg3_5.techretailpro.model;

public class User {
    private String username;
    private String userEmail;
    private String userPassword;

    public User() {
    }

    public User(String username, String userEmail, String userPassword) {
        this.username = username;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", userEmail=" + userEmail + ", userPassword=" + userPassword + '}';
    }
    
    public void login() {
        
    }
    
    public void logout() {
        
    }
    
    public static void listProduct() {
        
    }
    
    public void viewProduct() {
        
    }
    
    public void sortProduct() {
        
    }
    
    public void searchProduct() {
        
    }
    
    public void filterProduct() {
        
    }
}