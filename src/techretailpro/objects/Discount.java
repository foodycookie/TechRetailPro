package techretailpro.objects;

public class Discount {
    private String code;
    private double amount;

    public Discount(String code) {
        this.code = code;
    }
    
    public Discount(){
    }

    public String getCode() {
        return code;
    }

    public double getAmount() {
        return amount;
    }   
}