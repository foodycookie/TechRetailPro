package techretailpro.objects;

import java.util.List;

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
        sb.append(String.format("%-15s %-10s %-7s %-10s\n", "Product", "Price", "Qty", "Subtotal"));
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