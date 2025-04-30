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
            System.out.println("\n" + LocalData.getCurrentUserCart().toString());
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

        for (Product p : LocalData.getCurrentProductsAvailable()) {
            if (p.getName().equalsIgnoreCase(product.getName())) {
                p.setStock(p.getStock() - validQuantity);
                break;
            }
        }
    }
       
    public static void removeItemFromCart() {
        if (LocalData.getCurrentUserCart().isEmpty()) {
            System.out.println("\nCart is empty");
            return;
        }
        
        Integer productNumber;
        Integer validQuantity;
        
        viewCart();
        
        System.out.println("\nSelect a product by number");
        
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
        
        String removedProductName = LocalData.getCurrentUserCart().getItems().get(productNumber - 1).getProduct().getName();

        LocalData.getCurrentUserCart().removeItem(removedProductName, validQuantity);
        
        for (Product p : LocalData.getCurrentProductsAvailable()) {
            if (p.getName().equalsIgnoreCase(removedProductName)) {
                p.setStock(p.getStock() + validQuantity);
                break;
            }
        }
    }
}