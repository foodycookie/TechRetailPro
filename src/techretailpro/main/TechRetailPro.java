package techretailpro.main;

import techretailpro.functions.InputValidator;
import techretailpro.functions.ProductDatabaseManager;
import techretailpro.functions.OrderFunction;
import techretailpro.functions.Utility;
import techretailpro.objects.LocalData;
import techretailpro.pages.MainPage;

public class TechRetailPro {
    public static void main(String[] args) {
//        ProductDatabaseManager.initializeDatabaseFile();
//        OrderFunction.initCsv();
//
//        LocalData.setCurrentProductsAvailable(ProductDatabaseManager.fetchAllProducts());
//         
//        MainPage.display(null);

        double validPrice = 0;
        do {            
            String rawValidPrice = InputValidator.getUserInput("Enter new price (Leave blank to keep current)", "double", true);
        
            switch (rawValidPrice) {
                case "BLANK" -> validPrice = product.getPrice();

                case "EXIT" -> {
                    System.out.println("Cancelling product update (Type enter to exit)");
                    Utility.SCANNER.nextLine();
                    MainPage.display();
                }

                default -> validPrice = Double.parseDouble(rawValidPrice);
            }
        } while (!InputValidator.validatePositiveOrZeroDouble(validPrice)); 
        
        

        System.out.println("please choose one, type {exit} to go back");
        System.out.println("1. Display yes");
        System.out.println("2. Display no");
        
        Integer input = Utility.numberOptionChooser(1, 2);
        
        if (input == null) {
            System.out.println("u exit");
            System.exit(0);
        }
        
        
        switch (input) {
            case 1 -> System.out.println("Yes");
            
            case 2 -> System.out.println("No");
            
            default -> System.out.println("input error");
        }
    }
}