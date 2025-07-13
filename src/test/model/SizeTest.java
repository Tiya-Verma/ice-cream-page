package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Size class
 */
public class SizeTest {
    private Size small;
    private Size large;

    @BeforeEach
    void runBefore() {
        small = new Size("Small", 1.0);
        large = new Size("Large", 1.5);
    }

    @Test
    void testConstructor() {
        assertEquals("Small", small.getName());
        assertEquals(1.0, small.getPriceMultiplier());
    }

    @Test
    void testGetName() {
        assertEquals("Small", small.getName());
        assertEquals("Large", large.getName());
    }

    @Test
    void testGetPriceMultiplier() {
        assertEquals(1.0, small.getPriceMultiplier());
        assertEquals(1.5, large.getPriceMultiplier());
    }

    @Test
    void testToString() {
        String expectedSmall = "Small (x1.0)";
        assertEquals(expectedSmall, small.toString());
        
        String expectedLarge = "Large (x1.5)";
        assertEquals(expectedLarge, large.toString());
    }
} 