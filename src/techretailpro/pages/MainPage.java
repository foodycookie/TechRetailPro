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
import static techretailpro.pages.LoginPage.login;
import static techretailpro.pages.LoginPage.register;
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
        System.out.println("D. Exit program");
        //Admin functions
        System.out.println("AA. Check all low stock");
        System.out.println("AB. Create new product");
        

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
                    
                
                case "d" -> {
                    System.out.println("\nThank you for choosing TechRetailPro!");
                    
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

    public static void lyhMainPage() {
        LoginPage.loadUsers();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Login System ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> register(sc);
                case "2" -> login(sc);
                case "3" -> {
                    System.out.println("Bye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}