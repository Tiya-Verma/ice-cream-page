package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an ice cream order with flavor, toppings, size, and customer
 * information
 */
public class Order {
    private static int nextOrderId = 1;
    private int orderId;
    private String customerName;
    private Flavor flavor;
    private List<Topping> toppings;
    private Size size;
    private String pickupTime;
    private boolean isCompleted;
    private int estimatedPrepTime; // in minutes

    /**
     * REQUIRES: customerName has non-zero length, flavor is not null, size is not
     * null,
     * pickupTime has non-zero length, and prepTime >= 0
     * EFFECTS: creates an order with given details, initially not completed
     */
    public Order(String customerName, Flavor flavor, Size size, String pickupTime, int prepTime) {
        // stub
    }

    /**
     * EFFECTS: returns the order ID
     */
    public int getOrderId() {
        return 0;
        // stub
    }

    /**
     * EFFECTS: returns the customer name
     */
    public String getCustomerName() {
        return "";
        // stub
    }

    /**
     * EFFECTS: returns the selected flavor
     */
    public Flavor getFlavor() {
        return null;
        // stub
    }

    /**
     * EFFECTS: returns the list of selected toppings
     */
    public List<Topping> getToppings() {
        return null;
        // stub
    }

    /**
     * EFFECTS: returns the selected size
     */
    public Size getSize() {
        return null;
        // stub
    }

    /**
     * EFFECTS: returns the pickup time
     */
    public String getPickupTime() {
        return "";
        // stub
    }

    /**
     * EFFECTS: returns true if order is completed, false otherwise
     */
    public boolean isCompleted() {
        return false;
        // stub
    }

    /**
     * EFFECTS: returns the estimated preparation time in minutes
     */
    public int getEstimatedPrepTime() {
        return 0;
        // stub
    }

    /**
     * REQUIRES: topping is not null
     * MODIFIES: this
     * EFFECTS: adds the given topping to this order
     */
    public void addTopping(Topping topping) {
        // stub
    }

    /**
     * MODIFIES: this
     * EFFECTS: marks this order as completed
     */
    public void markCompleted() {
        // stub
    }

    /**
     * EFFECTS: calculates and returns the total price of this order
     */
    public double getTotalPrice() {
        return 0.0;
        // stub
    }

    /**
     * EFFECTS: returns a string representation of this order
     */
    @Override
    public String toString() {
        // stub
        return "";
    }
}