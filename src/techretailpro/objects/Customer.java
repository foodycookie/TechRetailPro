package techretailpro.objects;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import techretailpro.pages.LoginPage;
import techretailpro.functions.UtilityHelper;

public class Customer extends User {
    private String phoneNumber;
    private String address;

    public Customer(String username, String password, String email, String phoneNumber, String address) {
        super(username, password, email);
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    @Override
    public String toCSV() {
        return "customer," + username + "," + password + "," + email + "," + phoneNumber + "," + address;
    }

    @Override
    public boolean isAdmin() {
        return false;
    }
    
    @Override
    public boolean isCustomer() {
        return true;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public static void viewProfile(Scanner sc) {
        Customer customer = (Customer) LoginPage.getCurrentUser();

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
    
    public void viewOrderHistory() {
        
    }
   
    public static void saveUpdateProfile(String editTerm, String newUsername, String newPassword, String newEmail, String newPhone, String newAddress) {
        String filepath = UtilityHelper.USERS_DATABASE;
        String tempFile = UtilityHelper.TEMP_USERS_DATABASE;
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
}
