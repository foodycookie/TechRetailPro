package com.rswg3_5.techretailpro.models;

import com.rswg3_5.techretailpro.utils.Utility;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class User {
    private String username;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + '}';
    }
    
    public abstract void login();
    
    public abstract void displayRole();

    public void register() {
        
    }
    
    public void logout() {
        
    }
        
    public static List<Product> sortProduct(String[] input, List<Product> productList) {           
        if (input[1].equalsIgnoreCase("name")) {
            if (input[2].equalsIgnoreCase("asc")) {
                productList.sort(Comparator.comparing(Product::getProductName));
            }

            else if (input[2].equalsIgnoreCase("desc")) {
                productList.sort(Comparator.comparing(Product::getProductName).reversed());
            }

            else {
                System.out.println("\nInvalid input");
                Utility.displayReturnToPreviousProductList();
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
                System.out.println("\nInvalid input");
                Utility.displayReturnToPreviousProductList();
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
                System.out.println("\nInvalid input");
                Utility.displayReturnToPreviousProductList();
            }
        }

        else {
            System.out.println("\nInvalid input");
            Utility.displayReturnToPreviousProductList();
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
        
        if (newProductList.isEmpty()) {
            System.out.println("\nNo search result");
            Utility.displayReturnToPreviousProductList();
            return productList;
        }        
        
        return newProductList;
    }
    
    public static List<Product> filterProduct(String[] input, List<Product> productList) {
        List<Product> newProductList = new ArrayList<>();
        String[] arguments = input[2].split(",");
        
        if (input[1].equalsIgnoreCase("category")) {
            for (String argument : arguments) {
                for (Product product : productList) {
                    if (argument != null && product.getProductCategory().equalsIgnoreCase(argument)) {
                        newProductList.add(product);
                    }
                }
            }
            
            if (newProductList.isEmpty()) {
                System.out.println("\nNo search result");
                Utility.displayReturnToPreviousProductList();
                return productList;
            }
        }

        else if (input[1].equalsIgnoreCase("price") && arguments.length == 2 && Utility.isDouble(arguments[0]) && Utility.isDouble(arguments[1])) {
            for (Product product : productList) {
                if (product.getProductPrice() >= Double.parseDouble(arguments[0]) && product.getProductPrice() <= Double.parseDouble(arguments[1])) {
                    newProductList.add(product);
                }
            }
            
            if (newProductList.isEmpty()) {
                System.out.println("\nNo search result");
                Utility.displayReturnToPreviousProductList();
                return productList;
            }
        }

        else {
            System.out.println("\nInvalid input");
            Utility.displayReturnToPreviousProductList();
            return productList;
        }
        
        return newProductList;
    }
}