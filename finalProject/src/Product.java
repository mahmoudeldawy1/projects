import java.io.Serializable;

public abstract class Product implements Serializable {
    private final String id;
    private final String name;
    private final double price;
    private int stock;

    public Product(String id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public boolean reduceStock(int amount) {
        if (amount > stock) {
            return false;
        }
        stock -= amount;
        return true;
    }

    @Override
    public String toString() {
        return name + " (ID: " + id + ", Price: $" + price + ", Stock: " + stock + ")";
    }
}