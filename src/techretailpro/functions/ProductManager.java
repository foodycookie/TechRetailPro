package techretailpro.functions;

import java.util.List;
import techretailpro.objects.Headphone;
import techretailpro.objects.Keyboard;
import techretailpro.objects.Laptop;
import techretailpro.objects.LocalData;
import techretailpro.objects.Mouse;
import techretailpro.objects.Printer;
import techretailpro.objects.Product;

public class ProductManager {
    public static void createProductUI() {
        String category = ProductCategoryHelper.chooseCategory(ProductCategoryHelper.getExistingCategory());
        
        if (category == null) {
            return;
        }
                
        String validName;
        while (true) {            
            validName = UtilityHelper.getUserInput("Enter product name", "string", false);
            if (validName.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                return;
            }

            List<Product> list = LocalData.getCurrentProductsAvailable();
            boolean nameExists = false;

            if (list != null && !list.isEmpty()) {
                for (Product product : list) {
                    if (validName.equalsIgnoreCase(product.getName())) {
                        System.err.println("Invalid product name. Product name already exists");
                        nameExists = true;
                        break;
                    }
                }
            }

            if (nameExists) {
                continue;
            }

            break;
        }
        
        double validPrice;
        while (true) {            
            String rawValidPrice = UtilityHelper.getUserInput("Enter price", "double", false);

            switch (rawValidPrice) {
                case UtilityHelper.BACK_CONSTANT -> {
                    return;
                }

                default -> {
                    validPrice = Double.parseDouble(rawValidPrice);
                    
                    if (validPrice < 0) {
                        System.err.println("Invalid input. Input must be positive");
                        continue;
                    }
                }
            }
            
            break;
        }
        
        int validStock;
        while (true) {            
            String rawValidStock = UtilityHelper.getUserInput("Enter stock", "int", false);

            switch (rawValidStock) {
                case UtilityHelper.BACK_CONSTANT -> {
                    return;
                }

                default -> {
                    validStock = Integer.parseInt(rawValidStock);
                    
                    if (validStock < 0) {
                        System.err.println("Invalid input. Input must be positive");
                        continue;
                    }
                }
            }
            
            break;
        }

        String validDescription;
        validDescription = UtilityHelper.getUserInput("Enter description", "string", false);
        if (validDescription.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
            return;
        }

        switch (category.toLowerCase()) {
            case "keyboard" -> {
                boolean validWireless;
                String rawValidWireless = UtilityHelper.getUserInput("Is the keyboard wireless?", "boolean", false);
                if (rawValidWireless.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                validWireless = Boolean.parseBoolean(rawValidWireless);

                String validKeyType;
                validKeyType = UtilityHelper.getUserInput("Enter key type", "string", false);
                if (validKeyType.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }

                boolean validRgb;
                String rawValidRgb = UtilityHelper.getUserInput("Does the keyboard has RGB?", "boolean", false);
                if (rawValidRgb.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                validRgb = Boolean.parseBoolean(rawValidRgb);
                
                createProduct(new Keyboard(validName, validPrice, validStock, validDescription, validWireless, validKeyType, validRgb));
            }
            
            case "mouse" -> {
                boolean validWireless;
                String rawValidWireless = UtilityHelper.getUserInput("Is the mouse wireless?", "boolean", false);
                if (rawValidWireless.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                validWireless = Boolean.parseBoolean(rawValidWireless);

                String validSensorType;
                validSensorType = UtilityHelper.getUserInput("Enter sensor type", "string", false);
                if (validSensorType.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                
                int validDpi;
                while (true) {            
                    String rawValidDpi = UtilityHelper.getUserInput("Enter DPI", "int", false);

                    switch (rawValidDpi) {
                        case UtilityHelper.BACK_CONSTANT -> {
                            return;
                        }

                        default -> {
                            validDpi = Integer.parseInt(rawValidDpi);

                            if (validDpi < 0) {
                                System.err.println("Invalid input. Input must be positive");
                                continue;
                            }
                        }
                    }

                    break;
                }
                
                createProduct(new Mouse(validName, validPrice, validStock, validDescription, validWireless, validSensorType, validDpi));
            }
            
            case "laptop" -> {
                String validProcessor;
                validProcessor = UtilityHelper.getUserInput("Enter processor", "string", false);
                if (validProcessor.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                
                int validMemory;
                while (true) {            
                    String rawValidMemory = UtilityHelper.getUserInput("Enter memory (GB)", "int", false);

                    switch (rawValidMemory) {
                        case UtilityHelper.BACK_CONSTANT -> {
                            return;
                        }

                        default -> {
                            validMemory = Integer.parseInt(rawValidMemory);

                            if (validMemory < 0) {
                                System.err.println("Invalid input. Input must be positive");
                                continue;
                            }
                        }
                    }

                    break;
                }
                
                int validStorage;
                while (true) {            
                    String rawValidStorage = UtilityHelper.getUserInput("Enter storage (GB)", "int", false);

                    switch (rawValidStorage) {
                        case UtilityHelper.BACK_CONSTANT -> {
                            return;
                        }

                        default -> {
                            validStorage = Integer.parseInt(rawValidStorage);

                            if (validStorage < 0) {
                                System.err.println("Invalid input. Input must be positive");
                                continue;
                            }
                        }
                    }

                    break;
                }

                createProduct(new Laptop(validName, validPrice, validStock, validDescription, validProcessor, validMemory, validStorage));
            }

            case "headphone" -> {
                boolean validWireless;
                String rawValidWireless = UtilityHelper.getUserInput("Is the headphone wireless?", "boolean", false);
                if (rawValidWireless.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                validWireless = Boolean.parseBoolean(rawValidWireless);
                
                boolean validNoiseCancellation;
                String rawValidNoiseCancellation = UtilityHelper.getUserInput("Does the headphone has noise cancellation?", "boolean", false);
                if (rawValidNoiseCancellation.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                validNoiseCancellation = Boolean.parseBoolean(rawValidNoiseCancellation);
                
                boolean validMicrophone;
                String rawValidMicrophone = UtilityHelper.getUserInput("Does the headphone has microphone?", "boolean", false);
                if (rawValidMicrophone.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                validMicrophone = Boolean.parseBoolean(rawValidMicrophone);
                
                createProduct(new Headphone(validName, validPrice, validStock, validDescription, validWireless, validNoiseCancellation, validMicrophone));
            }
            
            case "printer" -> {
                boolean validWireless;
                String rawValidWireless = UtilityHelper.getUserInput("Is the printer wireless?", "boolean", false);
                if (rawValidWireless.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                validWireless = Boolean.parseBoolean(rawValidWireless);

                double validPrintSpeed;
                while (true) {            
                    String rawValidPrintSpeed = UtilityHelper.getUserInput("Enter print speed (PPM)", "double", false);

                    switch (rawValidPrintSpeed) {
                        case UtilityHelper.BACK_CONSTANT -> {
                            return;
                        }

                        default -> {
                            validPrintSpeed = Double.parseDouble(rawValidPrintSpeed);

                            if (validPrintSpeed < 0) {
                                System.err.println("Invalid input. Input must be positive");
                                continue;
                            }
                        }
                    }

                    break;
                }
                
                String validInkType;
                validInkType = UtilityHelper.getUserInput("Enter ink type", "string", false);
                if (validInkType.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                
                createProduct(new Printer(validName, validPrice, validStock, validDescription, validWireless, validPrintSpeed, validInkType));     
            }
                    
            default -> System.err.println("Invalid category");
        }
    }
    
    public static void createProduct(Product product) {
        LocalData.getCurrentProductsAvailable().add(product);
        
        ProductDatabaseManager.appendProductCsv(product);
        
        UtilityHelper.displayReturnMessage("Product created");
    }
        
    public static void deleteProduct(Product product) {
        LocalData.getCurrentProductsAvailable().remove(product);
        LocalData.getPreviousList().remove(product);
        
        ProductDatabaseManager.deleteProductCsv(product);
        
        UtilityHelper.displayReturnMessage("Product deleted");
    }
    
    public static void updateStockUI(Product product) {
        int validStock;
        while (true) {            
            String rawValidStock = UtilityHelper.getUserInput("Enter stock, negative number to reduce stock, positive number to add stock", "int", false);

            switch (rawValidStock) {
                case UtilityHelper.BACK_CONSTANT -> {
                    return;
                }

                default -> {
                    validStock = Integer.parseInt(rawValidStock);
                    
                    if (validStock == 0) {
                        System.err.println("Invalid input. Input cannot be 0");
                        continue;
                    }
                    
                    if ((product.getStock() + validStock) < 0) {
                        System.err.println("Invalid input. Total stock cannot be negative");
                        continue;
                    }
                }
            }
            
            break;
        }

        updateStock(product, validStock);
    }
    
    public static void updateStock(Product product, int amount) {
        if ((product.getStock() + amount) < 0) {
            UtilityHelper.displayReturnMessage("Stock cannot be negative");
        }
                
        product.setStock(product.getStock() + amount);
    }
    
    public static void updateStockToCsv(Product product) {
        ProductDatabaseManager.updateProductCsv(product, product);
        
        if (LocalData.getCurrentUser().isAdmin()) {
            UtilityHelper.displayReturnMessage("Stock updated");
        }
    }
    
    public static void updateProductUI(Product originalProduct) {
        String validNewName;
        while (true) {            
            validNewName = UtilityHelper.getUserInput("Enter new product name", "string", true);
            if (validNewName.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                return;
            }
            
            if (validNewName.equalsIgnoreCase(UtilityHelper.BLANK_CONSTANT)) {
                validNewName = originalProduct.getName();
                break;
            }
            
            if (validNewName.equalsIgnoreCase(originalProduct.getName())) {
                break;
            }
            
            List<Product> list = LocalData.getCurrentProductsAvailable();
            boolean nameExists = false;
            
            if (list != null && !list.isEmpty()) {
                for (Product product : list) {
                    if (validNewName.equalsIgnoreCase(product.getName())) {
                        System.err.println("Invalid product name. Product name already exists");
                        nameExists = true;
                        break;
                    }
                }
            }

            if (nameExists) {
                continue;
            }

            break;
        }
        
        double validNewPrice;
        while (true) {            
            String rawValidNewPrice = UtilityHelper.getUserInput("Enter new price", "double", true);

            switch (rawValidNewPrice) {
                case UtilityHelper.BACK_CONSTANT -> {
                    return;
                }
                
                case UtilityHelper.BLANK_CONSTANT -> {
                    validNewPrice = originalProduct.getPrice();
                }

                default -> {
                    validNewPrice = Double.parseDouble(rawValidNewPrice);
                    
                    if (validNewPrice < 0) {
                        System.err.println("Invalid input. Input must be positive");
                        continue;
                    }
                }
            }
            
            break;
        }
        
        int validNewStock;
        while (true) {            
            String rawValidNewStock = UtilityHelper.getUserInput("Enter new stock", "int", true);

            switch (rawValidNewStock) {
                case UtilityHelper.BACK_CONSTANT -> {
                    return;
                }
                
                case UtilityHelper.BLANK_CONSTANT -> {
                    validNewStock = originalProduct.getStock();
                }

                default -> {
                    validNewStock = Integer.parseInt(rawValidNewStock);
                    
                    if (validNewStock < 0) {
                        System.err.println("Invalid input. Input must be positive");
                        continue;
                    }
                }
            }
            
            break;
        }

        String validNewDescription;
        validNewDescription = UtilityHelper.getUserInput("Enter new description", "string", true);
        if (validNewDescription.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
            return;
        }
        if (validNewDescription.equalsIgnoreCase(UtilityHelper.BLANK_CONSTANT)) {
            validNewDescription = originalProduct.getDescription();
        }
        
        switch (originalProduct.getCategory().toLowerCase()) {
            case "keyboard" -> {
                Keyboard originalKeyboard;
                if (originalProduct instanceof Keyboard) {
                    originalKeyboard = (Keyboard) originalProduct;
                }
                
                else {
                    UtilityHelper.displayReturnMessage("Original product is not a Keyboard instance");
                    return;
                }

                boolean validNewWireless;
                String rawValidNewWireless = UtilityHelper.getUserInput("Is the keyboard wireless?", "boolean", true);
                if (rawValidNewWireless.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                if (rawValidNewWireless.equalsIgnoreCase(UtilityHelper.BLANK_CONSTANT)) {
                    validNewWireless = originalKeyboard.isWireless();
                }
                else {
                    validNewWireless = Boolean.parseBoolean(rawValidNewWireless);
                }
                
                String validNewKeyType;
                validNewKeyType = UtilityHelper.getUserInput("Enter new key type", "string", true);
                if (validNewKeyType.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                if (validNewKeyType.equalsIgnoreCase(UtilityHelper.BLANK_CONSTANT)) {
                    validNewKeyType = originalKeyboard.getKeyType();
                }
                
                boolean validNewRgb;
                String rawValidNewRgb = UtilityHelper.getUserInput("Does the keyboard has RGB?", "boolean", true);
                if (rawValidNewRgb.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                if (rawValidNewRgb.equalsIgnoreCase(UtilityHelper.BLANK_CONSTANT)) {
                    validNewRgb = originalKeyboard.isRgb();
                }
                else {
                    validNewRgb = Boolean.parseBoolean(rawValidNewRgb);
                }
                
                updateProduct(originalProduct, new Keyboard(validNewName, validNewPrice, validNewStock, validNewDescription, validNewWireless, validNewKeyType, validNewRgb));
            }
            
            case "mouse" -> {
                Mouse originalMouse;
                if (originalProduct instanceof Mouse) {
                    originalMouse = (Mouse) originalProduct;
                }
                
                else {
                    UtilityHelper.displayReturnMessage("Original product is not a Mouse instance");
                    return;
                }
                
                boolean validNewWireless;
                String rawValidNewWireless = UtilityHelper.getUserInput("Is the mouse wireless?", "boolean", true);
                if (rawValidNewWireless.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                if (rawValidNewWireless.equalsIgnoreCase(UtilityHelper.BLANK_CONSTANT)) {
                    validNewWireless = originalMouse.isWireless();
                }
                else {
                    validNewWireless = Boolean.parseBoolean(rawValidNewWireless);
                }

                String validNewSensorType;
                validNewSensorType = UtilityHelper.getUserInput("Enter new sensor type", "string", true);
                if (validNewSensorType.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                if (validNewSensorType.equalsIgnoreCase(UtilityHelper.BLANK_CONSTANT)) {
                    validNewSensorType = originalMouse.getSensorType();
                }
                                
                int validNewDpi;
                while (true) {            
                    String rawValidNewDpi = UtilityHelper.getUserInput("Enter new DPI", "int", true);

                    switch (rawValidNewDpi) {
                        case UtilityHelper.BACK_CONSTANT -> {
                            return;
                        }

                        case UtilityHelper.BLANK_CONSTANT -> {
                            validNewDpi = originalMouse.getDpi();
                        }

                        default -> {
                            validNewDpi = Integer.parseInt(rawValidNewDpi);

                            if (validNewDpi < 0) {
                                System.err.println("Invalid input. Input must be positive");
                                continue;
                            }
                        }
                    }

                    break;
                }
                
                updateProduct(originalProduct, new Mouse(validNewName, validNewPrice, validNewStock, validNewDescription, validNewWireless, validNewSensorType, validNewDpi));            
            }
            
            case "laptop" -> {
                Laptop originalLaptop;
                if (originalProduct instanceof Laptop) {
                    originalLaptop = (Laptop) originalProduct;
                }
                
                else {
                    UtilityHelper.displayReturnMessage("Original product is not a Laptop instance");
                    return;
                }

                String validNewProcessor;
                validNewProcessor = UtilityHelper.getUserInput("Enter new processor", "string", true);
                if (validNewProcessor.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                if (validNewProcessor.equalsIgnoreCase(UtilityHelper.BLANK_CONSTANT)) {
                    validNewProcessor = originalLaptop.getProcessor();
                }

                int validNewMemory;
                while (true) {            
                    String rawValidNewMemory = UtilityHelper.getUserInput("Enter new memory (GB)", "int", true);

                    switch (rawValidNewMemory) {
                        case UtilityHelper.BACK_CONSTANT -> {
                            return;
                        }

                        case UtilityHelper.BLANK_CONSTANT -> {
                            validNewMemory = originalLaptop.getMemory();
                        }

                        default -> {
                            validNewMemory = Integer.parseInt(rawValidNewMemory);

                            if (validNewMemory < 0) {
                                System.err.println("Invalid input. Input must be positive");
                                continue;
                            }
                        }
                    }

                    break;
                }
                
                int validNewStorage;
                while (true) {            
                    String rawValidNewStorage = UtilityHelper.getUserInput("Enter new storage (GB)", "int", true);

                    switch (rawValidNewStorage) {
                        case UtilityHelper.BACK_CONSTANT -> {
                            return;
                        }

                        case UtilityHelper.BLANK_CONSTANT -> {
                            validNewStorage = originalLaptop.getStorage();
                        }

                        default -> {
                            validNewStorage = Integer.parseInt(rawValidNewStorage);

                            if (validNewStorage < 0) {
                                System.err.println("Invalid input. Input must be positive");
                                continue;
                            }
                        }
                    }

                    break;
                }
                
                updateProduct(originalProduct, new Laptop(validNewName, validNewPrice, validNewStock, validNewDescription, validNewProcessor, validNewMemory, validNewStorage));
            }

            case "headphone" -> {
                Headphone originalHeadphone;
                if (originalProduct instanceof Headphone) {
                    originalHeadphone = (Headphone) originalProduct;
                }
                
                else {
                    UtilityHelper.displayReturnMessage("Original product is not a Headphone instance");
                    return;
                }
                
                boolean validNewWireless;
                String rawValidNewWireless = UtilityHelper.getUserInput("Is the headphone wireless?", "boolean", true);
                if (rawValidNewWireless.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                if (rawValidNewWireless.equalsIgnoreCase(UtilityHelper.BLANK_CONSTANT)) {
                    validNewWireless = originalHeadphone.isWireless();
                }
                else {
                    validNewWireless = Boolean.parseBoolean(rawValidNewWireless);
                }
                
                boolean validNewNoiseCancellation;
                String rawValidNewNoiseCancellation = UtilityHelper.getUserInput("Does the headphone has noise cancellation?", "boolean", true);
                if (rawValidNewNoiseCancellation.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                if (rawValidNewNoiseCancellation.equalsIgnoreCase(UtilityHelper.BLANK_CONSTANT)) {
                    validNewNoiseCancellation = originalHeadphone.isNoiseCancellation();
                }
                else {
                    validNewNoiseCancellation = Boolean.parseBoolean(rawValidNewNoiseCancellation);
                }
                
                boolean validNewMicrophone;
                String rawValidNewMicrophone = UtilityHelper.getUserInput("Does the headphone has microphone?", "boolean", true);
                if (rawValidNewMicrophone.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                if (rawValidNewMicrophone.equalsIgnoreCase(UtilityHelper.BLANK_CONSTANT)) {
                    validNewMicrophone = originalHeadphone.isMicrophone();
                }
                else {
                    validNewMicrophone = Boolean.parseBoolean(rawValidNewMicrophone);
                }
                
                updateProduct(originalProduct, new Headphone(validNewName, validNewPrice, validNewStock, validNewDescription, validNewWireless, validNewNoiseCancellation, validNewMicrophone));
            }
            
            case "printer" -> {
                Printer originalPrinter;
                if (originalProduct instanceof Printer) {
                    originalPrinter = (Printer) originalProduct;
                }
                
                else {
                    UtilityHelper.displayReturnMessage("Original product is not a Printer instance");
                    return;
                }
                
                boolean validNewWireless;
                String rawValidNewWireless = UtilityHelper.getUserInput("Is the printer wireless?", "boolean", true);
                if (rawValidNewWireless.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                if (rawValidNewWireless.equalsIgnoreCase(UtilityHelper.BLANK_CONSTANT)) {
                    validNewWireless = originalPrinter.isWireless();
                }
                else {
                    validNewWireless = Boolean.parseBoolean(rawValidNewWireless);
                }

                double validNewPrintSpeed;
                while (true) {            
                    String rawValidNewPrintSpeed = UtilityHelper.getUserInput("Enter new print speed (PPM)", "double", true);

                    switch (rawValidNewPrintSpeed) {
                        case UtilityHelper.BACK_CONSTANT -> {
                            return;
                        }

                        case UtilityHelper.BLANK_CONSTANT -> {
                            validNewPrintSpeed = originalPrinter.getPrintSpeed();
                        }

                        default -> {
                            validNewPrintSpeed = Double.parseDouble(rawValidNewPrintSpeed);

                            if (validNewPrintSpeed < 0) {
                                System.err.println("Invalid input. Input must be positive");
                                continue;
                            }
                        }
                    }

                    break;
                }

                String validNewInkType;
                validNewInkType = UtilityHelper.getUserInput("Enter new ink type", "string", true);
                if (validNewInkType.equalsIgnoreCase(UtilityHelper.BACK_CONSTANT)) {
                    return;
                }
                if (validNewInkType.equalsIgnoreCase(UtilityHelper.BLANK_CONSTANT)) {
                    validNewInkType = originalPrinter.getInkType();
                }
                
                updateProduct(originalProduct, new Printer(validNewName, validNewPrice, validNewStock, validNewDescription, validNewWireless, validNewPrintSpeed, validNewInkType));
            }
                    
            default -> System.err.println("Invalid category");
        }
    }
    
    public static void updateProduct(Product originalProduct, Product updatedProduct) {
        originalProduct.setName(updatedProduct.getName());
        originalProduct.setPrice(updatedProduct.getPrice());
        originalProduct.setStock(updatedProduct.getStock());
        originalProduct.setDescription(updatedProduct.getDescription());
        
        switch (originalProduct.getCategory().toLowerCase()) {
            case "keyboard" -> {
                Keyboard originalKeyboard;
                Keyboard updatedKeyboard;
                
                if (originalProduct instanceof Keyboard) {
                    originalKeyboard = (Keyboard) originalProduct;
                }
                
                else {
                    UtilityHelper.displayReturnMessage("Original product is not a Keyboard instance");
                    return;
                }
                
                if (updatedProduct instanceof Keyboard) {
                    updatedKeyboard = (Keyboard) updatedProduct;
                }
                
                else {
                    UtilityHelper.displayReturnMessage("Updated product is not a Keyboard instance");
                    return;
                }
                
                originalKeyboard.setWireless(updatedKeyboard.isWireless());
                originalKeyboard.setKeyType(updatedKeyboard.getKeyType());
                originalKeyboard.setRgb(updatedKeyboard.isRgb());
            }
            
            case "mouse" -> {
                Mouse originalMouse;
                Mouse updatedMouse;
                
                if (originalProduct instanceof Mouse) {
                    originalMouse = (Mouse) originalProduct;
                }
                
                else {
                    UtilityHelper.displayReturnMessage("Original product is not a Mouse instance");
                    return;
                }
                
                if (updatedProduct instanceof Mouse) {
                    updatedMouse = (Mouse) updatedProduct;
                }
                
                else {
                    UtilityHelper.displayReturnMessage("Updated product is not a Mouse instance");
                    return;
                }
                
                originalMouse.setWireless(updatedMouse.isWireless());
                originalMouse.setSensorType(updatedMouse.getSensorType());
                originalMouse.setDpi(updatedMouse.getDpi());
            }
            
            case "laptop" -> {
                Laptop originalLaptop;
                Laptop updatedLaptop;
                
                if (originalProduct instanceof Laptop) {
                    originalLaptop = (Laptop) originalProduct;
                }
                
                else {
                    UtilityHelper.displayReturnMessage("Original product is not a Laptop instance");
                    return;
                }
                
                if (updatedProduct instanceof Laptop) {
                    updatedLaptop = (Laptop) updatedProduct;
                }
                
                else {
                    UtilityHelper.displayReturnMessage("Updated product is not a Laptop instance");
                    return;
                }
                
                originalLaptop.setProcessor(updatedLaptop.getProcessor());
                originalLaptop.setMemory(updatedLaptop.getMemory());
                originalLaptop.setStorage(updatedLaptop.getStorage());
            }
            
            case "headphone" -> {
                Headphone originalHeadphone;
                Headphone updatedHeadphone;
                
                if (originalProduct instanceof Headphone) {
                    originalHeadphone = (Headphone) originalProduct;
                }
                
                else {
                    UtilityHelper.displayReturnMessage("Original product is not a Headphone instance");
                    return;
                }
                
                if (updatedProduct instanceof Headphone) {
                    updatedHeadphone = (Headphone) updatedProduct;
                }
                
                else {
                    UtilityHelper.displayReturnMessage("Updated product is not a Headphone instance");
                    return;
                }
                
                originalHeadphone.setWireless(updatedHeadphone.isWireless());
                originalHeadphone.setNoiseCancellation(updatedHeadphone.isNoiseCancellation());
                originalHeadphone.setMicrophone(updatedHeadphone.isMicrophone());
            }
            
            case "printer" -> {
                Printer originalPrinter;
                Printer updatedPrinter;
                
                if (originalProduct instanceof Printer) {
                    originalPrinter = (Printer) originalProduct;
                }
                
                else {
                    UtilityHelper.displayReturnMessage("Original product is not a Printer instance");
                    return;
                }
                
                if (updatedProduct instanceof Printer) {
                    updatedPrinter = (Printer) updatedProduct;
                }
                
                else {
                    UtilityHelper.displayReturnMessage("Updated product is not a Printer instance");
                    return;
                }
                
                originalPrinter.setWireless(updatedPrinter.isWireless());
                originalPrinter.setPrintSpeed(updatedPrinter.getPrintSpeed());
                originalPrinter.setInkType(updatedPrinter.getInkType());
            }
            
            default -> {
                System.err.println("Invalid category");
                return;
            }
        }
        
        ProductDatabaseManager.updateProductCsv(originalProduct, updatedProduct);
        
        UtilityHelper.displayReturnMessage("Product updated");
    }
}