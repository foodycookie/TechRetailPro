package techretailpro.objects;

import techretailpro.functions.Utility;

public class Printer extends Product {
    private boolean wireless;
    private double printSpeed;
    private String inkType;

    public Printer() {
    }

    public Printer(String name, double price, int stock, String description, boolean wireless, double printSpeed, String inkType) {
        super(name, price, stock, description);
        this.wireless = wireless;
        this.printSpeed = printSpeed;
        this.inkType = inkType;
    }

    public boolean isWireless() {
        return wireless;
    }

    public void setWireless(boolean wireless) {
        this.wireless = wireless;
    }

    public double getPrintSpeed() {
        return printSpeed;
    }

    public void setPrintSpeed(double printSpeed) {
        this.printSpeed = printSpeed;
    }

    public String getInkType() {
        return inkType;
    }

    public void setInkType(String inkType) {
        this.inkType = inkType;
    }

    @Override
    public String getCategory() {
        return "Printer";
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               "\nWireless: " + wireless +
               "\nPrint speed (PPM): " + printSpeed +
               "\nInk type: " + inkType;
    }

    @Override
    public String toStringForDetailedList() {
        return String.format("%-10s %-25s %-8.2f %-5s %-8s %-5.1f %-10s", getCategory(), getName(), getPrice(), getStock(), wireless, printSpeed, inkType);       
    }
    
    @Override
    public String getDetailedListHeader() {
        return String.format("%-5s %-10s %-25s %-8s %-5s %-8s %-5s %-10s", "No", "Category", "Name", "Price", "Stock", "Wireless", "Speed", "Ink");
    }
    
    @Override
    public String toStringForCsv() {
        return getCategory() + "," + getName() + "," + String.format("%.2f", getPrice()) + "," + getStock() + "," + getDescription() + "," + wireless + "," + printSpeed + "," + inkType;  
    }
    
    @Override
    public String getCsvHeader() {
        return "Category,Name,Price,Stock,Description,Wireless,PrintSpeed,InkType";
    }
    
    @Override
    public String getCsvFilePath() {
        return Utility.PRINTERS_DATABASE;
    }
    
    @Override
    public String getCsvTempFilePath() {
        return Utility.TEMP_PRINTERS_DATABASE;
    }
}