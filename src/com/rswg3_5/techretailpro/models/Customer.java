package com.rswg3_5.techretailpro.models;

public class Customer extends User {
    private int customerPhoneNumber;
    private int customerAddress;

    public Customer() {
    }

    public Customer(int customerPhoneNumber, int customerAddress, String username, String userEmail, String userPassword) {
        super(username, userEmail, userPassword);
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerAddress = customerAddress;
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
        return "Customer{" + "customerPhoneNumber=" + customerPhoneNumber + ", customerAddress=" + customerAddress + '}';
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
