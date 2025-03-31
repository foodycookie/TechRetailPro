package com.rswg3_5.techretailpro.main;

import com.rswg3_5.techretailpro.models.Product;
import java.util.Scanner;
import com.rswg3_5.techretailpro.models.User;
import com.rswg3_5.techretailpro.utils.Utility;
import java.io.File;
import java.util.List;

public class TechRetailPro {
    public static Scanner scanner = new Scanner(System.in);
    public static int optionInput;
    
    public static void mainPage() {
        final int MAX_OPTION = 2;
        
        Utility.border();
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
            
            optionInput = scanner.nextInt();
            
            if (optionInput < 1 || optionInput > MAX_OPTION) {
                System.out.println("Invalid choice. Please enter number from 1 to " + MAX_OPTION);
                continue;
            }
            
            break;
        }

        switch (optionInput) {
            case 1 -> {
                Utility.clearConsole();
                User.listProduct();
            }

            case 2 -> {
                System.out.println("Thank you for choosing TechRetailPro!");
                System.exit(0);
            }
                
            default -> throw new AssertionError();
        }
    }
    
    public static void main(String[] args) {
        mainPage();
    }    
}