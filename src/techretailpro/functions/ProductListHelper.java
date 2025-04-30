package techretailpro.functions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import techretailpro.objects.Headphone;
import techretailpro.objects.Keyboard;
import techretailpro.objects.Laptop;
import techretailpro.objects.LocalData;
import techretailpro.objects.Mouse;
import techretailpro.objects.Printer;
import techretailpro.objects.Product;
import techretailpro.utilities.Utility;

public class ProductListHelper {
    public static List<Keyboard> getKeyboardList() {
        List<Keyboard> list = new ArrayList<>();
        
        for (Product product : ProductCategoryHelper.getListByCategory(new Keyboard().getCategory())) {
            if (product.getCategory().equalsIgnoreCase(new Keyboard().getCategory())) {
                list.add((Keyboard) product);
            }
        }
        
        return list;
    }
    
    public static List<Mouse> getMouseList() {
        List<Mouse> list = new ArrayList<>();
                    
        for (Product product : ProductCategoryHelper.getListByCategory(new Mouse().getCategory())) {
            if (product.getCategory().equalsIgnoreCase(new Mouse().getCategory())) {
                list.add((Mouse) product);
            }
        }
        
        return list;
    }
    
    public static List<Laptop> getLaptopList() {
        List<Laptop> list = new ArrayList<>();
                    
        for (Product product : ProductCategoryHelper.getListByCategory(new Laptop().getCategory())) {
            if (product.getCategory().equalsIgnoreCase(new Laptop().getCategory())) {
                list.add((Laptop) product);
            }
        }
        
        return list;
    }
    
    public static List<Headphone> getHeadphoneList() {
        List<Headphone> list = new ArrayList<>();
                    
        for (Product product : ProductCategoryHelper.getListByCategory(new Headphone().getCategory())) {
            if (product.getCategory().equalsIgnoreCase(new Headphone().getCategory())) {
                list.add((Headphone) product);
            }
        }
        
        return list;
    }
    
    public static List<Printer> getPrinterList() {
        List<Printer> list = new ArrayList<>();
                    
        for (Product product : ProductCategoryHelper.getListByCategory(new Printer().getCategory())) {
            if (product.getCategory().equalsIgnoreCase(new Printer().getCategory())) {
                list.add((Printer) product);
            }
        }
        
        return list;
    }
    
    public static List<Product> getLowStockList() {
        List<Product> lowStockList = new ArrayList<>();

        for (Product product : LocalData.getCurrentProductsAvailable()) {
            if (product.isLowStock()) {
                lowStockList.add(product);
            }
        }
        
        return lowStockList;
    }
    
    
    public static List<Product> searchProductUI(List<Product> list) {
        System.out.println("\nEnter a search query");
        String validQuery = InputValidator.getString();
        if (validQuery == null) {
            return list;
        }
        
        return searchProduct(list, validQuery);
    }
    
    public static List<Product> searchProduct(List<Product> list, String query) {
        List<Product> newList = new ArrayList<>();
        
        for (Product product : list) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                newList.add(product);
            }
            
            else if (product.getCategory().toLowerCase().contains(query.toLowerCase())) {
                newList.add(product);
            }
            
            else if (product.getDescription().toLowerCase().contains(query.toLowerCase())) {
                newList.add(product);
            }
        }     
        
        return newList;
    }
    
    public static List<Product> sortProductListUI(List<Product> list) {
        System.out.println("\nSort by?");
        System.out.println("1. Name, ascending order");
        System.out.println("2. Name, descending order");
        System.out.println("3. Category, ascending order");
        System.out.println("4. Category, descending order");
        System.out.println("5. Price, ascending order");
        System.out.println("6. Price, descending order");
        
        Integer input = Utility.numberOptionChooser(1, 6);
        
        if (input == null) {
            return list;
        }
        
        return sortProductList(list, input);
    }
    
    public static List<Product> sortProductList(List<Product> list, int option) {
        List<Product> sortedList = new ArrayList<>(list);

        switch (option) {
            case 1 -> sortedList.sort(Comparator.comparing(Product::getName));

            case 2 -> sortedList.sort(Comparator.comparing(Product::getName).reversed());
            
            case 3 -> sortedList.sort(Comparator.comparing(Product::getCategory));
            
            case 4 -> sortedList.sort(Comparator.comparing(Product::getCategory).reversed());
            
            case 5 -> sortedList.sort(Comparator.comparing(Product::getPrice));
            
            case 6 -> sortedList.sort(Comparator.comparing(Product::getPrice).reversed());

            default -> {
                return list;
            }
        }
        
        return sortedList;
    }
    
    public static List<Product> filterProductByCategoryUI(List<Product> list) { 
        List<String> selectedCategories = new ArrayList<>();
        List<String> categoriesToRemove = new ArrayList<>();
        boolean chooseMoreCategory = true;
        
        while (chooseMoreCategory) {
            String selectedCategory = ProductCategoryHelper.chooseCategoryForFilterList(categoriesToRemove);
            
            if (selectedCategory == null && selectedCategories.isEmpty()) {
                return list;
            }
            
            if (selectedCategory == null) {
                return filterProductByCategory(list, selectedCategories);
            }
            
            selectedCategories.add(selectedCategory);
            categoriesToRemove.add(selectedCategory);

            System.out.println("\nAdd more category?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            
            Integer input = Utility.numberOptionChooser(1, 2);
            
            if (input == null) {
                return filterProductByCategory(list, selectedCategories);
            }
            
            switch (input) {
                case 1 -> chooseMoreCategory = true;

                case 2 -> chooseMoreCategory = false;

                default -> {
                    return filterProductByCategory(list, selectedCategories);
                }
            }
        }
        
        return filterProductByCategory(list, selectedCategories);
    }
    
    public static List<Product> filterProductByCategory(List<Product> list, List<String> categories) { 
        List<Product> newList = new ArrayList<>();
        
        for (Product product : list) {
            for (String category : categories) {
                if (product.getCategory().equalsIgnoreCase(category) && !newList.contains(product)) {
                    newList.add(product);
                    break;
                }
            }
            
        }
        
        return newList;
    }
    
    public static List<Product> filterProductByPriceUI(List<Product> list) {
        Double validMinPrice;
        Double validMaxPrice;
        
        while (true) {            
            System.out.println("\nEnter minimum price");
            validMinPrice = InputValidator.getPositiveOrZeroDouble();
            if (validMinPrice == null) {
                return list;
            }

            System.out.println("\nEnter maximum price");
            validMaxPrice = InputValidator.getPositiveOrZeroDouble();
            if (validMaxPrice == null) {
                return list;
            }
            
            if (validMaxPrice < validMinPrice) {
                System.err.println("\nInvalid input. Maximum price needs to be greater than minimum price");
                continue;
            }
            
            break;
        }
        
        return filterProductByPrice(list, validMinPrice, validMaxPrice);
    }
    
    public static List<Product> filterProductByPrice(List<Product> list, double minPrice, double maxPrice) {
        List<Product> newList = new ArrayList<>();
        
        for (Product product : list) {
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                newList.add(product);
            }
        }
        
        return newList;
    }   
    
    public static List<Product> filterProductUI(List<Product> list) {   
        System.out.println("\nFilter by?");
        System.out.println("1. Category");
        System.out.println("2. Price");
        
        Integer input = Utility.numberOptionChooser(1, 2);
        
        if (input == null) {
            return list;
        }
        
        return switch (input) {
            case 1 -> filterProductByCategoryUI(list);

            case 2 -> filterProductByPriceUI(list);

            default -> list;
        };
    }
}