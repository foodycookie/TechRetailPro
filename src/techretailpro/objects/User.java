package techretailpro.objects;

public abstract class User {
    protected String username;
    protected String password;
    protected String email;


    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + ", email=" + email + '}';
    }

    public void register() {
    }
    
    public abstract String toCSV();
    
    public abstract boolean isAdmin();
    
    public abstract boolean isCustomer();
    
    public void logout() {
        
    }
}