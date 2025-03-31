package com.rswg3_5.techretailpro.models;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public void register() {
        
    }
    
    public void login() {
        
    }
    
    public void logout() {
        
    }
    
    public static void listProduct() {
//        try {
//            FileReader reader = new FileReader("../product.txt");
//            
//            int data = reader.read();
//            while (data != -1) {                
//                System.out.print((char)data);
//                data = reader.read();
//            }
//            reader.close();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
//        }
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