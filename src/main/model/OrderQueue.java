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
        this.pendingOrders = new ArrayList<>();
        this.completedOrders = new ArrayList<>();
    }

    /**
     * REQUIRES: order is not null
     * MODIFIES: this
     * EFFECTS: adds the given order to the pending queue
     */
    public void addOrder(Order order) {
        pendingOrders.add(order);
    }

    /**
     * REQUIRES: orderId corresponds to an existing pending order
     * MODIFIES: this
     * EFFECTS: marks the order with given ID as completed and moves it to completed orders
     */
    public void completeOrder(int orderId) {
        Order orderToComplete = findOrderById(orderId);
        if (orderToComplete != null) {
            orderToComplete.markCompleted();
            pendingOrders.remove(orderToComplete);
            completedOrders.add(orderToComplete);
        }
    }

    /**
     * REQUIRES: orderId corresponds to an existing pending order
     * MODIFIES: this
     * EFFECTS: removes the order with given ID from the pending queue
     */
    public void cancelOrder(int orderId) {
        Order orderToCancel = findOrderById(orderId);
        if (orderToCancel != null) {
            pendingOrders.remove(orderToCancel);
        }
    }

    /**
     * EFFECTS: returns a list of all pending orders
     */
    public List<Order> getPendingOrders() {
        return new ArrayList<>(pendingOrders);
    }

    /**
     * EFFECTS: returns a list of all completed orders
     */
    public List<Order> getCompletedOrders() {
        return new ArrayList<>(completedOrders);
    }

    /**
     * EFFECTS: returns the number of pending orders
     */
    public int getPendingOrderCount() {
        return pendingOrders.size();
    }

    /**
     * EFFECTS: returns the number of completed orders
     */
    public int getCompletedOrderCount() {
        return completedOrders.size();
    }

    /**
     * EFFECTS: returns true if there are no pending orders, false otherwise
     */
    public boolean isEmpty() {
        return pendingOrders.isEmpty();
    }

    /**
     * REQUIRES: orderId is a positive integer
     * EFFECTS: returns the order with the given ID, or null if not found
     */
    public Order findOrderById(int orderId) {
        for (Order order : pendingOrders) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    }

    /**
     * EFFECTS: returns the estimated preparation time for the next order in queue
     */
    public int getNextOrderPrepTime() {
        if (!pendingOrders.isEmpty()) {
            return pendingOrders.get(0).getEstimatedPrepTime();
        }
        return 0;
    }

    /**
     * EFFECTS: returns a string representation of the order queue
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order Queue Status:\n");
        sb.append("Pending Orders: ").append(getPendingOrderCount()).append("\n");
        sb.append("Completed Orders: ").append(getCompletedOrderCount()).append("\n");
        
        if (!pendingOrders.isEmpty()) {
            sb.append("\nPending Orders:\n");
            for (Order order : pendingOrders) {
                sb.append(order.toString()).append("\n---\n");
            }
        }
        
        return sb.toString();
    }
} 