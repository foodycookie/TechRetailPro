package techretailpro.main;

import techretailpro.functions.ProductDatabaseManager;
import techretailpro.functions.OrderManager;
import techretailpro.pages.LoginPage;
import techretailpro.pages.MainPage;

public class TechRetailPro {
    public static void main(String[] args) {
        ProductDatabaseManager.initializeAllProductCsv();
        OrderManager.initCsv();
        LoginPage.initCsv();
         
        MainPage.display();
    }
}