package com.rswg3_5.techretailpro.model;

public class Cart {
    private Customer customer;
    private CartItem[] cartItems;
    private int totalItem;
    private double totalPrice;

    public Cart() {
    }

    public Cart(Customer customer, CartItem[] cartItems, int totalItem, double totalPrice) {
        this.customer = customer;
        this.cartItems = cartItems;
        this.totalItem = totalItem;
        this.totalPrice = totalPrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CartItem[] getCartItems() {
        return cartItems;
    }

    public void setCartItems(CartItem[] cartItems) {
        this.cartItems = cartItems;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Cart{" + "customer=" + customer + ", cartItems=" + cartItems + ", totalItem=" + totalItem + ", totalPrice=" + totalPrice + '}';
    }
    
    public void viewCart() {
        
    }
    
    public void editQuantity() {
        
    }
    
    public void removeFromCart() {
        
    }
    
    public void removeAllFromCart() {
        
    }
    
    public void placeOrder() {
        
    }
    
    public void cancelOrder() {
        
    }
}
