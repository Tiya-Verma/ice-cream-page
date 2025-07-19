package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for JsonReader
 */
public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            OrderQueue oq = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyIceCreamShop() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyIceCreamShop.json");
        try {
            OrderQueue oq = reader.read();
            checkOrderQueue(0, 0, oq);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralIceCreamShop() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralIceCreamShop.json");
        try {
            OrderQueue oq = reader.read();
            checkOrderQueue(2, 1, oq);
            List<Order> pending = oq.getPendingOrders();
            List<Order> completed = oq.getCompletedOrders();
            checkOrder("Alice", "Vanilla", "Small", 1, false, pending.get(0));
            checkOrder("Bob", "Chocolate", "Large", 2, false, pending.get(1));
            checkOrder("Carol", "Strawberry", "Medium", 0, true, completed.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
