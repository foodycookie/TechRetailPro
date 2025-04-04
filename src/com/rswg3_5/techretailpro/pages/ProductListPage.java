package com.rswg3_5.techretailpro.pages;

import com.rswg3_5.techretailpro.models.Product;
import static com.rswg3_5.techretailpro.models.Product.filterProduct;
import static com.rswg3_5.techretailpro.models.Product.searchProduct;
import static com.rswg3_5.techretailpro.models.Product.sortProduct;
import com.rswg3_5.techretailpro.utils.Utility;
import java.util.List;
import java.util.Scanner;

public class ProductListPage {

    //"Browse" product page, every product will be listed here, with a page system (Max = x product/page)
    //User will be able to change the number, right now is 50
    //Recursive method, used to constantly change the list of product according to user's command 
    public static void display(List<Product> productList, int currentPage) {
        //Final value, not sure if will move to another place
        final int MIN_OPTION = 1 + (Product.DATA_PER_PAGE * (currentPage - 1));
        final int MAX_OPTION = currentPage * Product.DATA_PER_PAGE;
        final int MAX_PAGE = (int) Math.ceil((double) productList.size() / Product.DATA_PER_PAGE);
        
        Scanner scanner = new Scanner(System.in);
        
        Utility.clearConsole();
        Utility.border();
        Product.displayProductList(productList, currentPage);
         
        System.out.println("\nPlease select a product by number to view its details");
        System.out.println("Type [0] to go back");
        System.out.println("For more commands, type [/help]");
        
        while(true) {
            System.out.print("\nOption > ");
            String[] input = scanner.nextLine().trim().split(" ");

            // /page, used to change current page
            if (input[0].equalsIgnoreCase("/page")) {
                if (!(input.length == 2)) {
                    System.out.println("\nInvalid input. Usage: [/page {page_number}]");
                    continue;
                }
                
                if (!Utility.isInteger(input[1])) {
                    System.out.println("\nInvalid input. Please enter number from 1 to " + MAX_PAGE + " if you want to select a page"); 
                    continue;
                }
                
                if (Integer.parseInt(input[1]) < 1 || Integer.parseInt(input[1]) > MAX_PAGE) {
                    System.out.println("\nInvalid input. Please enter number from 1 to " + MAX_PAGE + " if you want to select a page");
                    continue;
                }
                
                display(productList, Integer.parseInt(input[1]));     
            }
            
            // /help, used to display more commands
            else if (input[0].equalsIgnoreCase("/help")) {
                if (!(input.length == 1)) {
                    System.out.println("\nInvalid input. Usage: [/help]");
                    continue;
                }
                
                Product.displayCommand();
            }
            
            // /sort, used to sort product list according to name, category, or price, in ascending or descending order
            else if (input[0].equalsIgnoreCase("/sort")) {
                if (!(input.length == 3)) {
                    System.out.println("\nInvalid input. Usage: [/sort {name/category/price} {asc/desc}]"); 
                    continue;
                }
                
                if (sortProduct(input, productList).isEmpty()) {
                    System.out.println("\nInvalid input. Usage: [/sort {name/category/price} {asc/desc}]");
                    continue;
                }
                
                display(sortProduct(input, productList), 1);
            }
            
            // /search, used to search product
            else if (input[0].equalsIgnoreCase("/search")) {
                if (searchProduct(input, productList).isEmpty()) {
                    System.out.println("\nNo search result");
                    continue;
                }
                
                display(searchProduct(input, productList), 1);
            }
            
            // /filter, used to filter product according to category or price
            else if (input[0].equalsIgnoreCase("/filter")) {
                if (!(input.length == 3)) {
                    System.out.println("\nInvalid input. Usage: [/filter {category/price} {category/minPrice,maxPrice}]");
                    continue;
                }
                
                if (filterProduct(input, productList).isEmpty()) {
                    System.out.println("\nEither invalid input or no filter result. Usage: [/filter {category/price} {category/minPrice,maxPrice}]");
                    continue;
                }
                
                display(filterProduct(input, productList), 1);
            }
            
            // /restore, used to restore the product list back to normal
            else if (input[0].equalsIgnoreCase("/restore")) {
                if (!(input.length == 1)) {
                    System.out.println("\nInvalid input. Usage: [/restore]");
                    continue;
                }
                
                display(Product.fetchProduct(), 1);
            }
            
            // 0, used to return to MainPage.java
            else if (input[0].equalsIgnoreCase("0")) {
                if (!(input.length == 1)) {
                    System.out.println("\nInvalid input. Usage: [0]");
                    continue;
                }
                
                MainPage.display();
            }
            
            // number, used to select a product and enter ProductDetailsPage.java
            else if (Utility.isInteger(input[0])) {
                if (!(input.length == 1)) {
                    System.out.println("\nInvalid input. Usage: [product_no]");
                    continue;
                }
                
                if (Integer.parseInt(input[0]) < MIN_OPTION || Integer.parseInt(input[0]) > MAX_OPTION) {
                    System.out.println("\nInvalid input. Please enter number from " + MIN_OPTION + " to " + MAX_OPTION + " if you want to select a product");
                    continue;
                }
                
                ProductDetailsPage.display(productList.get((Integer.parseInt(input[0]))-1));
            }
              
            else {
                System.out.println("\nInvalid input. Type [/help] for commands");
            }
        }
    }
}