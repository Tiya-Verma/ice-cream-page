package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for JsonWriter
 */
public class JsonWriterTest extends JsonTest {
    private OrderQueue oq;

    @BeforeEach
    void runBefore() {
        oq = new OrderQueue();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyIceCreamShop() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyIceCreamShop.json");
            writer.open();
            writer.write(oq);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyIceCreamShop.json");
            oq = reader.read();
            checkOrderQueue(0, 0, oq);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralIceCreamShop() {
        try {
            // Add pending orders
            Order o1 = new Order("Alice", new Flavor("Vanilla", 3.5), new Size("Small", 1.0), "10:00", 5);
            o1.addTopping(new Topping("Sprinkles", 0.5));
            Order o2 = new Order("Bob", new Flavor("Chocolate", 4.0), new Size("Large", 1.5), "11:00", 7);
            o2.addTopping(new Topping("Nuts", 1.0));
            o2.addTopping(new Topping("Cherry", 0.5));
            oq.addOrder(o1);
            oq.addOrder(o2);
            
            // Add completed order
            Order o3 = new Order("Carol", new Flavor("Strawberry", 3.75), new Size("Medium", 1.25), "12:00", 6);
            o3.markCompleted();
            oq.completeOrder(o3.getOrderId()); // simulate as completed
            oq.getCompletedOrders().add(o3); // force add for test

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralIceCreamShop.json");
            writer.open();
            writer.write(oq);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralIceCreamShop.json");
            oq = reader.read();
            checkOrderQueue(2, 1, oq);
            List<Order> pending = oq.getPendingOrders();
            List<Order> completed = oq.getCompletedOrders();
            checkOrder("Alice", "Vanilla", "Small", 1, false, pending.get(0));
            checkOrder("Bob", "Chocolate", "Large", 2, false, pending.get(1));
            checkOrder("Carol", "Strawberry", "Medium", 0, true, completed.get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
