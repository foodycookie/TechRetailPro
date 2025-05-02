package techretailpro.functions;

import techretailpro.objects.LocalData;
import techretailpro.objects.Product;

public class CartManager {
    public static void viewCart() {
        if (LocalData.getCurrentUserCart().isEmpty()) {
            UtilityHelper.displayReturnMessage("Cart is empty");
        } 
        
        else {
            System.out.println("\n" + LocalData.getCurrentUserCart().toString());
            UtilityHelper.displayReturnMessage("");
        }
    }
    
    public static void addItemToCart(Product product) {
        int validQuantity;
        while (true) {            
            String rawValidQuantity = UtilityHelper.getUserInput("Enter quantity", "int", false);

            switch (rawValidQuantity) {
                case UtilityHelper.BACK_CONSTANT -> {
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
        
        ProductManager.updateStock(product, -validQuantity);

//        for (Product p : LocalData.getCurrentProductsAvailable()) {
//            if (p.getName().equalsIgnoreCase(product.getName())) {
//                p.setStock(p.getStock() - validQuantity);
//                break;
//            }
//        }

        UtilityHelper.displayReturnMessage("Item added to cart");
    }
       
    public static void removeItemFromCart() {
        if (LocalData.getCurrentUserCart().isEmpty()) {
            UtilityHelper.displayReturnMessage("Cart is empty");
            return;
        }

        System.out.println("\n" + LocalData.getCurrentUserCart().toString());
        
        Integer productNumber = UtilityHelper.numberOptionChooser("Select a product you want to remove by number", 1, LocalData.getCurrentUserCart().getItems().size());
        if (productNumber == null) {
            return;
        }

        int validQuantity;
        while (true) {            
            String rawValidQuantity = UtilityHelper.getUserInput("Enter quantity", "int", false);

            switch (rawValidQuantity) {
                case UtilityHelper.BACK_CONSTANT -> {
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
        
        productToRemove.setStock(productToRemove.getStock() + validQuantity);
        
//        for (Product p : LocalData.getCurrentProductsAvailable()) {
//            if (p.getName().equalsIgnoreCase(removedProductName)) {
//                p.setStock(p.getStock() + validQuantity);
//                break;
//            }
//        }

        UtilityHelper.displayReturnMessage("Item removed from cart");
    }
}