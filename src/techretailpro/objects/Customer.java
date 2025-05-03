package techretailpro.objects;

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
}