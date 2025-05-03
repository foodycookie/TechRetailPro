package techretailpro.pages;

import java.util.ArrayList;
import java.util.List;
import techretailpro.functions.CartFunctions;
import techretailpro.functions.ProductCategoryFunctions;
import techretailpro.functions.OrderFunctions;
import techretailpro.functions.ProductFunctions;
import techretailpro.functions.ProductListFunctions;
import techretailpro.functions.UserFunctions;
import techretailpro.objects.LocalData;
import techretailpro.objects.Product;
import techretailpro.functions.Utility;

public class MainPage {         
    public static void displayProductInList(List<Product> list) {
        if (list == null) {
            display();
            return;
        }
        
        if (list.isEmpty()) {
            Utility.displayReturnMessage("No product found");
            display();
            return;
        }
        
        LocalData.setPreviousList(list);

        ProductListPage.display(list, 1);
    }
    
    public static void display() {
        List<String> options = new ArrayList<>();
       
        Utility.clearConsole();
        
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
        System.out.println("Type {back} at anytime to go back");
                
        options.add("Browse products via category");
        options.add("View all the products");
        options.add("Search a product");
        
        options.add("Register");
        options.add("Login");
        
        if (UserFunctions.getCurrentUser().isAdmin()) {
            options.remove("Register");
            options.remove("Login");
            
            options.add("Check all low stock");
            options.add("Create new product");
            options.add("View all order history");
            
            options.add("Close account");
            options.add("Logout");
        }
        
        else if (UserFunctions.getCurrentUser().isCustomer()) {
            options.remove("Register");
            options.remove("Login");
            
            options.add("View cart");
            options.add("Remove item from cart");
            options.add("Checkout");
            options.add("View order history");
            
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
            Integer input = Utility.numberOptionChooser("Option", 1, options.size());
            if (input == null) {
                display();
            }
            
            String selectedOption = options.get(input - 1);
            
            switch (selectedOption) {               
                case "Browse products via category" -> displayProductInList(ProductCategoryFunctions.getListByCategory());

                case "View all the products" -> displayProductInList(LocalData.getCurrentProductsAvailable());

                case "Search a product" -> {
                    String validQuery;
                    validQuery = Utility.getUserInput("Enter a search query", "string", false);
                    if (validQuery.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                        display();
                    }
                    
                    displayProductInList(ProductListFunctions.searchProduct(LocalData.getCurrentProductsAvailable(), validQuery));
                }

                case "Check all low stock" -> displayProductInList(ProductListFunctions.getLowStockList());
         
                case "Create new product" -> {    
                    ProductFunctions.createProductUI();
                    display();
                }
                
                case "View all order history" -> {
                    OrderFunctions.viewOrderHistory();
                    display();
                }
                
                case "View order history" -> {
                    OrderFunctions.viewCurrentUserOrderHistory();
                    display();
                }
                
                case "View cart" -> {
                    CartFunctions.viewCart();
                    display();
                }
                
                case "Remove item from cart" -> {
                    CartFunctions.removeItemFromCart();
                    display();
                }
                
                case "Checkout" -> {
                    OrderFunctions.checkoutAndPay();
                    display();
                }
                
                case "Register" -> {
                    UserFunctions.register(Utility.SCANNER);
                    display();
                }
                    
                case "Login" -> {
                    UserFunctions.login(Utility.SCANNER);
                    display();
                }
                
                case "Logout" -> {
                    UserFunctions.logout();
                    display();
                }
                
                case "View profile" -> {
                    UserFunctions.viewProfile(Utility.SCANNER);
                    display();
                }
                
                case "Close account" -> {
                    UserFunctions.closeAdminAccount(Utility.SCANNER);
                    display();
                }
                
                case "Exit program" -> {
                    System.out.println("\nBye! Thank you for choosing TechRetailPro!");                    
                    System.exit(0);
                }
                
                case "Nothing you can do here. Go back" -> display();
                
                default -> System.err.println("\nInvalid input");
            }
        }
    }
}