package com.rswg3_5.techretailpro.models;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Product {
    final static String PRODUCT_DATABASE = "src/com/rswg3_5/techretailpro/databases/product.csv";
    
    private String productName;
    private String productCategory;
    private double productPrice;
    private int productStockQuantity;
    private String productDescription;

    public Product() {
    }

    public Product(String productName, String productCategory, double productPrice, int productStockQuantity, String productDescription) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productStockQuantity = productStockQuantity;
        this.productDescription = productDescription;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductStockQuantity() {
        return productStockQuantity;
    }

    public void setProductStockQuantity(int productStockQuantity) {
        this.productStockQuantity = productStockQuantity;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    @Override
    public String toString() {
        return "Name: " + productName + 
               "\nCategory: " + productCategory +
               "\nPrice: " + productPrice + 
               "\nCurrent Stock Quantity: " + productStockQuantity + 
               "\nDescription: " + productDescription;
    }
    
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
    
    public static void displayProductList(List<Product> productList) {
        System.out.printf("%-10s %-35s %-15s %-10s %-10s\n", "No", "Name", "Category", "Price", "Stock");
        
        for (int i = 0; i < productList.size(); i++) {
            System.out.printf("%-10d %-35s %-15s %-10.2f %-10d\n", (i+1), productList.get(i).getProductName(), productList.get(i).getProductCategory(), productList.get(i).getProductPrice(), productList.get(i).getProductStockQuantity());
        }
    }
    
    public static void displayCommand() {
        System.out.println("\nType [/sort {name/category/price} {asc/desc}] to sort the product");
        System.out.println("[/sort name asc] will sort every product alphabetically in ascending order\n");
        
        System.out.println("Type [/search {query}] to search product");
        System.out.println("[/search Keyboard a] will return every product that contains [Keyboard a] in its name\n");
        
        System.out.println("Type [/filter {category/price} {category/minPrice,maxPrice}] to filter product");
        System.out.println("[/filter category Keyboard] will return every product that is in the [Keyboard} category");
        System.out.println("[/filter category Keyboard,Mouse] will return every product that is in the [Keyboard} and [Mouse] category");
        System.out.println("[/filter price 10,20] will return every product that is in the price range of RM10 to Rm20\n");
        
        System.out.println("Type [/restore] to display the original list");
    }
    
    public void addToCart() {
        
    }
    
    public void directOrder() {
        
    }
}
