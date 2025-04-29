package techretailpro.functions;

import techretailpro.objects.LocalData;
import techretailpro.objects.Product;
import techretailpro.utilities.Utility;

public class CartManager {
    public static void viewCart() {
        if (LocalData.getCurrentUserCart().isEmpty()) {
            System.out.println("\nCart is empty");
        } 
        
        else {
            System.out.println(LocalData.getCurrentUserCart().toString());
        }
    }
    
    public static void addItemToCart(Product product) {
        Integer validQuantity;
        
        System.out.println("\nEnter quantity");
        
        while (true) {            
            validQuantity = InputValidator.getOneToOneHundredInt();
            if (validQuantity == null) {
                return;
            }
            
            if (validQuantity > product.getStock()) {
                System.err.println("Current stock is not enough");
                continue;
            }
            
            break;
        }
        
        LocalData.getCurrentUserCart().addItem(product, validQuantity);
    }
    
    public static void removeItemFromCart() {
        if (LocalData.getCurrentUserCart().isEmpty()) {
            System.out.println("\nCart is empty");
            return;
        }
        
        Integer productNumber;
        Integer validQuantity;
        
        System.out.println(LocalData.getCurrentUserCart().toString());
        
        System.out.print("\nSelect a product by number");
        
        productNumber = Utility.numberOptionChooser(1, LocalData.getCurrentUserCart().getItems().size());
            
        if (productNumber == null) {
            return;
        }

        System.out.println("\nEnter quantity to be removed");

        while (true) {            
            validQuantity = InputValidator.getOneToOneHundredInt();
            if (validQuantity == null) {
                return;
            }

            if (validQuantity > LocalData.getCurrentUserCart().getItems().get(productNumber - 1).getQuantity()) {
                System.err.println("Invalid quantity input");
                continue;
            }

            break;
        }

        LocalData.getCurrentUserCart().removeItem(LocalData.getCurrentUserCart().getItems().get(productNumber - 1).getProduct().getName(), validQuantity);
        System.out.println("\nItem removed");
    }
}