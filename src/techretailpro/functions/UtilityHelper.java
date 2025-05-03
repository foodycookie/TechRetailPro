package techretailpro.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import techretailpro.objects.Headphone;
import techretailpro.objects.Keyboard;
import techretailpro.objects.Laptop;
import techretailpro.objects.Mouse;
import techretailpro.objects.Printer;
import techretailpro.objects.Product;

public class UtilityHelper {
    public final static String KEYBOARDS_DATABASE = "src/techretailpro/databases/keyboards.csv";
    public final static String MICE_DATABASE = "src/techretailpro/databases/mice.csv";
    public final static String LAPTOPS_DATABASE = "src/techretailpro/databases/laptops.csv";
    public final static String HEADPHONES_DATABASE = "src/techretailpro/databases/headphones.csv";
    public final static String PRINTERS_DATABASE = "src/techretailpro/databases/printers.csv";
    
    public final static String TEMP_KEYBOARDS_DATABASE = "src/techretailpro/databases/temp_keyboards.csv";
    public final static String TEMP_MICE_DATABASE = "src/techretailpro/databases/temp_mice.csv";
    public final static String TEMP_LAPTOPS_DATABASE = "src/techretailpro/databases/temp_laptops.csv";
    public final static String TEMP_HEADPHONES_DATABASE = "src/techretailpro/databases/temp_headphones.csv";
    public final static String TEMP_PRINTERS_DATABASE = "src/techretailpro/databases/temp_printers.csv";
    
    public final static int DATA_PER_PAGE = 20;
    
    public final static String USERS_DATABASE = "src\\techretailpro\\databases\\users.csv";
    public final static String TEMP_USERS_DATABASE = "src\\techretailpro\\databases\\temp.csv";
    
    public final static String ORDER_HISTORY_DATABASE = "src/techretailpro/databases/order_history.txt";
    public final static String ORDER_HISTORY_DATABASE_HEADER = String.format("%-10s %-20s %-10s %-10s %-20s", "Username", "ProductName", "Quantity", "Subtotal", "Date/Time");

    public final static Scanner SCANNER = new Scanner(System.in);
    
    public final static String BACK_CONSTANT = "BACK";
    public final static String BLANK_CONSTANT = "BLANK";

    public static void clearConsole() {
        for (int i = 0; i < 10; i++) {
            System.out.println("");
        }
        
//        System.out.print("\033[H\033[2J");
    }

    public static boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static Integer stringToInteger(String string) {
        String s = string.trim();
        
        if (isInteger(s)) {
            return Integer.valueOf(s);
        }
        
        return null;
    }
    
    public static boolean isDouble(String string) {
        try {
            Double.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static Double stringToDouble(String string) {
        String s = string.trim();
        
        if (isDouble(s)) {
            return Double.valueOf(s);
        }
        
        return null;
    }
    
    public static boolean isBoolean(String string) {
        if (string == null) {
            return false;
        }
        
        String s = string.trim().toLowerCase();
        
        return s.equals("true") || s.equals("false");
    }
    
    public static Boolean stringToBoolean(String string) {
        if (string == null) {
            return null;
        }
        
        String s = string.trim().toLowerCase();
        
        if (s.equals("true")) {
            return true;
        }
        
        else if (s.equals("false")) {
            return false;
        }
        
        return null;
    }
    
    public static String getUserInput(String prompt, String type, boolean allowBlank) {
        String input;
        
        while (true) {
            if (prompt != null) {
                StringBuilder message = new StringBuilder("\n" + prompt);

                if (type.equalsIgnoreCase("boolean")) {
                    message.append(" (true / false)");
                }

                if (allowBlank) {
                    message.append(" (Leave blank to keep original)");
                }

                message.append(": ");
                System.out.print(message);
            }
            
            input = UtilityHelper.SCANNER.nextLine().trim();
            

            if (input.equalsIgnoreCase(BACK_CONSTANT)) {
                return BACK_CONSTANT;
            }

            if (input.isEmpty()) {
                if (allowBlank) {
                    return BLANK_CONSTANT;
                } 
                
                else {
                    System.err.println("Invalid input. Input cannot be blank");
                    continue;
                }
            }

            switch (type.toLowerCase()) {
                case "int" -> {
                    try {
                        Integer.valueOf(input);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid input. Please enter either {back} or a valid integer");
                        continue;
                    }
                    
                    return input;
                }

                case "double" -> {
                    try {
                        Double.valueOf(input);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid input. Please enter either {back} or a valid double");
                        continue;
                    }
                    
                    return input;
                }

                case "boolean" -> {
                    if (!input.equalsIgnoreCase("true") && !input.equalsIgnoreCase("false")) {
                        System.err.println("Invalid input. Please enter either {back} or true / false");
                        continue;
                    }
                    
                    return input;
                }

                case "string" -> {
                    if (input.contains(",")) {
                        System.err.println("Invalid input. Commas are not allowed");
                        continue;
                    }
                    
                    return input;
                }

                default -> {
                    System.err.println("Unsupported type: " + type);
                    return null;
                }
            }
        }
    }
    
    public static Integer numberOptionChooser(String prompt, int minOption, int maxOption) {     
        String input;
        Integer numberInput;
        
        while(true) {        
            if (prompt != null) {
                System.out.print("\n" + prompt + ": ");
            }

            input = SCANNER.nextLine().trim();
            
            if (input.equalsIgnoreCase(BACK_CONSTANT)) {
                return null;
            }
            
            numberInput = stringToInteger(input);
            
            if (numberInput == null) {
                System.err.println("Invalid input. Please enter either {back} or a whole number");
                continue;
            }

            if (numberInput < minOption || numberInput > maxOption) {
                System.err.println("Invalid choice. Please enter number from " + minOption + " to " + maxOption);
                continue;
            }

            break;
        }
        
        return numberInput;
    }
    
    public static void displayReturnMessage(String message) {
        if (message == null || message.isBlank()) {
            System.out.print("\nPress enter to proceed");
        }
        
        else {
            System.out.print("\n" + message + " (Press enter to proceed)");
        }
        
        UtilityHelper.SCANNER.nextLine();
    }
    
    public static void productGenerator() {
        List<Product> list = new ArrayList<>();
        Keyboard keyboard;
        Mouse mouse;
        Laptop laptop;
        Headphone headphone;
        Printer printer;

        for (int i = 1; i < 201; i++) {
            if (i % 2 != 0) {
                keyboard = new Keyboard(("Keyboard " + i), (i+20), 100, "A basic keyboard", false, "Scissor switch", false);
            }
            
            else {
                keyboard = new Keyboard(("Keyboard " + i), (i+50), 50, "A slightly better keyboard", true, "Mechanical", true);    
            }
            
            list.add(keyboard);
        }
        
        for (int i = 1; i < 201; i++) {
            if (i % 2 != 0) {
                mouse = new Mouse(("Mouse " + i), (i+20), 100, "A basic mouse", false, "Optical", 1000);
            }
            
            else {
                mouse = new Mouse(("Mouse " + i), (i+40), 50, "A slightly better mouse", true, "Laser", 2500);    
            }
            
            list.add(mouse);
        }
        
        for (int i = 1; i < 201; i++) {
            if (i % 2 != 0) {
                laptop = new Laptop(("Laptop " + i), (i+1000), 100, "A basic laptop", "AMD Ryzen 3 3100", 8, 128);
            }
            
            else {
                laptop = new Laptop(("Laptop " + i), (i+2000), 50, "A slightly better laptop", "AMD Ryzen 5 3600", 16, 256);    
            }
            
            list.add(laptop);
        }
        
        for (int i = 1; i < 201; i++) {
            if (i % 2 != 0) {
                headphone = new Headphone(("Headphone " + i), (i+80), 100, "A basic headphone", false, false, false);
            }
            
            else {
                headphone = new Headphone(("Headphone " + i), (i+160), 50, "A slightly better headphone", true, true, true);    
            }
            
            list.add(headphone);
        }
        
        for (int i = 1; i < 201; i++) {
            if (i % 2 != 0) {
                printer = new Printer(("Printer " + i), (i+80), 100, "A basic printer", false, 15, "Dye based");
            }
            
            else {
                printer = new Printer(("Printer " + i), (i+160), 50, "A slightly better printer", true, 30, "Pigment");    
            }
            
            list.add(printer);
        }
        
        for (Product product : list) {
            ProductDatabaseManager.appendProductCsv(product);
        }
    }
}