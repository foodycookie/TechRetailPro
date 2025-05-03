package techretailpro.objects;

import techretailpro.functions.Utility;

public class Headphone extends Product {
    private boolean wireless;
    private boolean noiseCancellation;
    private boolean microphone;

    public Headphone() {
    }

    public Headphone(String name, double price, int stock, String description, boolean wireless, boolean noiseCancellation, boolean microphone) {
        super(name, price, stock, description);
        this.wireless = wireless;
        this.noiseCancellation = noiseCancellation;
        this.microphone = microphone;
    }

    public boolean isWireless() {
        return wireless;
    }

    public void setWireless(boolean wireless) {
        this.wireless = wireless;
    }

    public boolean isNoiseCancellation() {
        return noiseCancellation;
    }

    public void setNoiseCancellation(boolean noiseCancellation) {
        this.noiseCancellation = noiseCancellation;
    }

    public boolean isMicrophone() {
        return microphone;
    }

    public void setMicrophone(boolean microphone) {
        this.microphone = microphone;
    }
    
    @Override
    public String getCategory() {
        return "Headphone";
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               "\nWireless: " + wireless +
               "\nNoise cancellation: " + noiseCancellation +
               "\nMicrophone: " + microphone;
    }

    @Override
    public String toStringForDetailedList() {
        return String.format("%-10s %-25s %-8.2f %-5s %-8s %-5s %-5s", getCategory(), getName(), getPrice(), getStock(), wireless, noiseCancellation, microphone);       
    }
    
    @Override
    public String getDetailedListHeader() {
        return String.format("%-5s %-10s %-25s %-8s %-5s %-8s %-5s %-5s", "No", "Category", "Name", "Price", "Stock", "Wireless", "NC", "Mic");
    }
    
    @Override
    public String toStringForCsv() {
        return getCategory() + "," + getName() + "," + String.format("%.2f", getPrice()) + "," + getStock() + "," + getDescription() + "," + wireless + "," + noiseCancellation + "," + microphone;  
    }
    
    @Override
    public String getCsvHeader() {
        return "Category,Name,Price,Stock,Description,Wireless,NoiseCancellation,Microphone";
    }
    
    @Override
    public String getCsvFilePath() {
        return Utility.HEADPHONES_DATABASE;
    }
    
    @Override
    public String getCsvTempFilePath() {
        return Utility.TEMP_HEADPHONES_DATABASE;
    }
}