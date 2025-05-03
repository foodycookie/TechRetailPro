package techretailpro.objects;

import java.util.*;

public class PickUpOrder extends CartOrder{
    public PickUpOrder(List<CartItem> orderItems, double originalTotal, double totalAmount, double discountAmount) {
        super(orderItems, originalTotal, totalAmount, discountAmount);
    }

    @Override
    public String getDeliveryMethod() {
        return "Pickup";
    }

    @Override
    public String toString() {
        return super.toString() + "\nDelivery Method     : Pickup";
    }
}