package techretailpro.objects;

import java.util.ArrayList;

public class Cart {
    private final ArrayList<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
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
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(productId)) {
                int currentQty = item.getQuantity();
                if (quantityToRemove > currentQty) {
                    // let user able to delete the exact amount they want only
                    System.out.println("Cannot remove more than existing quantity (" + currentQty + ").\n");
                } else if (quantityToRemove == currentQty) {
                    items.remove(item);
                    System.out.println("Removed all quantity of product " + productId + ".\n");
                } else {
                    item.updateQuantity(currentQty - quantityToRemove);
                    System.out.println("Removed " + quantityToRemove + " of product " + productId + ".\n");
                }
                return;
            }
        }
        System.out.println("Item with ID " + productId + " not found in the cart.\n");
    }

    public void displayCart() {
        if (items.isEmpty()) {
            System.out.println("\nYour cart is empty.\n");
            return;
        }

        System.out.println("\n=== YOUR CART ===");
        System.out.printf("%-10s %-15s %-10s %-8s %-10s%n", "Product ID", "Product", "Price", "Qty", "Subtotal");
        for (CartItem item : items) {
            System.out.printf("%-10s %-15s RM%-9.2f %-8d RM%-9.2f%n",
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getProduct().getPrice(),
                item.getQuantity(),
                item.getSubtotal());
        }
        System.out.println("----------------------------");
        System.out.printf("TOTAL: RM%.2f%n%n", calculateTotal());
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public double calculateTotal() {
        return items.stream().mapToDouble(CartItem::getSubtotal).sum();
    }

    public void clear() {
        items.clear();
    }
}
