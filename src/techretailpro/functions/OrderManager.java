package techretailpro.functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import techretailpro.objects.CartItem;
import techretailpro.objects.CartOrder;
import techretailpro.objects.LocalData;
import techretailpro.objects.Payment;
import techretailpro.objects.Transaction;

public class OrderManager {
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
        } else {
//            System.out.println("Order history file found.");
        }
    }

    public static void appendOrderToCsv(CartOrder order) {       
        try (PrintWriter pw = new PrintWriter(new FileWriter(UtilityHelper.ORDER_HISTORY_DATABASE, true))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (CartItem item : order.getOrderItems()) {
                pw.printf("%-10s %-20s %-10d %-10.2f %-20s%n",
                        LocalData.getCurrentUser().getUsername(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getSubtotal(),
                        LocalDateTime.now().format(formatter));
            }
            pw.printf("TOTAL RM%.2f%n", order.getTotalAmount());
        } catch (IOException e) {
            System.err.println("Error writing order history: " + e.getMessage());
        }
    }
    
    public static void viewOrderHistory() {
        try (BufferedReader br = new BufferedReader(new FileReader(UtilityHelper.ORDER_HISTORY_DATABASE))) {
            System.out.println("\n--- Order History ---");
            System.out.printf("%-10s %-20s %-10s %-10s %-20s\n", "Username", "ProductName", "Quantity", "Subtotal","Date/Time");
            br.lines().skip(1).forEach(System.out::println);
            System.out.println("---------------------");
        } catch (IOException e) {
            System.err.println("Error reading order history: " + e.getMessage());
        }
        
        UtilityHelper.displayReturnMessage("");
    }

    public static void checkoutAndPay() {
        if (LocalData.getCurrentUserCart().isEmpty()) {
            UtilityHelper.displayReturnMessage("Cart is empty");
            return;
        }
        
        List<CartOrder> orderHistory = new ArrayList<>();
        Transaction trans = new Transaction();
        Payment[] payments = {
            new Payment("Touch N Go"),
            new Payment("Credit / Debit"),
            new Payment("Online Banking")
        };

        System.out.println("\n=== Checkout ===");
        System.out.println(LocalData.getCurrentUserCart());

        // Discount
        System.out.print("\nEnter discount code (press Enter to skip, 0 to back)\ninput > ");
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
                        if(!cardNo.matches("\\d{16}")){
                            System.out.println("Please enter 16 digits only.\n");
                        }
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
                        if(accNo.matches("\\d{7,16}")){
                            System.out.println("Please enter 7 to 16 digits only.\n");
                        }
                    } while (!accNo.matches("\\d{7,16}"));
                    trans.setAccountNo(accNo);

                    paymentSelected = true;
                    System.out.println("");
                }
            }
        }
        
        String password;
        do{
            System.out.print("Enter your password (type back to cancel checkout)\ninput >  ");
            password = UtilityHelper.SCANNER.nextLine();
            if (password.equalsIgnoreCase("back")) {
                System.out.println("Cancelling checkout. Returning to menu...\n");
                return; // Exit the method or handle as needed
            }
            if (!password.equals(LocalData.getCurrentUser().getPassword())){
                System.out.println("Wrong password! Please enter again.\n");
            }
        }while(!password.equals(LocalData.getCurrentUser().getPassword()));

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

            CartOrder newOrder = new CartOrder(LocalData.getCurrentUserCart().getItems(),originalTotal, finalTotal, discountAmount);
            
            orderHistory.add(newOrder);
            appendOrderToCsv(newOrder);
            
            for (CartItem item : LocalData.getCurrentUserCart().getItems()) {
                ProductManager.updateStockToCsv(item.getProduct());
            }
            
            System.out.println("\n---------------------------------------------------------");
            System.out.println("TECH RETAIL PRO");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            System.out.println("Date / Time: " + LocalDateTime.now().format(formatter));
            System.out.println("---------------------------------------------------------");
            System.out.println(newOrder);
            System.out.println("Payment Method: " + payment.getPaymentMethod());
            System.out.println("---------------------------------------------------------");
            System.out.println("      Thank you for shopping with TECH RETAIL PRO!       ");
            System.out.print("\nPress Enter to Exit");
            UtilityHelper.SCANNER.nextLine();
 
            LocalData.getCurrentUserCart().clear();
        }
    }
}