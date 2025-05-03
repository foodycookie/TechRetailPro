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

public class ProductDatabaseFunctions {
    public static List<Product> fetchProducts(Product product) {
        if (product == null) {
            System.err.println("Instance is null");
            return null;
        }
        
        File file = new File(product.getCsvFilePath());
        List<Product> products = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String row;
            boolean isFirstLine = true;
    
            while ((row = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                String[] fields = row.split(",", -1);
                
                if (fields.length != 8) {
                    System.err.println("Skipping malformed line: " + Arrays.toString(fields));
                    continue;
                }
                
                switch (product.getCategory().toLowerCase()) {
                    case "keyboard" -> {
                        Keyboard keyboard = new Keyboard(
                                fields[1].trim(),
                                Utility.stringToDouble(fields[2].trim()),
                                Utility.stringToInteger(fields[3].trim()),
                                fields[4].trim(),
                                Utility.stringToBoolean(fields[5].trim()),
                                fields[6].trim(),
                                Utility.stringToBoolean(fields[7].trim()));
                        
                        if (fields[0] == null || fields[1] == null || fields[2] == null || fields[3] == null || fields[4] == null || fields[5] == null || fields[6] == null || fields[7] == null) {
                            if (fields[1] == null) {
                                System.err.println("Cannot add this product, the name is null");
                                continue;
                            }
                            
                            else {
                                System.err.println("Cannot add this product, " + fields[1]);
                                continue;
                            }
                        }
                        
                        products.add(keyboard);
                    }
                    
                    case "mouse" -> {
                        Mouse mouse = new Mouse(
                                fields[1].trim(),
                                Utility.stringToDouble(fields[2].trim()),
                                Utility.stringToInteger(fields[3].trim()),
                                fields[4].trim(),
                                Utility.stringToBoolean(fields[5].trim()),
                                fields[6].trim(),
                                Utility.stringToInteger(fields[7].trim()));
                        
                        if (fields[0] == null || fields[1] == null || fields[2] == null || fields[3] == null || fields[4] == null || fields[5] == null || fields[6] == null || fields[7] == null) {
                            if (fields[1] == null) {
                                System.err.println("Cannot add this product, the name is null");
                                continue;
                            }
                            
                            else {
                                System.err.println("Cannot add this product, " + fields[1]);
                                continue;
                            }
                        }
                        
                        products.add(mouse);
                    }
                    
                    case "laptop" -> {
                        Laptop laptop = new Laptop(
                                fields[1].trim(),
                                Utility.stringToDouble(fields[2].trim()),
                                Utility.stringToInteger(fields[3].trim()),
                                fields[4].trim(),
                                fields[5].trim(),
                                Utility.stringToInteger(fields[6].trim()),
                                Utility.stringToInteger(fields[7].trim()));
                        
                        if (fields[0] == null || fields[1] == null || fields[2] == null || fields[3] == null || fields[4] == null || fields[5] == null || fields[6] == null || fields[7] == null) {
                            if (fields[1] == null) {
                                System.err.println("Cannot add this product, the name is null");
                                continue;
                            }
                            
                            else {
                                System.err.println("Cannot add this product, " + fields[1]);
                                continue;
                            }
                        }
                        
                        products.add(laptop);
                    }
                    
                    case "headphone" -> {
                        Headphone headphone = new Headphone(
                                fields[1].trim(),
                                Utility.stringToDouble(fields[2].trim()),
                                Utility.stringToInteger(fields[3].trim()),
                                fields[4].trim(),
                                Utility.stringToBoolean(fields[5].trim()),
                                Utility.stringToBoolean(fields[6].trim()),
                                Utility.stringToBoolean(fields[7].trim()));
                        
                        if (fields[0] == null || fields[1] == null || fields[2] == null || fields[3] == null || fields[4] == null || fields[5] == null || fields[6] == null || fields[7] == null) {
                            if (fields[1] == null) {
                                System.err.println("Cannot add this product, the name is null");
                                continue;
                            }
                            
                            else {
                                System.err.println("Cannot add this product, " + fields[1]);
                                continue;
                            }
                        }
                        
                        products.add(headphone);
                    }
                    
                    case "printer" -> {
                        Printer printer = new Printer(
                                fields[1].trim(),
                                Utility.stringToDouble(fields[2].trim()),
                                Utility.stringToInteger(fields[3].trim()),
                                fields[4].trim(),
                                Utility.stringToBoolean(fields[5].trim()),
                                Utility.stringToDouble(fields[6].trim()),
                                fields[7].trim());
                        
                        if (fields[0] == null || fields[1] == null || fields[2] == null || fields[3] == null || fields[4] == null || fields[5] == null || fields[6] == null || fields[7] == null) {
                            if (fields[1] == null) {
                                System.err.println("Cannot add this product, the name is null");
                                continue;
                            }
                            
                            else {
                                System.err.println("Cannot add this product, " + fields[1]);
                                continue;
                            }
                        }
                        
                        products.add(printer);
                    }
                    
                    default -> System.err.println("No such category");
                } 
            }
        } catch (IOException e) {
            System.err.println("Error fetching product: " + e.getMessage());
        }
    
        return products;
    }
    
    public static List<Product> fetchAllProducts() {
        List<Product> list = new ArrayList<>();
        
        list.addAll(fetchProducts(new Keyboard()));
        list.addAll(fetchProducts(new Mouse()));
        list.addAll(fetchProducts(new Laptop()));
        list.addAll(fetchProducts(new Headphone()));
        list.addAll(fetchProducts(new Printer()));
        
        return list;
    }
    
    public static void appendProductCsv(Product product) {
        if (product == null) {
            System.err.println("Instance is null");
            return;
        }
        
        File file = new File(product.getCsvFilePath());
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append(product.toStringForCsv()+ "\n");
        } catch (IOException e) {
            System.err.println("Error appending product: " + e.getMessage());
        }
    }
    
    public static void updateProductCsv(Product originalProduct, Product updatedProduct) {
        File originalFile = new File(originalProduct.getCsvFilePath());
        File tempFile = new File(originalProduct.getCsvTempFilePath());
        
        try (
            BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String row;
            boolean isFirstLine = true;
    
            while ((row = reader.readLine()) != null) {
                if (isFirstLine) {
                    writer.write(row + "\n");
                    isFirstLine = false;
                    continue;
                }
    
                String[] fields = row.split(",", -1);
    
                if (fields.length > 1 && fields[1].equalsIgnoreCase(originalProduct.getName())) {
                    writer.write(updatedProduct.toStringForCsv()+ "\n");
                } 
                
                else {
                    writer.write(row + "\n");
                }
            }
    
        } catch (IOException e) {
            System.err.println("Error updating product: " + e.getMessage());
            return;
        }
    
        if (!originalFile.delete()) {
            System.err.println("Failed to delete original file");
        } 
        
        else if (!tempFile.renameTo(originalFile)) {
            System.err.println("Failed to rename temp file to original");
        }
    }
    
    public static void deleteProductCsv(Product product) {
        File originalFile = new File(product.getCsvFilePath());
        File tempFile = new File(product.getCsvTempFilePath());
        
        try (
            BufferedReader reader = new BufferedReader(new FileReader(originalFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String row;
            boolean isFirstLine = true;
    
            while ((row = reader.readLine()) != null) {
                if (isFirstLine) {
                    writer.write(row + "\n");
                    isFirstLine = false;
                    continue;
                }
    
                String[] fields = row.split(",", -1);
                
                if (fields.length > 1 && fields[1].equalsIgnoreCase(product.getName())) {
                    continue;
                }
    
                writer.write(row + "\n");
            }
    
        } catch (IOException e) {
            System.err.println("Error deleting product: " + e.getMessage());
            return;
        }
    
        if (!originalFile.delete()) {
            System.err.println("Failed to delete original file");
        } 
        
        else if (!tempFile.renameTo(originalFile)) {
            System.err.println("Failed to rename temp file to original");
        }
    }
        
    public static void initializeProductCsv(Product product) {
        File file = new File(product.getCsvFilePath());
        
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(product.getCsvHeader() + "\n");
            } catch (IOException e) {
                System.err.println("Error creating file: " + e.getMessage());
            }
        }
    }
    
    public static void initializeAllProductCsv() {
        initializeProductCsv(new Keyboard());
        initializeProductCsv(new Mouse());
        initializeProductCsv(new Laptop());
        initializeProductCsv(new Headphone());
        initializeProductCsv(new Printer());
    }
}