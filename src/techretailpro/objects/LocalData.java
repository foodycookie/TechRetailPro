package techretailpro.objects;

import java.util.List;

public class LocalData {
    private static List<Product> products; 
    private static List<Product> previousList; 

    public static List<Product> getProducts() {
        return products;
    }

    public static void setProducts(List<Product> products) {
        LocalData.products = products;
    }

    public static List<Product> getPreviousList() {
        return previousList;
    }

    public static void setPreviousList(List<Product> previousList) {
        LocalData.previousList = previousList;
    }
}