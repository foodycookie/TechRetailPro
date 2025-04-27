package techretailpro.main;

import techretailpro.functions.DatabaseManager;
import techretailpro.objects.LocalData;
import techretailpro.pages.MainPage;
import techretailpro.utilities.Utility;

public class TechRetailPro {
    public static void main(String[] args) {
//        Utility.generateDummyProduct();

        DatabaseManager.initializeDatabaseFile();

        LocalData.setProducts(DatabaseManager.fetchAllProducts());
         
        MainPage.display(null);
    }    
}