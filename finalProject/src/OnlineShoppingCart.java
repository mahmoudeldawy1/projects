import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class OnlineShoppingCart {
    private static ArrayList<Product> productCatalog = new ArrayList<>();
    private static ArrayList<Customer> customers = new ArrayList<>();
    private static ArrayList<Moderator> moderators = new ArrayList<>();
    private static String moderatorPassword = "mahmoud"; // Default password

    /**
     * Loads data from the file to restore application state.
     */
    public static void load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("save.data"))) {
            productCatalog = (ArrayList<Product>) in.readObject();
            customers = (ArrayList<Customer>) in.readObject();
            moderators = (ArrayList<Moderator>) in.readObject();
        } catch (Exception e) {
            System.out.println("No save file found. Starting fresh.");
        }
    }

    /**
     * Saves application state to the file.
     */
    public static void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save.data"))) {
            out.writeObject(productCatalog);
            out.writeObject(customers);
            out.writeObject(moderators);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method to start the application.
     */
    public static void main(String[] args) {
        load(); // Load data from file

        while (true) {
            String[] options = {"Customer Login", "Moderator Login", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "Welcome to Online Shopping System",
                    "Main Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            switch (choice) {
                case 0 -> handleCustomerOperations();
                case 1 -> handleModeratorOperations();
                case 2 -> {
                    save(); // Save data to file before exit
                    JOptionPane.showMessageDialog(null, "Thank you for using our system. Goodbye!");
                    return;
                }
                default -> JOptionPane.showMessageDialog(null, "Invalid Choice!");
            }
        }
    }

    /**
     * Handles operations for customers.
     */
    private static void handleCustomerOperations() {
        String name = JOptionPane.showInputDialog("Enter your name:");
        if (name == null || name.isEmpty()) return;

        Customer customer = findOrCreateCustomer(name);

        while (true) {
            String[] options = {"View Products", "Add Product to Cart", "View Cart", "Remove Product from Cart",
                    "Make Payment", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "Choose an option",
                    "Customer Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            switch (choice) {
                case 0 -> viewAvailableProducts();
                case 1 -> addProductToCart(customer);
                case 2 -> viewCart(customer);
                case 3 -> removeProductFromCart(customer);
                case 4 -> makePayment(customer);
                case 5 -> {
                    JOptionPane.showMessageDialog(null, "Exiting customer menu.");
                    return;
                }
                default -> JOptionPane.showMessageDialog(null, "Invalid choice!");
            }
        }
    }

    /**
     * Handles operations for moderators.
     */
    private static void handleModeratorOperations() {
        String enteredPassword = JOptionPane.showInputDialog("Enter the moderator password:");
        if (enteredPassword == null || !enteredPassword.equals(moderatorPassword)) {
            JOptionPane.showMessageDialog(null, "Incorrect password.");
            return;
        }

        String name = JOptionPane.showInputDialog("Enter your name:");
        if (name == null || name.isEmpty()) return;

        Moderator moderator = findOrCreateModerator(name);

        while (true) {
            String[] options = {"Add Product to Catalog", "View All Customers & Carts", "Change Password",
                    "Clear Data", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "Choose an option",
                    "Moderator Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);

            switch (choice) {
                case 0 -> moderator.addProductToCatalog();
                case 1 -> viewCustomersAndCarts();
                case 2 -> updateModeratorPassword();
                case 3 -> clearAllData();
                case 4 -> {
                    JOptionPane.showMessageDialog(null, "Exiting moderator menu.");
                    return;
                }
                default -> JOptionPane.showMessageDialog(null, "Invalid choice!");
            }
        }
    }

    /**
     * Displays all products available in the catalog.
     */
    private static void viewAvailableProducts() {
        if (productCatalog.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No products available.");
            return;
        }

        StringBuilder productList = new StringBuilder("Product Catalog:\n");
        for (Product product : productCatalog) {
            productList.append(product).append("\n");
        }
        JOptionPane.showMessageDialog(null, productList.toString());
    }

    /**
     * Adds a product to a customer's shopping cart.
     */
    private static void addProductToCart(Customer customer) {
        if (productCatalog.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No products available to add to the cart.");
            return;
        }

        StringBuilder sb = new StringBuilder("Available Products:\n");
        for (int i = 0; i < productCatalog.size(); i++) {
            sb.append(i + 1).append(". ").append(productCatalog.get(i)).append("\n");
        }

        int productIndex;
        try {
            productIndex = Integer.parseInt(JOptionPane.showInputDialog(sb + "\nEnter the product number to add:")) - 1;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input.");
            return;
        }

        if (productIndex < 0 || productIndex >= productCatalog.size()) {
            JOptionPane.showMessageDialog(null, "Invalid product selection!");
            return;
        }

        Product selectedProduct = productCatalog.get(productIndex);

        int quantity;
        try {
            quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity to add:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input.");
            return;
        }

        if (selectedProduct.reduceStock(quantity)) {
            customer.getShoppingCart().addProduct(selectedProduct, quantity);
            JOptionPane.showMessageDialog(null, "Product added!\nCart total: $" + customer.getShoppingCart().getCartTotal());
        } else {
            JOptionPane.showMessageDialog(null, "Not enough stock!");
        }
    }

    /**
     * Displays the contents of a customer's shopping cart.
     */
    private static void viewCart(Customer customer) {
        JOptionPane.showMessageDialog(null, customer.getShoppingCart().toString());
    }

    /**
     * Removes a product from a customer's shopping cart.
     */
    private static void removeProductFromCart(Customer customer) {
        String productId = JOptionPane.showInputDialog("Enter the product ID to remove:");
        Product removed = customer.getShoppingCart().removeProduct(productId);
        if (removed != null) {
            JOptionPane.showMessageDialog(null, "Product removed from cart: " + removed.getName());
        } else {
            JOptionPane.showMessageDialog(null, "Product not found in cart.");
        }
    }

    /**
     * Handles the payment process for a customer's shopping cart.
     */
    private static void makePayment(Customer customer) {
        double total = customer.getShoppingCart().getCartTotal();

        if (total == 0) {
            JOptionPane.showMessageDialog(null, "Your cart is empty!");
            return;
        }

        String[] paymentOptions = {"Credit Card", "PayPal"};
        int choice = JOptionPane.showOptionDialog(null, "Choose your payment method:",
                "Payment", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, paymentOptions, paymentOptions[0]);

        if (choice == -1) return;

        String method = paymentOptions[choice];
        JOptionPane.showMessageDialog(null, "Processing payment with " + method + "...\nPayment successful!");

        // Clear cart after payment
        customer.getShoppingCart().clear();
        JOptionPane.showMessageDialog(null, "Your cart is now empty. Payment complete!");
    }

    /**
     * Displays all customers and their carts for the moderator.
     */
    private static void viewCustomersAndCarts() {
        StringBuilder sb = new StringBuilder("Customer Data:\n");
        for (Customer customer : customers) {
            sb.append(customer).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    /**
     * Updates the moderator password.
     */
    private static void updateModeratorPassword() {
        String newPassword = JOptionPane.showInputDialog("Enter the new moderator password:");
        if (newPassword != null && !newPassword.isEmpty()) {
            moderatorPassword = newPassword;
            JOptionPane.showMessageDialog(null, "Password updated successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Password cannot be blank.");
        }
    }

    /**
     * Clears all data for moderators.
     */
    private static void clearAllData() {
        productCatalog.clear();
        customers.clear();
        JOptionPane.showMessageDialog(null, "All data has been cleared.");
    }

    /**
     * Finds or creates a customer based on the given name.
     */
    private static Customer findOrCreateCustomer(String name) {
        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(name)) {
                return customer;
            }
        }
        Customer newCustomer = new Customer(String.valueOf(customers.size() + 1), name);
        customers.add(newCustomer);
        return newCustomer;
    }

    /**
     * Finds or creates a moderator based on the given name.
     */
    private static Moderator findOrCreateModerator(String name) {
        for (Moderator moderator : moderators) {
            if (moderator.getName().equalsIgnoreCase(name)) {
                return moderator;
            }
        }
        Moderator newModerator = new Moderator(String.valueOf(moderators.size() + 1), name);
        moderators.add(newModerator);
        return newModerator;
    }

    /**
     * Adds a product to the catalog.
     */
    public static void addProductToCatalog(Product product) {
        if (productCatalog == null) {
            productCatalog = new ArrayList<>();
        }
        productCatalog.add(product);
        JOptionPane.showMessageDialog(null, "Product added to catalog successfully!");
    }
}