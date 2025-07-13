package model;

/**
 * Represents an ice cream flavor available in the shop
 */
public class Flavor {
    private String name;
    private double price;
    private boolean isAvailable;

    /**
     * REQUIRES: flavorName has non-zero length and price >= 0
     * EFFECTS: creates a flavor with given name and price
     */
    public Flavor(String flavorName, double price) {
        // stub
    }

    /**
     * EFFECTS: returns the name of this flavor
     */
    public String getName() {
        return "";
        // stub
    }

    /**
     * EFFECTS: returns the price of this flavor
     */
    public double getPrice() {
        return 0.0;
        // stub
    }

    /**
     * EFFECTS: returns true if this flavor is available, false otherwise
     */
    public boolean isAvailable() {
        return false;
        // stub
    }

    /**
     * MODIFIES: this
     * EFFECTS: sets the availability of this flavor
     */
    public void setAvailable(boolean available) {
        // stub
    }

    /**
     * EFFECTS: returns a string representation of this flavor
     */
    @Override
    public String toString() {
        return "";
        // stub
    }
} 