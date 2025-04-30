package techretailpro.main;

import techretailpro.functions.ProductDatabaseManager;
import techretailpro.functions.OrderFunction;
import techretailpro.objects.LocalData;
import techretailpro.pages.MainPage;

public class TechRetailPro {
    public static void main(String[] args) {
        ProductDatabaseManager.initializeDatabaseFile();
        OrderFunction.initCsv();

        LocalData.setCurrentProductsAvailable(ProductDatabaseManager.fetchAllProducts());
         
        MainPage.display(null);
    }    
}