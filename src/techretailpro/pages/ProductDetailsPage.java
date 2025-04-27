package techretailpro.pages;

import techretailpro.functions.ProductManager;
import techretailpro.objects.LocalData;
import techretailpro.objects.Product;
import techretailpro.utilities.Utility;

public class ProductDetailsPage {
    public static void display(Product product, String message) {
        if (product == null) {
            ProductListPage.display(LocalData.getPreviousList(), 1, "No product found");
            return;
        }
        
        String input;
        Integer numberInput;
        
        Utility.clearConsole();
        
        if (message != null && !message.isEmpty()) {
            System.err.println(message + ". Jump to product details page...");
        }
        
        System.out.println("\n" + product.toString());
        
        System.out.println("\nPlease select an option by alphabet(s)");
        System.out.println("Type {exit} at anytime to go back");
        //Admin functions   
        System.out.println("AA. Restock");
        System.out.println("AB. Update product");
        System.out.println("AC. Delete product");
        
        while(true) {
            System.out.print("Option > ");
            
            input = Utility.SCANNER.nextLine().trim();
            
            switch (input.toLowerCase()) {
                case "exit" -> ProductListPage.display(LocalData.getPreviousList(), 1, null);
                
                /// Admin commands ///
                //Add a condition at else if to detect if user is admin     
                
                case "aa" -> {
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

                case "ab" -> {
                    Boolean action = ProductManager.updateProductUI(product.getCategory(), product.getName());
                    
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

                case "ac" -> {
                    System.err.println("\nAre you sure?");
                    System.err.println("1. Yes");
                    System.err.println("2. No");
                    
                    numberInput = Utility.numberOptionChooser(1, 2);
                    
                    if (numberInput == null) {
                        display(product, null);
                        return;
                    }
                    
                    switch (numberInput) {
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

                default -> System.err.println("\nInvalid input");
            }
        }
    }
}