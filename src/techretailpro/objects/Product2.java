package techretailpro.objects;

// Product class
public class Product2 {
    private final String id;
    private final String name;
    private final double price;
    // encapsulation implemented

    public Product2(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
}
