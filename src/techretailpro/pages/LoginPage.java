package techretailpro.pages;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import techretailpro.functions.ProductManager;
import techretailpro.objects.Admin;
import techretailpro.objects.CartItem;
import techretailpro.objects.Customer;
import techretailpro.objects.LocalData;
import techretailpro.objects.Product;
import techretailpro.objects.User;
import techretailpro.functions.UtilityHelper;

public class LoginPage {
    
    public static List<User> users = new ArrayList<>();

    public static void register(Scanner sc) {

    System.out.println("\n\nRegister Page");
    System.out.print("Type \"back\" anytime to quit this section.\n");

    String type = "";
    while (true) {
        System.out.println("Register as:");
        System.out.println("1. Admin");
        System.out.println("2. Customer");
        System.out.print("Option: ");
        String choice = sc.nextLine();

        if (choice.equalsIgnoreCase("back")) {
            return;
        }

        if (choice.equals("1")) {
            type = "admin";
            break;
        } else if (choice.equals("2")) {
            type = "customer";
            break;
        } else {
            System.err.println("Invalid choice. Please enter 1 or 2.\n\n");
        }
    }

    String username;
    while (true) {
        System.out.print("\nUsername: ");
        username = sc.nextLine();
        if (username.equalsIgnoreCase("back")) {
            return;
        }
        if (username.isBlank()) {
            System.err.println("Username cannot be empty.\n");
        } else if (doesUsernameExist(username)) {
            System.err.println("Username already exists. Please choose a different one.");
        } else {
            break;
        }
    }

    String password;
    while (true) {
        System.out.print("Password: ");
        password = sc.nextLine();
        if (password.equalsIgnoreCase("back")) {
            return;
        }
        if (password.isBlank()) {
            System.err.println("Password cannot be empty.\n");
        } else {
            break;
        }
    }

    String email;
    while (true) {
        System.out.print("Email: ");
        email = sc.nextLine();
        if (email.equalsIgnoreCase("back")) {
            return;
        }
        if (email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            break;
        } else {
            System.err.println("Invalid email format. Please enter a valid email (example@example.com).\n");
        }
    }

    User user;
    if (type.equals("admin")) {
        user = new Admin(username, password, email);
    } else {
        String phone;
        while (true) {
            System.out.print("Phone Number: ");
            phone = sc.nextLine();
            if (phone.equalsIgnoreCase("back")) {
                return;
            }
            if (phone.matches("\\d{10,11}")) {
                break;
            } else {
                System.err.println("Invalid phone number. Must be 10 to 11 digits.");
            }
        }

        String address;
        System.out.print("Address (or type 'back' to cancel): ");
        address = sc.nextLine();
        if (address.equalsIgnoreCase("back")) {
            return;
        }

        user = new Customer(username, password, email, phone, address);
    }

    saveUser(user);
    UtilityHelper.displayReturnMessage("Registration successful");
}

    public static void login(Scanner sc) {
    if (LocalData.getCurrentUser().isAdmin() || LocalData.getCurrentUser().isCustomer()) {
        System.out.println("Already logged in as " + LocalData.getCurrentUser().getUsername());
        return;
    }

    System.out.println("\n\nLogin Page");
    System.out.println("Type \"back\" anytime to quit this section.\n");
    
    System.out.print("Username: ");
    String username = sc.nextLine();
    if (username.equalsIgnoreCase("back")) {
        return ;
    }

    System.out.print("Password: ");
    String password = sc.nextLine();
    if (password.equalsIgnoreCase("back")) {
        return;
    }

    try {
        List<String> lines = Files.readAllLines(Paths.get(UtilityHelper.USERS_DATABASE));
        for (String line : lines) {
            String[] data = line.split(",", -1);
            String type = data[0];
            String uname = data[1];
            String pwd = data[2];
            String email = data[3];
            String phone = data.length > 4 ? data[4] : "";
            String address = data.length > 5 ? data[5] : "";

            if (uname.equals(username) && pwd.equals(password)) {
                if (type.equals("admin")) {
                    LocalData.setCurrentUser(new Admin(uname, pwd, email));
                } else {
                    LocalData.setCurrentUser(new Customer(uname, pwd, email, phone, address));
                }

//                System.out.println("Login successful. Welcome, " + LocalData.getCurrentUser().getUsername());
//                MainPage.display(); // Proceed to main page
                UtilityHelper.displayReturnMessage("Login successful, welcome, " + LocalData.getCurrentUser().getUsername());
                return;
            }
        }
    } catch (IOException e) {
        System.err.println("Login failed: " + e.getMessage());
    }

    UtilityHelper.displayReturnMessage("Login failed: Invalid username or password");
}
    
    public static void logout() {
    if (LocalData.getCurrentUser().isAdmin() || LocalData.getCurrentUser().isCustomer()) {
        UtilityHelper.displayReturnMessage("Logged out: " + LocalData.getCurrentUser().getUsername());
        LocalData.setCurrentUser(new User());
        
        for (CartItem item : LocalData.getCurrentUserCart().getItems()) {
            Product productToRemove = item.getProduct();
            int productQuantityToRemove = item.getQuantity();
            
            LocalData.getCurrentUserCart().removeItem(productToRemove.getName(), productQuantityToRemove);
            
            ProductManager.updateStock(productToRemove, productQuantityToRemove);
        }

        LocalData.getCurrentUserCart().clear();
        MainPage.display();
    }
}

    private static void saveUser(User user) {
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(UtilityHelper.USERS_DATABASE, true))) {
            bw.write(user.toCSV());
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error saving user: " + e.getMessage());
        }
    }
    
    public static void loadUsers() {
    users.clear(); // clear existing users if any
    
    try (BufferedReader br = new BufferedReader(new FileReader(UtilityHelper.USERS_DATABASE))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",", -1);
            String type = data[0];
            String username = data[1];
            String password = data[2];
            String email = data[3];

            if ("admin".equalsIgnoreCase(type)) {
                users.add(new Admin(username, password, email));
            } else if ("customer".equalsIgnoreCase(type)) {
                String phone = data.length > 4 ? data[4] : "";
                String address = data.length > 5 ? data[5] : "";
                users.add(new Customer(username, password, email, phone, address));
            }
            
        }
    } catch (IOException e) {
        System.err.println("Error loading users: " + e.getMessage());
    }
}
    public static User getCurrentUser(){
        return LocalData.getCurrentUser();
    }
    
    public static boolean doesUsernameExist(String username) {
    
    try (Scanner scanner = new Scanner(new File(UtilityHelper.USERS_DATABASE))) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(",", -1);
            if (data[1].equals(username)) {
                return true;
            }
        }
    } catch (IOException e) {
        System.out.print("");
    }
    
    return false;
}
    
    public static void initCsv() {
        File file = new File(UtilityHelper.USERS_DATABASE);
        if (!file.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            } catch (IOException e) {
                System.err.println("Could not initialize order history file: " + e.getMessage());
            }
        }
    }
        

}
