package techretailpro.functions;

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
import techretailpro.objects.Admin;
import techretailpro.objects.CartItem;
import techretailpro.objects.Customer;
import techretailpro.objects.LocalData;
import techretailpro.objects.Product;
import techretailpro.objects.User;
import techretailpro.pages.MainPage;

public class UserFunctions {
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
        System.out.print("Address: ");
        address = sc.nextLine();
        if (address.equalsIgnoreCase("back")) {
            return;
        }

        user = new Customer(username, password, email, phone, address);
    }

    saveUser(user);
    Utility.displayReturnMessage("Registration successful");
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
        List<String> lines = Files.readAllLines(Paths.get(Utility.USERS_DATABASE));
        for (String line : lines) {
            String[] data = line.split(",", -1);
            String type = data[0];
            String uname = data[1];
            String pwd = data[2];
            String email = data[3];
            String phone = data.length > 4 ? data[4] : "";
            String address = data.length > 5 ? data[5] : "";
            boolean status = data.length > 6 ? Boolean.parseBoolean(data[6]) : true;

            if (uname.equals(username) && pwd.equals(password)) {
                if (type.equals("admin")) {
                    if (status){
                    LocalData.setCurrentUser(new Admin(uname, pwd, email));
                    }else{
                        Utility.displayReturnMessage("Login failed: Admin account is inactive");
                        MainPage.display();
                    }
                } else {
                    LocalData.setCurrentUser(new Customer(uname, pwd, email, phone, address));
                }

                Utility.displayReturnMessage("Login successful, welcome, " + LocalData.getCurrentUser().getUsername());
                return;
            }
        }

    } catch (IOException e) {
        System.err.println("Login failed: " + e.getMessage());
    }

    Utility.displayReturnMessage("Login failed: Invalid username or password");
}
    
    public static void logout() {
        if (LocalData.getCurrentUser().isAdmin() || LocalData.getCurrentUser().isCustomer()) {
            Utility.displayReturnMessage("Logged out: " + LocalData.getCurrentUser().getUsername());
            LocalData.setCurrentUser(new User());

            for (CartItem item : LocalData.getCurrentUserCart().getItems()) {
                Product productToRemove = item.getProduct();
                int productQuantityToRemove = item.getQuantity();

                LocalData.getCurrentUserCart().removeItem(productToRemove.getName(), productQuantityToRemove);

                ProductFunctions.updateStock(productToRemove, productQuantityToRemove);
            }

            LocalData.getCurrentUserCart().clear();
            MainPage.display();
        }
    }

    private static void saveUser(User user) {
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Utility.USERS_DATABASE, true))) {
            bw.write(user.toCSV());
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error saving user: " + e.getMessage());
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
        System.err.println("Error loading users: " + e.getMessage());
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
                return true;
            }
        }
    } catch (IOException e) {
        System.out.print("");
    }
    
    return false;
}
    
    public static void initCsv() {
        File file = new File(Utility.USERS_DATABASE);
        if (!file.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            } catch (IOException e) {
                System.err.println("Could not initialize order history file: " + e.getMessage());
            }
        }
    }
    
    public static void viewProfile(Scanner sc) {
        Customer customer = (Customer) getCurrentUser();

        while (true) {
            System.out.println("\n=== Customer Profile ===");
            System.out.println("Username     : " + customer.getUsername());
            System.out.println("Email        : " + customer.getEmail());
            System.out.println("Phone Number : " + customer.getPhoneNumber());
            System.out.println("Address      : " + customer.getAddress());

            System.out.println("\nPress 1 to update profile");
            System.out.println("Press \"back\" to return to front page");
            System.out.print("Option: ");
            String input = sc.nextLine();

            if (input.equals("1")) {
                updateProfile(sc, customer);

            } else if (input.equalsIgnoreCase("back")) {
                return;
            } else {
                System.err.println("Invalid input. Please try again. Thank you.\n\n");
            }
        }  
    }
    
    public static void updateProfile(Scanner sc, Customer customer) {
    String oldUsername = customer.getUsername();
    String newUsername, newEmail, newPhone, newAddress;

    System.out.print("Enter new username (leave blank to keep current): ");
    newUsername = sc.nextLine();
    if (newUsername.equalsIgnoreCase("back")) {
        return;
    }
    if (newUsername.isBlank()) {
        newUsername = oldUsername;
    }

    while (true) {
        System.out.print("Enter new email (leave blank to keep current): ");
        newEmail = sc.nextLine();
        if (newEmail.equalsIgnoreCase("back")) {
            return;
        }
        if (newEmail.isBlank()) {
            newEmail = customer.getEmail();
            break;
        } else if (newEmail.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            break;
        } else {
            System.out.println("Invalid email format. Try again.");
        }
    }

    while (true) {
        System.out.print("Enter new phone number (leave blank to keep current): ");
        newPhone = sc.nextLine();
        if (newPhone.equalsIgnoreCase("back")) {
            return;
        }
        if (newPhone.isBlank()) {
            newPhone = customer.getPhoneNumber();
            break;
        } else if (newPhone.matches("\\d{10,11}")) {
            break;
        } else {
            System.out.println("Invalid phone number. Must be 10 to 11 digits.");
        }
    }

    System.out.print("Enter new address (leave blank to keep current): ");
    newAddress = sc.nextLine();
    if (newAddress.equalsIgnoreCase("back")) {
        return;
    }
    if (newAddress.isBlank()) {
        newAddress = customer.getAddress();
    }

    customer.setUsername(newUsername);
    customer.setEmail(newEmail);
    customer.setPhoneNumber(newPhone);
    customer.setAddress(newAddress);

    System.out.println("Profile updated successfully!");

    saveUpdateProfile(oldUsername, newUsername, customer.getPassword(), newEmail, newPhone, newAddress);
}
   
    public static void saveUpdateProfile(String editTerm, String newUsername, String newPassword, String newEmail, String newPhone, String newAddress) {
        String filepath = Utility.USERS_DATABASE;
        String tempFile = Utility.TEMP_USERS_DATABASE;
        File oldFile = new File(filepath);
        File newFile = new File(tempFile);

        try (Scanner checkScanner = new Scanner(oldFile)) {
            while (checkScanner.hasNextLine()) {
                String[] data = checkScanner.nextLine().split(",", -1);
                if (data.length >= 2 && data[1].equals(newUsername) && !data[1].equals(editTerm)) {
                    System.out.println("Username '" + newUsername + "' is already taken. Please choose a different one.");
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(newFile))) {
            Scanner scanner = new Scanner(oldFile);
            
            while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(",", -1);
            if (data.length >= 6 && data[1].equals(editTerm)) {
                writer.printf("customer,%s,%s,%s,%s,%s%n",
                        newUsername, newPassword, newEmail, newPhone, newAddress);
            } else {
                writer.println(line);
            }
        }

        scanner.close();
        } catch (IOException e) {
            System.out.println("Error editing record: " + e.getMessage());
        }
       
        
    if (oldFile.exists() && oldFile.delete()) {

        if (newFile.renameTo(oldFile)) {
        } else {
            System.out.println("Failed to rename temp file.");
        }
    } else {
        System.out.println("Failed to delete original file.");
        System.out.println("File still exists: " + oldFile.exists());
        System.out.println("Is it writable? " + oldFile.canWrite());
    }
    
    }
    
    public static void closeAdminAccount(Scanner sc) {
    if (!LocalData.getCurrentUser().isAdmin()) {
        Utility.displayReturnMessage("Only admins can close their accounts.");
        return;
    }

    System.out.println("Are you sure you want to close your account? Type 'yes' to confirm, or 'no' to cancel.");
    String confirmation = sc.nextLine();

    if ("yes".equalsIgnoreCase(confirmation)) {
        Admin currentAdmin = (Admin) LocalData.getCurrentUser();
        currentAdmin.setStatus(false); 

        try {
            List<String> lines = Files.readAllLines(Paths.get(Utility.USERS_DATABASE));
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] data = line.split(",", -1);
                String type = data[0];
                String uname = data[1];

                if (uname.equals(currentAdmin.getUsername()) && type.equals("admin")) {
                    data[6] = "false";  
                    lines.set(i, String.join(",", data));  
                    break;
                }
            }

            Files.write(Paths.get(Utility.USERS_DATABASE), lines);
            Utility.displayReturnMessage("Your account has been successfully closed.");
            logout();
        } catch (IOException e) {
            System.err.println("Error closing account: " + e.getMessage());
            Utility.displayReturnMessage("An error occurred while closing the account.");
        }
        } else if("no".equalsIgnoreCase(confirmation)) {
            System.out.println("Account closure canceled.");
        }else{
            System.out.println("Invalid input. Please try again");
        }
    } 
}