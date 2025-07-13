package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the OrderQueue class
 */
public class OrderQueueTest {
    private OrderQueue queue;
    private Order order1;
    private Order order2;
    private Flavor vanilla;
    private Size small;

    @BeforeEach
    void runBefore() {
        queue = new OrderQueue();
        vanilla = new Flavor("Vanilla", 3.50);
        small = new Size("Small", 1.0);
        order1 = new Order("John", vanilla, small, "2:30 PM", 5);
        order2 = new Order("Jane", vanilla, small, "3:00 PM", 7);
    }

    @Test
    void testConstructor() {
        assertEquals(0, queue.getPendingOrderCount());
        assertEquals(0, queue.getCompletedOrderCount());
        assertTrue(queue.isEmpty());
    }

    @Test
    void testAddOrder() {
        queue.addOrder(order1);
        assertEquals(1, queue.getPendingOrderCount());
        assertFalse(queue.isEmpty());
        
        queue.addOrder(order2);
        assertEquals(2, queue.getPendingOrderCount());
    }

    @Test
    void testGetPendingOrders() {
        queue.addOrder(order1);
        queue.addOrder(order2);
        
        assertEquals(2, queue.getPendingOrders().size());
        assertTrue(queue.getPendingOrders().contains(order1));
        assertTrue(queue.getPendingOrders().contains(order2));
    }

    @Test
    void testCompleteOrder() {
        queue.addOrder(order1);
        queue.addOrder(order2);
        
        queue.completeOrder(order1.getOrderId());
        assertEquals(1, queue.getPendingOrderCount());
        assertEquals(1, queue.getCompletedOrderCount());
        assertTrue(order1.isCompleted());
        assertFalse(order2.isCompleted());
    }

    @Test
    void testCancelOrder() {
        queue.addOrder(order1);
        queue.addOrder(order2);
        
        queue.cancelOrder(order1.getOrderId());
        assertEquals(1, queue.getPendingOrderCount());
        assertEquals(0, queue.getCompletedOrderCount());
        assertFalse(order1.isCompleted());
    }

    @Test
    void testFindOrderById() {
        queue.addOrder(order1);
        queue.addOrder(order2);
        
        assertEquals(order1, queue.findOrderById(order1.getOrderId()));
        assertEquals(order2, queue.findOrderById(order2.getOrderId()));
        assertNull(queue.findOrderById(999));
    }

    @Test
    void testGetNextOrderPrepTime() {
        assertEquals(0, queue.getNextOrderPrepTime());
        
        queue.addOrder(order1);
        assertEquals(5, queue.getNextOrderPrepTime());
        
        queue.addOrder(order2);
        assertEquals(5, queue.getNextOrderPrepTime()); // Still first order
    }

    @Test
    void testGetCompletedOrders() {
        queue.addOrder(order1);
        queue.addOrder(order2);
        
        queue.completeOrder(order1.getOrderId());
        assertEquals(1, queue.getCompletedOrders().size());
        assertTrue(queue.getCompletedOrders().contains(order1));
        assertFalse(queue.getCompletedOrders().contains(order2));
    }

    @Test
    void testCompleteNonExistentOrder() {
        queue.addOrder(order1);
        queue.completeOrder(999);
        assertEquals(1, queue.getPendingOrderCount());
        assertEquals(0, queue.getCompletedOrderCount());
    }

    @Test
    void testCancelNonExistentOrder() {
        queue.addOrder(order1);
        queue.cancelOrder(999);
        assertEquals(1, queue.getPendingOrderCount());
    }

    @Test
    void testCancelAlreadyCompletedOrder() {
        queue.addOrder(order1);
        queue.completeOrder(order1.getOrderId());
        queue.cancelOrder(order1.getOrderId());
        assertEquals(0, queue.getPendingOrderCount());
        assertEquals(1, queue.getCompletedOrderCount());
    }

    @Test
    void testCompleteAlreadyCompletedOrder() {
        queue.addOrder(order1);
        queue.completeOrder(order1.getOrderId());
        assertEquals(0, queue.getPendingOrderCount());
        assertEquals(1, queue.getCompletedOrderCount());
        
        // Try to complete the same order again
        queue.completeOrder(order1.getOrderId());
        assertEquals(0, queue.getPendingOrderCount());
        assertEquals(1, queue.getCompletedOrderCount());
    }

    @Test
    void testCompleteOrderWithNullOrder() {
        // Test completeOrder when order is not found (findOrderById returns null)
        queue.completeOrder(999);
        assertEquals(0, queue.getPendingOrderCount());
        assertEquals(0, queue.getCompletedOrderCount());
    }

    @Test
    void testCancelOrderWithNullOrder() {
        // Test cancelOrder when order is not found (findOrderById returns null)
        queue.cancelOrder(999);
        assertEquals(0, queue.getPendingOrderCount());
        assertEquals(0, queue.getCompletedOrderCount());
    }

    @Test
    void testCompleteOrderWithCompletedOrder() {
        // Test completeOrder when order is already completed
        queue.addOrder(order1);
        queue.completeOrder(order1.getOrderId());
        assertEquals(0, queue.getPendingOrderCount());
        assertEquals(1, queue.getCompletedOrderCount());
        
        // Try to complete the same order again (should not change anything)
        queue.completeOrder(order1.getOrderId());
        assertEquals(0, queue.getPendingOrderCount());
        assertEquals(1, queue.getCompletedOrderCount());
    }

    @Test
    void testCancelOrderWithCompletedOrder() {
        // Test cancelOrder when order is already completed
        queue.addOrder(order1);
        queue.completeOrder(order1.getOrderId());
        assertEquals(0, queue.getPendingOrderCount());
        assertEquals(1, queue.getCompletedOrderCount());
        
        // Try to cancel the completed order (should not change anything)
        queue.cancelOrder(order1.getOrderId());
        assertEquals(0, queue.getPendingOrderCount());
        assertEquals(1, queue.getCompletedOrderCount());
    }

    @Test
    void testToString() {
        String result = queue.toString();
        assertTrue(result.contains("Order Queue Status:"));
        assertTrue(result.contains("Pending Orders: 0"));
        assertTrue(result.contains("Completed Orders: 0"));
        
        queue.addOrder(order1);
        result = queue.toString();
        assertTrue(result.contains("Pending Orders: 1"));
        assertTrue(result.contains("Order #"));
        assertTrue(result.contains("John"));
    }
} 