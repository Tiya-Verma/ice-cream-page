package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Topping class
 */
public class ToppingTest {
    private Topping sprinkles;
    private Topping nuts;

    @BeforeEach
    void runBefore() {
        sprinkles = new Topping("Sprinkles", 0.50);
        nuts = new Topping("Nuts", 1.00);
    }

    @Test
    void testConstructor() {
        assertEquals("Sprinkles", sprinkles.getName());
        assertEquals(0.50, sprinkles.getPrice());
        assertTrue(sprinkles.isAvailable());
    }

    @Test
    void testGetName() {
        assertEquals("Sprinkles", sprinkles.getName());
        assertEquals("Nuts", nuts.getName());
    }

    @Test
    void testGetPrice() {
        assertEquals(0.50, sprinkles.getPrice());
        assertEquals(1.00, nuts.getPrice());
    }

    @Test
    void testIsAvailable() {
        assertTrue(sprinkles.isAvailable());
        assertTrue(nuts.isAvailable());
    }

    @Test
    void testSetAvailable() {
        sprinkles.setAvailable(false);
        assertFalse(sprinkles.isAvailable());
        
        sprinkles.setAvailable(true);
        assertTrue(sprinkles.isAvailable());
    }

    @Test
    void testToString() {
        String expected = "Sprinkles ($0.50)";
        assertEquals(expected, sprinkles.toString());
        
        String expectedNuts = "Nuts ($1.00)";
        assertEquals(expectedNuts, nuts.toString());
    }
} 