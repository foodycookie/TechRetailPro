package techretailpro.pages;

import java.util.ArrayList;
import java.util.List;
import techretailpro.functions.CartManager;
import techretailpro.functions.ProductCategoryHelper;
import techretailpro.functions.OrderManager;
import techretailpro.functions.ProductManager;
import techretailpro.functions.ProductListHelper;
import techretailpro.objects.Customer;
import techretailpro.objects.LocalData;
import techretailpro.objects.Product;
import techretailpro.functions.UtilityHelper;

public class MainPage {         
    public static void displayProductInList(List<Product> list) {
        if (list == null) {
            display();
            return;
        }
        
        if (list.isEmpty()) {
            UtilityHelper.displayReturnMessage("No product found");
            display();
            return;
        }
        
        LocalData.setPreviousList(list);

        ProductListPage.display(list, 1);
    }
    
    public static void display() {
        List<String> options = new ArrayList<>();
       
        UtilityHelper.clearConsole();
        
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
            Integer input = UtilityHelper.numberOptionChooser("Option", 1, options.size());
            if (input == null) {
                display();
            }
            
            String selectedOption = options.get(input - 1);
            
            switch (selectedOption) {               
                case "Browse products via category" -> displayProductInList(ProductCategoryHelper.getListByCategory());

                case "View all the products" -> displayProductInList(LocalData.getCurrentProductsAvailable());

                case "Search a product" -> {
                    String validQuery;
                    validQuery = UtilityHelper.getUserInput("Enter a search query", "string", false);
                    if (validQuery.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                        display();
                    }
                    
                    displayProductInList(ProductListHelper.searchProduct(LocalData.getCurrentProductsAvailable(), validQuery));
                }

                case "Check all low stock" -> displayProductInList(ProductListHelper.getLowStockList());
         
                case "Create new product" -> {    
                    ProductManager.createProductUI();
                    display();
                }
                
                case "View order history" -> {
                    OrderManager.viewOrderHistory();
                    display();
                }
                
                case "View cart" -> {
                    CartManager.viewCart();
                    display();
                }
                
                case "Remove item from cart" -> {
                    CartManager.removeItemFromCart();
                    display();
                }
                
                case "Checkout" -> {
                    OrderManager.checkoutAndPay();
                    display();
                }
                
                case "Register" -> {
                    LoginPage.register(UtilityHelper.SCANNER);
                    display();
                }
                    
                case "Login" -> {
                    LoginPage.login(UtilityHelper.SCANNER);
                    display();
                }
                
                case "Logout" -> {
                    LoginPage.logout();
                    display();
                }
                
                case "View profile" -> {
                    Customer.viewProfile(UtilityHelper.SCANNER);
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