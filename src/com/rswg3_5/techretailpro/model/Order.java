package com.rswg3_5.techretailpro.model;

public class Order {
    private Cart cart;
    private String paymentMethod;
    private boolean paymentStatus;

    public Order() {
    }

    public Order(Cart cart, String paymentMethod, boolean paymentStatus) {
        this.cart = cart;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "Order{" + "cart=" + cart + ", paymentMethod=" + paymentMethod + ", paymentStatus=" + paymentStatus + '}';
    }
    
    public void displayProductOrdered() {
        
    }
    
    public void paymentProcess() {
        
    }
    
    public void displayReceipt() {
        
    }
}
