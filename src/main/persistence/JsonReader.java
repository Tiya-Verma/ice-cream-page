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
 * 
 * Referenced from the JsonSerialization Demo
 * https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */
public class JsonReader {
    private String source;

    /**
     * REQUIRES: source is a valid file path
     * EFFECTS: constructs reader to read from source file
     */
    public JsonReader(String source) {
        this.source = source;
    }

    /**
     * EFFECTS: reads order queue from file and returns it;
     * throws IOException if an error occurs reading data from file
     */
    public OrderQueue read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseOrderQueue(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses order queue from JSON object and returns it
    private OrderQueue parseOrderQueue(JSONObject jsonObject) {
        OrderQueue oq = new OrderQueue();

        addPendingOrders(oq, jsonObject);
        addCompletedOrders(oq, jsonObject);
        
        // Set the next order ID after loading all orders to maintain consistency
        if (jsonObject.has("nextOrderId")) {
            Order.setNextOrderId(jsonObject.getInt("nextOrderId"));
        }
        
        return oq;
    }

    // MODIFIES: oq
    // EFFECTS: parses pending orders from JSON object and adds them to order queue
    private void addPendingOrders(OrderQueue oq, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("pendingOrders");
        for (Object json : jsonArray) {
            JSONObject nextOrder = (JSONObject) json;
            Order order = parseOrder(nextOrder);
            oq.addOrder(order);
        }
    }

    // MODIFIES: oq
    // EFFECTS: parses completed orders from JSON object and adds them to order
    // queue
    private void addCompletedOrders(OrderQueue oq, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("completedOrders");
        for (Object json : jsonArray) {
            JSONObject nextOrder = (JSONObject) json;
            Order order = parseOrder(nextOrder);
            // Add directly to completed orders list
            oq.addCompletedOrder(order);
        }
    }

    // EFFECTS: parses order from JSON object and returns it
    private Order parseOrder(JSONObject jsonObject) {
        String customerName = jsonObject.getString("customerName");
        Flavor flavor = parseFlavor(jsonObject.getJSONObject("flavor"));
        Size size = parseSize(jsonObject.getJSONObject("size"));
        String pickupTime = jsonObject.getString("pickupTime");
        int estimatedPrepTime = jsonObject.getInt("estimatedPrepTime");

        Order order = new Order(customerName, flavor, size, pickupTime, estimatedPrepTime);
        
        // Override the auto-generated order ID with the saved one
        order.setOrderId(jsonObject.getInt("orderId"));

        addToppings(order, jsonObject);

        if (jsonObject.getBoolean("isCompleted")) {
            order.markCompleted();
        }

        return order;
    }

    // EFFECTS: parses flavor from JSON object and returns it
    private Flavor parseFlavor(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double price = jsonObject.getDouble("price");
        boolean isAvailable = jsonObject.getBoolean("isAvailable");

        Flavor flavor = new Flavor(name, price);
        flavor.setAvailable(isAvailable);
        return flavor;
    }

    // EFFECTS: parses size from JSON object and returns it
    private Size parseSize(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double priceMultiplier = jsonObject.getDouble("priceMultiplier");

        return new Size(name, priceMultiplier);
    }

    // MODIFIES: order
    // EFFECTS: parses toppings from JSON object and adds them to order
    private void addToppings(Order order, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("toppings");
        for (Object json : jsonArray) {
            JSONObject nextTopping = (JSONObject) json;
            Topping topping = parseTopping(nextTopping);
            order.addTopping(topping);
        }
    }

    // EFFECTS: parses topping from JSON object and returns it
    private Topping parseTopping(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double price = jsonObject.getDouble("price");
        boolean isAvailable = jsonObject.getBoolean("isAvailable");

        Topping topping = new Topping(name, price);
        topping.setAvailable(isAvailable);
        return topping;
    }
}