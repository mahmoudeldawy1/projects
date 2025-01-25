import java.io.Serializable;

public class Customer extends User implements Serializable {
    private final ShoppingCart shoppingCart;

    public Customer(String id, String name) {
        super(id, name);
        this.shoppingCart = new ShoppingCart();
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    @Override
    public String toString() {
        return super.toString() + "\nCart: " + shoppingCart;
    }
}