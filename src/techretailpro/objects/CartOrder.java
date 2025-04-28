/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assg;
import java.time.format.DateTimeFormatter;
import java.util.List;
/**
 *
 * @author TAY TIAN YOU
 */
public class CartOrder {
    private final int id;
    private final List<CartItem> orderItems;
    private final double originalTotal;
    private final double totalAmount;
    private final double discountAmount;

    public CartOrder(int id, List<CartItem> orderItems, double originalTotal, double totalAmount, double discountAmount) {
        this.id = id;
        this.orderItems = orderItems;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.originalTotal = originalTotal;
    }

    public int getId() {
        return id;
    }

    public List<CartItem> getOrderItems() {
        return orderItems;
    }

    public double getOriginalTotal() {
        return originalTotal;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-15s %-10s %-7s %-10s\n", "Product ID", "Product", "Price", "Qty", "Subtotal"));
        for (CartItem item : orderItems) {
            sb.append(item).append("\n");
        }
        sb.append("\n"); 
        sb.append(String.format("Gross Total         : RM%.2f\n", originalTotal));
        if (discountAmount > 0) {
            sb.append(String.format("Discount Applied    : RM%.2f\n", discountAmount));
        }
        sb.append(String.format("TOTAL PAID          : RM%.2f", totalAmount));
        return sb.toString();
    }
    
}
