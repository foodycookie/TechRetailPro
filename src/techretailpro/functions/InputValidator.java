package techretailpro.functions;

import java.util.List;
import static techretailpro.functions.Utility.SCANNER;
import static techretailpro.functions.Utility.stringToInteger;
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
                System.out.println("Input cannot be blank");
                continue;
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
    
    /*
    double validPrice = 0;
    do {            
        String rawValidPrice = InputValidator.getUserInput("Enter new price (Leave blank to keep current)", "double", true);

        switch (rawValidPrice) {
            case "BLANK" -> validPrice = product.getPrice();

            case "EXIT" -> {
                System.out.println("Cancelling product update (Type enter to exit)");
                Utility.SCANNER.nextLine();
                MainPage.display();
            }

            default -> validPrice = Double.parseDouble(rawValidPrice);
        }
    } while (!InputValidator.validatePositiveOrZeroDouble(validPrice));
    */
    
    public static Integer numberOptionChooser(int minOption, int maxOption) {     
        String input;
        Integer numberInput;
        
        while(true) {
            System.out.print("Input > ");
            
            input = SCANNER.nextLine().trim();
            
            if (input.equalsIgnoreCase("exit")) {
                return null;
            }
            
            numberInput = stringToInteger(input);
            
            if (numberInput == null) {
                System.err.println("\nInvalid input. Please enter either {exit} or a whole number");
                continue;
            }

            if (numberInput < minOption || numberInput > maxOption) {
                System.err.println("\nInvalid choice. Please enter number from " + minOption + " to " + maxOption);
                continue;
            }

            break;
        }
        
        return numberInput;
    }
    
    /*
    System.out.println("please choose one, type {exit} to go back");
    System.out.println("1. Display yes");
    System.out.println("2. Display no");

    Integer input = Utility.numberOptionChooser(1, 2);

    if (input == null) {
        System.out.println("u exit");
        System.exit(0);
    }


    switch (input) {
        case 1 -> System.out.println("Yes");

        case 2 -> System.out.println("No");

        default -> System.out.println("input error");
    }
    */

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