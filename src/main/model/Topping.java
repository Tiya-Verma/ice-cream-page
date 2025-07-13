package model;

/**
 * Represents a topping available for ice cream orders
 */
public class Topping {
    private String name;
    private double price;
    private boolean isAvailable;

    /**
     * REQUIRES: toppingName has non-zero length and price >= 0
     * EFFECTS: creates a topping with given name and price, initially available
     */
    public Topping(String toppingName, double price) {
        this.name = toppingName;
        this.price = price;
        this.isAvailable = true;
    }

    /**
     * EFFECTS: returns the name of this topping
     */
    public String getName() {
        return name;
    }

    /**
     * EFFECTS: returns the price of this topping
     */
    public double getPrice() {
        return price;
    }

    /**
     * EFFECTS: returns true if this topping is available, false otherwise
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * MODIFIES: this
     * EFFECTS: sets the availability of this topping
     */
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    /**
     * EFFECTS: returns a string representation of this topping
     */
    @Override
    public String toString() {
        return name + " ($" + String.format("%.2f", price) + ")";
    }
} 