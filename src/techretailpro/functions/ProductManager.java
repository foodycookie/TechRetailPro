package techretailpro.functions;

import java.util.ArrayList;
import java.util.List;
import techretailpro.objects.Headphone;
import techretailpro.objects.Keyboard;
import techretailpro.objects.Laptop;
import techretailpro.objects.LocalData;
import techretailpro.objects.Mouse;
import techretailpro.objects.Printer;
import techretailpro.objects.Product;

public class ProductManager {
    //Can use UI or directly call, ex: customer bought something, call updateStock()
    
    public static Boolean createProductUI() {
        String category = ProductCategoryHelper.chooseCategory(ProductCategoryHelper.getExistingCategory());
        
        if (category == null) {
            return null;
        }
        
        System.out.println("\nEnter name");
        String validName = InputValidator.getValidProductName();
        if (validName == null) {
            return null;
        }
        
        System.out.println("\nEnter price");
        Double validPrice = InputValidator.getPositiveOrZeroDouble();
        if (validPrice == null) {
            return null;
        }
        
        System.out.println("\nEnter stock");
        Integer validStock = InputValidator.getPositiveOrZeroInt();
        if (validStock == null) {
            return null;
        }
        
        System.out.println("\nEnter description");
        String validDescription = InputValidator.getNoCommaString();
        if (validDescription == null) {
            return null;
        }

        switch (category.toLowerCase()) {
            case "keyboard" -> {
                System.out.println("\nIs the keyboard wireless? (yes/no)");
                Boolean validWireless = InputValidator.getBoolean();
                if (validWireless == null) {
                    return null;
                }
                
                System.out.println("\nEnter key type");
                String validKeyType = InputValidator.getNoCommaString();
                if (validKeyType == null) {
                    return null;
                }
                
                System.out.println("\nDoes the keyboard has RGB? (yes/no)");
                Boolean validRgb = InputValidator.getBoolean();
                if (validRgb == null) {
                    return null;
                }
                
                return createProduct(new Keyboard(validName, validPrice, validStock, validDescription, validWireless, validKeyType, validRgb));
            }
            
            case "mouse" -> {
                System.out.println("\nIs the mouse wireless? (yes/no)");
                Boolean validWireless = InputValidator.getBoolean();
                if (validWireless == null) {
                    return null;
                }
                
                System.out.println("\nEnter sensor type");
                String validSensorType = InputValidator.getNoCommaString();
                if (validSensorType == null) {
                    return null;
                }
                
                System.out.println("\nEnter DPI");
                Integer validDpi = InputValidator.getPositiveOrZeroInt();
                if (validDpi == null) {
                    return null;
                }
                
                return createProduct(new Mouse(validName, validPrice, validStock, validDescription, validWireless, validSensorType, validDpi));
            }
            
            case "laptop" -> {
                System.out.println("\nEnter processor");
                String validProcessor = InputValidator.getNoCommaString();
                if (validProcessor == null) {
                    return null;
                }
                
                System.out.println("\nEnter memory (GB)");
                Integer validMemory = InputValidator.getPositiveOrZeroInt();
                if (validMemory == null) {
                    return null;
                }
                
                System.out.println("\nEnter storage (GB)");
                Integer validStorage = InputValidator.getPositiveOrZeroInt();
                if (validStorage == null) {
                    return null;
                }
                
                return createProduct(new Laptop(validName, validPrice, validStock, validDescription, validProcessor, validMemory, validStorage));
            }

            case "headphone" -> {
                System.out.println("\nIs the headphone wireless? (yes/no)");
                Boolean validWireless = InputValidator.getBoolean();
                if (validWireless == null) {
                    return null;
                }
                
                System.out.println("\nDoes the headphone has noise cancellation? (yes/no)");
                Boolean validNoiseCancellation = InputValidator.getBoolean();
                if (validNoiseCancellation == null) {
                    return null;
                }
                
                System.out.println("\nDoes the headphone has microphone? (yes/no)");
                Boolean validMicrophone = InputValidator.getBoolean();
                if (validMicrophone == null) {
                    return null;
                }
                
                return createProduct(new Headphone(validName, validPrice, validStock, validDescription, validWireless, validNoiseCancellation, validMicrophone));
            }
            
            case "printer" -> {
                System.out.println("\nIs the printer wireless? (yes/no)");
                Boolean validWireless = InputValidator.getBoolean();
                if (validWireless == null) {
                    return null;
                }
                
                System.out.println("\nEnter print speed (PPM)");
                Double validPrintSpeed = InputValidator.getPositiveOrZeroDouble();
                if (validPrintSpeed == null) {
                    return null;
                }
                
                System.out.println("\nEnter ink type");
                String validInkType = InputValidator.getNoCommaString();
                if (validInkType == null) {
                    return null;
                }
                
                return createProduct(new Printer(validName, validPrice, validStock, validDescription, validWireless, validPrintSpeed, validInkType));     
            }
                    
            default -> System.err.println("\nInvalid category");
        }
        
        return false;
    }
    
    public static Boolean createProduct(Product product) {
        List<Product> list = LocalData.getProducts();
        List<Product> tempList = new ArrayList<>(LocalData.getProducts());
        
        tempList.add(product);

        if (!list.equals(tempList)) {
            LocalData.setProducts(tempList);

            switch (product.getCategory().toLowerCase()) {
                case "keyboard" -> DatabaseManager.rewriteKeyboards(ProductListHelper.getKeyboardList());

                case "mouse" -> DatabaseManager.rewriteMice(ProductListHelper.getMouseList());
 
                case "laptop" -> DatabaseManager.rewriteLaptops(ProductListHelper.getLaptopList());

                case "headphone" -> DatabaseManager.rewriteHeadphones(ProductListHelper.getHeadphoneList());

                case "printer" -> DatabaseManager.rewritePrinters(ProductListHelper.getPrinterList());

                default -> {
                    System.err.println("\nInvalid category");
                    return false;
                }
            }
            
            return true;
        }
        
        return false;
    }
    
    public static Boolean updateProductUI(String category, String oldName) {
        System.out.println("\nEnter new name");
        String validName = InputValidator.getValidProductNameForUpdate(oldName);
        if (validName == null) {
            return null;
        }
        
        System.out.println("\nEnter new price");
        Double validPrice = InputValidator.getPositiveOrZeroDouble();
        if (validPrice == null) {
            return null;
        }
        
        System.out.println("\nEnter new stock");
        Integer validStock = InputValidator.getPositiveOrZeroInt();
        if (validStock == null) {
            return null;
        }
        
        System.out.println("\nEnter new description");
        String validDescription = InputValidator.getNoCommaString();
        if (validDescription == null) {
            return null;
        }

        switch (category.toLowerCase()) {
            case "keyboard" -> {
                System.out.println("\nIs the keyboard wireless? (yes/no)");
                Boolean validWireless = InputValidator.getBoolean();
                if (validWireless == null) {
                    return null;
                }
                
                System.out.println("\nEnter new key type");
                String validKeyType = InputValidator.getNoCommaString();
                if (validKeyType == null) {
                    return null;
                }
                
                System.out.println("\nDoes the keyboard has RGB? (yes/no)");
                Boolean validRgb = InputValidator.getBoolean();
                if (validRgb == null) {
                    return null;
                }
                
                return updateProduct(oldName, new Keyboard(validName, validPrice, validStock, validDescription, validWireless, validKeyType, validRgb));
            }
            
            case "mouse" -> {
                System.out.println("\nIs the mouse wireless? (yes/no)");
                Boolean validWireless = InputValidator.getBoolean();
                if (validWireless == null) {
                    return null;
                }
                
                System.out.println("\nEnter new sensor type");
                String validSensorType = InputValidator.getNoCommaString();
                if (validSensorType == null) {
                    return null;
                }
                
                System.out.println("\nEnter new DPI");
                Integer validDpi = InputValidator.getPositiveOrZeroInt();
                if (validDpi == null) {
                    return null;
                }
                
                return updateProduct(oldName, new Mouse(validName, validPrice, validStock, validDescription, validWireless, validSensorType, validDpi));
            }
            
            case "laptop" -> {
                System.out.println("\nEnter new processor");
                String validProcessor = InputValidator.getNoCommaString();
                if (validProcessor == null) {
                    return null;
                }
                
                System.out.println("\nEnter new memory (GB)");
                Integer validMemory = InputValidator.getPositiveOrZeroInt();
                if (validMemory == null) {
                    return null;
                }
                
                System.out.println("\nEnter new storage (GB)");
                Integer validStorage = InputValidator.getPositiveOrZeroInt();
                if (validStorage == null) {
                    return null;
                }
                
                return updateProduct(oldName, new Laptop(validName, validPrice, validStock, validDescription, validProcessor, validMemory, validStorage));
            }

            case "headphone" -> {
                System.out.println("\nIs the headphone wireless? (yes/no)");
                Boolean validWireless = InputValidator.getBoolean();
                if (validWireless == null) {
                    return null;
                }
                
                System.out.println("\nDoes the headphone has noise cancellation? (yes/no)");
                Boolean validNoiseCancellation = InputValidator.getBoolean();
                if (validNoiseCancellation == null) {
                    return null;
                }
                
                System.out.println("Does the headphone has microphone? (yes/no)");
                Boolean validMicrophone = InputValidator.getBoolean();
                if (validMicrophone == null) {
                    return null;
                }
                
                return updateProduct(oldName, new Headphone(validName, validPrice, validStock, validDescription, validWireless, validNoiseCancellation, validMicrophone));
            }
            
            case "printer" -> {
                System.out.println("\nIs the printer wireless? (yes/no)");
                Boolean validWireless = InputValidator.getBoolean();
                if (validWireless == null) {
                    return null;
                }
                
                System.out.println("\nEnter new print speed (PPM)");
                Double validPrintSpeed = InputValidator.getPositiveOrZeroDouble();
                if (validPrintSpeed == null) {
                    return null;
                }
                
                System.out.println("\nEnter new ink type");
                String validInkType = InputValidator.getNoCommaString();
                if (validInkType == null) {
                    return null;
                }
                
                return updateProduct(oldName, new Printer(validName, validPrice, validStock, validDescription, validWireless, validPrintSpeed, validInkType));
            }
                    
            default -> System.err.println("\nInvalid category");
        }
        
        return false;
    }
    
    public static Boolean updateProduct(String name, Product updatedProduct) {
        List<Product> list = LocalData.getProducts();
        List<Product> tempList = new ArrayList<>(LocalData.getProducts());
        String category = "";

        if (list == null || list.isEmpty()) {
            System.err.println("\nNo available list");
            return false;
        }
        
        if (tempList.isEmpty()) {
            System.err.println("\nNo available list");
            return false;
        }

        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).getName().equalsIgnoreCase(name)) {
                tempList.set(i, updatedProduct);
                category = tempList.get(i).getCategory();   
                break;
            }
        }

        if (!list.equals(tempList)) {
            LocalData.setProducts(tempList);
            
            for (int i = 0; i < LocalData.getPreviousList().size(); i++) {
                if (LocalData.getPreviousList().get(i).getName().equalsIgnoreCase(name)) {
                    LocalData.getPreviousList().set(i, updatedProduct);
                    break;
                }
            }

            switch (category.toLowerCase()) {
                case "keyboard" -> DatabaseManager.rewriteKeyboards(ProductListHelper.getKeyboardList());

                case "mouse" -> DatabaseManager.rewriteMice(ProductListHelper.getMouseList());
 
                case "laptop" -> DatabaseManager.rewriteLaptops(ProductListHelper.getLaptopList());

                case "headphone" -> DatabaseManager.rewriteHeadphones(ProductListHelper.getHeadphoneList());

                case "printer" -> DatabaseManager.rewritePrinters(ProductListHelper.getPrinterList());

                default -> {
                    System.err.println("\nInvalid category");
                    return false;
                }
            }
            
            return true;
        }
        
        else {
            return false;
        }
    }
    
    public static Boolean deleteProduct(String name) {
        List<Product> list = LocalData.getProducts();
        List<Product> tempList = new ArrayList<>(LocalData.getProducts());        
        String category = "";
        
        if (list.isEmpty()) {
            System.err.println("\nNo available list");
            return false;
        }
        
        if (tempList.isEmpty()) {
            System.err.println("\nNo available list");
            return false;
        }
        
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).getName().equalsIgnoreCase(name)) {
                category = tempList.get(i).getCategory();
                tempList.remove(tempList.get(i));
                break;
            }
        }
        
        if (!list.equals(tempList)) {
            LocalData.setProducts(tempList);
            
            for (int i = 0; i < LocalData.getPreviousList().size(); i++) {
                if (LocalData.getPreviousList().get(i).getName().equalsIgnoreCase(name)) {
                    LocalData.getPreviousList().remove(LocalData.getPreviousList().get(i));
                    break;
                }
            }

            switch (category.toLowerCase()) {
                case "keyboard" -> DatabaseManager.rewriteKeyboards(ProductListHelper.getKeyboardList());

                case "mouse" -> DatabaseManager.rewriteMice(ProductListHelper.getMouseList());
 
                case "laptop" -> DatabaseManager.rewriteLaptops(ProductListHelper.getLaptopList());

                case "headphone" -> DatabaseManager.rewriteHeadphones(ProductListHelper.getHeadphoneList());

                case "printer" -> DatabaseManager.rewritePrinters(ProductListHelper.getPrinterList());

                default -> {
                    System.err.println("\nInvalid category");
                    return false;
                }
            }
            
            return true;
        }
        
        return false;
    }
    
    public static Boolean updateStockUI(Product product) {
        Integer validStock;
        
        System.out.println("\nEnter stock, negative number for stock out, positive number for stock in");
        
        while (true) {            
            validStock = InputValidator.getInt();
            if (validStock == null) {
                return null;
            }

            if ((product.getStock() + validStock) < 0) {
                System.err.println("\nStock cannot be negative");
                continue;
            }
            
            break;
        }
        
        return updateStock(product.getName(), validStock);
    }
    
    public static Boolean updateStock(String name, int amount) {
        List<Product> list = LocalData.getProducts();
        String category = "";
        
        int oldStock = 0;
        int newStock = 0;
        
        if (list == null || list.isEmpty()) {
            System.err.println("\nNo available list");
            return false;
        }
                
        for (Product product : list) {
            if (product.getName().equalsIgnoreCase(name)) {
                oldStock = product.getStock();
                
                if ((product.getStock() + amount) < 0) {
                    System.err.println("\nStock cannot be negative");
                    return false;
                }
                
                product.setStock(product.getStock() + amount);
                
                category = product.getCategory();               
                newStock = product.getStock();
                break;
            }
        }
        
        if (oldStock != newStock) {
            LocalData.setProducts(list);
            
            switch (category.toLowerCase()) {
                case "keyboard" -> DatabaseManager.rewriteKeyboards(ProductListHelper.getKeyboardList());

                case "mouse" -> DatabaseManager.rewriteMice(ProductListHelper.getMouseList());
 
                case "laptop" -> DatabaseManager.rewriteLaptops(ProductListHelper.getLaptopList());

                case "headphone" -> DatabaseManager.rewriteHeadphones(ProductListHelper.getHeadphoneList());

                case "printer" -> DatabaseManager.rewritePrinters(ProductListHelper.getPrinterList());

                default -> {
                    System.err.println("\nInvalid category");
                    return false;
                }
            }
            
            return true;
        }
        
        return false;
    }  
}