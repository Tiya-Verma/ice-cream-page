package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Represents a writer that can write ice cream shop data to JSON file
 */
public class JsonWriter {
   // stub

    /**
     * REQUIRES: destination is a valid file path
     * EFFECTS: constructs writer to write to destination file
     */
    public JsonWriter(String destination) {
       // stub
    }

    /**
     * REQUIRES: orderQueue is not null
     * MODIFIES: this
     * EFFECTS: writes the JSON representation of orderQueue to file
     * throws IOException if an error occurs writing data to file
     */
    public void write(OrderQueue orderQueue) throws IOException {
        // stub
    }

    /**
     * EFFECTS: writes string to file
     * throws IOException if an error occurs writing data to file
     */
    private void saveToFile(String json) throws IOException {
       // stub
    }

    /**
     * REQUIRES: orderQueue is not null
     * EFFECTS: returns JSON object representing orderQueue
     */
    private JSONObject orderQueueToJson(OrderQueue orderQueue) {
        // stub
        return null;
    }

    /**
     * REQUIRES: orders is not null
     * EFFECTS: returns JSON array representing list of orders
     */
    private JSONArray ordersToJson(List<Order> orders) {
        // stub
        return null;
    }

    /**
     * REQUIRES: order is not null
     * EFFECTS: returns JSON object representing order
     */
    private JSONObject orderToJson(Order order) {
       // stub
       return null;
    }

    /**
     * REQUIRES: flavor is not null
     * EFFECTS: returns JSON object representing flavor
     */
    private JSONObject flavorToJson(Flavor flavor) {
       // stub
       return null;
    }

    /**
     * REQUIRES: size is not null
     * EFFECTS: returns JSON object representing size
     */
    private JSONObject sizeToJson(Size size) {
        // stub
        return null;
    }

    /**
     * REQUIRES: toppings is not null
     * EFFECTS: returns JSON array representing list of toppings
     */
    private JSONArray toppingsToJson(List<Topping> toppings) {
       // stub
       return null;
    }

    /**
     * REQUIRES: topping is not null
     * EFFECTS: returns JSON object representing topping
     */
    private JSONObject toppingToJson(Topping topping) {
        // stub
        return null;
    }
} 
