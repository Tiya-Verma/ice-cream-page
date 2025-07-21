package model;

import org.json.JSONObject;
import persistence.Writable;

/**
 * Represents an ice cream flavor available in the shop
 */
public class Flavor implements Writable {
    private String name;
    private double price;
    private boolean isAvailable;

    /**
     * REQUIRES: flavorName has non-zero length and price >= 0
     * EFFECTS: creates a flavor with given name and price
     */
    public Flavor(String flavorName, double price) {
        this.name = flavorName;
        this.price = price;
        this.isAvailable = true;
    }

    /**
     * EFFECTS: returns the name of this flavor
     */
    public String getName() {
        return name;
    }

    /**
     * EFFECTS: returns the price of this flavor
     */
    public double getPrice() {
        return price;
    }

    /**
     * EFFECTS: returns true if this flavor is available, false otherwise
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * MODIFIES: this
     * EFFECTS: sets the availability of this flavor
     */
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("price", price);
        json.put("isAvailable", isAvailable);
        return json;
    }

    /**
     * EFFECTS: returns a string representation of this flavor
     */
    @Override
    public String toString() {
        return name + " ($" + String.format("%.2f", price) + ")";
    }
}