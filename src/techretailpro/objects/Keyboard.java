package techretailpro.objects;

public class Keyboard extends Product {
    private boolean wireless;
    private String keyType;
    private boolean rgb;

    public Keyboard() {
    }

    public Keyboard(String name, double price, int stock, String description, boolean wireless, String keyType, boolean rgb) {
        super(name, price, stock, description);
        this.wireless = wireless;
        this.keyType = keyType;
        this.rgb = rgb;
    }

    public boolean isWireless() {
        return wireless;
    }

    public void setWireless(boolean wireless) {
        this.wireless = wireless;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public boolean isRgb() {
        return rgb;
    }

    public void setRgb(boolean rgb) {
        this.rgb = rgb;
    }

    @Override
    public String getCategory() {
        return "Keyboard";
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               "\nWireless: " + wireless +
               "\nKey type: " + keyType +
               "\nRGB: " + rgb;
    }
    
    @Override
    public String toStringForDetailedList() {
        return String.format("%-10s %-25s %-8.2f %-5s %-8s %-15s %-5s", getCategory(), getName(), getPrice(), getStock(), wireless, keyType, rgb);       
    }
    
    @Override
    public String toStringForDatabase() {
        return getCategory() + "," + getName() + "," + String.format("%.2f", getPrice()) + "," + getStock() + "," + getDescription() + "," + wireless + "," + keyType + "," + rgb;  
    }
    
    @Override
    public String detailedListHeader() {
        return String.format("%-5s %-10s %-25s %-8s %-5s %-8s %-15s %-5s", "No", "Category", "Name", "Price", "Stock", "Wireless", "Key type", "RGB");
    }
}