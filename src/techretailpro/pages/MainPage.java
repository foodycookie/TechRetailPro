package techretailpro.pages;

import java.util.ArrayList;
import java.util.List;
import techretailpro.functions.ProductCategoryHelper;
import techretailpro.functions.DatabaseManager;
import techretailpro.functions.InputValidator;
import techretailpro.functions.ProductManager;
import techretailpro.functions.ProductListHelper;
import techretailpro.objects.LocalData;
import techretailpro.objects.Product;
import techretailpro.utilities.Utility;

public class MainPage {
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
        
        options.add("Browse products via category");
        options.add("View all the products");
        options.add("Search something");
        options.add("Register");
        options.add("Login");
        
        if (LoginPage.getCurrentUser().isAdmin()) {
            options.add("Check all low stock");
            options.add("Create new product");
            options.add("Exit program");
        }
        
        else if (LoginPage.getCurrentUser().isCustomer()) {
            options.add("Exit program");
        }
        
        else {
            options.add("Exit program");
        }
        
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }

        while(true) {
            System.out.print("Option > ");
            
            Integer input = Utility.numberOptionChooser(1, options.size());
            
            if (input == null) {
                display(null);
            }
            
            String selectedOption = options.get(input - 1);
            
            switch (selectedOption) {                
                case "Browse products via category" -> displayProductInList(ProductCategoryHelper.getListByCategory());

                case "View all the products" -> displayProductInList(LocalData.getProducts());

                case "Search something" -> {
                    System.out.println("\nEnter a search query");
                    String validQuery = InputValidator.getString();
                    if (validQuery == null) {
                        display(null);
                    }
                    
                    ProductListHelper.searchProduct(LocalData.getProducts(), validQuery);
                }
                
                case "Register" -> LoginPage.register(Utility.SCANNER);
                    
                case "Login" -> LoginPage.login(Utility.SCANNER);
                
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
                
                case "Exit program" -> {
                    System.out.println("\nBye! Thank you for choosing TechRetailPro!");
                    
                    DatabaseManager.rewriteAllProducts(LocalData.getProducts());
                    
                    Utility.closeScanner();
                    
                    System.exit(0);
                }
                
                default -> System.err.println("\nInvalid input");
            }
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        System.out.println("A. Browse products via category");
        System.out.println("B. View all the products");
        System.out.println("C. Search something");
        System.out.println("------------------------------");
        System.out.println("D. Register");
        System.out.println("E. Login");
        System.out.println("------------------------------");
        System.out.println("F. Exit program");
        if (LoginPage.getCurrentUser().isAdmin()) {
            System.out.println("------------------------------");
            System.out.println("AA. Check all low stock");
            System.out.println("AB. Create new product");
        }
        
        while(true) {
            System.out.print("Option > ");
            
            String input = Utility.SCANNER.nextLine().trim();
            
            switch (input.toLowerCase()) {
                case "exit" -> display(null);
                
                case "a" -> displayProductInList(ProductCategoryHelper.getListByCategory());

                case "b" -> displayProductInList(LocalData.getProducts());

                case "c" -> {
                    System.out.println("\nEnter a search query");
                    String validQuery = InputValidator.getString();
                    if (validQuery == null) {
                        display(null);
                    }
                    
                    ProductListHelper.searchProduct(LocalData.getProducts(), validQuery);
                }
                
                case "d" -> LoginPage.register(Utility.SCANNER);
                    
                case "e" -> LoginPage.login(Utility.SCANNER);
                
                case "f" -> {
                    System.out.println("\nBye! Thank you for choosing TechRetailPro!");
                    
                    DatabaseManager.rewriteAllProducts(LocalData.getProducts());
                    
                    Utility.closeScanner();
                    
                    System.exit(0);
                }

                /// Admin commands ///
                //Add a condition at else if to detect if user is admin
                
                case "aa" -> displayProductInList(ProductListHelper.getLowStockList());
         
                case "ab" -> {                    
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
                
                default -> System.err.println("\nInvalid input");
            }
        }
    }
}