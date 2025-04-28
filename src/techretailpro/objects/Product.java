package techretailpro.objects;

//Abstract because me do not want to create a product object
public abstract class Product {
    private String name;
    private double price;
    private int stock;
    private String description;

    public Product() {
    }

    public Product(String name, double price, int stock, String description) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract String getCategory();
    
    public boolean isAvailable() {
        return stock > 0;
    }
    
    public boolean isLowStock() {
        return stock < 5;
    }

    @Override
    public String toString() {
        return "Category: " + getCategory() + 
               "\nName: " + name +
               "\nPrice: " + String.format("%.2f", price) + 
               "\nStock: " + stock + 
               "\nDescription: " + description;
    }
    
    public String toStringForGeneralList() {
        return String.format("%-10s %-25s %-8.2f %-5d", getCategory(), name, price, stock);       
    }
    
    public abstract String toStringForDetailedList();

    public abstract String toStringForDatabase();
    
    public String generalListHeader() {
        return String.format("%-5s %-10s %-25s %-8s %-5s", "No", "Category", "Name", "Price", "Stock");
    }
    
    public abstract String detailedListHeader();
}