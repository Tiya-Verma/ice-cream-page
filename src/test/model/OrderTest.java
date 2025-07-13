package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Order class
 */
public class OrderTest {
    private Order order;
    private Flavor vanilla;
    private Size small;
    private Topping sprinkles;
    private Topping nuts;

    @BeforeEach
    void runBefore() {
        vanilla = new Flavor("Vanilla", 3.50);
        small = new Size("Small", 1.0);
        sprinkles = new Topping("Sprinkles", 0.50);
        nuts = new Topping("Nuts", 1.00);
        order = new Order("John Doe", vanilla, small, "2:30 PM", 5);
    }

    @Test
    void testConstructor() {
        assertEquals("John Doe", order.getCustomerName());
        assertEquals(vanilla, order.getFlavor());
        assertEquals(small, order.getSize());
        assertEquals("2:30 PM", order.getPickupTime());
        assertEquals(5, order.getEstimatedPrepTime());
        assertFalse(order.isCompleted());
        assertTrue(order.getToppings().isEmpty());
    }

    @Test
    void testGetCustomerName() {
        assertEquals("John Doe", order.getCustomerName());
    }

    @Test
    void testGetFlavor() {
        assertEquals(vanilla, order.getFlavor());
    }

    @Test
    void testGetSize() {
        assertEquals(small, order.getSize());
    }

    @Test
    void testGetPickupTime() {
        assertEquals("2:30 PM", order.getPickupTime());
    }

    @Test
    void testGetEstimatedPrepTime() {
        assertEquals(5, order.getEstimatedPrepTime());
    }

    @Test
    void testIsCompleted() {
        assertFalse(order.isCompleted());
    }

    @Test
    void testAddTopping() {
        order.addTopping(sprinkles);
        assertEquals(1, order.getToppings().size());
        assertTrue(order.getToppings().contains(sprinkles));
        
        order.addTopping(nuts);
        assertEquals(2, order.getToppings().size());
        assertTrue(order.getToppings().contains(nuts));
    }

    @Test
    void testMarkCompleted() {
        assertFalse(order.isCompleted());
        order.markCompleted();
        assertTrue(order.isCompleted());
    }

    @Test
    void testGetTotalPrice() {
        // Base price: $3.50, no toppings, small size (1.0x)
        assertEquals(3.50, order.getTotalPrice());
        
        // Add toppings
        order.addTopping(sprinkles);
        assertEquals(4.00, order.getTotalPrice()); // 3.50 + 0.50
        
        order.addTopping(nuts);
        assertEquals(5.00, order.getTotalPrice()); // 3.50 + 0.50 + 1.00
    }

    @Test
    void testGetTotalPriceWithLargeSize() {
        Size large = new Size("Large", 1.5);
        Order largeOrder = new Order("Jane", vanilla, large, "3:00 PM", 7);
        largeOrder.addTopping(sprinkles);
        
        // Base price: $3.50 + $0.50 = $4.00, large size (1.5x)
        assertEquals(6.00, largeOrder.getTotalPrice());
    }

    @Test
    void testToString() {
        String result = order.toString();
        assertTrue(result.contains("Order #"));
        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("Vanilla"));
        assertTrue(result.contains("Small"));
        assertTrue(result.contains("2:30 PM"));
        assertTrue(result.contains("$3.50"));
        assertTrue(result.contains("Pending"));
    }

    @Test
    void testToStringWithToppings() {
        order.addTopping(sprinkles);
        order.addTopping(nuts);
        String result = order.toString();
        assertTrue(result.contains("Toppings:"));
        assertTrue(result.contains("Sprinkles"));
        assertTrue(result.contains("Nuts"));
    }

    @Test
    void testToStringStatusPending() {
        String result = order.toString();
        assertTrue(result.contains("Status: Pending"));
        assertFalse(result.contains("Status: Completed"));
    }

    @Test
    void testToStringStatusCompleted() {
        order.markCompleted();
        String result = order.toString();
        assertTrue(result.contains("Status: Completed"));
        assertFalse(result.contains("Status: Pending"));
    }
} 