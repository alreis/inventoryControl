

import java.util.List;
import java.util.Scanner;



public class App {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;
        do {
            System.out.println("==== Inventory Control ====");
            System.out.println("1. Add Product");
            System.out.println("2. Update Inventory");
            System.out.println("3. Generate Report");
            System.out.println("0. Exit\n");

            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        addProduct();
                        break;
                    case 2:
                        updateInventory();
                        break;
                    case 3:
                        generateReport();
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 0, 1, 2, or 3.\n");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.\n");
                sc.nextLine(); 
                choice = -1; 
            } 
        } while (choice != 0);
        
        System.out.println("System Closed.");

    }
    private static void addProduct() {

        Product product = new Product();
        System.out.print("Enter the product name: ");
        product.setName(sc.nextLine());
        System.out.print("Enter the quantity in inventory: ");
        product.setAmount(sc.nextInt());
        product.addProduct();
        System.out.println("Product added successfully!\n");
  
    }

    private static Product getProductById(int id) {
        try {
            List<Product> products = Product.getReport();
            for (Product product : products) {
                if (product.getId() == id) {
                    return product;
                }
            }
            return null; 
        } catch (Exception e) {
            System.out.println("Error getting product by ID: " + e.getMessage());
            return null;
        }
    }

    private static void updateInventory() {
     
        System.out.print("Enter de product ID: ");
        int id = sc.nextInt();
        Product product = getProductById(id);
        if (product != null) {
            System.out.print("Enter the new quantity in inventory: ");
            product.setAmount(sc.nextInt());
            product.updateInventory();
            System.out.println("Inventory updated successfully!\n");
        } else {
            System.out.println("Product not found.");
        }

    }

    private static void generateReport() {
        List<Product> products = Product.getReport();
        System.out.println("\n==== Inventory Report ====");
        for (Product product : products) {
            System.out.println("ID: " + product.getId() +
                    ", Name: " + product.getName() +
                    ", Quantity in inventory: " + product.getAmount());
        }
        System.out.println("\n");
    }
    
}
