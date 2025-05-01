package techretailpro.pages;

import java.util.ArrayList;
import java.util.List;
import techretailpro.functions.CartFunction;
import techretailpro.functions.ProductCategoryHelper;
import techretailpro.functions.ProductDatabaseManager;
import techretailpro.functions.InputValidator;
import techretailpro.functions.OrderFunction;
import techretailpro.functions.ProductManager;
import techretailpro.functions.ProductListHelper;
import techretailpro.objects.CartOrder;
import techretailpro.objects.Customer;
import techretailpro.objects.LocalData;
import techretailpro.objects.Payment;
import techretailpro.objects.Product;
import techretailpro.objects.Transaction;
import techretailpro.functions.Utility;

public class MainPage {
    static List<CartOrder> orderHistory = new ArrayList<>();
    static Transaction trans = new Transaction();
    static Payment[] payments = {
        new Payment("Touch N Go"),
        new Payment("Credit / Debit"),
        new Payment("Online Banking")
    };
                
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
        
        if (LocalData.getCurrentUser().isCustomer()) {
            System.out.println("\nWelcome to TechRetailPro, " + LocalData.getCurrentUser().getUsername() + "!");
        }
        
        else if (LocalData.getCurrentUser().isAdmin()) {
            System.out.println("\nWelcome to TechRetailPro, Admin " + LocalData.getCurrentUser().getUsername() + "!");
        }
        
        else {
            System.out.println("\nWelcome to TechRetailPro!");
        }
        
        System.out.println("\nPlease select an option");
        System.out.println("Type {exit} at anytime to go back");
                
        options.add("Browse products via category");
        options.add("View all the products");
        options.add("Search something");
        
        options.add("Register");
        options.add("Login");
        
        if (LoginPage.getCurrentUser().isAdmin()) {
            options.remove("Register");
            options.remove("Login");
            
            options.add("Check all low stock");
            options.add("Create new product");
            options.add("View order history");
            
            options.add("Logout");
        }
        
        else if (LoginPage.getCurrentUser().isCustomer()) {
            options.remove("Register");
            options.remove("Login");
            
            options.add("View cart");
            options.add("Remove item from cart");
            options.add("Checkout");
            
            options.add("View profile");
            options.add("Logout");
        }
        
        else {
        }
        
        options.add("Exit program");
        
        if (options.isEmpty()) {
            options.add("Nothing you can do here. Go back");
        }
        
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        
        while(true) {
            Integer input = InputValidator.numberOptionChooser(1, options.size());
            
            if (input == null) {
                display(null);
            }
            
            String selectedOption = options.get(input - 1);
            
            switch (selectedOption) {               
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

                case "Check all low stock" -> displayProductInList(ProductListHelper.getLowStockList());
         
                case "Create new product" -> {    
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
                
                case "View order history" -> OrderFunction.viewOrderHistory();
                
                case "View cart" -> CartFunction.viewCart();
                
                case "Remove item from cart" -> {
                    CartFunction.removeItemFromCart();
                    
                    MainPage.display("Item removed from cart");
                }
                
                case "Checkout" -> {
                    OrderFunction.checkoutAndPay(orderHistory, payments, trans);
                    
                    MainPage.display(null);
                }
                
                case "Register" -> LoginPage.register(Utility.SCANNER);
                    
                case "Login" -> LoginPage.login(Utility.SCANNER);
                
                case "Logout" -> LoginPage.logout();
                
                case "View profile" -> Customer.viewProfile(Utility.SCANNER);
                
                case "Exit program" -> {
                    System.out.println("\nBye! Thank you for choosing TechRetailPro!");
                    
                    ProductDatabaseManager.rewriteAllProducts(LocalData.getCurrentProductsAvailable());
                    
                    System.exit(0);
                }
                
                case "Nothing you can do here. Go back" -> display(null);
                
                default -> System.err.println("\nInvalid input");
            }
        }
    }
}