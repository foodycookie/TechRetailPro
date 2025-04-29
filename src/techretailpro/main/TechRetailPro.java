package techretailpro.main;

import techretailpro.functions.DatabaseManager;
import techretailpro.functions.OrderManager;
import techretailpro.objects.LocalData;
import techretailpro.pages.MainPage;
import techretailpro.utilities.Utility;

public class TechRetailPro {
    public static void main(String[] args) {
//        Utility.我这边打中文会怎样这是生成测试数据的();

        DatabaseManager.initializeDatabaseFile();
        OrderManager.initCsv();

        LocalData.setCurrentProductsAvailable(DatabaseManager.fetchAllProducts());
         
        MainPage.display(null);
    }    
}