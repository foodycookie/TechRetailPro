/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.assg;
import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author TAY TIAN YOU
 */
public class Assg {
    private static final String CSV_FILE = "order_history.txt";
    private static final String CSV_HEADER = String.format("%-10s %-20s %-10s %-10s", "OrderID", "ProductName", "Quantity", "Subtotal");
    private static int nextOrderId;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Cart cart = new Cart();
        List<CartOrder> orderHistory = new ArrayList<>();
        Transaction trans = new Transaction();
        Payment[] payments = {
            new Payment("Touch N Go"),
            new Payment("Credit / Debit"),
            new Payment("Online Banking")
        };

        List<Product> products = Arrays.asList(
                new Product("P001", "Laptop", 2999.90),
                new Product("P002", "Mouse", 79.90),
                new Product("P003", "Keyboard", 129.90),
                new Product("D001", "Headset", 499.99)
        );

        initCsv();

        int choice;
        do {
            System.out.println("""
                === ONLINE SHOPPING SYSTEM ===
                [1] Add Item to Cart
                [2] View Cart
                [3] Remove Item from Cart
                [4] Checkout & Payment
                [5] View Order History
                [0] Exit
                """);
            System.out.print("Select an option: ");

            while (!input.hasNextInt()) {
                System.out.print("Invalid input. Enter a number: ");
                input.next();
            }
            choice = input.nextInt();
            input.nextLine(); // clear buffer

            switch (choice) {
                case 1 -> addItemFlow(input, cart, products);
                case 2 -> viewCart(cart);
                case 3 -> removeItemFlow(input, cart);
                case 4 -> checkoutAndPay(input, cart, orderHistory, payments, trans);
                case 5 -> viewOrderHistory();
                case 0 -> System.out.println("Thank you for using our system!");
                default -> System.out.println("Invalid option.\n");
            }
        } while (choice != 0);
    }
    
    private static void addItemFlow(Scanner input, Cart cart, List<Product> products) {
        System.out.println("\nAvailable Products:");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.printf("%d. %-5s %-15s (RM%.2f)\n", i + 1, p.getId(), p.getName(), p.getPrice());
        }
        System.out.print("Select product (1-" + products.size() + ") or B to go back: ");
        String choice = input.nextLine();
        if (choice.equalsIgnoreCase("B")) {
            System.out.println();
            return;
        }
        try {
            int idx = Integer.parseInt(choice) - 1;
            if (idx < 0 || idx >= products.size()) {
                System.out.println("Invalid product selection!\n");
                return;
            }
            Product selectedProduct = products.get(idx);
            System.out.print("Enter quantity: ");
            int qty = input.nextInt();
            input.nextLine(); // clear buffer
            if (qty <= 0 || qty > 100) {
                System.out.println("Quantity must be between 1 and 100!\n");
                return;
            }
            cart.addItem(selectedProduct, qty);
            System.out.println("Item added to cart!\n");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!\n");
        }
    }

    private static void viewCart(Cart cart) {
        System.out.println();
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.\n");
        } else {
            System.out.println(cart);
        }
    }

    private static void removeItemFlow(Scanner input, Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.\n");
            return;
        }
        System.out.println(cart);
        System.out.print("Enter product ID to remove or B to go back: ");
        String productId = input.nextLine();
        if (productId.equalsIgnoreCase("B")) {
            System.out.println();
            return;
        }
        System.out.print("Enter quantity to remove: ");
        String qtyStr = input.nextLine();
        if (qtyStr.equalsIgnoreCase("B")) {
            System.out.println();
            return;
        }
        try {
            int qty = Integer.parseInt(qtyStr);
            cart.removeItem(productId, qty);
            System.out.println("Item removed!\n");
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity input!\n");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + "\n");
        }
    }

    private static void checkoutAndPay(Scanner input, Cart cart, List<CartOrder> orderHistory, Payment[] payments, Transaction trans) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Add items first!\n");
            return;
        }

        System.out.println("\n=== Checkout ===");
        System.out.println(cart);

        // Discount
        System.out.print("Enter discount code (press Enter to skip, 0 to back): ");
        String code = input.nextLine();
        double discountAmount = 0;
        
        if (code.equals("0")) {
            System.out.println("Cancelling checkout. Returning to menu...\n");
            return;
        }

        if (!code.isEmpty()) {
            if (code.equalsIgnoreCase("SAVE150")) {
                if (cart.calculateTotal() < 150) {
                    System.out.println("Cart total too low to apply RM150 discount. No discount applied.\n");
                } else {
                    discountAmount = 150.00;
                    System.out.println("Discount applied: RM" + discountAmount + "\n");
                }
            } else {
                System.out.println("Invalid discount code. No discount applied.\n");
            }
        } else {
            System.out.println("No discount code entered.\n");
        }

        double originalTotal = cart.calculateTotal();
        double finalTotal = originalTotal - discountAmount;
        if (finalTotal < 0) finalTotal = 0;

        Payment payment = null;
        boolean paymentSelected = false;

        while (!paymentSelected) {
            System.out.println("Payment Method:");
            for (int i = 0; i < payments.length; i++) {
                System.out.println((i + 1) + ". " + payments[i].getPaymentMethod());
            }
            System.out.println("0. Cancel Payment");

            int paymentChoose;
            do {
                System.out.print("Select payment method (0-3): ");
                while (!input.hasNextInt()) {
                    System.out.print("Invalid input. Enter a number (0-3): ");
                    input.next();
                }
                paymentChoose = input.nextInt();
                input.nextLine();
            } while (paymentChoose < 0 || paymentChoose > 3);
            
            if (paymentChoose == 0) {
                System.out.println("Cancelling checkout. Returning to menu...\n");
                return;
            }

            payment = payments[paymentChoose - 1];
            trans.setChoosenMethod(paymentChoose);

            switch (paymentChoose) {
                case 1 -> {
                    System.out.println("""                                      
                                       Scan QR code to complete payment.                                      
                                            ******** ********
                                            *    * * **     *
                                            *  **  * *  **  *
                                            *  **  * *  **  *
                                            * *    * * *    *
                                            ******** ********
                                       
                                            ******** ********
                                            ****  ** **   ***
                                            *******  ****  **
                                            *  ***** ** * * *
                                            ***  *** ***  ***
                                            ******** ********
                                                   """);
                    paymentSelected = true;
                }
                case 2 -> {
                    String cardNo;
                    do {
                        System.out.print("Enter 16-digit card number: ");
                        cardNo = input.nextLine();
                    } while (!cardNo.matches("\\d{16}"));
                    trans.setCardNo(cardNo);

                    int cvv;
                    do {
                        System.out.print("Enter 3-digit CVV: ");
                        while (!input.hasNextInt()) {
                            System.out.print("Invalid input. Enter a 3-digit CVV: ");
                            input.next();
                        }
                        cvv = input.nextInt();
                        input.nextLine();
                    } while (cvv < 100 || cvv > 999);
                    trans.setCvv(cvv);

                    System.out.print("Enter expiry date (MM/YY): ");
                    String expiry = input.nextLine();
                    if (!validateExpiry(expiry)) {
                        System.out.println("Card expired or invalid date. Please select payment again.\n");
                        continue;
                    }
                    paymentSelected = true;
                }
                case 3 -> {
                    String accNo;
                    do {
                        System.out.print("Enter account number (7-16 digits): ");
                        accNo = input.nextLine();
                    } while (!accNo.matches("\\d{7,16}"));
                    trans.setAccountNo(accNo);

                    System.out.print("Enter password: ");
                    trans.setPassword(input.nextLine());
                    paymentSelected = true;
                }
            }
        }

        double amount;
        do {
            System.out.printf("Enter the amount to pay (RM%.2f): RM", finalTotal);
            while (!input.hasNextDouble()) {
                System.out.print("Invalid input. Enter a valid amount: RM");
                input.next();
            }
            amount = input.nextDouble();
            input.nextLine();
            if (Math.abs(amount - finalTotal) > 0.01) {
                System.err.println("Amount not matched. Please enter exactly RM" + finalTotal + "\n");
            }
        } while (Math.abs(amount - finalTotal) > 0.01); //to avoid real floating point comparison problem (9.90000000011)

        if (payment.processPayment(finalTotal)) {
            System.out.println("Payment Successful!");

            CartOrder newOrder = new CartOrder(getNextOrderId(), cart.getItems(),originalTotal, finalTotal, discountAmount);
            int orderId = getNextOrderId();
            orderHistory.add(newOrder);
            appendOrderToCsv(newOrder);
            
            System.out.println("\n---------------------------------------------------------");
            System.out.println("TECH RETAIL PRO");
            System.out.println("Order ID " + orderId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            System.out.println("Date / Time: " + LocalDateTime.now().format(formatter));
            System.out.println("---------------------------------------------------------");
            System.out.println(newOrder);
            System.out.println("Payment Method: " + payment.getPaymentMethod());
            System.out.println("---------------------------------------------------------");
            System.out.println("      Thank you for shopping with TECH RETAIL PRO!       ");
            System.out.print("\nPress Enter to Exit");
            input.nextLine();

            cart.clear();
        }
    }

    private static void viewOrderHistory() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            System.out.println("\n--- Order History ---");
            System.out.printf("%-10s %-20s %-10s %-10s\n", "OrderID", "ProductName", "Quantity", "Subtotal");
            br.lines().skip(1).forEach(System.out::println);
            System.out.println("---------------------\n");
        } catch (IOException e) {
            System.err.println("Error reading order history: " + e.getMessage());
        }
    }

    private static boolean validateExpiry(String expiry) {
        try {
            if (!expiry.matches("\\d{2}/\\d{2}")) {
                return false; // Must be exactly format MM/YY
            }
            
            String[] parts = expiry.split("/");
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]) + 2000;
            if (month < 1 || month > 12) return false;

            Calendar now = Calendar.getInstance();
            int currentMonth = now.get(Calendar.MONTH) + 1;
            int currentYear = now.get(Calendar.YEAR);

            return year > currentYear || (year == currentYear && month >= currentMonth);
        } catch (Exception e) {
            return false;
        }
    }

    private static void initCsv() {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                pw.println(CSV_HEADER);
            } catch (IOException e) {
                System.err.println("Could not initialize order history file: " + e.getMessage());
            }
            nextOrderId = 1;
        } else {
            int maxId = 0;
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                br.readLine();
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",", 2);
                    int id = Integer.parseInt(parts[0]);
                    if (id > maxId) maxId = id;
                }
            } catch (IOException | NumberFormatException ignored) {}
            nextOrderId = maxId + 1;
        }
    }

    private static void appendOrderToCsv(CartOrder order) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_FILE, true))) {
            for (CartItem item : order.getOrderItems()) {
                pw.printf("%-10d %-20s %-10d %-10.2f%n",
                        order.getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getSubtotal());                
            }
            pw.printf("TOTAL for Order #%d: RM%.2f%n\n", order.getId(), order.getTotalAmount());
        } catch (IOException e) {
            System.err.println("Error writing order history: " + e.getMessage());
        }
    }

    private static int getNextOrderId() {
        return nextOrderId++;
    }
}
