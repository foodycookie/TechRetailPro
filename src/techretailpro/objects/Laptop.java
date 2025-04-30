package techretailpro.objects;

public class Laptop extends Product {
    private String processor;    
    private int memory;
    private int storage;

    public Laptop() {
    }

    public Laptop(String name, double price, int stock, String description, String processor, int memory, int storage) {
        super(name, price, stock, description);
        this.processor = processor;
        this.memory = memory;
        this.storage = storage;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }
    
    @Override
    public String getCategory() {
        return "Laptop";
    }
    
    @Override
    public String toString() {
        return super.toString() +
               "\nProcessor: " + processor +
               "\nMemory (GB): " + memory +
               "\nStorage (GB): " + storage;
    }
    
    @Override
    public String toStringForDetailedList() {
        return String.format("%-10s %-25s %-8.2f %-5s %-25s %-6d %-7d", getCategory(), getName(), getPrice(), getStock(), processor, memory, storage);       
    }
    
    @Override
    public String toStringForDatabase() {
        return getCategory() + "," + getName() + "," + String.format("%.2f", getPrice()) + "," + getStock() + "," + getDescription() + "," + processor + "," + memory + "," + storage;  
    }
    
    @Override
    public String getDetailedListHeader() {
        return String.format("%-5s %-10s %-25s %-8s %-5s %-25s %-6s %-7s", "No", "Category", "Name", "Price", "Stock", "Processor", "Memory", "Storage");
    }
}