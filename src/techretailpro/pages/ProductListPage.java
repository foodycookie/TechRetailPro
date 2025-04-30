package techretailpro.pages;

import java.util.ArrayList;
import java.util.List;
import techretailpro.functions.CartManager;
import techretailpro.functions.InputValidator;
import techretailpro.functions.OrderManager;
import techretailpro.functions.ProductListHelper;
import techretailpro.objects.CartOrder;
import techretailpro.objects.LocalData;
import techretailpro.objects.Payment;
import techretailpro.objects.Product;
import techretailpro.objects.Transaction;
import techretailpro.utilities.Utility;

public class ProductListPage {
    static List<CartOrder> orderHistory = new ArrayList<>();
    static Transaction trans = new Transaction();
    static Payment[] payments = {
        new Payment("Touch N Go"),
        new Payment("Credit / Debit"),
        new Payment("Online Banking")
    };
    
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
        
        int minOptionForProductNumber = 101 + (dataPerPage * (currentPage - 1));
        int maxOptionForProductNumber = 100 + (currentPage * dataPerPage);     
        int startForProductNumber = dataPerPage * (currentPage - 1);
        int endForProductNumber = Math.min(startForProductNumber + dataPerPage, list.size());
        
        List<String> options = new ArrayList<>();
        Integer input;
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
        
        if (allSameCategory) {
            System.out.println(list.get(0).getDetailedListHeader());
            
            for (int i = startForProductNumber; i < endForProductNumber; i++) {
                System.out.print(String.format("%-5d ", (i + 101)) + list.get(i).toStringForDetailedList() + "\n");
            }
        }
        
        else {
            System.out.println(list.get(0).getGeneralListHeader());
            
            for (int i = startForProductNumber; i < endForProductNumber; i++) {
                System.out.print(String.format("%-5d ", (i + 101)) + list.get(i).toStringForGeneralList() + "\n");
            }
        }
        
        System.out.println("Page " + currentPage + "/" + totalPage); 
    
        if (currentPage == 1 && currentPage == totalPage) {
        }
        
        else if (currentPage == 1) {
            options.add("Choose a page");
            options.add("Next page");
        }
        
        else if (currentPage == totalPage) {
            options.add("Previous page");
            options.add("Choose a page");
        }
        
        else {
            options.add("Previous page");
            options.add("Choose a page");
            options.add("Next page");
        }
  
        System.out.println("\nPlease select a product by number to view its details");
        System.out.println("Or select an option");
        System.out.println("Type {exit} at anytime to go back");
        
        options.add("Sort list");
        options.add("Search product");
        options.add("Filter product");
        options.add("Reset list");
        
        if (LoginPage.getCurrentUser().isAdmin()) {
        }
        
        else if (LoginPage.getCurrentUser().isCustomer()) {
            options.add("View cart");
            options.add("Remove item from cart");
            options.add("Checkout");
        }
        
        else {
        }

        if (options.isEmpty()) {
            options.add("Nothing you can do here. Go back");
        }
        
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }

        while(true) {
            input = InputValidator.getPositiveOrZeroInt();
            
            if (input == null) {
                MainPage.display(null);
            }

            if (input >= 1 && input <= options.size()) {
                String selectedOption = options.get(input - 1);
            
                switch (selectedOption) {
                    case "Previous page" -> {
                        if (currentPage == 1) {
                            System.err.println("\nYou are at the first page");
                            continue;
                        }
                        
                        display(list, (currentPage - 1), null);  
                    }
                    
                    case "Choose a page" -> {
                        if (currentPage == 1 && currentPage == totalPage) {
                            System.err.println("\nThere is only one page, what are you choosing?");
                            continue;
                        }
                        
                        System.out.println("\nChooose a page");
                        
                        input = Utility.numberOptionChooser(1, totalPage);
                        
                        if (input == null) {
                            display(list, 1, null);
                        }
                        
                        display(list, input, null);
                    }
                    
                    case "Next page" -> {
                        if (currentPage == totalPage) {
                            System.err.println("\nYou are at the last page");
                            continue;
                        }
                        
                        display(list, (currentPage + 1), null);
                    }
                    
                    case "Sort list" -> display(ProductListHelper.sortProductListUI(list), 1, null);
                    
                    case "Search product" -> display(ProductListHelper.searchProductUI(list), 1, null);
                    
                    case "Filter product" -> display(ProductListHelper.filterProductUI(list), 1, null);

                    case "Reset list" -> display(LocalData.getPreviousList(), 1, null); 
                    
                    case "View cart" -> CartManager.viewCart();
                
                    case "Remove item from cart" -> {
                        CartManager.removeItemFromCart();
                        
                        ProductListPage.display(LocalData.getPreviousList(), 1,  "Item removed from cart");
                    }

                    case "Checkout" -> {
                        OrderManager.checkoutAndPay(orderHistory, payments, trans);
                        
                        ProductListPage.display(LocalData.getPreviousList(), 1,  null);
                    }

                    default -> System.err.println("\nInvalid input");
                }
            }
            
            else if (input >= minOptionForProductNumber && input <= maxOptionForProductNumber) {
                ProductDetailsPage.display(list.get(input - 101), null);
            }
            
            else {
                System.err.println("\nInvalid choice. Please enter number from " + 1 + " to " + options.size() + ", or " + minOptionForProductNumber + " to " + maxOptionForProductNumber);
            }
        }
    }
}