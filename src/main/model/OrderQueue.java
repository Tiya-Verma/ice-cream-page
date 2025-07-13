package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a queue of ice cream orders that manages pending and completed orders
 */
public class OrderQueue {
    private List<Order> pendingOrders;
    private List<Order> completedOrders;

    /**
     * EFFECTS: creates an empty order queue
     */
    public OrderQueue() {
        // stub
    }

    /**
     * REQUIRES: order is not null
     * MODIFIES: this
     * EFFECTS: adds the given order to the pending queue
     */
    public void addOrder(Order order) {
        // stub
    }

    /**
     * REQUIRES: orderId corresponds to an existing pending order
     * MODIFIES: this
     * EFFECTS: marks the order with given ID as completed and moves it to completed orders
     */
    public void completeOrder(int orderId) {
        // stub
    }

    /**
     * REQUIRES: orderId corresponds to an existing pending order
     * MODIFIES: this
     * EFFECTS: removes the order with given ID from the pending queue
     */
    public void cancelOrder(int orderId) {
        // stub
    }

    /**
     * EFFECTS: returns a list of all pending orders
     */
    public List<Order> getPendingOrders() {
        return null;
        // stub
    }

    /**
     * EFFECTS: returns a list of all completed orders
     */
    public List<Order> getCompletedOrders() {
        return null;
        // stub
    }

    /**
     * EFFECTS: returns the number of pending orders
     */
    public int getPendingOrderCount() {
        return 0;
        // stub
    }

    /**
     * EFFECTS: returns the number of completed orders
     */
    public int getCompletedOrderCount() {
        return 0;
        // stub
    }

    /**
     * EFFECTS: returns true if there are no pending orders, false otherwise
     */
    public boolean isEmpty() {
        return false;
        // stub
    }

    /**
     * REQUIRES: orderId is a positive integer
     * EFFECTS: returns the order with the given ID, or null if not found
     */
    public Order findOrderById(int orderId) {
        // stub
        return null;
    }

    /**
     * EFFECTS: returns the estimated preparation time for the next order in queue
     */
    public int getNextOrderPrepTime() {
        // stub
        return 0;
    }

    /**
     * EFFECTS: returns a string representation of the order queue
     */
    @Override
    public String toString() {
        // stub
        return "";
    }
} 