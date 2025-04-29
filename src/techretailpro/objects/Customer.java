package techretailpro.objects;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import techretailpro.pages.LoginPage;
import techretailpro.utilities.Utility;



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
        System.out.println("Press 0 to return to front page");
        System.out.print("Choose: ");
        String input = sc.nextLine();

        switch (input) {
            case "1" -> updateProfile(sc, customer);
            case "0" -> {
                return; // ⬅️ Back to user menu
            }
            default -> System.out.println("Invalid input. Please try again.");
        }
    }
        
    }
    
    public static void updateProfile(Scanner sc, Customer customer) {
        String oldUsername = customer.getUsername(); // Used to match the original record

        System.out.print("Enter new username (leave blank to keep current): ");
        String newUsername = sc.nextLine();
        if (!newUsername.isBlank()) {
            customer.setUsername(newUsername);
        } else {
            newUsername = oldUsername; // use the old one if not changed
        }

        System.out.print("Enter new email (leave blank to keep current): ");
        String newEmail = sc.nextLine();
        if (!newEmail.isBlank()) {
            customer.setEmail(newEmail);
        } else {
            newEmail = customer.getEmail();
        }

        System.out.print("Enter new phone number (leave blank to keep current): ");
        String newPhone = sc.nextLine();
        if (!newPhone.isBlank()) {
            customer.setPhoneNumber(newPhone);
        } else {
            newPhone = customer.getPhoneNumber();
        }

        System.out.print("Enter new address (leave blank to keep current): ");
        String newAddress = sc.nextLine();
        if (!newAddress.isBlank()) {
            customer.setAddress(newAddress);
        } else {
            newAddress = customer.getAddress();
        }

        System.out.println("Profile updated successfully!");

        saveUpdateProfile(oldUsername, newUsername, customer.getPassword(), newEmail, newPhone, newAddress);
}
    
    public void viewOrderHistory() {
        
    }
   
    public static void saveUpdateProfile(String editTerm, String newUsername, String newPassword, String newEmail, String newPhone, String newAddress) {
        String filepath = Utility.USERS_DATABASE;
        String tempFile = Utility.TEMP_USERS_DATABASE;
        File oldFile = new File(filepath);
        File newFile = new File(tempFile);
        
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
        System.out.println("Old file deleted successfully.");

        // ✅ Rename temp to old file
        if (newFile.renameTo(oldFile)) {
            System.out.println("Temp file renamed to original file successfully.");
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
