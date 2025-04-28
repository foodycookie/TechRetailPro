package techretailpro.pages;

import java.util.List;
import java.util.Scanner;
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
        Utility.clearConsole();
        
        if (message != null && !message.isEmpty()) {
            System.err.println(message + ". Jump to main page...");
        }
        
        System.out.println("\nWelcome to TechRetailPro");
        System.out.println("\nPlease select an option by alphabet(s)");
        System.out.println("Type {exit} at anytime to go back");
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
                
                case "aa" -> {
                    if (!LoginPage.getCurrentUser().isAdmin()) {
                        System.err.println("\nInvalid input");
                        continue;
                    }
                    
                    displayProductInList(ProductListHelper.getLowStockList());
                }
                    
                
                case "ab" -> {
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
                
                default -> System.err.println("\nInvalid input");
            }
        }
    }
}