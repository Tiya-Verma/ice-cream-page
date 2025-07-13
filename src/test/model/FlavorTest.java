package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Flavor class
 */
public class FlavorTest {
    private Flavor vanilla;
    private Flavor chocolate;

    @BeforeEach
    void runBefore() {
        vanilla = new Flavor("Vanilla", 3.50);
        chocolate = new Flavor("Chocolate", 4.00);
    }

    @Test
    void testConstructor() {
        assertEquals("Vanilla", vanilla.getName());
        assertEquals(3.50, vanilla.getPrice());
        assertTrue(vanilla.isAvailable());
    }

    @Test
    void testGetName() {
        assertEquals("Vanilla", vanilla.getName());
        assertEquals("Chocolate", chocolate.getName());
    }

    @Test
    void testGetPrice() {
        assertEquals(3.50, vanilla.getPrice());
        assertEquals(4.00, chocolate.getPrice());
    }

    @Test
    void testIsAvailable() {
        assertTrue(vanilla.isAvailable());
        assertTrue(chocolate.isAvailable());
    }

    @Test
    void testSetAvailable() {
        vanilla.setAvailable(false);
        assertFalse(vanilla.isAvailable());
        
        vanilla.setAvailable(true);
        assertTrue(vanilla.isAvailable());
    }

    @Test
    void testToString() {
        String expected = "Vanilla ($3.50)";
        assertEquals(expected, vanilla.toString());
        
        String expectedChoc = "Chocolate ($4.00)";
        assertEquals(expectedChoc, chocolate.toString());
    }
} 