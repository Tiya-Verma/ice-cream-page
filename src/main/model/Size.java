package model;

/**
 * Represents a size option for ice cream orders
 */
public class Size {
    private String name;
    private double priceMultiplier;

    /**
     * REQUIRES: sizeName has non-zero length and multiplier > 0
     * EFFECTS: creates a size with given name and price multiplier
     */
    public Size(String sizeName, double multiplier) {
        // stub
    }

    /**
     * EFFECTS: returns the name of this size
     */
    public String getName() {
        return "";
        // stub
    }

    /**
     * EFFECTS: returns the price multiplier for this size
     */
    public double getPriceMultiplier() {
        return 0.0;
        // stub
    }

    /**
     * EFFECTS: returns a string representation of this size
     */
    @Override
    public String toString() {
        // stub
        return "";
    }
} 