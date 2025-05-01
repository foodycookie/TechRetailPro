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
            
            else {
                
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
}