import java.io.Serializable;
import java.util.HashMap;

public class ShoppingCart implements Serializable {
    private final HashMap<Product, Integer> products = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        products.put(product, products.getOrDefault(product, 0) + quantity);
    }

    public Product removeProduct(String productId) {
        for (Product product : products.keySet()) {
            if (product.getId().equals(productId)) {
                products.remove(product);
                return product;
            }
        }
        return null;
    }

    public double getCartTotal() {
        return products.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue()).sum();
    }

    public void clear() {
        products.clear();
    }

    @Override
    public String toString() {
        if (products.isEmpty()) return "Your cart is empty.";
        StringBuilder sb = new StringBuilder("Shopping Cart:\n");
        products.forEach((product, quantity) -> sb.append(product).append(", Quantity: ").append(quantity).append("\n"));
        sb.append("Total: $").append(getCartTotal());
        return sb.toString();
    }
}