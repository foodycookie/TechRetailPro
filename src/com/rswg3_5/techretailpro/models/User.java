package com.rswg3_5.techretailpro.models;

import com.rswg3_5.techretailpro.pages.MainPage;
import com.rswg3_5.techretailpro.utils.Utility;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
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
    
    public static void listProduct(List<Product> productList) {     
        Scanner scanner = new Scanner(System.in);
        final int MAX_OPTION = productList.size();
        
        Utility.clearConsole();
        Utility.border();
        Product.displayProduct(productList);
         
        System.out.println("\nPlease select a product by number to view its details (Type 0 to go back)");
        System.out.println("For more commands, type /help");
        
        while(true) {
            System.out.print("\nOption > ");
            String[] input = scanner.nextLine().trim().split(" ");
            
//            for (String string : input) {
//                System.out.println(string);
//            }
                        
            if (input[0].equalsIgnoreCase("/help") && input.length == 1) {
                Product.displayCommand();
            }
            
            else if (input[0].equalsIgnoreCase("/sort") && input.length == 3) {
                listProduct(sortProduct(input, productList));
            }
            
            else if (input[0].equalsIgnoreCase("/search") && input.length == 2) {
                listProduct(searchProduct(input, productList));
            }
            
            else if (input[0].equalsIgnoreCase("/filter") && input.length == 3) {
                listProduct(filterProduct(input, productList));
            }
            
            else if (input[0].equalsIgnoreCase("/restore") && input.length == 1) {
                listProduct(Product.fetchProduct());
            }
            
            else if (input[0].equalsIgnoreCase("0") && input.length == 1) {
                MainPage.display();
            }
            
            else if ((isInteger(input[0])) && input.length == 1) {
                if ((Integer.parseInt(input[0]) < 1 || Integer.parseInt(input[0]) > MAX_OPTION)) {
                    System.out.println("\nInvalid input");
                    continue;
                }
                
                viewProduct(productList.get((Integer.parseInt(input[0]))-1));
            }
              
            else {
                System.out.println("\nInvalid input");
            }
        }
    }
    
    public static void viewProduct(Product product) {    
        System.out.println(product.toString());
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
                System.out.println("Returning to the previous list...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                }
                return productList;
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
                System.out.println("Returning to the previous list...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                }
                return productList;
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
                System.out.println("Returning to the previous list...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
                }
                return productList;
            }
        }

        else {
            System.out.println("\nInvalid input");
            System.out.println("Returning to the previous list...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
            return productList;
        }
        
        return productList;
    }
    
    public static List<Product> searchProduct(String[] input, List<Product> productList) {
        List<Product> newProductList = new ArrayList<>();
        
        for (Product product : productList) {
            if (product.getProductName().toLowerCase().contains(input[1].toLowerCase())) {
                newProductList.add(product);
            }
        }
        
        if (newProductList.isEmpty()) {
            System.out.println("\nNo search result");
            System.out.println("Returning to the previous list...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
            return productList;
        }
        
        newProductList.sort(Comparator.comparing(Product::getProductName));
        
        return newProductList;
    }
    
    public static List<Product> filterProduct(String[] input, List<Product> productList) {
        List<Product> newProductList = new ArrayList<>();
        String[] arguments = input[2].split(",");
        
        if (input[1].equalsIgnoreCase("category")) {
            for (String argument : arguments) {
                if (argument != null) {
                    for (Product product : productList) {
                        if (product.getProductCategory().toLowerCase().equalsIgnoreCase(argument)) {
                            newProductList.add(product);
                        }
                    }
                }
            }
            
        newProductList.sort(Comparator.comparing(Product::getProductName));
        
        }

        else if (input[1].equalsIgnoreCase("price") && arguments.length == 2) {
            for (Product product : productList) {
                if (product.getProductPrice() >= Double.parseDouble(arguments[0]) && product.getProductPrice() <= Double.parseDouble(arguments[1])) {
                    newProductList.add(product);
                }
            }
        
        newProductList.sort(Comparator.comparing(Product::getProductPrice));
        
        }

        else {
            System.out.println("\nInvalid input");
            System.out.println("Returning to the previous list...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
            return productList;
        }
        
        return newProductList;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}