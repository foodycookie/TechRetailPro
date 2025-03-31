package com.rswg3_5.techretailpro.utils;

public class Utility {
    public static void border() {
        System.out.println("------------------------------");
    }
    
    public static void clearConsole() {
        for (int i = 0; i < 10; i++) {
            System.out.println("");
        }
        
//        System.out.print("\033[H\033[2J");
    }
}