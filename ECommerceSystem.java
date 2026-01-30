import java.io.*;
import java.util.*;

// 1. OOP: Product Class represents items in the shop
class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return id + ". " + name + " - $" + price;
    }
}

// 1. OOP: User Class represents a registered user
class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}

public class ECommerceSystem {
    // Array/List Management
    private static List<Product> productCatalog = new ArrayList<>();
    private static List<Product> cart = new ArrayList<>();
    private static final String PRODUCTS_FILE = "products.txt";
    private static final String USERS_FILE = "users.txt";
    private static final String ORDERS_FILE = "orders.txt";
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        initializeFiles(); // Ensure files exist with dummy data
        loadProducts();    // Load products into memory

        System.out.println("=== Welcome to the Java E-Commerce System ===");

        // User Authentication Loop
        while (currentUser == null) {
            System.out.println("\n1. Login\n2. Register\n3. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                login();
            } else if (choice.equals("2")) {
                register();
            } else if (choice.equals("3")) {
                System.out.println("Goodbye!");
                return;
            } else {
                System.out.println("Invalid option.");
            }
        }

        // Main Application Loop
        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Browse Products");
            System.out.println("2. View Cart");
            System.out.println("3. Checkout");
            System.out.println("4. Logout");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    browseProducts();
                    break;
                case "2":
                    viewCart();
                    break;
                case "3":
                    checkout();
                    break;
                case "4":
                    running = false;
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // --- FEATURE: File Handling & Data Persistence ---
    
    // Create initial files if they are missing
    private static void initializeFiles() {
        File pFile = new File(PRODUCTS_FILE);
        if (!pFile.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(pFile))) {
                writer.write("1,Apple,0.99\n");
                writer.write("2,Bread,2.50\n");
                writer.write("3,Milk,1.20\n");
                writer.write("4,Cheese,5.00\n");
                writer.write("5,Coffee,12.99\n");
            } catch (IOException e) {
                System.out.println("Error creating product file: " + e.getMessage());
            }
        }
    }

    // Read products from file into ArrayList
    private static void loadProducts() {
        productCatalog.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    productCatalog.add(new Product(id, name, price));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading products.");
        }
    }

    // Append new order to orders.txt
    private static void saveOrder(double total) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDERS_FILE, true))) {
            writer.write("User: " + currentUser.getUsername() + ", Total: $" + String.format("%.2f", total) + ", Date: " + new Date() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving order.");
        }
    }

    // Append new user to users.txt
    private static void saveUser(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(username + "," + password + "\n");
        } catch (IOException e) {
            System.out.println("Error registering user.");
        }
    }

    // Check credentials against users.txt
    private static boolean validateUser(String username, String password) {
        File uFile = new File(USERS_FILE);
        if (!uFile.exists()) return false;

        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error validating user.");
        }
        return false;
    }

    // --- FEATURE: User Authentication ---

    private static void register() {
        System.out.print("Enter new username: ");
        String user = scanner.nextLine();
        System.out.print("Enter new password: ");
        String pass = scanner.nextLine();
        
        saveUser(user, pass);
        System.out.println("Registration successful! Please login.");
    }

    private static void login() {
        System.out.print("Username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        if (validateUser(user, pass)) {
            currentUser = new User(user, pass);
            System.out.println("Login successful! Welcome, " + user);
        } else {
            System.out.println("Invalid credentials or user does not exist.");
        }
    }

    // --- FEATURE: Browsing & Cart Management ---

    private static void browseProducts() {
        System.out.println("\n--- Product Catalog ---");
        for (Product p : productCatalog) {
            System.out.println(p);
        }
        System.out.print("Enter Product ID to add to cart (or 0 to go back): ");
        try {
            int pid = Integer.parseInt(scanner.nextLine());
            if (pid == 0) return;

            Product selected = null;
            for (Product p : productCatalog) {
                if (p.getId() == pid) {
                    selected = p;
                    break;
                }
            }

            if (selected != null) {
                cart.add(selected);
                System.out.println(selected.getName() + " added to cart.");
            } else {
                System.out.println("Product not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private static void viewCart() {
        System.out.println("\n--- Your Cart ---");
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
        } else {
            double total = 0;
            for (Product p : cart) {
                System.out.println(p.getName() + " - $" + p.getPrice());
                total += p.getPrice();
            }
            System.out.println("Total: $" + String.format("%.2f", total));
        }
    }

    private static void checkout() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Add items first.");
            return;
        }

        double total = 0;
        for (Product p : cart) {
            total += p.getPrice();
        }

        System.out.println("Processing payment for $" + String.format("%.2f", total) + "...");
        saveOrder(total); // Data Persistence: Save order to file
        cart.clear(); // Empty the cart
        System.out.println("Payment successful! Order saved.");
    }
}
