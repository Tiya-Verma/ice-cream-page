package model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

/**
 * Represents an ice cream order with flavor, toppings, size, and customer
 * information
 */
public class Order implements Writable {
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
        this.orderId = nextOrderId++;
        this.customerName = customerName;
        this.flavor = flavor;
        this.toppings = new ArrayList<>();
        this.size = size;
        this.pickupTime = pickupTime;
        this.isCompleted = false;
        this.estimatedPrepTime = prepTime;
    }

    /**
     * EFFECTS: returns the order ID
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * EFFECTS: returns the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * EFFECTS: returns the selected flavor
     */
    public Flavor getFlavor() {
        return flavor;
    }

    /**
     * EFFECTS: returns the list of selected toppings
     */
    public List<Topping> getToppings() {
        return new ArrayList<>(toppings);
    }

    /**
     * EFFECTS: returns the selected size
     */
    public Size getSize() {
        return size;
    }

    /**
     * EFFECTS: returns the pickup time
     */
    public String getPickupTime() {
        return pickupTime;
    }

    /**
     * EFFECTS: returns true if order is completed, false otherwise
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * EFFECTS: returns the estimated preparation time in minutes
     */
    public int getEstimatedPrepTime() {
        return estimatedPrepTime;
    }

    /**
     * REQUIRES: topping is not null
     * MODIFIES: this
     * EFFECTS: adds the given topping to this order
     */
    public void addTopping(Topping topping) {
        toppings.add(topping);
    }

    /**
     * MODIFIES: this
     * EFFECTS: marks this order as completed
     */
    public void markCompleted() {
        this.isCompleted = true;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("orderId", orderId);
        json.put("customerName", customerName);
        json.put("flavor", flavor.toJson());
        json.put("size", size.toJson());
        json.put("toppings", toppingsToJson());
        json.put("pickupTime", pickupTime);
        json.put("isCompleted", isCompleted);
        json.put("estimatedPrepTime", estimatedPrepTime);
        return json;
    }

    // EFFECTS: returns toppings in this order as a JSON array
    private JSONArray toppingsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Topping topping : toppings) {
            jsonArray.put(topping.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns the current nextOrderId value
    public static int getNextOrderId() {
        return nextOrderId;
    }

    // MODIFIES: this (static field)
    // EFFECTS: sets the nextOrderId to the given value
    public static void setNextOrderId(int id) {
        nextOrderId = id;
    }

    // MODIFIES: this
    // EFFECTS: sets the order ID for this order (used when loading from JSON)
    public void setOrderId(int id) {
        this.orderId = id;
    }

    /**
     * EFFECTS: calculates and returns the total price of this order
     */
    public double getTotalPrice() {
        double basePrice = flavor.getPrice();
        double toppingPrice = 0;
        for (Topping topping : toppings) {
            toppingPrice += topping.getPrice();
        }
        return (basePrice + toppingPrice) * size.getPriceMultiplier();
    }

    /**
     * EFFECTS: returns a string representation of this order
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(orderId).append(" - ").append(customerName).append("\n");
        sb.append("Flavor: ").append(flavor.getName()).append("\n");
        sb.append("Size: ").append(size.getName()).append("\n");
        if (!toppings.isEmpty()) {
            sb.append("Toppings: ");
            for (int i = 0; i < toppings.size(); i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(toppings.get(i).getName());
            }
            sb.append("\n");
        }
        sb.append("Pickup Time: ").append(pickupTime).append("\n");
        sb.append("Total Price: $").append(String.format("%.2f", getTotalPrice())).append("\n");
        sb.append("Status: ").append(isCompleted ? "Completed" : "Pending");
        return sb.toString();
    }
}