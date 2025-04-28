/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assg;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author TAY TIAN YOU
 */
public class Cart {
    private final List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.updateQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    public void removeItem(String productId, int quantityToRemove) {
        for (CartItem item : new ArrayList<>(items)) {
            if (item.getProduct().getId().equals(productId)) {
                int currentQty = item.getQuantity();
                if (quantityToRemove > currentQty) {
                    throw new IllegalArgumentException(
                        "Cannot remove more than existing quantity (" + currentQty + ")");
                } else if (quantityToRemove == currentQty) {
                    items.remove(item);
                } else {
                    item.updateQuantity(currentQty - quantityToRemove);
                }
                return;
            }
        }
        throw new IllegalArgumentException("Item with ID " + productId + " not found");
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double calculateTotal() {
        return items.stream().mapToDouble(CartItem::getSubtotal).sum();
    }
    
    public void clear() {
        items.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== YOUR CART ===\n");
        sb.append(String.format("%-10s %-15s %-10s %-7s %-10s\n", "Product ID", "Product", "Price", "Qty", "Subtotal"));
        for (CartItem item : items) {
            sb.append(item).append("\n");
        }
        sb.append(String.format("TOTAL: RM%.2f\n", calculateTotal()));
        return sb.toString();
    }

}
