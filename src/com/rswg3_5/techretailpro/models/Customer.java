package com.rswg3_5.techretailpro.models;

public class Customer extends User {
    private String customerEmail;
    private String customerPassword;
    private int customerPhoneNumber;
    private int customerAddress;

    public Customer() {
    }

    public Customer(String customerEmail, String customerPassword, int customerPhoneNumber, int customerAddress, String username) {
        super(username);
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerAddress = customerAddress;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public int getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(int customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public int getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(int customerAddress) {
        this.customerAddress = customerAddress;
    }

    @Override
    public String toString() {
        return "Customer{" + "customerEmail=" + customerEmail + ", customerPassword=" + customerPassword + ", customerPhoneNumber=" + customerPhoneNumber + ", customerAddress=" + customerAddress + '}';
    }
    
    
    @Override
    public void login() {
        
    }
    
    @Override
    public void displayRole() {
        
    }
    
    public void viewProfile() {
        
    }
    
    public void updateProfile() {
        
    }
    
    public void updatePaymentMethod() {
        
    }
    
    public void updateAddress() {
        
    }
    
    public void viewOrderHistory() {
        
    }
}
