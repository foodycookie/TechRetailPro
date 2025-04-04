package com.rswg3_5.techretailpro.models;

import com.rswg3_5.techretailpro.utils.Utility;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Product {
    //Final value, not sure if will move to another place, DATA_PER_PAGE needs to be changeable
    final static String PRODUCT_DATABASE = "src/com/rswg3_5/techretailpro/databases/product.csv";
    public final static int DATA_PER_PAGE = 50;
    
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
    
    //SUB-OPTIMAL READ FUNCTION
    //Need to handle error
    public static List<Product> fetchProduct() {
        List<Product> productList = new ArrayList<>();
        
        String row;
        boolean firstRow = true;
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_DATABASE));
            
            //Skipping the first row, which is the header
            //Read until there is nothing left
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
    
    //SUB-OPTIMAL WRITE FUNCTION
    //Need to handle error
    //IF YOU WANT TO USE REMEMBER TO SET THE APPEND TO TRUE OR ELSE IT WILL OVERRIDE THE DATABASE
    public static void writeProduct(Product product) {
        try {
            //new FileWriter(PRODUCT_DATABASE, true), the true is the append option
            BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCT_DATABASE, true));
            
            writer.append(product.getProductName() + "," + product.getProductCategory() + "," + product.getProductPrice() + "," + product.getProductStockQuantity() + "," + product.getProductDescription() + "\n");

            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Display only x amount of product per page
    public static void displayProductList(List<Product> productList, int currentPage) {
        int dataPerPage = DATA_PER_PAGE;
        int totalPage = (int) Math.ceil((double) productList.size() / dataPerPage);        
        int start = (currentPage - 1) * dataPerPage;
        int end = Math.min(start + dataPerPage, productList.size());

        System.out.printf("%-10s %-35s %-15s %-10s %-10s\n", "No", "Name", "Category", "Price", "Stock");
        
        for (int i = start; i < end; i++) {
            System.out.printf("%-10d %-35s %-15s %-10.2f %-10d\n", (i+1), productList.get(i).getProductName(), productList.get(i).getProductCategory(), productList.get(i).getProductPrice(), productList.get(i).getProductStockQuantity());
        }
        
        System.out.println("Page " + currentPage + "/" + totalPage);
    }
        
    //Sort, search, and filter will return an empty list if it got a invalid input or no search result. ProductListPage.java will handle the rest
    public static List<Product> sortProduct(String[] input, List<Product> productList) {           
        if (input[1].equalsIgnoreCase("name")) {
            if (input[2].equalsIgnoreCase("asc")) {
                productList.sort(Comparator.comparing(Product::getProductName));
            }

            else if (input[2].equalsIgnoreCase("desc")) {
                productList.sort(Comparator.comparing(Product::getProductName).reversed());
            }

            else {
                return new ArrayList<>();
            }
        }

        else if (input[1].equalsIgnoreCase("category")) {
            if (input[2].equalsIgnoreCase("asc")) {
                productList.sort(Comparator.comparing(Product::getProductCategory));
            }

            else if (input[2].equalsIgnoreCase("desc")) {
                productList.sort(Comparator.comparing(Product::getProductCategory).reversed());
            }

            else {
                return new ArrayList<>();
            }
        }

        else if (input[1].equalsIgnoreCase("price")) {
            if (input[2].equalsIgnoreCase("asc")) {
                productList.sort(Comparator.comparing(Product::getProductPrice));
            }

            else if (input[2].equalsIgnoreCase("desc")) {
                productList.sort(Comparator.comparing(Product::getProductPrice).reversed());
            }

            else {
                return new ArrayList<>();
            }
        }

        else {
            return new ArrayList<>();
        }
        
        return productList;
    }
    
    public static List<Product> searchProduct(String[] input, List<Product> productList) {
        List<Product> newProductList = new ArrayList<>();
        String query = "";
        
        for (int i = 1; i < input.length; i++) {
            if (input[i] != null) {
                query += input[i] + " ";
            }
        }
        
        query = query.trim();
        
        for (Product product : productList) {
            if (product.getProductName().toLowerCase().contains(query.toLowerCase())) {
                newProductList.add(product);
            }
        }     
        
        return newProductList;
    }
    
    public static List<Product> filterProduct(String[] input, List<Product> productList) {
        List<Product> newProductList = new ArrayList<>();
        
        if (input[1].equalsIgnoreCase("category") && !input[2].isEmpty()) {
            String[] arguments = input[2].split(",");
            
            for (String argument : arguments) {
                for (Product product : productList) {
                    if (argument != null && product.getProductCategory().equalsIgnoreCase(argument)) {
                        newProductList.add(product);
                    }
                }
            }
        }
        
        else if (input[1].equalsIgnoreCase("price") && !input[2].isEmpty()) {
            String[] arguments = input[2].split(",");

            if (arguments.length == 2 && Utility.isDouble(arguments[0]) && Utility.isDouble(arguments[1])) {
                for (Product product : productList) {
                    if (product.getProductPrice() >= Double.parseDouble(arguments[0]) && product.getProductPrice() <= Double.parseDouble(arguments[1])) {
                        newProductList.add(product);
                    }
                }
            }
        }
        
        return newProductList;
    }
    
    public static void displayCommand() {
        System.out.println("\n*NOTHING IS CASE SENSITIVE*\n");
        
        System.out.println("Type [/page {page_number}] to go to the desire page");
        System.out.println("[/page 1] will go to the first page\n");
        
        System.out.println("Type [/sort {name/category/price} {asc/desc}] to sort the product");
        System.out.println("[/sort name asc] will sort every product alphabetically in ascending order\n");
        
        System.out.println("Type [/search {query}] to search product");
        System.out.println("[/search Keyboard a] will return every product that contains [Keyboard a] in its name\n");
        
        System.out.println("Type [/filter {category/price} {category/minPrice,maxPrice}] to filter product");
        System.out.println("[/filter category Keyboard] will return every product that is in the [Keyboard} category");
        System.out.println("[/filter category Keyboard,Mouse] will return every product that is in the [Keyboard} and [Mouse] category");
        System.out.println("[/filter price 10,20] will return every product that is in the price range of RM10 to Rm20\n");
        
        System.out.println("Type [/restore] to display the original list");
    }
}