package techretailpro.objects;

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
}