package com.rswg3_5.techretailpro.pages;

import com.rswg3_5.techretailpro.models.Product;
import com.rswg3_5.techretailpro.utils.Utility;

public class ProductDetailsPage {
    public static void display(Product product) {
        Utility.clearConsole();
        Utility.border();
        System.out.println(product.toString());
        
        //More function, like add to cart or something
    }
}
