package techretailpro.objects;

import java.util.List;

public class LocalData {
    private static List<Product> currentProductsAvailable; 
    private static List<Product> previousList; 
    private static User currentUser = new User();
    private static Cart currentUserCart = new Cart(); 

    public static List<Product> getCurrentProductsAvailable() {
        return currentProductsAvailable;
    }

    public static void setCurrentProductsAvailable(List<Product> currentProductsAvailable) {
        LocalData.currentProductsAvailable = currentProductsAvailable;
    }

    public static List<Product> getPreviousList() {
        return previousList;
    }

    public static void setPreviousList(List<Product> previousList) {
        LocalData.previousList = previousList;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        LocalData.currentUser = currentUser;
    }
    
    public static Cart getCurrentUserCart() {
        return currentUserCart;
    }

    public static void setCurrentUserCart(Cart currentUserCart) {
        LocalData.currentUserCart = currentUserCart;
    }
}