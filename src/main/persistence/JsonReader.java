package persistence;

import model.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

/**
 * Represents a reader that can read ice cream shop data from JSON file
 */
public class JsonReader {
    // stub

     /**
     * REQUIRES: source is a valid file path
     * EFFECTS: constructs reader to read from source file
     */
    public JsonReader(String source) {
        // stub
    }

    /**
     * REQUIRES: source is a valid file path
     * EFFECTS: reads ice cream shop data from source file and returns it as a JSONObject
     *throws IOException if an error occurs reading data from file    
     */
    public OrderQueue read() throws IOException {
        // stub
        return null;
    }

    /**
     * EFFECTS: reads source file as string and returns it
     * throws IOException if an error occurs reading data from file
     */
    private String readFile(String source) throws IOException {
        // stub
        return "";
    }

    /**
     * REQUIRES: jsonObject is not null
     * EFFECTS: parses OrderQueue from JSON object and return it
    */
    private OrderQueue parseOrderQueue(JSONObject jsonObject) {
        // stub
        return null;

    }
   
        /**
         * MODIFIES: orderQueue
         * EFFECTS: parses pending orders from JSON object and adds them to orderQueue
         */
        private void addPendingOrders(OrderQueue orderQueue, JSONObject jsonObject) {
            // stub
    }

    /**
     * MODIFIES: orderQueue
     * EFFECTS: parses completed orders from JSON object and adds them to orderQueue
     */
    private void addCompletedOrders(OrderQueue orderQueue, JSONObject jsonObject) {
        // stub
    }

    /**
     * REQUIRES; jsonObject is not null
     * EFFECTS: parses Order from JSON object and returns it
     */
    private Order parseOrder(JSONObject jsonObject) {
        // stub
        return null;
    }

    /**
     * MODIFIES: order
     * EFFECTS: parses toppings from JSON object and adds them to order
     */
    private void addToppings(Order order, JSONObject jsonObject) {
        // stub
    }

    /**
     * REQUIRES: jsonObject is not null
     * EFFECTS: parses Flavour from JSON object and returns it
     */
    private Flavor parseFlavor(JSONObject jsonObject) {
        // stub
        return null;
    }

    /**
     * EFFECTS: parses Size from JSON object and returns it
     */
    private Size parseSize(JSONObject jsonObject) {
        // stub
        return null;
    }
    /**
     * REQUIRES: jsonObject is not null
     * EFFECTS: parses Topping from JSON object and returns it
     */
    private Topping parseTopping(JSONObject jsonObject) {
        // stub
        return null;
    }
}