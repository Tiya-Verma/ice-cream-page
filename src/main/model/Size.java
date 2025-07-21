package model;

import org.json.JSONObject;
import persistence.Writable;

/**
 * Represents a size option for ice cream orders
 */
public class Size implements Writable {
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("priceMultiplier", priceMultiplier);
        return json;
    }

    /**
     * EFFECTS: returns a string representation of this size
     */
    @Override
    public String toString() {
        return name + " (x" + String.format("%.1f", priceMultiplier) + ")";
    }
}