package techretailpro.pages;

import java.util.ArrayList;
import java.util.List;
import techretailpro.functions.CartFunction;
import techretailpro.functions.InputValidator;
import techretailpro.functions.ProductManager;
import techretailpro.objects.LocalData;
import techretailpro.objects.Product;
import techretailpro.functions.Utility;

public class ProductDetailsPage {
    public static void display(Product product, String message) {
        if (product == null) {
            ProductListPage.display(LocalData.getPreviousList(), 1, "No product found");
            return;
        }
        
        List<String> options = new ArrayList<>();
        Integer input;
        
        Utility.clearConsole();
        
        if (message != null && !message.isEmpty()) {
            System.err.println(message + ". Jump to product details page...");
        }
        
        System.out.println("\n" + product.toString());
        
        System.out.println("\nPlease select an option");
        System.out.println("Type {exit} at anytime to go back");
                
        
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
            input = InputValidator.numberOptionChooser(1, options.size());
            
            if (input == null) {
                ProductListPage.display(LocalData.getPreviousList(), 1, null);
            }
            
            String selectedOption = options.get(input - 1);
            
            switch (selectedOption) {
                case "Restock" -> {
                    Boolean action = ProductManager.updateStockUI(product);
                    
                    if (action == null) {
                        display(product, null);
                    }
                    
                    else if (action) {
                        display(product, "Stock updated");
                    }
                    
                    else {
                        display(product, "Stock was not updated");
                    }
                }

                case "Update product" -> {
                    Boolean action = ProductManager.updateProductUI(product.getCategory(), product);
                    
                    if (action == null) {
                        display(product, null);
                    }
                    
                    else if (action) {
                        ProductListPage.display(LocalData.getPreviousList(), 1, "Product updated");
                    }
                    
                    else {
                        display(product, "Product was not updated");
                    } 
                }

                case "Delete product" -> {
                    System.err.println("\nAre you sure?");
                    System.err.println("1. Yes");
                    System.err.println("2. No");
                    
                    input = InputValidator.numberOptionChooser(1, 2);
                    
                    if (input == null) {
                        display(product, null);
                        return;
                    }
                    
                    switch (input) {
                        case 1 -> {
                            Boolean action = ProductManager.deleteProduct(product.getName());
                    
                            if (action == null) {
                                display(product, null);
                            }

                            else if (action) {
                                ProductListPage.display(LocalData.getPreviousList(), 1,  "Product deleted");
                            }

                            else {
                                display(product, "Product was not deleted");
                            }   
                        }

                        case 2 -> display(product, null);

                        default -> System.err.println("\nInvalid input");
                    }    
                }
                
                case "Add to cart" -> {
                    CartFunction.addItemToCart(product);
                    
                    ProductListPage.display(LocalData.getPreviousList(), 1,  "Item added to cart");
                }
                
                case "Nothing you can do here. Go back" -> ProductListPage.display(LocalData.getPreviousList(), 1, null);

                default -> System.err.println("\nInvalid input");
            }
        }
    }
}