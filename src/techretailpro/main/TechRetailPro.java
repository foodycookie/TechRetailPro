package techretailpro.main;

import techretailpro.functions.ProductDatabaseFunctions;
import techretailpro.functions.OrderFunctions;
import techretailpro.functions.UserFunctions;
import techretailpro.pages.MainPage;

public class TechRetailPro {
    public static void main(String[] args) {
        ProductDatabaseFunctions.initializeAllProductCsv();
        OrderFunctions.initCsv();
        UserFunctions.initCsv();
         
        MainPage.display();
    }
}