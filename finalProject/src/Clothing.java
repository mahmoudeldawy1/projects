public class Clothing extends Product {
    private final String size;
    private final String fabric;

    public Clothing(String id, String name, double price, int stock, String size, String fabric) {
        super(id, name, price, stock);
        this.size = size;
        this.fabric = fabric;
    }

    @Override
    public String toString() {
        return super.toString() + ", Size: " + size + ", Fabric: " + fabric;
    }
}