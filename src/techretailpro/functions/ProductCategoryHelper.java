package techretailpro.functions;

import java.util.ArrayList;
import java.util.List;
import techretailpro.objects.Headphone;
import techretailpro.objects.Keyboard;
import techretailpro.objects.Laptop;
import techretailpro.objects.LocalData;
import techretailpro.objects.Mouse;
import techretailpro.objects.Printer;
import techretailpro.objects.Product;
import techretailpro.utilities.Utility;

public class ProductCategoryHelper {
    public static List<String> getExistingCategory() {
        List<String> list = new ArrayList<>();
        
        list.add(new Keyboard().getCategory());
        list.add(new Mouse().getCategory());
        list.add(new Laptop().getCategory());
        list.add(new Headphone().getCategory());
        list.add(new Printer().getCategory());

        return list;
    }
    
    public static void displayCategory(List<String> categories) {
        if (categories == null || categories.isEmpty()) {
            System.err.println("\nNo available categories");
            return;
        }
        
        int i = 1;
        
        for (String category : categories) {
            System.out.println(i + ". " + category);
            i++;
        }
    }
    
    public static String chooseCategory(List<String> categories) {
        if (categories == null || categories.isEmpty()) {
            System.err.println("\nNo available categories");
            return null;
        }
        
        System.out.println("\nSelect a category");
        displayCategory(categories);

        Integer input = Utility.numberOptionChooser(1, categories.size());
        
        if (input == null) {
            return null;
        }

        return categories.get(input - 1);
    }

    //User can choose multiple categories, selection will remove the chosen category
    public static String chooseCategoryForFilterList(List<String> categoriesToRemove) {
        List<String> categories = getExistingCategory();
        
        for (String categoryToRemove : categoriesToRemove) {
            if (categoryToRemove != null && !categoryToRemove.isBlank() && categories.contains(categoryToRemove)) {
                categories.remove(categoryToRemove);
            }     
        }
        
        if (categories.isEmpty()) {
            System.err.println("\nNo remaining categories");
            return null;
        }
        
        return chooseCategory(categories);
    }
    
    //UI choose
    public static List<Product> getListByCategory() {
        List<Product> list = new ArrayList<>();
        
        String category = chooseCategory(getExistingCategory());
        
        if (category == null) {
            return null;
        }
        
        for (Product product : LocalData.getCurrentProductsAvailable()) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                list.add(product);
            }
        }
        
        return list;
    }
    
    //Passed data choose
    public static List<Product> getListByCategory(String category) {
        List<Product> list = new ArrayList<>();
        
        if (category == null) {
            return null;
        }
        
        for (Product product : LocalData.getCurrentProductsAvailable()) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                list.add(product);
            }
        }
        
        return list;
    }
}