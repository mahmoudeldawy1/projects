public class Electronics extends Product {
    private final String brand;
    private final int warranty;

    public Electronics(String id, String name, double price, int stock, String brand, int warranty) {
        super(id, name, price, stock);
        this.brand = brand;
        this.warranty = warranty;
    }

    @Override
    public String toString() {
        return super.toString() + ", Brand: " + brand + ", Warranty: " + warranty + " months";
    }
}