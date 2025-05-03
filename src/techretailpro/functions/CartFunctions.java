package techretailpro.functions;

import techretailpro.objects.LocalData;
import techretailpro.objects.Product;

public class CartFunctions {
    public static void viewCart() {
        if (LocalData.getCurrentUserCart().isEmpty()) {
            Utility.displayReturnMessage("Cart is empty");
        } 
        
        else {
            System.out.println("\n" + LocalData.getCurrentUserCart().toString());
            Utility.displayReturnMessage("");
        }
    }
    
    public static void addItemToCart(Product product) {
        int validQuantity;
        while (true) {            
            String rawValidQuantity = Utility.getUserInput("Enter quantity", "int", false);

            switch (rawValidQuantity) {
                case Utility.BACK_CONSTANT -> {
                    return;
                }

                default -> {
                    validQuantity = Integer.parseInt(rawValidQuantity);
                    
                    if (validQuantity <= 0) {
                        System.err.println("Invalid input. Input must be greater than 0");
                        continue;
                    }
                    
                    if (validQuantity > product.getStock()) {
                        System.err.println("Current stock is not enough");
                        continue;
                    }
                }
            }
            
            break;
        }
 
        LocalData.getCurrentUserCart().addItem(product, validQuantity);
        
        ProductFunctions.updateStock(product, -validQuantity);

        System.out.println("\nItem added");
        
        viewCart();
    }
       
    public static void removeItemFromCart() {
        if (LocalData.getCurrentUserCart().isEmpty()) {
            Utility.displayReturnMessage("Cart is empty");
            return;
        }

        System.out.println("\n" + LocalData.getCurrentUserCart().toString());
        
        Integer productNumber = Utility.numberOptionChooser("Select a product you want to remove by number", 1, LocalData.getCurrentUserCart().getItems().size());
        if (productNumber == null) {
            return;
        }

        int validQuantity;
        while (true) {            
            String rawValidQuantity = Utility.getUserInput("Enter quantity", "int", false);

            switch (rawValidQuantity) {
                case Utility.BACK_CONSTANT -> {
                    return;
                }

                default -> {
                    validQuantity = Integer.parseInt(rawValidQuantity);
                    
                    if (validQuantity <= 0) {
                        System.err.println("Invalid input. Input must be greater than 0");
                        continue;
                    }
                    
                    if (validQuantity > LocalData.getCurrentUserCart().getItems().get(productNumber - 1).getQuantity()) {
                        System.err.println("Invalid input. Input must not greater than the actual amount");
                        continue;
                    }
                }
            }
            
            break;
        }
        
        Product productToRemove = LocalData.getCurrentUserCart().getItems().get(productNumber - 1).getProduct();

        LocalData.getCurrentUserCart().removeItem(productToRemove.getName(), validQuantity);
        
        ProductFunctions.updateStock(productToRemove, validQuantity);

        System.out.println("\nItem deleted");
        
        viewCart();
    }
}