package com.rswg3_5.techretailpro.pages;

import com.rswg3_5.techretailpro.models.Product;
import com.rswg3_5.techretailpro.utils.Utility;
import java.util.Scanner;

public class MainPage {
    public static void display() { 
        final int MAX_OPTION = 2;
        
        Scanner scanner = new Scanner(System.in);
        int input;
        
        Utility.clearConsole();
        Utility.border();
        //Add more option here like login and register and update the MAX_OPTION
        System.out.println("Welcome to TechRetailPro");
        System.out.println("Please select an option");
        System.out.println("1. View all the products");
        System.out.println("2. Exit program");

        while(true) {
            System.out.print("\nOption > ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter number from 1 to " + MAX_OPTION);
                scanner.next();
                continue;
            }

            input = scanner.nextInt();

            if (input < 1 || input > MAX_OPTION) {
                System.out.println("Invalid choice. Please enter number from 1 to " + MAX_OPTION);
                continue;
            }

            break;
        }

        switch (input) {
            case 1 -> {
                ProductListPage.display(Product.fetchProduct(), 1);
            }

            case 2 -> {
                System.out.println("\nThank you for choosing TechRetailPro!");
                System.exit(0);
            }

            default -> throw new AssertionError();
        }
    }
}
