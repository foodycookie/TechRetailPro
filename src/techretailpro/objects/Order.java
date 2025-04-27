package techretailpro.objects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;



public class Order {
    private static int orderCounter = 1;
    private int orderID;
    private List<Item> items;
    private double discount;
    private boolean completed;
    private LocalDateTime paymentDate;

    public Order(List<Item> items) {
        this.orderID = orderCounter++;
        this.items = items;
        this.discount = 0.0;
        this.completed = false;
    }
    
    public Order(){
    }

    public int getOrderID() {
        return orderID;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getOriginalTotal() {
        double sum = 0;
        for (Item item : items) {
            sum += item.getPrice();
        }
        return sum;
    }
    
    public double getTotalAmount() {
        return getOriginalTotal() - discount;
    }
        
    public double getDiscountUsed() {
        return discount;
    }

    public void applyDiscount(double discount) {
        this.discount = discount;
    }

    public void markCompleted() {
        this.completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }
    
          
    public String getReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("Order ID: ").append(orderID).append("\n");
        if (paymentDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            receipt.append("Payment Date: ").append(paymentDate.format(formatter)).append("\n");
            receipt.append(String.format("%-28s %4s\n","Payment Status", "Paid"));
        }
        receipt.append("---------------------------------\n");
        for (Item item : items) {
            receipt.append(String.format("%-19s %-4s %8s\n", item.getName(), ": RM", item.getPrice()));
        }
        receipt.append("---------------------------------\n");
        receipt.append(String.format("%-19s %-4s %8s\n", "Subtotal", ": RM", getOriginalTotal()));
        receipt.append(String.format("%-19s %-4s %8s\n", "Discount", ": RM", discount));
        receipt.append(String.format("%-19s %-4s %8s\n", "Total", ": RM", getTotalAmount()));
        
        receipt.append("---------------------------------\n");
        return receipt.toString();
    }
}
