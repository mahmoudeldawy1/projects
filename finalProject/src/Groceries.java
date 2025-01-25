public class Groceries extends Product {
    private final String expiryDate;

    public Groceries(String id, String name, double price, int stock, String expiryDate) {
        super(id, name, price, stock);
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return super.toString() + ", Expiry Date: " + expiryDate;
    }
}