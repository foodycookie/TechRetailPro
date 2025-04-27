package techretailpro.objects;

import java.util.ArrayList;



public class Order2 {
    private final ArrayList<CartItem> orderItems;
    private final double totalAmount;

    public Order2(ArrayList<CartItem> orderItems, double totalAmount) {
        this.orderItems = new ArrayList<>(orderItems);
        this.totalAmount = totalAmount;
    }

    // this code doesn’t store the data, so no persistency
    public void displayOrder() {
        System.out.println("\n--- Order Receipt ---");
        System.out.printf("%-10s %-15s %-10s %-8s %-10s%n", "Product ID", "Product", "Price", "Qty", "Subtotal");
        for (CartItem item : orderItems) {
            System.out.printf("%-10s %-15s RM%-9.2f %-8d RM%-9.2f%n",
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getProduct().getPrice(),
                item.getQuantity(),
                item.getSubtotal());
        }
        System.out.println("----------------------------");
        System.out.printf("TOTAL PAID: RM%.2f%n", totalAmount);
    }
}
