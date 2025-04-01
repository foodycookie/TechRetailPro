package com.rswg3_5.techretailpro.utils;

import com.rswg3_5.techretailpro.models.User;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utility {
    public static void border() {
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
    }
    
    public static void clearConsole() {
        for (int i = 0; i < 10; i++) {
            System.out.println("");
        }
        
//        System.out.print("\033[H\033[2J");
    }
    
    public static boolean isInteger(String s) {
        try {
            Integer.valueOf(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isDouble(String s) {
        try {
            Double.valueOf(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static void displayReturnToPreviousProductList() {
        System.out.println("Returning to the previous list...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}