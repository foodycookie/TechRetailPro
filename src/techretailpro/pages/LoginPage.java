package techretailpro.pages;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import techretailpro.objects.Admin;
import techretailpro.objects.Customer;
import techretailpro.objects.LocalData;
import techretailpro.objects.User;
import techretailpro.utilities.Utility;



public class LoginPage {
    
    public static List<User> users = new ArrayList<>();

    
    public static void register(Scanner sc) {
    System.out.println("Register as:");
    System.out.println("1. Admin");
    System.out.println("2. Customer");
    System.out.print("Choose option (1 or 2): ");
    String choice = sc.nextLine();

    String type;
    if (choice.equals("1")) {
        type = "admin";
    } else if (choice.equals("2")) {
        type = "customer";
    } else {
        System.out.println("Invalid choice. Registration cancelled.");
        return;
    }

    System.out.print("Username: ");
    String username = sc.nextLine();
    
    try{
     if (doesUsernameExist(username)) {
        System.out.println("Username already exists. Please choose a different username.");
        return;
    }
    }
    catch(Exception e){
        System.out.println("");
    }
     
    System.out.print("Password: ");
    String password = sc.nextLine();
    System.out.print("Email: ");
    String email = sc.nextLine();

    User user;
    if (type.equals("admin")) {
        user = new Admin(username, password, email);
    } else {
        System.out.print("Phone Number: ");
        String phone = sc.nextLine();
        System.out.print("Address: ");
        String address = sc.nextLine();
        user = new Customer(username, password, email, phone, address);
    }

    saveUser(user);
    System.out.println("Registration successful.");
}

    
    public static void login(Scanner sc) {
        if (LocalData.getCurrentUser().isAdmin() || LocalData.getCurrentUser().isCustomer()) {
            System.out.println("Already logged in as " + LocalData.getCurrentUser().getUsername());
            return;
        }

        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        

        try{
             List<String> lines = Files.readAllLines(Paths.get(Utility.USERS_DATABASE));
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
//                        showUserMenu(sc);
                        MainPage.display(null);

                    } else {
                        LocalData.setCurrentUser(new Customer(uname, pwd, email, phone, address));
                    }
                    try{
                    System.out.println("Login successful. Welcome, " + LocalData.getCurrentUser().getUsername());
//                    showUserMenu(sc);
                    MainPage.display(null);
                    return;
                    }
                    catch(Exception ex){
                        System.err.println("");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Login failed: " + e.getMessage());
        }

        System.out.println("Invalid username or password.");
    }
    
    public static void logout() {
    if (LocalData.getCurrentUser().isAdmin() || LocalData.getCurrentUser().isCustomer()) {
        System.out.println("Logged out: " + LocalData.getCurrentUser().getUsername());
        LocalData.setCurrentUser(new User());
        LocalData.getCurrentUserCart().clear();
        MainPage.display(null);
    }
}

    private static void saveUser(User user) {
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Utility.USERS_DATABASE, true))) {
            bw.write(user.toCSV());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }
    
    private static void showUserMenu(Scanner sc) {
    if (LocalData.getCurrentUser() == null) return;

    while (true) {
        System.out.println("\n--- Menu for " + LocalData.getCurrentUser().getUsername() + " ---");

        if (LocalData.getCurrentUser().isAdmin()) {
            System.out.println("1. Manage Product");
            System.out.println("2. View Product");
            System.out.println("3. Manage Inventory");
        } else {
            System.out.println("1. List Product");
            System.out.println("2. Profile");
            System.out.println("3. Cart");
        }

        System.out.println("0. Logout to main menu");
        System.out.print("Choose an option: ");
        String input = sc.nextLine();

        switch (input) {
                case "1" -> System.out.println("Wait function unlock");
                case "2" -> Customer.viewProfile(sc);
                case "3" -> System.out.println("Wait function unlock");
                case "0" -> {
                    logout();
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
    }
    
}
    
    public static void loadUsers() {
    users.clear(); // clear existing users if any
    
    try (BufferedReader br = new BufferedReader(new FileReader(Utility.USERS_DATABASE))) {
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
        System.out.println("Error loading users: " + e.getMessage());
    }
}
    public static User getCurrentUser(){
        return LocalData.getCurrentUser();
    }
    
    public static boolean doesUsernameExist(String username) {
    
    try (Scanner scanner = new Scanner(new File(Utility.USERS_DATABASE))) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(",", -1);
            if (data[1].equals(username)) {
                return true; // Username found, already exists
            }
        }
    } catch (IOException e) {
        System.out.print("");
    }
    
    return false; // Username doesn't exist
}
        

}
