package techretailpro.functions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import techretailpro.objects.Item;
import techretailpro.objects.Order;
import techretailpro.objects.Payment;
import techretailpro.objects.SalesReport;
import techretailpro.objects.Transaction;



public class PaymentModule {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Transaction trans = new Transaction();
        Payment[] payments = {
            new Payment("Touch N Go"),
            new Payment("Credit / Debit"),
            new Payment("Online Banking")
        };
        List<Order> cart = new ArrayList<>(); // this is the cart
        SalesReport report = new SalesReport();
        
        List<Item> items1 = new ArrayList<>();
        items1.add(new Item("Laptop", 3000.00));
        items1.add(new Item("Mouse", 100.00));
        cart.add(new Order(items1));
        
        List<Item> items2 = new ArrayList<>();
        items2.add(new Item("Keyboard", 250.00));
        cart.add(new Order(items2));

        int choice;
        do {
            System.out.println("""
                               Please select:
                               [1] Payment
                               [2] Order History
                               [3] Sales Report
                               [0] Exit
                               """);
            System.out.print("Enter a number: ");
            while (!input.hasNextInt()) {  //prevents InputMismatchException
                System.out.print("Invalid input. Enter a number: ");
                input.next();
            }
            choice = input.nextInt();
            input.nextLine(); 

            switch (choice) {
                case 1 -> {
                    System.out.println("\n---- Payment ----");
                    
                    if (cart.isEmpty()) {
                        System.out.println("No orders available in cart.\n");
                        break;
                    }
                    
                    System.out.println("Available Cart Orders:");
                    for (Order order : cart) {
                        System.out.println("Order ID: " + order.getOrderID() + " - Total: RM" + order.getTotalAmount());
                    }
                    
                    System.out.print("Enter Order ID to pay: ");
                    int selectedID = input.nextInt();
                    input.nextLine();
                    
                    Order selectedOrder = null;
                    for (Order order : cart) {
                        if (order.getOrderID() == selectedID) {
                            selectedOrder = order;
                            break;
                        }
                    }

                    if (selectedOrder == null) {
                        System.out.println("Invalid Order ID.");
                        break;
                    }
                    System.out.println("");
                    System.out.print(selectedOrder.getReceipt());

                    // Apply discount
                    System.out.print("\nEnter discount code (or press Enter to skip): ");
                    String code = input.nextLine();
                    
                    double discountAmount = 0;
                    if (!code.isEmpty()) {
                        if (code.equalsIgnoreCase("SAVE150")) {
                            discountAmount = 150.00;
                            System.out.println("Discount applied: RM" + discountAmount);
                        } else {
                            System.out.println("Invalid code. No discount applied.");
                        }
                    }
                    selectedOrder.applyDiscount(discountAmount);

                    // Show updated order
                    System.out.print(selectedOrder.getReceipt());

                    // Choose payment method
                    Payment payment = null;
                    boolean paymentSelected = false;
                    while (!paymentSelected) {
                        System.out.println("\nPayment Method:");
                        for (int i = 0; i < payments.length; i++) {
                            System.out.println((i + 1) + ". " + payments[i].getPaymentMethod());
                        }

                        int paymentChoose;
                        do {
                            System.out.print("Select payment method (1-3): ");
                            while (!input.hasNextInt()) {
                                System.out.print("Invalid input. Enter a number (1-3): ");
                                input.next();
                            }
                            paymentChoose = input.nextInt();
                            input.nextLine();
                        } while (paymentChoose < 1 || paymentChoose > 3);

                        payment = payments[paymentChoose - 1];
                        trans.setChoosenMethod(paymentChoose);

                        switch (paymentChoose) {
                            case 1 -> {
                                System.out.println("""
                                                   Please scan QR code.
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
                                    System.out.println("Card expired or invalid date. Payment cancelled.");
                                    continue;
                                }
                                paymentSelected = true;
                            }
                            case 3 -> {
                                String accountNo;
                                do {
                                    System.out.print("Enter account number (7-16 digits): ");
                                    accountNo = input.nextLine();
                                } while (!accountNo.matches("\\d{7,16}"));
                                trans.setAccountNo(accountNo);
                                System.out.print("Enter password: ");
                                trans.setPassword(input.nextLine());
                                paymentSelected = true; 
                            }
                            default -> System.out.println("Invalid selection.");
                        } 
                    }

                    double amount;
                    do{
                        System.out.print("Enter the amount: RM");
                        while (!input.hasNextDouble()) {
                            System.out.print("Invalid input. Enter amount: RM");
                            input.next();
                        }
                        amount = input.nextDouble();
                        input.nextLine();
                        if(amount != selectedOrder.getTotalAmount()){
                            System.err.println("Amount not match, please enter again.\n");
                        }
                    }while(amount != selectedOrder.getTotalAmount());

                    // Process payment complete
                    if (payment.processPayment(selectedOrder.getTotalAmount())) {
                        selectedOrder.markCompleted();
                        selectedOrder.setPaymentDate(LocalDateTime.now()); // set current time
                        report.addOrder(selectedOrder);
                        cart.remove(selectedOrder);
                        System.out.println("Payment Successful.");
                    }

                    System.out.println("\n------------ Recipt -------------");
                    System.out.println(selectedOrder.getReceipt());
                    System.out.print("\nPress Enter to Exit.");
                    input.nextLine();                    
                }

                case 2 -> {
                    System.out.println(report.getOrderHistory());
                    System.out.print("\nPress Enter to Exit.");
                    input.nextLine();
                }

                case 3 -> {
                    System.out.println(report.generateReport());
                    System.out.print("\nPress Enter to Exit.");
                    input.nextLine();
                }

                case 0 -> System.out.println("\nThank you. Exiting...");

                default -> System.out.println("\nInvalid choice, please try again.\n");
            }
            System.out.println("");
        } while (choice != 0);
    }
    
    public static boolean validateExpiry(String expiry) {
        try {
            String[] parts = expiry.split("/");
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]) + 2000;
            
            if (month < 1 || month > 12) { // check month valid
                return false;
            }

            Calendar now = Calendar.getInstance();
            int currentMonth = now.get(Calendar.MONTH) + 1;
            int currentYear = now.get(Calendar.YEAR);

            if (year > currentYear || (year == currentYear && month >= currentMonth)) {
                return true;
            }
        } catch (Exception e) {           
        }
        return false;
    }
}


