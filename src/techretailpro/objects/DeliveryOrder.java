package techretailpro.objects;

import java.util.*;

public class DeliveryOrder extends CartOrder{
    private final double deliveryFee;
    private final String address;

    public DeliveryOrder(List<CartItem> orderItems, double originalTotal, double totalAmount,
                         double discountAmount, double deliveryFee, String address) {
        super(orderItems, originalTotal, totalAmount, discountAmount);
        this.deliveryFee = deliveryFee;
        this.address = address;
    }
    
    @Override
    public double getDeliveryFee() {
        return deliveryFee;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String getDeliveryMethod() {
        return "Delivery";
    }

    @Override
    public String toString() {
        return super.toString()
             + String.format("\nDelivery Fee        : RM%.2f", deliveryFee)
             + String.format("\nDelivery Address    : %s", address)
             + "\nDelivery Method     : Delivery";
    }
}