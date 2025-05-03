package techretailpro.objects;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import javax.management.relation.Role;
import techretailpro.functions.UtilityHelper;
import techretailpro.pages.LoginPage;
import techretailpro.pages.MainPage;

public class Admin extends User {
    
    private boolean status;
    
    public Admin (){
        
    }
    
    public Admin(String username, String password, String email) {
        super(username, password, email);
        this.status = true;
    }

    @Override
    public String toCSV() {
        return "admin," + username + "," + password + "," + email + ",,," + status;
    }
    
    public boolean getStatus() {
        return status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean isAdmin() {
        return true;
    }
    
    @Override
    public boolean isCustomer() {
        return false;
    }
    
//    public void addProduct() {
//        
//    }
//    
//    public void deleteProduct() {
//        
//    }
//    
//    public void updateProduct() {
//        
//    }
    
    public void listCustomerAccount() {
        
    }
    
    public void viewCustomerAccount() {
        
    }
    
    public void viewCustomerOrderHistory() {
        
    }
    
    public static void closeAdminAccount(Scanner sc) {
    if (!LocalData.getCurrentUser().isAdmin()) {
        UtilityHelper.displayReturnMessage("Only admins can close their accounts.");
        return;
    }

    System.out.println("Are you sure you want to close your account? Type 'yes' to confirm, or 'no' to cancel.");
    String confirmation = sc.nextLine();

    if ("yes".equalsIgnoreCase(confirmation)) {
        Admin currentAdmin = (Admin) LocalData.getCurrentUser();
        currentAdmin.setStatus(false); 

        try {
            List<String> lines = Files.readAllLines(Paths.get(UtilityHelper.USERS_DATABASE));
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

            Files.write(Paths.get(UtilityHelper.USERS_DATABASE), lines);
            UtilityHelper.displayReturnMessage("Your account has been successfully closed.");
            LoginPage.logout();
        } catch (IOException e) {
            System.err.println("Error closing account: " + e.getMessage());
            UtilityHelper.displayReturnMessage("An error occurred while closing the account.");
        }
    } else if("no".equalsIgnoreCase(confirmation)){
        System.out.println("Account closure canceled.");
    }else{
        System.out.println("Invalid input. Please try again");
    }

}
    
}
