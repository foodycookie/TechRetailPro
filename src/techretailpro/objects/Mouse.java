package techretailpro.objects;

import techretailpro.functions.UtilityHelper;

public class Mouse extends Product {
    private boolean wireless;
    private String sensorType;
    private int dpi;

    public Mouse() {
    }

    public Mouse(String name, double price, int stock, String description, boolean wireless, String sensorType, int dpi) {
        super(name, price, stock, description);
        this.wireless = wireless;
        this.sensorType = sensorType;
        this.dpi = dpi;
    }

    public boolean isWireless() {
        return wireless;
    }

    public void setWireless(boolean wireless) {
        this.wireless = wireless;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public int getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }
    
    @Override
    public String getCategory() {
        return "Mouse";
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               "\nWireless: " + wireless +
               "\nSensor type: " + sensorType +
               "\nDPI: " + dpi;
    }
    
    @Override
    public String toStringForDetailedList() {
        return String.format("%-10s %-30s %-8.2f %-5s %-8s %-12s %-5d", getCategory(), getName(), getPrice(), getStock(), wireless, sensorType, dpi);       
    }
    
    @Override
    public String getDetailedListHeader() {
        return String.format("%-5s %-10s %-30s %-8s %-5s %-8s %-12s %-5s", "No", "Category", "Name", "Price", "Stock", "Wireless", "Sensor type", "DPI");
    }
    
    @Override
    public String toStringForCsv() {
        return getCategory() + "," + getName() + "," + String.format("%.2f", getPrice()) + "," + getStock() + "," + getDescription() + "," + wireless + "," + sensorType + "," + dpi;  
    }
    
    @Override
    public String getCsvHeader() {
        return "Category,Name,Price,Stock,Description,Wireless,SensorType,DPI";
    }
    
    @Override
    public String getCsvFilePath() {
        return UtilityHelper.MICE_DATABASE;
    }
    
    @Override
    public String getCsvTempFilePath() {
        return UtilityHelper.TEMP_MICE_DATABASE;
    }
}