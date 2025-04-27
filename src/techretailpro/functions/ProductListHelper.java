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

        for (Product product : LocalData.getProducts()) {
            if (product.getStock() < 5) {
                lowStockList.add(product);
            }
        }
        
        return lowStockList;
    }
    
    public static List<Product> searchProduct(List<Product> list) {
        List<Product> newList = new ArrayList<>();
        
        System.out.println("\nEnter a search query");
        String validQuery = InputValidator.getString();
        if (validQuery == null) {
            return null;
        }
        
        for (Product product : list) {
            if (product.getName().toLowerCase().contains(validQuery.toLowerCase())) {
                newList.add(product);
            }
            
            else if (product.getCategory().toLowerCase().contains(validQuery.toLowerCase())) {
                newList.add(product);
            }
        }     
        
        return newList;
    }
    
    //Good luck explaining sort
    public static List<Product> sortProductList(List<Product> list) {
        List<Product> sortedList = new ArrayList<>(list);
        
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

        switch (input) {
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
    
    public static List<Product> filterProductByCategory(List<Product> list) { 
        List<Product> newList = new ArrayList<>();
        boolean chooseMoreCategory = true;
        
        List<String> categoriesToRemove = new ArrayList<>();
                
        while (chooseMoreCategory) {        
            String chosenCategory = ProductCategoryHelper.chooseCategoryForFilterList(categoriesToRemove);
            
            if (chosenCategory == null) {
                return newList;
            }

            for (Product product : list) {
                if (product.getCategory().equalsIgnoreCase(chosenCategory) && !newList.contains(product)) {
                    newList.add(product);
                }
            }

            categoriesToRemove.add(chosenCategory);

            System.out.println("\nAdd more category?");
            System.out.println("1. Yes");
            System.out.println("2. No");

            Integer input = Utility.numberOptionChooser(1, 2);
            
            if (input == null) {
                return list;
            }

            switch (input) {
                case 1 -> chooseMoreCategory = true;

                case 2 -> chooseMoreCategory = false;

                default -> {
                    return newList;
                }
            }
        }
        
        return newList;
    }  
    
    public static List<Product> filterProductByPrice(List<Product> list) {
        List<Product> newList = new ArrayList<>();
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
        
        for (Product product : list) {
            if (product.getPrice() >= validMinPrice && product.getPrice() <= validMaxPrice) {
                newList.add(product);
            }
        }
        
        return newList;
    }   
    
    public static List<Product> filterProduct(List<Product> list) {   
        System.out.println("\nFilter by?");
        System.out.println("1. Category");
        System.out.println("2. Price");
        
        Integer input = Utility.numberOptionChooser(1, 2);
        
        if (input == null) {
            return list;
        }
        
        return switch (input) {
            case 1 -> filterProductByCategory(list);

            case 2 -> filterProductByPrice(list);

            default -> list;
        };
    }
}