package techretailpro.functions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import techretailpro.objects.Headphone;
import techretailpro.objects.Keyboard;
import techretailpro.objects.Laptop;
import techretailpro.objects.Mouse;
import techretailpro.objects.Printer;
import techretailpro.objects.Product;
import techretailpro.utilities.Utility;
import static techretailpro.utilities.Utility.HEADPHONES_DATABASE;
import static techretailpro.utilities.Utility.KEYBOARDS_DATABASE;
import static techretailpro.utilities.Utility.LAPTOPS_DATABASE;
import static techretailpro.utilities.Utility.MICE_DATABASE;
import static techretailpro.utilities.Utility.PRINTERS_DATABASE;

public class DatabaseManager {
    public static List<String[]> readCSV(File file) {
        if (file == null) {
            System.err.println("File not found");
            return null;
        }
        
        List<String[]> separatedRows = new ArrayList<>();
        
        String row;
        boolean firstRow = true;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((row = reader.readLine()) != null) {
                if (firstRow) {
                    firstRow = false;
                    continue;
                }
                
                String[] separatedRow = row.split(",");

                separatedRows.add(separatedRow);
            }
        } catch (IOException ex) {
            System.err.println("Error occured while reading file: " + file);
        }
        
        return separatedRows;
    }
    
    public static List<Product> fetchAllProducts() {
        List<Product> list = new ArrayList<>();

        if (fetchKeyboards() != null) {
            list.addAll(fetchKeyboards());
        }
        
        if (fetchMice() != null) {
            list.addAll(fetchMice());
        }
        
        if (fetchLaptops() != null) {
            list.addAll(fetchLaptops());
        }
        
        if (fetchHeadphones() != null) {
            list.addAll(fetchHeadphones());
        }
        
        if (fetchPrinters() != null) {
            list.addAll(fetchPrinters());
        }
        
        return list;
    }
    
    public static List<Keyboard> fetchKeyboards() {
        List<Keyboard> list = new ArrayList<>();
        List<String[]> separatedRows = readCSV(new File(Utility.KEYBOARDS_DATABASE));
        
        if (separatedRows == null) {
            System.err.println("No keyboard available");
            return null;
        }
        
        for (String[] separatedRow : separatedRows) {
            String rawCategory = separatedRow[0].trim();
            String rawName = separatedRow[1].trim();
            String rawPrice = separatedRow[2].trim();
            String rawStock = separatedRow[3].trim();
            String rawDescription = separatedRow[4].trim();

            String category = rawCategory;
            String name = rawName;
            Double price = Utility.stringToDouble(rawPrice);
            Integer stock = Utility.stringToInteger(rawStock);
            String description = rawDescription;

            String rawWireless = separatedRow[5].trim();
            String rawKeytype = separatedRow[6].trim();
            String rawRgb = separatedRow[7].trim();

            Boolean wireless = Utility.stringToBoolean(rawWireless);
            String keytype = rawKeytype;
            Boolean rgb = Utility.stringToBoolean(rawRgb);
            
            if (category == null || name == null || price == null || stock == null || description == null || wireless == null || keytype == null || rgb == null) {
                if (name == null) {
                    System.err.println("Cannot add this product, this name is null");
                }
                
                else {
                    System.err.println("Cannot add this product: " + name);
                }
                
                continue;
            }
            
            Keyboard keyboard = new Keyboard(name, price, stock, description, wireless, keytype, rgb);

            list.add(keyboard);
        }

        return list;
    }
    
    public static List<Mouse> fetchMice() {
        List<Mouse> list = new ArrayList<>();
        List<String[]> separatedRows = readCSV(new File(Utility.MICE_DATABASE));
        
        if (separatedRows == null) {
            System.err.println("No mouse available");
            return null;
        }
        
        for (String[] separatedRow : separatedRows) {
            String rawCategory = separatedRow[0].trim();
            String rawName = separatedRow[1].trim();
            String rawPrice = separatedRow[2].trim();
            String rawStock = separatedRow[3].trim();
            String rawDescription = separatedRow[4].trim();

            String category = rawCategory;
            String name = rawName;
            Double price = Utility.stringToDouble(rawPrice);
            Integer stock = Utility.stringToInteger(rawStock);
            String description = rawDescription;

            String rawWireless = separatedRow[5].trim();
            String rawSensorType = separatedRow[6].trim();
            String rawDpi = separatedRow[7].trim();

            Boolean wireless = Utility.stringToBoolean(rawWireless);
            String sensorType = rawSensorType;
            Integer dpi = Utility.stringToInteger(rawDpi);
            
            if (category == null || name == null || price == null || stock == null || description == null || wireless == null || sensorType == null || dpi == null) {
                if (name == null) {
                    System.err.println("Cannot add this product, this name is null");
                }
                
                else {
                    System.err.println("Cannot add this product: " + name);
                }
                
                continue;
            }
            
            Mouse mouse = new Mouse(name, price, stock, description, wireless, sensorType, dpi);

            list.add(mouse);
        }

        return list;
    }
    
    public static List<Laptop> fetchLaptops() {
        List<Laptop> list = new ArrayList<>();
        List<String[]> separatedRows = readCSV(new File(Utility.LAPTOPS_DATABASE));
        
        if (separatedRows == null) {
            System.err.println("No laptop available");
            return null;
        }
        
        for (String[] separatedRow : separatedRows) {
            String rawCategory = separatedRow[0].trim();
            String rawName = separatedRow[1].trim();
            String rawPrice = separatedRow[2].trim();
            String rawStock = separatedRow[3].trim();
            String rawDescription = separatedRow[4].trim();

            String category = rawCategory;
            String name = rawName;
            Double price = Utility.stringToDouble(rawPrice);
            Integer stock = Utility.stringToInteger(rawStock);
            String description = rawDescription;

            String rawProcessor = separatedRow[5].trim();
            String rawMemory = separatedRow[6].trim();
            String rawStorage = separatedRow[7].trim();

            String processor = rawProcessor;
            Integer memory = Utility.stringToInteger(rawMemory);
            Integer storage = Utility.stringToInteger(rawStorage);
            
            if (category == null || name == null || price == null || stock == null || description == null || processor == null || memory == null || storage == null) {
                if (name == null) {
                    System.err.println("Cannot add this product, this name is null");
                }
                
                else {
                    System.err.println("Cannot add this product: " + name);
                }
                
                continue;
            }
            
            Laptop laptop = new Laptop(name, price, stock, description, processor, memory, storage);

            list.add(laptop);
        }

        return list;
    }
    
    public static List<Headphone> fetchHeadphones() {
        List<Headphone> list = new ArrayList<>();
        List<String[]> separatedRows = readCSV(new File(Utility.HEADPHONES_DATABASE));
        
        if (separatedRows == null) {
            System.err.println("No headphone available");
            return null;
        }
        
        for (String[] separatedRow : separatedRows) {
            String rawCategory = separatedRow[0].trim();
            String rawName = separatedRow[1].trim();
            String rawPrice = separatedRow[2].trim();
            String rawStock = separatedRow[3].trim();
            String rawDescription = separatedRow[4].trim();

            String category = rawCategory;
            String name = rawName;
            Double price = Utility.stringToDouble(rawPrice);
            Integer stock = Utility.stringToInteger(rawStock);
            String description = rawDescription;

            String rawWireless = separatedRow[5].trim();
            String rawNoiseCancellation = separatedRow[6].trim();
            String rawMicrophone = separatedRow[7].trim();

            Boolean wireless = Utility.stringToBoolean(rawWireless);
            Boolean noiseCancellation = Utility.stringToBoolean(rawNoiseCancellation);
            Boolean microphone = Utility.stringToBoolean(rawMicrophone);
            
            if (category == null || name == null || price == null || stock == null || description == null || wireless == null || noiseCancellation == null || microphone == null) {
                if (name == null) {
                    System.err.println("Cannot add this product, this name is null");
                }
                
                else {
                    System.err.println("Cannot add this product: " + name);
                }
                
                continue;
            }
            
            Headphone headphone = new Headphone(name, price, stock, description, wireless, noiseCancellation, microphone);

            list.add(headphone);
        }

        return list;
    }
    
    public static List<Printer> fetchPrinters() {
        List<Printer> list = new ArrayList<>();
        List<String[]> separatedRows = readCSV(new File(Utility.PRINTERS_DATABASE));
        
        if (separatedRows == null) {
            System.err.println("No printer available");
            return null;
        }
        
        for (String[] separatedRow : separatedRows) {
            String rawCategory = separatedRow[0].trim();
            String rawName = separatedRow[1].trim();
            String rawPrice = separatedRow[2].trim();
            String rawStock = separatedRow[3].trim();
            String rawDescription = separatedRow[4].trim();

            String category = rawCategory;
            String name = rawName;
            Double price = Utility.stringToDouble(rawPrice);
            Integer stock = Utility.stringToInteger(rawStock);
            String description = rawDescription;

            String rawWireless = separatedRow[5].trim();
            String rawPrintSpeed = separatedRow[6].trim();
            String rawInkType = separatedRow[7].trim();

            Boolean wireless = Utility.stringToBoolean(rawWireless);
            Double printSpeed = Utility.stringToDouble(rawPrintSpeed);
            String inkType = rawInkType;
            
            if (category == null || name == null || price == null || stock == null || description == null || wireless == null || printSpeed == null || inkType == null) {
                if (name == null) {
                    System.err.println("Cannot add this product, this name is null");
                }
                
                else {
                    System.err.println("Cannot add this product: " + name);
                }
                
                continue;
            }
            
            Printer printer = new Printer(name, price, stock, description, wireless, printSpeed, inkType);

            list.add(printer);
        }

        return list;
    }
    
    public static void appendProductToDatabase(Product product) {
        if (product == null) {
            System.err.println("Instance is null");
            return;
        }
        
        String file;
        
        switch (product.getCategory()) {
            case "Keyboard" -> file = Utility.KEYBOARDS_DATABASE;
            
            case "Mouse" -> file = Utility.MICE_DATABASE;

            case "Laptop" -> file = Utility.LAPTOPS_DATABASE;

            case "Headphone" -> file = Utility.HEADPHONES_DATABASE;

            case "Printer" -> file = Utility.PRINTERS_DATABASE;

            default -> {
                System.err.println("Invalid category");
                return;
            }
        }
        
        //Append mode, will not override the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append(product.toStringForDatabase() + "\n");
        } catch (IOException ex) {
            System.err.println("Error occured while writing file: " + file);
        }
    }
    
    public static void rewriteProductCSV(List<Product> list, File originalFile, File tempFile, String header) {
        if (list == null || originalFile == null || tempFile == null || header == null) {
            System.err.println("Invalid arguments provided");
            return;
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.append(header);
            
            for (Product product : list) {
                writer.append(product.toStringForDatabase() + "\n");
            }         
        } catch (IOException ex) {
            System.err.println("Error occured while writing temp file:" + tempFile);
        }
        
        if (originalFile.exists() && !originalFile.delete()) {
            System.err.println("File cannot be deleted: " + originalFile);
        }
        
        if (!tempFile.renameTo(originalFile)) {
            System.err.println("File cannot be renamed: " + tempFile);
        }        
    }
    
    public static void rewriteAllProducts(List<Product> list) {
        if (list == null) {
            System.err.println("List is null");
            return;
        }
        
        List<Keyboard> keyboards = new ArrayList<>();
        List<Mouse> mice = new ArrayList<>();
        List<Laptop> laptops = new ArrayList<>();
        List<Headphone> headphones = new ArrayList<>();
        List<Printer> printers = new ArrayList<>();
        
        
        for (Product product : list) {
            if (product == null) {
                System.err.println("Product is null");
                continue;
            }
            
            String category = product.getCategory();
            
            switch (category.toLowerCase()) {
                case "keyboard" -> keyboards.add((Keyboard) product);
                
                case "mouse" -> mice.add((Mouse) product);
                    
                case "laptop" -> laptops.add((Laptop) product);
                
                case "headphone" -> headphones.add((Headphone) product);
                
                case "printer" -> printers.add((Printer) product);
                    
                default -> System.err.println("Invalid category");
            }
        }
        
        rewriteKeyboards(keyboards);
        rewriteMice(mice);
        rewriteLaptops(laptops);
        rewriteHeadphones(headphones);
        rewritePrinters(printers);
    }
    
    public static void rewriteKeyboards(List<Keyboard> list) {
        List<Product> productList = new ArrayList<>();
        
        productList.addAll(list);

        rewriteProductCSV(productList, new File(Utility.KEYBOARDS_DATABASE), new File(Utility.TEMP_KEYBOARDS_DATABASE), "Category,Name,Price,Stock,Description,Wireless,KeyType,RGB\n");
    }
    
    public static void rewriteMice(List<Mouse> list) {
        List<Product> productList = new ArrayList<>();
        
        productList.addAll(list);

        rewriteProductCSV(productList, new File(Utility.MICE_DATABASE), new File(Utility.TEMP_MICE_DATABASE), "Category,Name,Price,Stock,Description,Wireless,SensorType,DPI\n");
    }
    
    public static void rewriteLaptops(List<Laptop> list) {
        List<Product> productList = new ArrayList<>();
        
        productList.addAll(list);

        rewriteProductCSV(productList, new File(Utility.LAPTOPS_DATABASE), new File(Utility.TEMP_LAPTOPS_DATABASE), "Category,Name,Price,Stock,Description,Processor,Memory,Storage\n");
    }
    
    public static void rewriteHeadphones(List<Headphone> list) {
        List<Product> productList = new ArrayList<>();
        
        productList.addAll(list);

        rewriteProductCSV(productList, new File(Utility.HEADPHONES_DATABASE), new File(Utility.TEMP_HEADPHONES_DATABASE), "Category,Name,Price,Stock,Description,Wireless,NoiseCancellation,Microphone\n");
    }
    
    public static void rewritePrinters(List<Printer> list) {
        List<Product> productList = new ArrayList<>();
        
        productList.addAll(list);

        rewriteProductCSV(productList, new File(Utility.PRINTERS_DATABASE), new File(Utility.TEMP_PRINTERS_DATABASE), "Category,Name,Price,Stock,Description,Wireless,PrintSpeed,InkType\n");
    }
    
    public static void initializeDatabaseFile() {
        if (!new File(KEYBOARDS_DATABASE).exists()) {
            DatabaseManager.rewriteKeyboards(Arrays.asList(new Keyboard("Adam", 930, 1, "One of the first product", false, "Not found", false)));
        }
        
        if (!new File(MICE_DATABASE).exists()) {
            DatabaseManager.rewriteMice(Arrays.asList(new Mouse("Eve", 930, 1, "One of the first product", false, "Not found", 0)));
        }
        
        if (!new File(LAPTOPS_DATABASE).exists()) {
            DatabaseManager.rewriteLaptops(Arrays.asList(new Laptop("Cain", 860, 1, "One of the first product", "Not found", 0, 0)));
        }
        
        if (!new File(HEADPHONES_DATABASE).exists()) {
            DatabaseManager.rewriteHeadphones(Arrays.asList(new Headphone("Abel", 122, 1, "One of the first product", false, false, false)));
        }
        
        if (!new File(PRINTERS_DATABASE).exists()) {
            DatabaseManager.rewritePrinters(Arrays.asList(new Printer("Seth", 912, 1, "One of the first product", false, 0, "Not found")));
        }
    }
}