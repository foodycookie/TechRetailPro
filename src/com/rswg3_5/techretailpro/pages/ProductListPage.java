package com.rswg3_5.techretailpro.pages;

import com.rswg3_5.techretailpro.models.Product;
import static com.rswg3_5.techretailpro.models.User.filterProduct;
import static com.rswg3_5.techretailpro.models.User.searchProduct;
import static com.rswg3_5.techretailpro.models.User.sortProduct;
import com.rswg3_5.techretailpro.utils.Utility;
import java.util.List;
import java.util.Scanner;

public class ProductListPage {

    public static void display(List<Product> productList, int currentPage) {
        Scanner scanner = new Scanner(System.in);
        final int MAX_OPTION = productList.size();
        
        Utility.clearConsole();
        Utility.border();
        Product.displayProductList(productList, currentPage);
         
        System.out.println("\nPlease select a product by number to view its details");
        System.out.println("Type [0] to go back");
        System.out.println("For more commands, type /help");
        
        while(true) {
            System.out.print("\nOption > ");
            String[] input = scanner.nextLine().trim().split(" ");

            if (input[0].equalsIgnoreCase("/page") && Utility.isInteger(input[1]) && input.length == 2) {
                if ((Integer.parseInt(input[1]) < 1 || Integer.parseInt(input[1]) > (int) Math.ceil(productList.size() / Product.DATA_PER_PAGE))) {
                    System.out.println("\nInvalid input");
                    Utility.displayReturnToPreviousProductList();
                    display(productList, currentPage);
                }
                
                display(productList, Integer.parseInt(input[1]));
            }
            
            else if (input[0].equalsIgnoreCase("/help") && input.length == 1) {
                Product.displayCommand();
            }
            
            else if (input[0].equalsIgnoreCase("/sort") && input.length == 3) {
                display(sortProduct(input, productList), 1);
            }
            
            else if (input[0].equalsIgnoreCase("/search")) {
                display(searchProduct(input, productList), 1);
            }
            
            else if (input[0].equalsIgnoreCase("/filter") && input.length == 3) {
                display(filterProduct(input, productList), 1);
            }
            
            else if (input[0].equalsIgnoreCase("/restore") && input.length == 1) {
                display(Product.fetchProduct(), 1);
            }
            
            else if (input[0].equalsIgnoreCase("0") && input.length == 1) {
                MainPage.display();
            }
            
            else if (Utility.isInteger(input[0]) && input.length == 1) {
                if ((Integer.parseInt(input[0]) < 1 || Integer.parseInt(input[0]) > MAX_OPTION)) {
                    System.out.println("\nInvalid input");
                    Utility.displayReturnToPreviousProductList();
                    display(productList, currentPage);
                }
                
                ProductDetailsPage.display(productList.get((Integer.parseInt(input[0]))-1));
            }
              
            else {
                System.out.println("\nInvalid input");
                Utility.displayReturnToPreviousProductList();
                display(productList, currentPage);
            }
        }
    }
}