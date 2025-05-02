package techretailpro.pages;

import java.util.ArrayList;
import java.util.List;
import techretailpro.functions.CartManager;
import techretailpro.functions.ProductManager;
import techretailpro.objects.LocalData;
import techretailpro.objects.Product;
import techretailpro.functions.UtilityHelper;

public class ProductDetailsPage {
    public static void display(Product product) {
        if (product == null) {
            UtilityHelper.displayReturnMessage("No product found");
            ProductListPage.display(LocalData.getPreviousList(), 1);
            return;
        }
        
        List<String> options = new ArrayList<>();
        Integer input;
        
        UtilityHelper.clearConsole();
        
        System.out.println("\n" + product.toString());
        
        System.out.println("\nPlease select an option");
        System.out.println("Type {back} at anytime to go back");
                
        
        if (LoginPage.getCurrentUser().isAdmin()) {
            options.add("Restock");
            options.add("Update product");
            options.add("Delete product");
        }
        
        else if (LoginPage.getCurrentUser().isCustomer()) {
            if (product.isAvailable()) {
                options.add("Add to cart");
            } 
        }
        
        else {
        }
        
        if (options.isEmpty()) {
            options.add("Nothing you can do here. Go back");
        }

        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        
        while(true) {
            input = UtilityHelper.numberOptionChooser("Option", 1, options.size());
            if (input == null) {
                ProductListPage.display(LocalData.getPreviousList(), 1);
            }
            
            String selectedOption = options.get(input - 1);
            
            switch (selectedOption) {
                case "Restock" -> {
                    ProductManager.updateStockUI(product);
                    ProductManager.updateStockToCsv(product);
                    display(product);
                }

                case "Update product" -> {
                    ProductManager.updateProductUI(product);
                    display(product);
                }

                case "Delete product" -> {
                    System.err.println("\nAre you sure?");
                    System.err.println("1. Yes");
                    System.err.println("2. No");
                    
                    input = UtilityHelper.numberOptionChooser("Option", 1, 2);
                    if (input == null) {
                        display(product);
                        return;
                    }
                    
                    switch (input) {
                        case 1 -> {
                            ProductManager.updateProductUI(product);
                            ProductListPage.display(LocalData.getPreviousList(), 1); 
                        }

                        case 2 -> display(product);

                        default -> System.err.println("\nInvalid input");
                    }    
                }
                
                case "Add to cart" -> {
                    CartManager.addItemToCart(product);
                    ProductListPage.display(LocalData.getPreviousList(), 1);
                }
                
                case "Nothing you can do here. Go back" -> ProductListPage.display(LocalData.getPreviousList(), 1);

                default -> System.err.println("\nInvalid input");
            }
        }
    }
}