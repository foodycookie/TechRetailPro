package techretailpro.objects;

import java.util.ArrayList;
import java.util.List;

public class SalesReport {
    private List<CartOrder> orderHistory = new ArrayList<>();
    private List<CartItem> allSoldItems = new ArrayList<>();
    
    public void addOrder(CartOrder order) {
        orderHistory.add(order);
        allSoldItems.addAll(order.getOrderItems());
    }
    public boolean hasOrders() {
        return !orderHistory.isEmpty();
    }
    
    public String getOrderHistory() {
        if (orderHistory.isEmpty()) {
            return "No orders found.";
        }
        StringBuilder result = new StringBuilder("--- All Order History ---\n");
        for (CartOrder order : orderHistory) {
            result.append(order.toString());
        }
        return result.toString();
    }

    public String generateReport() {
        double totalSales = 0;
        double totalDiscount = 0;
        StringBuilder report = new StringBuilder("--- Sales Report ---\n");
        
        for (CartOrder order : orderHistory) {
            totalSales += order.getTotalAmount();
            totalDiscount += order.getDiscountAmount();
        }
        
        report.append("Total Sales         : RM").append(totalSales).append("\n")
              .append("Total Discount Given: RM").append(totalDiscount).append("\n")
              .append("Best Sellers:\n");

        List<String> checked = new ArrayList<>();
        for (CartItem item : allSoldItems) {
            if (!checked.contains(item.getProduct().getName())) {
                int count = 0;
                for (CartItem i : allSoldItems) {
                    if (i.getProduct().getName().equals(item.getProduct().getName())) {
                        count++;
                    }
                }
                report.append(String.format("%-19s %-1s %-3s %5s\n",item.getProduct().getName(), ":", count, "sold"));
                checked.add(item.getProduct().getName());
            }
        }
        return report.toString();
    }
}
