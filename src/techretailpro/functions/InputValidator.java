package techretailpro.functions;

import java.util.List;
import techretailpro.objects.LocalData;
import techretailpro.objects.Product;
import techretailpro.utilities.Utility;

public class InputValidator {
    /// Basic input validation ///
    //Only check if it is the correct data type and not empty
    
    public static Integer getInt() {
        String input;
        Integer intValue;
        
        while (true) {            
            System.out.print("Input > ");

            input = Utility.SCANNER.nextLine().trim();
            
            if (input.equalsIgnoreCase("exit")) {
                return null;
            }
            
            intValue = Utility.stringToInteger(input);
            
            if (intValue == null) {
                System.err.println("\nInvalid input. Input must be a whole number");
                continue;
            }
            
            break;
        }
        
        return intValue;
    }
    
    public static Double getDouble() {
        String input;
        Double doubleValue;
        
        while (true) {            
            System.out.print("Input > ");

            input = Utility.SCANNER.nextLine().trim();
            
            if (input.equalsIgnoreCase("exit")) {
                return null;
            }
            
            doubleValue = Utility.stringToDouble(input);
            
            if (doubleValue == null) {
                System.err.println("\nInvalid input. Input must be a number");
                continue;
            }
            
            break;
        }
        
        return doubleValue;
    }
    
    public static String getString() {
        String input;
        
        while (true) {            
            System.out.print("Input > ");

            input = Utility.SCANNER.nextLine().trim();
            
            if (input.isEmpty()) {
                System.err.println("\nInvalid input. Input cannot be empty");
                continue;
            }
            
            if (input.equalsIgnoreCase("exit")) {
                return null;
            }

            break;
        }
        
        return input;
    }
    
    public static Boolean getBoolean() {
        String input;
        Boolean booleanValue;
        
        while (true) {            
            System.out.print("Input > ");

            input = Utility.SCANNER.nextLine().trim();
            
            if (input.isEmpty()) {
                System.err.println("\nInvalid input. Input cannot be empty");
                continue;
            }

            if (input.equalsIgnoreCase("exit")) {
                return null;
            }
            
            booleanValue = Utility.stringToBoolean(input);
            
            if (booleanValue == null) {
                System.err.println("\nInvalid input. Input must be either true or false, yes or no");
                continue;
            }
            
            break;
        }
        
        return booleanValue;
    }
    
    /// Special condition ///
    
    public static boolean validatePositiveOrZeroInt(int intValue) {
        if (intValue < 0) {
            System.err.println("\nInvalid input. Input cannot be negative");
            return false;
        }
        
        return true;
    }
    
    public static boolean validatePositiveOrZeroDouble(double doubleValue) {
        if (doubleValue < 0) {
            System.err.println("\nInvalid input. Input cannot be negative");
            return false;
        }
        
        return true;
    }
    
    public static boolean validateNoCommaString(String string) {
        if (string.contains(",")) {
            System.err.println("\nInvalid input. Input cannot have comma");
            return false;
        }

        return true;
    }
    
    public static boolean validateIfProductNameIsRepeated(String name) {
        List<Product> list = LocalData.getProducts();

        if (list != null && !list.isEmpty()) {
            for (Product product : list) {
                if (name.equalsIgnoreCase(product.getName())) {
                    System.err.println("\nInvalid name. Name already exists");
                    return false;
                }
            }
        }

        return true;
    }
    
    public static boolean validateIfProductNameIsRepeatedForUpdateWhereYouCanRepeatYourOriginalNameUwu(String oldName, String newName) {
        List<Product> list = LocalData.getProducts();

        if (list != null && !list.isEmpty()) {
            for (Product product : list) {
                if (product.getName().equalsIgnoreCase(oldName)) {
                    continue;
                }
                
                if (newName.equalsIgnoreCase(product.getName())) {
                    System.err.println("\nInvalid name. Name already exists");
                    return false;
                }
            }
        }

        return true;
    }
    
    /// Modified input validation with special condition ///
    
    public static Integer getPositiveOrZeroInt() {
        Integer positiveInt;
        
        while (true) {            
            positiveInt = getInt();
            
            if (positiveInt == null) {
                return null;
            }
            
            if (!validatePositiveOrZeroInt(positiveInt)) {
                continue;
            }
            
            break;
        }
        
        return positiveInt;
    }
    
    public static Double getPositiveOrZeroDouble() {
        Double positiveDouble;
        
        while (true) {            
            positiveDouble = getDouble();
            
            if (positiveDouble == null) {
                return null;
            }
            
            if (!validatePositiveOrZeroDouble(positiveDouble)) {
                continue;
            }
            
            break;
        }
        
        return positiveDouble;
    }
    
    public static String getNoCommaString() {
        String noCommaString;
        
        while (true) {            
            noCommaString = getString();
            
            if (noCommaString == null) {
                return null;
            }
            
            if (!validateNoCommaString(noCommaString)) {
                continue;
            }
            
            break;
        }
        
        return noCommaString;
    }
    
    public static String getValidProductName() {
        String validProductName;
        
        while (true) {            
            validProductName = getString();
            
            if (validProductName == null) {
                return null;
            }
            
            if (!validateNoCommaString(validProductName)) {
                continue;
            }
            
            if (!validateIfProductNameIsRepeated(validProductName)) {
                continue;
            } 
            
            break;
        }
        
        return validProductName;
    }
    
    public static String getValidProductNameForUpdate(String oldName) {
        String validProductName;
        
        while (true) {            
            validProductName = getString();
            
            if (validProductName == null) {
                return null;
            }
            
            if (!validateNoCommaString(validProductName)) {
                continue;
            }
            
            if (!validateIfProductNameIsRepeatedForUpdateWhereYouCanRepeatYourOriginalNameUwu(oldName, validProductName)) {
                continue;
            } 
            
            break;
        }
        
        return validProductName;
    }
}