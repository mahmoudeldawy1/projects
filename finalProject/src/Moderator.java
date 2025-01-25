import javax.swing.*;
import java.io.Serializable;

public class Moderator extends User implements Serializable {
    public Moderator(String id, String name) {
        super(id, name);
    }

    public void addProductToCatalog() {
        String type = JOptionPane.showInputDialog("Enter product type (Electronics, Groceries, Clothing):");
        if (type == null || type.isEmpty()) return;

        String id = JOptionPane.showInputDialog("Enter product ID:");
        String name = JOptionPane.showInputDialog("Enter product name:");
        double price = Double.parseDouble(JOptionPane.showInputDialog("Enter product price:"));
        int stock = Integer.parseInt(JOptionPane.showInputDialog("Enter product stock:"));

        Product newProduct = switch (type.toLowerCase()) {
            case "electronics" -> {
                String brand = JOptionPane.showInputDialog("Enter brand:");
                int warranty = Integer.parseInt(JOptionPane.showInputDialog("Enter warranty (in months):"));
                yield new Electronics(id, name, price, stock, brand, warranty);
            }
            case "groceries" -> {
                String expiryDate = JOptionPane.showInputDialog("Enter expiry date:");
                yield new Groceries(id, name, price, stock, expiryDate);
            }
            case "clothing" -> {
                String size = JOptionPane.showInputDialog("Enter size:");
                String fabric = JOptionPane.showInputDialog("Enter fabric:");
                yield new Clothing(id, name, price, stock, size, fabric);
            }
            default -> {
                JOptionPane.showMessageDialog(null, "Invalid product type.");
                yield null;
            }
        };

        if (newProduct != null) {
            OnlineShoppingCart.addProductToCatalog(newProduct);
        }
    }
}