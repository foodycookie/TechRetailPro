package techretailpro.functions;

import java.util.List;
import techretailpro.objects.LocalData;
import techretailpro.objects.Product;

public class InputValidator {
    public static String getUserInput(String prompt, String type, boolean allowBlank) {
        String input;
        
        while (true) {
            System.out.println("\n" + prompt);
            System.out.print("Input > ");
            input = Utility.SCANNER.nextLine().trim();

            if (allowBlank) {
                if (input.isEmpty()) {
                    return "BLANK";
                }
            }
            
            if (input.equalsIgnoreCase("exit")) {
                return "EXIT";
            }

            switch (type.toLowerCase()) {
                case "int" -> {
                    try {
                        Integer.valueOf(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid integer");
                        continue;
                    }
                    
                    return input;
                }

                case "double" -> {
                    try {
                        Double.valueOf(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid double");
                        continue;
                    }
                    
                    return input;
                }

                case "boolean" -> {
                    if (!input.equalsIgnoreCase("true") && !input.equalsIgnoreCase("false")) {
                        System.out.println("Please enter true or false");
                        continue;
                    }
                    
                    return input;
                }

                case "string" -> {
                    if (input.contains(",")) {
                        System.out.println("Commas are not allowed");
                        continue;
                    }
                    
                    return input;
                }

                default -> {
                    System.out.println("Unsupported type: " + type);
                    return null;
                }
            }
        }
    }


    
    
    
    


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
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
    
    public static boolean validateOneToOneHundred(int intValue) {
        if (intValue <= 0 || intValue > 100) {
            System.err.println("\nInvalid input. Input must be between 1 and 100");
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
        List<Product> list = LocalData.getCurrentProductsAvailable();

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
        List<Product> list = LocalData.getCurrentProductsAvailable();

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
    
    public static Integer getOneToOneHundredInt() {
        Integer oneToOneHundredInt;
        
        while (true) {            
            oneToOneHundredInt = getInt();
            
            if (oneToOneHundredInt == null) {
                return null;
            }
            
            if (!validateOneToOneHundred(oneToOneHundredInt)) {
                continue;
            }
            
            break;
        }
        
        return oneToOneHundredInt;
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