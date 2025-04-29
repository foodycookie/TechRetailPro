package techretailpro.objects;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<CartItem> items = new ArrayList<>();

//    public Cart() {
//        this.items = new ArrayList<>();
//    }

    public void addItem(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getName().equals(product.getName())) {
                item.updateQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        
        items.add(new CartItem(product, quantity));
    }

    public void removeItem(String productName, int quantityToRemove) {
        for (CartItem item : new ArrayList<>(items)) {
            if (item.getProduct().getName().equals(productName)) {
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
        throw new IllegalArgumentException("Item with name " + productName + " not found");
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
        sb.append(String.format("%-5s %-15s %-10s %-7s %-10s\n", "No", "Product", "Price", "Qty", "Subtotal"));
        
        for (int i = 0; i < items.size(); i++) {
            sb.append(i + 1).append(items.get(i)).append("\n");
        }

        sb.append(String.format("TOTAL: RM%.2f\n", calculateTotal()));
        return sb.toString();
    }
}