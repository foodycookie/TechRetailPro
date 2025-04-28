package techretailpro.pages;

import java.util.List;
import techretailpro.functions.ProductListHelper;
import techretailpro.objects.LocalData;
import techretailpro.objects.Product;
import techretailpro.utilities.Utility;

public class ProductListPage {
    public static void display(List<Product> list, int currentPage, String message) {
        if (list == null) {
            display(LocalData.getPreviousList(), 1, null);
            return;
        }
        
        if (list.isEmpty()) {
            display(LocalData.getPreviousList(), 1, "No product found");
            return;
        }
        
        int dataPerPage = Utility.DATA_PER_PAGE;
        int totalPage = (int) Math.ceil((double) list.size() / dataPerPage);        
        int minOption = 1 + (dataPerPage * (currentPage - 1));
        int maxOption = currentPage * dataPerPage;            
        int start = (currentPage - 1) * dataPerPage;
        int end = Math.min(start + dataPerPage, list.size());
        
        String input;
        Integer numberInput;
        boolean allSameCategory = true;
        
        Utility.clearConsole();
        
        if (message != null && !message.isEmpty()) {
            System.err.println(message + ". Jump to product list page...");
        }

        for (Product product : list) {
            if (!list.get(0).getCategory().equals(product.getCategory())) {
                allSameCategory = false;
                break;
            }
        }
        
        System.out.println();
        
        //Will check if customer, then cannot see product with 0 stock
        if (allSameCategory) {
            System.out.println(list.get(0).detailedListHeader());
            
            for (int i = start; i < end; i++) {
                System.out.print(String.format("%-5d ", (i + 1)) + list.get(i).toStringForDetailedList() + "\n");
            }
        }
        
        else {
            System.out.println(list.get(0).generalListHeader());
            
            for (int i = start; i < end; i++) {
                System.out.print(String.format("%-5d ", (i + 1)) + list.get(i).toStringForGeneralList() + "\n");
            }
        }
        
        System.out.println("Page " + currentPage + "/" + totalPage); 
        
        if (currentPage == 1 && currentPage == totalPage) {
        }
        
        else if (currentPage == 1) {
            System.out.println("CP. Choose a page   NP. Next Page"); 
        }
        
        else if (currentPage == totalPage) {
            System.out.println("PP. Previous Page   CP. Choose a page"); 
        }
        
        else {
            System.out.println("PP. Previous Page   CP. Choose a page   NP. Next Page"); 
        }
        
        System.out.println("\nPlease select a product by number to view its details");
        System.out.println("Type {exit} at anytime to go back");
        System.out.println("Or select an option by alphabet(s)");    
        System.out.println("A. Sort list");
        System.out.println("B. Search product");
        System.out.println("C. Filter product");
        System.out.println("D. Reset list");
        
        while(true) {
            System.out.print("Option > ");
            
            if (Utility.SCANNER.hasNextInt()) {
                input = Utility.SCANNER.nextLine();
                
                numberInput = Integer.valueOf(input);
                
                if (numberInput < minOption || numberInput > maxOption) {
                    System.err.println("\nInvalid choice. Please enter number from " + minOption + " to " + maxOption);
                    continue;
                }

                ProductDetailsPage.display(list.get(Integer.parseInt(input) - 1), null);
            }
            
            else {
                input = Utility.SCANNER.nextLine().trim();
                
                switch (input.toLowerCase()) {
                    case "exit" -> MainPage.display(null);
                    
                    case "pp" -> {
                        if (currentPage == 1) {
                            System.err.println("\nYou are at the first page");
                            continue;
                        }
                        
                        display(list, (currentPage - 1), null);  
                    }
                    
                    case "cp" -> {
                        if (currentPage == 1 && currentPage == totalPage) {
                            System.err.println("\nThere is only one page, what are you choosing?");
                            continue;
                        }
                        
                        System.out.println("\nChooose a page");
                        
                        numberInput = Utility.numberOptionChooser(1, totalPage);
                        
                        if (numberInput == null) {
                            display(list, 1, null);
                        }
                        
                        display(list, numberInput, null);
                    }
                    
                    case "np" -> {
                        if (currentPage == totalPage) {
                            System.err.println("\nYou are at the last page");
                            continue;
                        }
                        
                        display(list, (currentPage + 1), null);
                    }
                    
                    case "a" -> display(ProductListHelper.sortProductListUI(list), 1, null);
                    
                    case "b" -> display(ProductListHelper.searchProductUI(list), 1, null);
                    
                    case "c" -> display(ProductListHelper.filterProductUI(list), 1, null);

                    case "d" -> display(LocalData.getPreviousList(), 1, null); 

                    default -> System.err.println("\nInvalid input");
                }
            }
        }
    }
}