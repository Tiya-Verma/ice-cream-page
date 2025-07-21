package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Console-based user interface for the Ice Cream Shop Pre-Order System
 */
public class IceCreamShopApp {
    private static final String JSON_STORE = "./data/orderqueue.json";
    private OrderQueue orderQueue;
    private List<Flavor> availableFlavors;
    private List<Topping> availableToppings;
    private List<Size> availableSizes;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    /**
     * EFFECTS: runs the ice cream shop application
     */
    public IceCreamShopApp() {
        runIceCreamShop();
    }

    /**
     * MODIFIES: this
     * EFFECTS: processes user input
     */
    private void runIceCreamShop() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nThank you for using the Ice Cream Shop Pre-Order System!");
    }

    /**
     * MODIFIES: this
     * EFFECTS: processes user command
     */
    private void processCommand(String command) {
        if (command.equals("a")) {
            addNewOrder();
        } else if (command.equals("v")) {
            viewPendingOrders();
        } else if (command.equals("c")) {
            completeOrder();
        } else if (command.equals("x")) {
            cancelOrder();
        } else if (command.equals("t")) {
            viewPrepTime();
        } else if (command.equals("m")) {
            viewMenu();
        } else if (command.equals("s")) {
            saveOrderQueue();
        } else if (command.equals("l")) {
            loadOrderQueue();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    /**
     * MODIFIES: this
     * EFFECTS: initializes the application with sample data
     */
    private void init() {
        orderQueue = new OrderQueue();
        input = new Scanner(System.in);
        input.useDelimiter("\r?\n|\r");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        // Initialize available flavors
        availableFlavors = new ArrayList<>();
        availableFlavors.add(new Flavor("Vanilla", 3.50));
        availableFlavors.add(new Flavor("Chocolate", 4.00));
        availableFlavors.add(new Flavor("Strawberry", 3.75));
        availableFlavors.add(new Flavor("Mint Chocolate Chip", 4.25));
        availableFlavors.add(new Flavor("Cookie Dough", 4.50));

        // Initialize available toppings
        availableToppings = new ArrayList<>();
        availableToppings.add(new Topping("Sprinkles", 0.50));
        availableToppings.add(new Topping("Nuts", 1.00));
        availableToppings.add(new Topping("Chocolate Chips", 0.75));
        availableToppings.add(new Topping("Berries", 0.25));
        availableToppings.add(new Topping("Waffle", 0.50));

        // Initialize available sizes
        availableSizes = new ArrayList<>();
        availableSizes.add(new Size("Small", 1.0));
        availableSizes.add(new Size("Medium", 1.25));
        availableSizes.add(new Size("Large", 1.5));
    }

    /**
     * EFFECTS: displays menu of options to user
     */
    private void displayMenu() {
        System.out.println("\n=== Ice Cream Shop Pre-Order System ===");
        System.out.println("Select from:");
        System.out.println("\ta -> add new order");
        System.out.println("\tv -> view pending orders");
        System.out.println("\tc -> complete order");
        System.out.println("\tx -> cancel order");
        System.out.println("\tt -> view preparation time");
        System.out.println("\tm -> view menu");
        System.out.println("\ts -> save order queue to file");
        System.out.println("\tl -> load order queue from file");
        System.out.println("\tq -> quit");
    }

    /**
     * MODIFIES: this
     * EFFECTS: conducts adding a new order
     */
    private void addNewOrder() {
        System.out.println("\n=== Add New Order ===");

        // Get customer name
        System.out.print("Enter customer name: ");
        input.nextLine(); // consume newline
        String customerName = input.nextLine();

        // Select flavor
        Flavor selectedFlavor = selectFlavor();
        if (selectedFlavor == null) {
            return;
        }

        // Select size
        Size selectedSize = selectSize();
        if (selectedSize == null) {
            return;
        }

        // Create order
        Order newOrder = new Order(customerName, selectedFlavor, selectedSize, "ASAP", 5);

        // Add toppings
        addToppingsToOrder(newOrder);

        // Add to queue
        orderQueue.addOrder(newOrder);
        System.out.println("\nOrder added successfully!");
        System.out.println("Order ID: " + newOrder.getOrderId());
        System.out.println("Total Price: $" + String.format("%.2f", newOrder.getTotalPrice()));
    }

    /**
     * EFFECTS: prompts user to select a flavor and returns it
     */
    private Flavor selectFlavor() {
        System.out.println("\nAvailable Flavors:");
        for (int i = 0; i < availableFlavors.size(); i++) {
            Flavor flavor = availableFlavors.get(i);
            System.out.println((i + 1) + ". " + flavor.toString());
        }

        System.out.print("Select flavor (1-" + availableFlavors.size() + "): ");
        int selection = input.nextInt();

        if (selection >= 1 && selection <= availableFlavors.size()) {
            return availableFlavors.get(selection - 1);
        } else {
            System.out.println("Invalid selection.");
            return null;
        }
    }

    /**
     * EFFECTS: prompts user to select a size and returns it
     */
    private Size selectSize() {
        System.out.println("\nAvailable Sizes:");
        for (int i = 0; i < availableSizes.size(); i++) {
            Size size = availableSizes.get(i);
            System.out.println((i + 1) + ". " + size.toString());
        }

        System.out.print("Select size (1-" + availableSizes.size() + "): ");
        int selection = input.nextInt();

        if (selection >= 1 && selection <= availableSizes.size()) {
            return availableSizes.get(selection - 1);
        } else {
            System.out.println("Invalid selection.");
            return null;
        }
    }

    /**
     * MODIFIES: order
     * EFFECTS: prompts user to add toppings to the order
     */
    private void addToppingsToOrder(Order order) {
        System.out.println("\nAvailable Toppings:");
        for (int i = 0; i < availableToppings.size(); i++) {
            Topping topping = availableToppings.get(i);
            System.out.println((i + 1) + ". " + topping.toString());
        }
        System.out.println("0. No toppings");

        while (true) {
            System.out.print("Select topping (0-" + availableToppings.size() + "): ");
            int selection = input.nextInt();

            if (selection == 0) {
                break;
            } else if (selection >= 1 && selection <= availableToppings.size()) {
                Topping selectedTopping = availableToppings.get(selection - 1);
                order.addTopping(selectedTopping);
                System.out.println("Added: " + selectedTopping.getName());
            } else {
                System.out.println("Invalid selection.");
            }
        }
    }

    /**
     * EFFECTS: displays all pending orders
     */
    private void viewPendingOrders() {
        System.out.println("\n=== Pending Orders ===");
        List<Order> pendingOrders = orderQueue.getPendingOrders();

        if (pendingOrders.isEmpty()) {
            System.out.println("No pending orders.");
        } else {
            for (Order order : pendingOrders) {
                System.out.println(order.toString());
                System.out.println("---");
            }
        }
    }

    /**
     * MODIFIES: this
     * EFFECTS: conducts completing an order
     */
    private void completeOrder() {
        System.out.println("\n=== Complete Order ===");

        if (orderQueue.isEmpty()) {
            System.out.println("No pending orders to complete.");
            return;
        }

        System.out.print("Enter order ID to complete: ");
        int orderId = input.nextInt();

        Order order = orderQueue.findOrderById(orderId);
        if (order != null && !order.isCompleted()) {
            orderQueue.completeOrder(orderId);
            System.out.println("Order #" + orderId + " marked as completed.");
        } else {
            System.out.println("Order not found or already completed.");
        }
    }

    /**
     * MODIFIES: this
     * EFFECTS: conducts canceling an order
     */
    private void cancelOrder() {
        System.out.println("\n=== Cancel Order ===");

        if (orderQueue.isEmpty()) {
            System.out.println("No pending orders to cancel.");
            return;
        }

        System.out.print("Enter order ID to cancel: ");
        int orderId = input.nextInt();

        Order order = orderQueue.findOrderById(orderId);
        if (order != null && !order.isCompleted()) {
            orderQueue.cancelOrder(orderId);
            System.out.println("Order #" + orderId + " has been cancelled.");
        } else {
            System.out.println("Order not found or already completed.");
        }
    }

    /**
     * EFFECTS: displays preparation time information
     */
    private void viewPrepTime() {
        System.out.println("\n=== Preparation Time ===");

        if (orderQueue.isEmpty()) {
            System.out.println("No pending orders.");
        } else {
            int prepTime = orderQueue.getNextOrderPrepTime();
            System.out.println("Estimated preparation time for next order: " + prepTime + " minutes");
            System.out.println("Number of pending orders: " + orderQueue.getPendingOrderCount());
        }
    }

    /**
     * EFFECTS: displays the menu of available items
     */
    private void viewMenu() {
        System.out.println("\n=== Menu ===");

        System.out.println("FLAVORS:");
        for (Flavor flavor : availableFlavors) {
            System.out.println("  " + flavor.toString());
        }

        System.out.println("\nSIZES:");
        for (Size size : availableSizes) {
            System.out.println("  " + size.toString());
        }

        System.out.println("\nTOPPINGS:");
        for (Topping topping : availableToppings) {
            System.out.println("  " + topping.toString());
        }
    }

    /**
     * EFFECTS: saves the order queue to file
     */
    private void saveOrderQueue() {
        try {
            jsonWriter.open();
            jsonWriter.write(orderQueue);
            jsonWriter.close();
            System.out.println("Saved order queue to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    /**
     * MODIFIES: this
     * EFFECTS: loads order queue from file
     */
    private void loadOrderQueue() {
        try {
            orderQueue = jsonReader.read();
            System.out.println("Loaded order queue from " + JSON_STORE);
            System.out.println("Pending orders: " + orderQueue.getPendingOrderCount());
            System.out.println("Completed orders: " + orderQueue.getCompletedOrderCount());
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            System.out.println("Starting with empty order queue");
        }
    }
}