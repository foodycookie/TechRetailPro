package techretailpro.pages;

import java.util.ArrayList;
import java.util.List;
import techretailpro.functions.CartManager;
import techretailpro.functions.OrderManager;
import techretailpro.functions.ProductListHelper;
import techretailpro.functions.ProductManager;
import techretailpro.objects.CartOrder;
import techretailpro.objects.LocalData;
import techretailpro.objects.Payment;
import techretailpro.objects.Product;
import techretailpro.objects.Transaction;
import techretailpro.functions.UtilityHelper;

public class ProductListPage {
    static List<CartOrder> orderHistory = new ArrayList<>();
    static Transaction trans = new Transaction();
    static Payment[] payments = {
        new Payment("Touch N Go"),
        new Payment("Credit / Debit"),
        new Payment("Online Banking")
    };
    
    public static void display(List<Product> list, int currentPage) {
        if (list == null) {
            display(LocalData.getPreviousList(), 1);
            return;
        }
        
        if (list.isEmpty()) {
            UtilityHelper.displayReturnMessage("No product found");
            display(LocalData.getPreviousList(), 1);
            return;
        }
        
        int dataPerPage = UtilityHelper.DATA_PER_PAGE;
        int totalPage = (int) Math.ceil((double) list.size() / dataPerPage);        
        
        int minOptionForProductNumber = 101 + (dataPerPage * (currentPage - 1));
        int maxOptionForProductNumber = 100 + (currentPage * dataPerPage);     
        int startForProductNumber = dataPerPage * (currentPage - 1);
        int endForProductNumber = Math.min(startForProductNumber + dataPerPage, list.size());
        
        List<String> options = new ArrayList<>();
        int numberInput;
        Integer optionInput;
        boolean allSameCategory = true;
        
        UtilityHelper.clearConsole();

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
        System.out.println("Type {back} at anytime to go back");
        
        options.add("Sort list");
        options.add("Search product");
        options.add("Filter product");
        options.add("Reset list");
        
        if (LoginPage.getCurrentUser().isAdmin()) {
            options.add("Check all low stock");
            options.add("Create new product");
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
        
        while (true) {            
            String rawInput = UtilityHelper.getUserInput("Option", "int", false);

            switch (rawInput) {
                case UtilityHelper.BACK_CONSTANT -> {
                    MainPage.display();
                }

                default -> {
                    numberInput = Integer.parseInt(rawInput);
                    
                    if (numberInput >= 1 && numberInput <= options.size()) {
                        String selectedOption = options.get(numberInput - 1);

                        switch (selectedOption) {
                            case "Previous page" -> {
                                if (currentPage == 1) {
                                    System.err.println("\nYou are at the first page");
                                    continue;
                                }

                                display(list, (currentPage - 1));  
                            }

                            case "Choose a page" -> {
                                if (currentPage == 1 && currentPage == totalPage) {
                                    System.err.println("\nThere is only one page, what are you choosing?");
                                    continue;
                                }

                                optionInput = UtilityHelper.numberOptionChooser("Chooose a page", 1, totalPage);
                                if (optionInput == null) {
                                    display(list, 1);
                                }

                                display(list, optionInput);
                            }

                            case "Next page" -> {
                                if (currentPage == totalPage) {
                                    System.err.println("\nYou are at the last page");
                                    continue;
                                }

                                display(list, (currentPage + 1));
                            }

                            case "Sort list" -> display(ProductListHelper.sortProductListUI(list), 1);

                            case "Search product" -> display(ProductListHelper.searchProductUI(list), 1);

                            case "Filter product" -> display(ProductListHelper.filterProductUI(list), 1);

                            case "Reset list" -> display(LocalData.getPreviousList(), 1); 

                            case "Check all low stock" -> display(ProductListHelper.getLowStockList(), 1);

                            case "Create new product" -> {    
                                ProductManager.createProductUI();
                                display(LocalData.getPreviousList(), 1);
                                
                            }

                            case "View cart" -> {
                                CartManager.viewCart();
                                display(LocalData.getPreviousList(), 1);
                            }

                            case "Remove item from cart" -> {
                                CartManager.removeItemFromCart();
                                display(LocalData.getPreviousList(), 1);
                            }

                            case "Checkout" -> {
                                OrderManager.checkoutAndPay(orderHistory, payments, trans);
                                display(LocalData.getPreviousList(), 1);
                            }

                            default -> System.err.println("\nInvalid input");
                        }
                    }

                    else if (numberInput >= minOptionForProductNumber && numberInput <= maxOptionForProductNumber) {
                        ProductDetailsPage.display(list.get(numberInput - 101));
                    }

                    else {
                        System.err.println("Invalid choice. Please enter number from " + 1 + " to " + options.size() + ", or " + minOptionForProductNumber + " to " + maxOptionForProductNumber);
                        continue;
                    }
                }
            }
            
            break;
        }
    }
}