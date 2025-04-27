package techretailpro.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import techretailpro.objects.Cart;
import techretailpro.objects.Order;
import techretailpro.objects.Product;

public class CartModule {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cart cart = new Cart();
        ArrayList<Order> orderHistory = new ArrayList<>();
        final int MAX_QUANTITY = 100;

        List<Product> products = new ArrayList<>(Arrays.asList(
            new Product("P001", "Laptop", 2999.00),
            new Product("P002", "Mouse", 79.90),
            new Product("P003", "Keyboard", 129.90),
            new Product("D001", "Headset", 499.99)
        ));

        while (true) {
            try {
                System.out.println("=== CART SYSTEM ===");
                System.out.println("1. Add Item");
                System.out.println("2. View Cart");
                System.out.println("3. Remove Item");
                System.out.println("4. Checkout");
                System.out.println("5. View Order History");
                System.out.println("6. Exit");
                System.out.print("Select option: ");

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1: // Add item
                        System.out.println("\nAvailable Products:");
                        for (int i = 0; i < products.size(); i++) {
                            Product p = products.get(i);
                            System.out.printf("%d. %-5s %-15s (RM%.2f)%n", i + 1, p.getId(), p.getName(), p.getPrice());
                        }
                        System.out.print("Select product (1-" + products.size() + ") or B to go back: ");
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("B")) {
                            System.out.println();
                            break;
                        }
                        int productIndex;
                        try {
                            productIndex = Integer.parseInt(input) - 1;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input!\n");
                            break;
                        }
                        if (productIndex >= 0 && productIndex < products.size()) {
                            System.out.print("Enter quantity (max " + MAX_QUANTITY + ") or B to go back: ");
                            String qtyInput = scanner.nextLine();
                            if (qtyInput.equalsIgnoreCase("B")) {
                                System.out.println();
                                break;
                            }
                            int quantity;
                            try {
                                quantity = Integer.parseInt(qtyInput);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid quantity!\n");
                                break;
                            }
                            if (quantity > 0 && quantity <= MAX_QUANTITY) {
                                cart.addItem(products.get(productIndex), quantity);
                                System.out.println("Item added successfully!\n");
                            } else {
                                System.out.println("Quantity must be between 1 and " + MAX_QUANTITY + "!\n");
                            }
                        } else {
                            System.out.println("Invalid product selection!\n");
                        }
                        break;

                    case 2:
                        cart.displayCart();
                        break;

                    case 3:
                        cart.displayCart();
                        if (!cart.isEmpty()) {
                            System.out.print("Enter product ID to remove or B to go back: ");
                            String productId = scanner.nextLine();
                            if (productId.equalsIgnoreCase("B")) {
                                System.out.println();
                                break;
                            }
                            System.out.print("Enter quantity to remove or B to go back: ");
                            String qtyInput = scanner.nextLine();
                            if (qtyInput.equalsIgnoreCase("B")) {
                                System.out.println();
                                break;
                            }
                            int quantityToRemove;
                            try {
                                quantityToRemove = Integer.parseInt(qtyInput);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid quantity!\n");
                                break;
                            }
                            cart.removeItem(productId, quantityToRemove);
                        }
                        break;

                    case 4:
                        if (cart.isEmpty()) {
                            System.out.println("Cart is empty. Add items before checkout.\n");
                        } else {
                            double total = cart.calculateTotal();
                            System.out.printf("Processing payment of RM%.2f...%n", total);
                            orderHistory.add(new Order(cart.getItems(), total));
                            cart.clear();
                            System.out.println("Checkout complete! Order has been placed.\n");
                            // haven't implement the payment
                        }
                        break;

                    case 5:
                        if (orderHistory.isEmpty()) {
                            System.out.println("No orders placed yet.\n");
                        } else {
                            System.out.println("\n=== ORDER HISTORY ===");
                            for (int i = 0; i < orderHistory.size(); i++) {
                                System.out.println("Order #" + (i + 1));
                                orderHistory.get(i).displayOrder();
                                System.out.println();
                            }
                        }
                        break;

                    case 6:
                        System.out.println("Thank you for using our system!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid option!\n");
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter valid numbers only!\n");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage() + "\n");
            }
        }
    }
}
