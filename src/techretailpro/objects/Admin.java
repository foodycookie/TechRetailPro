package techretailpro.objects;

public class Admin extends User {
    
    public Admin(String username, String password, String email) {
        super(username, password, email);
    }

    @Override
    public String toCSV() {
        return "admin," + username + "," + password + "," + email + ",,,";
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
    
}
