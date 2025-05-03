package techretailpro.functions;

import java.util.List;
import techretailpro.objects.Headphone;
import techretailpro.objects.Keyboard;
import techretailpro.objects.Laptop;
import techretailpro.objects.LocalData;
import techretailpro.objects.Mouse;
import techretailpro.objects.Printer;
import techretailpro.objects.Product;

public class ProductFunctions {
    public static void createProductUI() {
        String category = ProductCategoryFunctions.chooseCategory(ProductCategoryFunctions.getExistingCategory());
        
        if (category == null) {
            return;
        }
                
        String validName;
        while (true) {            
            validName = Utility.getUserInput("Enter product name", "string", false);
            if (validName.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
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
            String rawValidPrice = Utility.getUserInput("Enter price", "double", false);

            switch (rawValidPrice) {
                case Utility.BACK_CONSTANT -> {
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
            String rawValidStock = Utility.getUserInput("Enter stock", "int", false);

            switch (rawValidStock) {
                case Utility.BACK_CONSTANT -> {
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
        validDescription = Utility.getUserInput("Enter description", "string", false);
        if (validDescription.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
            return;
        }

        switch (category.toLowerCase()) {
            case "keyboard" -> {
                boolean validWireless;
                String rawValidWireless = Utility.getUserInput("Is the keyboard wireless?", "boolean", false);
                if (rawValidWireless.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                validWireless = Boolean.parseBoolean(rawValidWireless);

                String validKeyType;
                validKeyType = Utility.getUserInput("Enter key type", "string", false);
                if (validKeyType.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }

                boolean validRgb;
                String rawValidRgb = Utility.getUserInput("Does the keyboard has RGB?", "boolean", false);
                if (rawValidRgb.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                validRgb = Boolean.parseBoolean(rawValidRgb);
                
                createProduct(new Keyboard(validName, validPrice, validStock, validDescription, validWireless, validKeyType, validRgb));
            }
            
            case "mouse" -> {
                boolean validWireless;
                String rawValidWireless = Utility.getUserInput("Is the mouse wireless?", "boolean", false);
                if (rawValidWireless.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                validWireless = Boolean.parseBoolean(rawValidWireless);

                String validSensorType;
                validSensorType = Utility.getUserInput("Enter sensor type", "string", false);
                if (validSensorType.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                
                int validDpi;
                while (true) {            
                    String rawValidDpi = Utility.getUserInput("Enter DPI", "int", false);

                    switch (rawValidDpi) {
                        case Utility.BACK_CONSTANT -> {
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
                validProcessor = Utility.getUserInput("Enter processor", "string", false);
                if (validProcessor.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                
                int validMemory;
                while (true) {            
                    String rawValidMemory = Utility.getUserInput("Enter memory (GB)", "int", false);

                    switch (rawValidMemory) {
                        case Utility.BACK_CONSTANT -> {
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
                    String rawValidStorage = Utility.getUserInput("Enter storage (GB)", "int", false);

                    switch (rawValidStorage) {
                        case Utility.BACK_CONSTANT -> {
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
                String rawValidWireless = Utility.getUserInput("Is the headphone wireless?", "boolean", false);
                if (rawValidWireless.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                validWireless = Boolean.parseBoolean(rawValidWireless);
                
                boolean validNoiseCancellation;
                String rawValidNoiseCancellation = Utility.getUserInput("Does the headphone has noise cancellation?", "boolean", false);
                if (rawValidNoiseCancellation.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                validNoiseCancellation = Boolean.parseBoolean(rawValidNoiseCancellation);
                
                boolean validMicrophone;
                String rawValidMicrophone = Utility.getUserInput("Does the headphone has microphone?", "boolean", false);
                if (rawValidMicrophone.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                validMicrophone = Boolean.parseBoolean(rawValidMicrophone);
                
                createProduct(new Headphone(validName, validPrice, validStock, validDescription, validWireless, validNoiseCancellation, validMicrophone));
            }
            
            case "printer" -> {
                boolean validWireless;
                String rawValidWireless = Utility.getUserInput("Is the printer wireless?", "boolean", false);
                if (rawValidWireless.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                validWireless = Boolean.parseBoolean(rawValidWireless);

                double validPrintSpeed;
                while (true) {            
                    String rawValidPrintSpeed = Utility.getUserInput("Enter print speed (PPM)", "double", false);

                    switch (rawValidPrintSpeed) {
                        case Utility.BACK_CONSTANT -> {
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
                validInkType = Utility.getUserInput("Enter ink type", "string", false);
                if (validInkType.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                
                createProduct(new Printer(validName, validPrice, validStock, validDescription, validWireless, validPrintSpeed, validInkType));     
            }
                    
            default -> System.err.println("Invalid category");
        }
    }
    
    public static void createProduct(Product product) {
        LocalData.getCurrentProductsAvailable().add(product);
        
        ProductDatabaseFunctions.appendProductCsv(product);
        
        Utility.displayReturnMessage("Product created");
    }
        
    public static void deleteProduct(Product product) {
        LocalData.getCurrentProductsAvailable().remove(product);
        LocalData.getPreviousList().remove(product);
        
        ProductDatabaseFunctions.deleteProductCsv(product);
        
        Utility.displayReturnMessage("Product deleted");
    }
    
    public static void updateStockUI(Product product) {
        int validStock;
        while (true) {            
            String rawValidStock = Utility.getUserInput("Enter stock, negative number to reduce stock, positive number to add stock", "int", false);

            switch (rawValidStock) {
                case Utility.BACK_CONSTANT -> {
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
            Utility.displayReturnMessage("Stock cannot be negative");
        }
                
        product.setStock(product.getStock() + amount);
    }
    
    public static void updateStockToCsv(Product product) {
        ProductDatabaseFunctions.updateProductCsv(product, product);
        
        if (LocalData.getCurrentUser().isAdmin()) {
            Utility.displayReturnMessage("Stock updated");
        }
    }
    
    public static void updateProductUI(Product originalProduct) {
        String validNewName;
        while (true) {            
            validNewName = Utility.getUserInput("Enter new product name", "string", true);
            if (validNewName.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                return;
            }
            
            if (validNewName.equalsIgnoreCase(Utility.BLANK_CONSTANT)) {
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
            String rawValidNewPrice = Utility.getUserInput("Enter new price", "double", true);

            switch (rawValidNewPrice) {
                case Utility.BACK_CONSTANT -> {
                    return;
                }
                
                case Utility.BLANK_CONSTANT -> {
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
            String rawValidNewStock = Utility.getUserInput("Enter new stock", "int", true);

            switch (rawValidNewStock) {
                case Utility.BACK_CONSTANT -> {
                    return;
                }
                
                case Utility.BLANK_CONSTANT -> {
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
        validNewDescription = Utility.getUserInput("Enter new description", "string", true);
        if (validNewDescription.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
            return;
        }
        if (validNewDescription.equalsIgnoreCase(Utility.BLANK_CONSTANT)) {
            validNewDescription = originalProduct.getDescription();
        }
        
        switch (originalProduct.getCategory().toLowerCase()) {
            case "keyboard" -> {
                Keyboard originalKeyboard;
                if (originalProduct instanceof Keyboard) {
                    originalKeyboard = (Keyboard) originalProduct;
                }
                
                else {
                    Utility.displayReturnMessage("Original product is not a Keyboard instance");
                    return;
                }

                boolean validNewWireless;
                String rawValidNewWireless = Utility.getUserInput("Is the keyboard wireless?", "boolean", true);
                if (rawValidNewWireless.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                if (rawValidNewWireless.equalsIgnoreCase(Utility.BLANK_CONSTANT)) {
                    validNewWireless = originalKeyboard.isWireless();
                }
                else {
                    validNewWireless = Boolean.parseBoolean(rawValidNewWireless);
                }
                
                String validNewKeyType;
                validNewKeyType = Utility.getUserInput("Enter new key type", "string", true);
                if (validNewKeyType.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                if (validNewKeyType.equalsIgnoreCase(Utility.BLANK_CONSTANT)) {
                    validNewKeyType = originalKeyboard.getKeyType();
                }
                
                boolean validNewRgb;
                String rawValidNewRgb = Utility.getUserInput("Does the keyboard has RGB?", "boolean", true);
                if (rawValidNewRgb.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                if (rawValidNewRgb.equalsIgnoreCase(Utility.BLANK_CONSTANT)) {
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
                    Utility.displayReturnMessage("Original product is not a Mouse instance");
                    return;
                }
                
                boolean validNewWireless;
                String rawValidNewWireless = Utility.getUserInput("Is the mouse wireless?", "boolean", true);
                if (rawValidNewWireless.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                if (rawValidNewWireless.equalsIgnoreCase(Utility.BLANK_CONSTANT)) {
                    validNewWireless = originalMouse.isWireless();
                }
                else {
                    validNewWireless = Boolean.parseBoolean(rawValidNewWireless);
                }

                String validNewSensorType;
                validNewSensorType = Utility.getUserInput("Enter new sensor type", "string", true);
                if (validNewSensorType.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                if (validNewSensorType.equalsIgnoreCase(Utility.BLANK_CONSTANT)) {
                    validNewSensorType = originalMouse.getSensorType();
                }
                                
                int validNewDpi;
                while (true) {            
                    String rawValidNewDpi = Utility.getUserInput("Enter new DPI", "int", true);

                    switch (rawValidNewDpi) {
                        case Utility.BACK_CONSTANT -> {
                            return;
                        }

                        case Utility.BLANK_CONSTANT -> {
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
                    Utility.displayReturnMessage("Original product is not a Laptop instance");
                    return;
                }

                String validNewProcessor;
                validNewProcessor = Utility.getUserInput("Enter new processor", "string", true);
                if (validNewProcessor.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                if (validNewProcessor.equalsIgnoreCase(Utility.BLANK_CONSTANT)) {
                    validNewProcessor = originalLaptop.getProcessor();
                }

                int validNewMemory;
                while (true) {            
                    String rawValidNewMemory = Utility.getUserInput("Enter new memory (GB)", "int", true);

                    switch (rawValidNewMemory) {
                        case Utility.BACK_CONSTANT -> {
                            return;
                        }

                        case Utility.BLANK_CONSTANT -> {
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
                    String rawValidNewStorage = Utility.getUserInput("Enter new storage (GB)", "int", true);

                    switch (rawValidNewStorage) {
                        case Utility.BACK_CONSTANT -> {
                            return;
                        }

                        case Utility.BLANK_CONSTANT -> {
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
                    Utility.displayReturnMessage("Original product is not a Headphone instance");
                    return;
                }
                
                boolean validNewWireless;
                String rawValidNewWireless = Utility.getUserInput("Is the headphone wireless?", "boolean", true);
                if (rawValidNewWireless.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                if (rawValidNewWireless.equalsIgnoreCase(Utility.BLANK_CONSTANT)) {
                    validNewWireless = originalHeadphone.isWireless();
                }
                else {
                    validNewWireless = Boolean.parseBoolean(rawValidNewWireless);
                }
                
                boolean validNewNoiseCancellation;
                String rawValidNewNoiseCancellation = Utility.getUserInput("Does the headphone has noise cancellation?", "boolean", true);
                if (rawValidNewNoiseCancellation.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                if (rawValidNewNoiseCancellation.equalsIgnoreCase(Utility.BLANK_CONSTANT)) {
                    validNewNoiseCancellation = originalHeadphone.isNoiseCancellation();
                }
                else {
                    validNewNoiseCancellation = Boolean.parseBoolean(rawValidNewNoiseCancellation);
                }
                
                boolean validNewMicrophone;
                String rawValidNewMicrophone = Utility.getUserInput("Does the headphone has microphone?", "boolean", true);
                if (rawValidNewMicrophone.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                if (rawValidNewMicrophone.equalsIgnoreCase(Utility.BLANK_CONSTANT)) {
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
                    Utility.displayReturnMessage("Original product is not a Printer instance");
                    return;
                }
                
                boolean validNewWireless;
                String rawValidNewWireless = Utility.getUserInput("Is the printer wireless?", "boolean", true);
                if (rawValidNewWireless.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                if (rawValidNewWireless.equalsIgnoreCase(Utility.BLANK_CONSTANT)) {
                    validNewWireless = originalPrinter.isWireless();
                }
                else {
                    validNewWireless = Boolean.parseBoolean(rawValidNewWireless);
                }

                double validNewPrintSpeed;
                while (true) {            
                    String rawValidNewPrintSpeed = Utility.getUserInput("Enter new print speed (PPM)", "double", true);

                    switch (rawValidNewPrintSpeed) {
                        case Utility.BACK_CONSTANT -> {
                            return;
                        }

                        case Utility.BLANK_CONSTANT -> {
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
                validNewInkType = Utility.getUserInput("Enter new ink type", "string", true);
                if (validNewInkType.equalsIgnoreCase(Utility.BACK_CONSTANT)) {
                    return;
                }
                if (validNewInkType.equalsIgnoreCase(Utility.BLANK_CONSTANT)) {
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
                    Utility.displayReturnMessage("Original product is not a Keyboard instance");
                    return;
                }
                
                if (updatedProduct instanceof Keyboard) {
                    updatedKeyboard = (Keyboard) updatedProduct;
                }
                
                else {
                    Utility.displayReturnMessage("Updated product is not a Keyboard instance");
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
                    Utility.displayReturnMessage("Original product is not a Mouse instance");
                    return;
                }
                
                if (updatedProduct instanceof Mouse) {
                    updatedMouse = (Mouse) updatedProduct;
                }
                
                else {
                    Utility.displayReturnMessage("Updated product is not a Mouse instance");
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
                    Utility.displayReturnMessage("Original product is not a Laptop instance");
                    return;
                }
                
                if (updatedProduct instanceof Laptop) {
                    updatedLaptop = (Laptop) updatedProduct;
                }
                
                else {
                    Utility.displayReturnMessage("Updated product is not a Laptop instance");
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
                    Utility.displayReturnMessage("Original product is not a Headphone instance");
                    return;
                }
                
                if (updatedProduct instanceof Headphone) {
                    updatedHeadphone = (Headphone) updatedProduct;
                }
                
                else {
                    Utility.displayReturnMessage("Updated product is not a Headphone instance");
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
                    Utility.displayReturnMessage("Original product is not a Printer instance");
                    return;
                }
                
                if (updatedProduct instanceof Printer) {
                    updatedPrinter = (Printer) updatedProduct;
                }
                
                else {
                    Utility.displayReturnMessage("Updated product is not a Printer instance");
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
        
        ProductDatabaseFunctions.updateProductCsv(originalProduct, updatedProduct);
        
        Utility.displayReturnMessage("Product updated");
    }
}