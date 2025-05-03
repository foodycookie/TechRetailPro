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
import techretailpro.objects.CartItem;
import techretailpro.objects.CartOrder;
import techretailpro.objects.LocalData;
import techretailpro.objects.Payment;
import techretailpro.objects.Transaction;
import techretailpro.objects.Customer;
import techretailpro.objects.PickUpOrder;
import techretailpro.objects.DeliveryOrder;

public class OrderFunctions {
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
        File file = new File(Utility.ORDER_HISTORY_DATABASE);
        if (!file.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                pw.println(Utility.ORDER_HISTORY_DATABASE_HEADER);
            } catch (IOException e) {
                System.err.println("Could not initialize order history file: " + e.getMessage());
            }
        }
    }

    public static void appendOrderToCsv(CartOrder order) { 
        try (PrintWriter pw = new PrintWriter(new FileWriter(Utility.ORDER_HISTORY_DATABASE, true))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (CartItem item : order.getOrderItems()) {
                pw.printf("%-10s %-20s %-10d %-10.2f %-20s%n",
                        LocalData.getCurrentUser().getUsername(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getSubtotal(),
                        LocalDateTime.now().format(formatter));
            }
            pw.printf("DeliveryFee RM%.2f%n", order.getDeliveryFee());
            pw.printf("Discount    RM%.2f%n", order.getDiscountAmount());
            pw.printf("TOTAL       RM%.2f%n", order.getTotalAmount());  
        } catch (IOException e) {
            System.err.println("Error writing order history: " + e.getMessage());
        }
    }
    
    public static void viewOrderHistory() {
        try (BufferedReader br = new BufferedReader(new FileReader(Utility.ORDER_HISTORY_DATABASE))) {
            System.out.println("\n--- Order History ---");
            System.out.printf("%-10s %-20s %-10s %-10s %-20s\n", "Username", "ProductName", "Quantity", "Subtotal", "Date/Time");
            br.lines().skip(1).forEach(System.out::println);
            System.out.println("---------------------");
        } catch (IOException e) {
            System.err.println("Error reading order history: " + e.getMessage());
        }
        
        Utility.displayReturnMessage("");
    }
    
    public static void viewCurrentUserOrderHistory() {
        String currentUser = LocalData.getCurrentUser().getUsername();
        try (BufferedReader br = new BufferedReader(new FileReader(Utility.ORDER_HISTORY_DATABASE))) {
            System.out.println("\n--- Your Order History ---");
            System.out.printf("%-10s %-20s %-10s %-10s %-20s\n", "Username", "ProductName", "Quantity", "Subtotal", "Date/Time");
            br.lines()
              .skip(1) // Skip header
              .filter(line -> line.startsWith(currentUser + " "))
              .forEach(System.out::println);
            System.out.println("--------------------------");
        } catch (IOException e) {
            System.err.println("Error reading order history: " + e.getMessage());
        }
        Utility.displayReturnMessage("");
    }

    public static void checkoutAndPay() {
        if (LocalData.getCurrentUserCart().isEmpty()) {
            Utility.displayReturnMessage("Cart is empty");
            return;
        }
        
        Transaction trans = new Transaction();
        Payment[] payments = {
            new Payment("Touch N Go"),
            new Payment("Credit / Debit"),
            new Payment("Online Banking")
        };

        System.out.println("\n=== Checkout ===");
        System.out.println(LocalData.getCurrentUserCart());
        
        int orderType;
        System.out.println("Choose order type:");
        System.out.println("1. Pickup");
        System.out.println("2. Delivery");
        do {  
            System.out.print("Select option (1-2):\ninput > ");

            if (Utility.SCANNER.hasNextInt()) {
                orderType = Utility.SCANNER.nextInt();
                Utility.SCANNER.nextLine(); // Consume the newline character
                if (orderType != 1 && orderType != 2) {
                    System.out.println("Invalid selection. Please enter 1 or 2.\n");
                }
            } else {
                System.out.println("Invalid input. Please enter a number (1 or 2).\n");
                Utility.SCANNER.nextLine(); 
                orderType = 0;                       // Reset to ensure the loop continues
            }
        } while (orderType != 1 && orderType != 2);

        String address = null;
        double deliveryFee = 0.0;

        if (orderType == 2) { // Delivery
            if(LocalData.getCurrentUser() instanceof Customer) {
                Customer customer = (Customer) LocalData.getCurrentUser();
                address = customer.getAddress();
                if (address == null || address.isBlank()) {
                    System.out.print("You haven't set a delivery address. Please enter your address:\ninput > ");
                    address = Utility.SCANNER.nextLine();
                    customer.setAddress(address);
                }
                deliveryFee = 10.0;
            }else{
                System.out.println("Delivery option not available: current user is not a customer.");
                return;
            }   
        }

        // Discount
        System.out.print("\nEnter discount code (press Enter to skip, 0 to back)\ninput > ");
        String code = Utility.SCANNER.nextLine();
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
        double finalTotal = originalTotal - discountAmount + deliveryFee;
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
                if (Utility.SCANNER.hasNextInt()) {
                    paymentChoose = Utility.SCANNER.nextInt();
                    Utility.SCANNER.nextLine(); // Consume the newline character
                    if (paymentChoose < 0 || paymentChoose > 3) {
                        System.out.println("Invalid selection. Please enter a number between 0 and 3.\n");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number between 0 and 3.\n");
                    Utility.SCANNER.nextLine(); 
                    paymentChoose = -1; 
                }
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
                        cardNo = Utility.SCANNER.nextLine();
                        if(!cardNo.matches("\\d{16}")){
                            System.out.println("Please enter 16 digits only.\n");
                        }
                    } while (!cardNo.matches("\\d{16}"));
                    trans.setCardNo(cardNo);

                    int cvv;
                    do {
                        System.out.print("Enter 3-digit CVV\ninput > ");
                        while (!Utility.SCANNER.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a 3-digit CVV.\n");
                            System.out.print("Enter 3-digit CVV\ninput > ");
                            Utility.SCANNER.next(); 
                        }
                        cvv = Utility.SCANNER.nextInt();
                        Utility.SCANNER.nextLine(); 
                        if (cvv < 100 || cvv > 999) {
                            System.out.println("Invalid input. Please enter a 3-digit CVV.\n");
                        }
                    } while (cvv < 100 || cvv > 999);
                    trans.setCvv(cvv);

                    System.out.print("Enter expiry date (MM/YY)\ninput >  ");
                    String expiry = Utility.SCANNER.nextLine();
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
                        accNo = Utility.SCANNER.nextLine();
                        if(!accNo.matches("\\d{7,16}")){
                            System.out.println("Please enter 7 to 16 digits only.\n");
                        }
                    } while (!accNo.matches("\\d{7,16}"));
                    trans.setAccountNo(accNo);

                    paymentSelected = true;
                }
            }
        }
        
        String password;
        do{
            System.out.print("Enter your password (type back to cancel checkout)\ninput >  ");
            password = Utility.SCANNER.nextLine();
            if (password.equalsIgnoreCase("back")) {
                System.out.println("Cancelling checkout. Returning to menu...\n");
                return; 
            }
            if (!password.equals(LocalData.getCurrentUser().getPassword())){
                System.out.println("Wrong password! Please enter again.\n");
            }
        }while(!password.equals(LocalData.getCurrentUser().getPassword()));

        double amount;
        do {
            System.out.printf("Enter the amount to pay (RM%.2f)\ninput >  RM", finalTotal);
            while (!Utility.SCANNER.hasNextDouble()) {
                System.out.print("Invalid input. Enter a valid amount\ninput >  RM");
                Utility.SCANNER.next();
            }
            amount = Utility.SCANNER.nextDouble();
            Utility.SCANNER.nextLine();
            if (Math.abs(amount - finalTotal) > 0.01) {
                System.err.println("Amount not matched. Please enter exactly RM" + finalTotal + "\n");
            }
        } while (Math.abs(amount - finalTotal) > 0.01); //to avoid real floating point comparison problem (9.90000000011)

        if (payment != null && payment.processPayment(finalTotal)) {
            System.out.println("Payment Successful!");

            CartOrder newOrder;
            if(orderType == 1){
                newOrder = new PickUpOrder(LocalData.getCurrentUserCart().getItems(),originalTotal, finalTotal, discountAmount);
            }else{
                newOrder = new DeliveryOrder(LocalData.getCurrentUserCart().getItems(),originalTotal, finalTotal, discountAmount, deliveryFee, address);
            }
            
            appendOrderToCsv(newOrder);
            
            for (CartItem item : LocalData.getCurrentUserCart().getItems()) {
                ProductFunctions.updateStockToCsv(item.getProduct());
            }
            
            System.out.println("\n---------------------------------------------------------");
            System.out.println("TECH RETAIL PRO");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            System.out.println("Date / Time: " + LocalDateTime.now().format(formatter));
            System.out.println("---------------------------------------------------------");
            System.out.println(newOrder);
            if(orderType == 1){
                System.out.println("Our address         : 88. Taman MAJU, 53300 Kuala Lumpur");
            }
            System.out.println("Payment Method      : " + payment.getPaymentMethod());
            System.out.println("---------------------------------------------------------");
            System.out.println("      Thank you for shopping with TECH RETAIL PRO!       ");
            System.out.print("\nPress Enter to Exit");
            Utility.SCANNER.nextLine();
 
            LocalData.getCurrentUserCart().clear();
        }
    }
}
