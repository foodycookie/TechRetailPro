package techretailpro.functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import techretailpro.objects.CartItem;
import techretailpro.objects.CartOrder;
import techretailpro.objects.LocalData;
import techretailpro.objects.Payment;
import techretailpro.objects.Transaction;

public class OrderManager {
    private static int nextOrderId;
    
    public static int getNextOrderId() {
        return nextOrderId++;
    }
    
    public static boolean validateExpiry(String expiry) {
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
    
    public static void initCsv() {
        File file = new File(UtilityHelper.ORDER_HISTORY_DATABASE);
        if (!file.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                pw.println(UtilityHelper.ORDER_HISTORY_DATABASE_HEADER);
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

    public static void appendOrderToCsv(CartOrder order) {       
        try (PrintWriter pw = new PrintWriter(new FileWriter(UtilityHelper.ORDER_HISTORY_DATABASE, true))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (CartItem item : order.getOrderItems()) {
                pw.printf("%-10s %-20s %-10s %-10s %-20s%n",
                        LocalData.getCurrentUser(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getSubtotal(),
                        LocalDateTime.now().format(formatter));
            }
            pw.printf("TOTAL RM%.2f%n%n", order.getTotalAmount());
        } catch (IOException e) {
            System.err.println("Error writing order history: " + e.getMessage());
        }
    }
    
    public static void viewOrderHistory() {
        try (BufferedReader br = new BufferedReader(new FileReader(UtilityHelper.ORDER_HISTORY_DATABASE))) {
            System.out.println("\n--- Order History ---");
            System.out.printf("%-10s %-20s %-10s %-10s %-20s\n", "OrderID", "ProductName", "Quantity", "Subtotal","Date/Time");
            br.lines().skip(1).forEach(System.out::println);
            System.out.println("---------------------\n");
        } catch (IOException e) {
            System.err.println("Error reading order history: " + e.getMessage());
        }
        
        UtilityHelper.displayReturnMessage("");
    }

    public static void checkoutAndPay(List<CartOrder> orderHistory, Payment[] payments, Transaction trans) {
        if (LocalData.getCurrentUserCart().isEmpty()) {
            System.out.println("Cart is empty. Add items first!\n");
            return;
        }

        System.out.println("\n=== Checkout ===");
        System.out.println(LocalData.getCurrentUserCart());

        // Discount
        System.out.print("Enter discount code (press Enter to skip, 0 to back)\ninput > ");
        String code = UtilityHelper.SCANNER.nextLine();
        double discountAmount = 0;
        
        if (code.equals("0")) {
            System.out.println("Cancelling checkout. Returning to menu...\n");
            return;
        }

        if (!code.isEmpty()) {
            if (code.equalsIgnoreCase("SAVE150")) {
                if (LocalData.getCurrentUserCart().calculateTotal() < 150) {
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

        double originalTotal = LocalData.getCurrentUserCart().calculateTotal();
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
                System.out.print("Select payment method (0-3)\ninput > ");
                while (!UtilityHelper.SCANNER.hasNextInt()) {
                    System.out.print("Invalid input. Enter a number (0-3)\ninput > ");
                    UtilityHelper.SCANNER.next();
                }
                paymentChoose = UtilityHelper.SCANNER.nextInt();
                UtilityHelper.SCANNER.nextLine();
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
                        System.out.print("Enter 16-digit card number\ninput >  ");
                        cardNo = UtilityHelper.SCANNER.nextLine();
                    } while (!cardNo.matches("\\d{16}"));
                    trans.setCardNo(cardNo);

                    int cvv;
                    do {
                        System.out.print("Enter 3-digit CVV\ninput >  ");
                        while (!UtilityHelper.SCANNER.hasNextInt()) {
                            System.out.print("Invalid input. Enter a 3-digit CVV\ninput >  ");
                            UtilityHelper.SCANNER.next();
                        }
                        cvv = UtilityHelper.SCANNER.nextInt();
                        UtilityHelper.SCANNER.nextLine();
                    } while (cvv < 100 || cvv > 999);
                    trans.setCvv(cvv);

                    System.out.print("Enter expiry date (MM/YY)\ninput >  ");
                    String expiry = UtilityHelper.SCANNER.nextLine();
                    if (!validateExpiry(expiry)) {
                        System.out.println("Card expired or invalid date. Please select payment again.\n");
                        continue;
                    }
                    paymentSelected = true;
                }
                case 3 -> {
                    String accNo;
                    do {
                        System.out.print("Enter account number (7-16 digits)\ninput >  ");
                        accNo = UtilityHelper.SCANNER.nextLine();
                    } while (!accNo.matches("\\d{7,16}"));
                    trans.setAccountNo(accNo);

                    System.out.print("Enter password\ninput >  ");
                    trans.setPassword(UtilityHelper.SCANNER.nextLine());
                    paymentSelected = true;
                    System.out.println("");
                }
            }
        }

        double amount;
        do {
            System.out.printf("Enter the amount to pay (RM%.2f)\ninput >  RM", finalTotal);
            while (!UtilityHelper.SCANNER.hasNextDouble()) {
                System.out.print("Invalid input. Enter a valid amount\ninput >  RM");
                UtilityHelper.SCANNER.next();
            }
            amount = UtilityHelper.SCANNER.nextDouble();
            UtilityHelper.SCANNER.nextLine();
            if (Math.abs(amount - finalTotal) > 0.01) {
                System.err.println("Amount not matched. Please enter exactly RM" + finalTotal + "\n");
            }
        } while (Math.abs(amount - finalTotal) > 0.01); //to avoid real floating point comparison problem (9.90000000011)

        if (payment != null && payment.processPayment(finalTotal)) {
            System.out.println("Payment Successful!");

            CartOrder newOrder = new CartOrder(getNextOrderId(), LocalData.getCurrentUserCart().getItems(),originalTotal, finalTotal, discountAmount);
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
            UtilityHelper.SCANNER.nextLine();
 
            for (CartItem item : LocalData.getCurrentUserCart().getItems()) {
                ProductManager.updateStockToCsv(item.getProduct());
            }

            LocalData.getCurrentUserCart().clear();
        }
    }
}