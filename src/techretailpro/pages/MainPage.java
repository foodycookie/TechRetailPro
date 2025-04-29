package techretailpro.pages;

import java.util.ArrayList;
import java.util.List;
import techretailpro.functions.CartManager;
import techretailpro.functions.ProductCategoryHelper;
import techretailpro.functions.DatabaseManager;
import techretailpro.functions.InputValidator;
import techretailpro.functions.OrderManager;
import techretailpro.functions.ProductManager;
import techretailpro.functions.ProductListHelper;
import techretailpro.objects.CartOrder;
import techretailpro.objects.LocalData;
import techretailpro.objects.Payment;
import techretailpro.objects.Product;
import techretailpro.objects.Transaction;
import techretailpro.utilities.Utility;

public class MainPage {
    /*
    List<CartOrder> orderHistory = new ArrayList<>();
    Transaction trans = new Transaction();
    Payment[] payments = {
        new Payment("Touch N Go"),
        new Payment("Credit / Debit"),
        new Payment("Online Banking")
    };
*/
                
    public static void displayProductInList(List<Product> list) {
        if (list == null) {
            display(null);
            return;
        }
        
        if (list.isEmpty()) {
            display("No product found");
            return;
        }
        
        LocalData.setPreviousList(list);

        ProductListPage.display(list, 1, null);
    }
    
    public static void display(String message) {
        List<String> options = new ArrayList<>();
       
        Utility.clearConsole();
        
        if (message != null && !message.isEmpty()) {
            System.err.println(message + ". Jump to main page...");
        }
        
        System.out.println("\nWelcome to TechRetailPro");
        System.out.println("\nPlease select an option");
        System.out.println("Type {exit} at anytime to go back");
        
        options.add("Register");
        options.add("Login");
        options.add("Browse products via category");
        options.add("View all the products");
        options.add("Search something");
        
        if (LoginPage.getCurrentUser().isAdmin()) {
            options.add("Check all low stock");
            options.add("Create new product");
            options.add("View order history");
            options.add("Exit program");
        }
        
        else if (LoginPage.getCurrentUser().isCustomer()) {
            options.add("View cart");
            options.add("Remove item from cart");
            options.add("Checkout");
            options.add("Exit program");
        }
        
        else {
            options.add("Exit program");
        }
        
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }

        while(true) {
            Integer input = Utility.numberOptionChooser(1, options.size());
            
            if (input == null) {
                display(null);
            }
            
            String selectedOption = options.get(input - 1);
            
            switch (selectedOption) {               
                case "Register" -> LoginPage.register(Utility.SCANNER);
                    
                case "Login" -> LoginPage.login(Utility.SCANNER);
                
                case "Browse products via category" -> displayProductInList(ProductCategoryHelper.getListByCategory());

                case "View all the products" -> displayProductInList(LocalData.getCurrentProductsAvailable());

                case "Search something" -> {
                    System.out.println("\nEnter a search query");
                    String validQuery = InputValidator.getString();
                    if (validQuery == null) {
                        display(null);
                    }
                    
                    ProductListHelper.searchProduct(LocalData.getCurrentProductsAvailable(), validQuery);
                }

                case "Check all low stock" -> {
                    if (!LoginPage.getCurrentUser().isAdmin()) {
                        System.err.println("\nInvalid input");
                        continue;
                    }
                        
                    displayProductInList(ProductListHelper.getLowStockList());
                }
         
                case "Create new product" -> {  
                    if (!LoginPage.getCurrentUser().isAdmin()) {
                        System.err.println("\nInvalid input");
                        continue;
                    }
                        
                    Boolean action = ProductManager.createProductUI();
                    
                    if (action == null) {
                        display(null);
                    }
                    
                    else if (action) {
                        display("Product created");
                    }
                    
                    else {
                        display("Product was not created");
                    }
                }
                
                case "View order history" -> {
                    if (!LoginPage.getCurrentUser().isAdmin()) {
                        System.err.println("\nInvalid input");
                        continue;
                    }
                        
                    OrderManager.viewOrderHistory();
                }
                
                case "View cart" -> {
                    if (!LoginPage.getCurrentUser().isCustomer()) {
                        System.err.println("\nInvalid input");
                        continue;
                    }
                        
                    CartManager.viewCart();
                }
                
                case "Remove item from cart" -> {
                    if (!LoginPage.getCurrentUser().isCustomer()) {
                        System.err.println("\nInvalid input");
                        continue;
                    }
                        
                    CartManager.removeItemFromCart();
                }
                
                case "Checkout" -> {
                    if (!LoginPage.getCurrentUser().isCustomer()) {
                        System.err.println("\nInvalid input");
                        continue;
                    }
                        
//                    OrderManager.checkoutAndPay(orderHistory, payments, trans);
                }
                
                case "Exit program" -> {
                    System.out.println("\nBye! Thank you for choosing TechRetailPro!");
                    
                    DatabaseManager.rewriteAllProducts(LocalData.getCurrentProductsAvailable());
                    
                    System.exit(0);
                }
                
                default -> System.err.println("\nInvalid input");
            }
        }
    }
}