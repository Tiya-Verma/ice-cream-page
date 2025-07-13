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
        this.name = sizeName;
        this.priceMultiplier = multiplier;
    }

    /**
     * EFFECTS: returns the name of this size
     */
    public String getName() {
        return name;
    }

    /**
     * EFFECTS: returns the price multiplier for this size
     */
    public double getPriceMultiplier() {
        return priceMultiplier;
    }

    /**
     * EFFECTS: returns a string representation of this size
     */
    @Override
    public String toString() {
        return name + " (x" + String.format("%.1f", priceMultiplier) + ")";
    }
} 