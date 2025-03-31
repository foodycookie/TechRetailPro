package com.rswg3_5.techretailpro.models;

import static com.rswg3_5.techretailpro.main.TechRetailPro.optionInput;
import static com.rswg3_5.techretailpro.main.TechRetailPro.scanner;
import com.rswg3_5.techretailpro.utils.Utility;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
    final static String PRODUCT_DATABASE = "src/com/rswg3_5/techretailpro/databases/product.csv";
    
    private String username;
    private String userEmail;
    private String userPassword;

    public User() {
    }

    public User(String username, String userEmail, String userPassword) {
        this.username = username;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", userEmail=" + userEmail + ", userPassword=" + userPassword + '}';
    }
    
    public void register() {
        
    }
    
    public void login() {
        
    }
    
    public void logout() {
        
    }
    
    public static void listProduct() {        
        final int MAX_OPTION = fetchProduct().size() + 1;
        
        Utility.border();
        displayProduct();
         
        System.out.println("\nPlease select a product by number to view its details (Type 0 to go back)");
        System.out.println("For more commands, type /help");
        
        while(true) {
            System.out.print("\nOption > ");
            
            // /help /sort /search /filter 0 number
            if (true) {
                displayCommand();
            }
            
            else if (true) {
                
            }
            
            else if (true) {
                
            }
            
            else if (true) {
                
            }
            
            else if (true) {
                
            }
            
            else if (true) {
                
            }
            
            else {
                
            }
            
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter number from 1 to " + MAX_OPTION);
                scanner.next();
                continue;
            }
            
            optionInput = scanner.nextInt();
            
            if (optionInput < 1 || optionInput > MAX_OPTION) {
                System.out.println("Invalid choice. Please enter number from 1 to " + MAX_OPTION);
                continue;
            }
            
            break;
        }
    }
    
    public void viewProduct() {
        
    }
    
    public void sortProduct() {
        
    }
    
    public void searchProduct() {
        
    }
    
    public void filterProduct() {
        
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static List<Product> fetchProduct() {
        List<Product> productList = new ArrayList<>();
        
        String row;
        boolean firstRow = true;
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_DATABASE));
            
            while ((row = reader.readLine()) != null) {                
                if (firstRow) {
                    firstRow = false;
                    continue;
                }
                
                String[] separatedRow = row.split(",");
                
                productList.add(new Product(separatedRow[0], separatedRow[1], Double.parseDouble(separatedRow[2]), Integer.parseInt(separatedRow[3]), separatedRow[4]));
            }
            
            reader.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return productList;
    }
    
    public static void displayProduct() {
        List<Product> productList = fetchProduct();
        
        System.out.printf("%-10s %-35s %-15s %-10s %-10s\n", "No", "Name", "Category", "Price", "Stock");
        
        for (int i = 0; i < productList.size(); i++) {
            System.out.printf("%-10d %-35s %-15s %-10.2f %-10d\n", (i+1), productList.get(i).getProductName(), productList.get(i).getProductCategory(), productList.get(i).getProductPrice(), productList.get(i).getProductStockQuantity());
        }
    }
    
    public static void displayCommand() {
        System.out.println("Type [/sort {name/category/price/quantity} {asc/desc}] to sort the product");
        System.out.println("[/sort name asc] will sort the product alphabetically in ascending order\n");
        
        System.out.println("Type /search {itemName} to search product");
        System.out.println("[/search keyboard a] will return product that contains [keyboard a] in its name (Not case sensitive)\n");
        
//        System.out.println("Type /filter {name/category/price/quantity} to enter filter menu");
//        System.out.println("Type /filter {name/category/price/quantity} to enter filter menu");
//        name category price quantity
    }
}