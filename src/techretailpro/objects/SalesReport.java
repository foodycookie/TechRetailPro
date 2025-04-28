/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paymentmodule;
import java.util.*;
/**
 *
 * @author TAY TIAN YOU
 */
public class SalesReport {
    private List<Order> orderHistory = new ArrayList<>();
    private List<Item> allSoldItems = new ArrayList<>();
    
    public void addOrder(Order order) {
        orderHistory.add(order);
        allSoldItems.addAll(order.getItems());
    }
    public boolean hasOrders() {
        return !orderHistory.isEmpty();
    }
    
    public String getOrderHistory() {
        if (orderHistory.isEmpty()) {
            return "No orders found.";
        }
        StringBuilder result = new StringBuilder("--- All Order History ---\n");
        for (Order order : orderHistory) {
            result.append(order.getReceipt());
        }
        return result.toString();
    }

    public String generateReport() {
        double totalSales = 0;
        double totalDiscount = 0;
        StringBuilder report = new StringBuilder("--- Sales Report ---\n");
        
        for (Order order : orderHistory) {
            totalSales += order.getTotalAmount();
            totalDiscount += order.getDiscountUsed();
        }
        
        report.append("Total Sales         : RM").append(totalSales).append("\n")
              .append("Total Discount Given: RM").append(totalDiscount).append("\n")
              .append("Best Sellers:\n");

        List<String> checked = new ArrayList<>();
        for (Item item : allSoldItems) {
            if (!checked.contains(item.getName())) {
                int count = 0;
                for (Item i : allSoldItems) {
                    if (i.getName().equals(item.getName())) {
                        count++;
                    }
                }
                report.append(String.format("%-19s %-1s %-3s %5s\n",item.getName(), ":", count, "sold"));
                checked.add(item.getName());
            }
        }
        return report.toString();
    }
}
