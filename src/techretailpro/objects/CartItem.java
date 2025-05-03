package techretailpro.objects;

public class CartItem {
    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    public double getSubtotal() {
        return product.getPrice() * quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return String.format("%-15s RM%-8.2f %-10d RM%-10.2f",
            product.getName(), product.getPrice(), quantity, getSubtotal());
    }
}
