package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for persistence functionality
 */
public class PersistenceIntegrationTest extends JsonTest {
    private OrderQueue orderQueue;
    private JsonWriter writer;
    private JsonReader reader;
    private Flavor chocolate;
    private Size medium;
    private Topping sprinkles;

    @BeforeEach
    void runBefore() {
        orderQueue = new OrderQueue();
        writer = new JsonWriter("./data/testIceCreamShop.json");
        reader = new JsonReader("./data/testIceCreamShop.json");

        chocolate = new Flavor("Chocolate", 3.50);
        medium = new Size("Medium", 1.0);
        sprinkles = new Topping("Sprinkles", 0.5);
    }

    @Test
    void testSaveIntegration() {
        try {
            // create test orders
            Order order1 = new Order("Tiya", chocolate, medium, "2:30PM", 10);
            order1.addTopping(sprinkles);

            Order order2 = new Order("Mochi", chocolate, medium, "3:00PM", 7);

            // add orders to the queue
            orderQueue.addOrder(order1);
            orderQueue.addOrder(order2);

            // complete one order
            orderQueue.completeOrder(order2.getOrderId());

            // save the order queue to a file
            writer.open();
            writer.write(orderQueue);
            writer.close();
        } catch (IOException e) {
            fail("IOException should not have been thrown:");
        }
    }

    @Test
    void testLoadIntegration() {
        try {
            // create test orders
            Order order1 = new Order("Tiya", chocolate, medium, "2:30PM", 10);
            order1.addTopping(sprinkles);

            Order order2 = new Order("Mochi", chocolate, medium, "3:00PM", 7);

            // load order queue from the file
            OrderQueue loadedOrderQueue = reader.read();

            // verify the data integrity
            assertEquals(1, loadedOrderQueue.getPendingOrderCount());
            assertEquals(1, loadedOrderQueue.getCompletedOrderCount());

            // check the pending order details
            List<Order> pendingOrders = loadedOrderQueue.getPendingOrders();
            Order loadedOrder1 = pendingOrders.get(0);
            assertEquals("Tiya", loadedOrder1.getCustomerName());
            assertEquals("Chocolate", loadedOrder1.getFlavor().getName());
            assertEquals("Medium", loadedOrder1.getSize().getName());
            assertEquals(1, loadedOrder1.getToppings().size());
            checkTopping("Sprinkles", 0.5, true, loadedOrder1.getToppings().get(0));
            assertFalse(loadedOrder1.isCompleted());

            // check the completed order details
            List<Order> completedOrders = loadedOrderQueue.getCompletedOrders();
            Order loadedOrder2 = completedOrders.get(0);
            assertEquals("Mochi", loadedOrder2.getCustomerName());
            assertTrue(loadedOrder2.isCompleted());
        } catch (IOException e) {
            fail("IOException should not have been thrown:");
        }
    }

    @Test
    void testEmptySaveAndLoad() {
        try {
            // save an empty order queue
            writer.open();
            writer.write(orderQueue);
            writer.close();

            // load the empty order queue from file
            OrderQueue loadedOrderQueue = reader.read();

            // verify that loaded order queue is empty
            assertEquals(0, loadedOrderQueue.getPendingOrderCount());
            assertEquals(0, loadedOrderQueue.getCompletedOrderCount());
            assertTrue(loadedOrderQueue.isEmpty());
        } catch (IOException e) {
            fail("IOException should not have been thrown:");
        }
    }

    @Test
    void testNextOrderIdPersistence() {
        try {
            // Get the initial nextOrderId before creating any orders
            int initialNextOrderId = Order.getNextOrderId();

            // Create some orders to increment the nextOrderId
            Order order1 = new Order("Alice", chocolate, medium, "1:00PM", 5);
            Order order2 = new Order("Bob", chocolate, medium, "2:00PM", 7);
            Order order3 = new Order("Carol", chocolate, medium, "3:00PM", 8);

            orderQueue.addOrder(order1);
            orderQueue.addOrder(order2);
            orderQueue.addOrder(order3);

            // Get the current nextOrderId before saving (should be initial + 3)
            int expectedNextOrderId = Order.getNextOrderId();
            assertEquals(initialNextOrderId + 3, expectedNextOrderId);

            // Save and reload the order queue
            writer.open();
            writer.write(orderQueue);
            writer.close();

            OrderQueue loadedOrderQueue = reader.read();

            // Verify that nextOrderId was properly restored
            assertEquals(expectedNextOrderId, Order.getNextOrderId());

            // Create a new order after loading to verify ID sequence continues correctly
            Order newOrder = new Order("Dave", chocolate, medium, "4:00PM", 6);
            assertEquals(expectedNextOrderId, newOrder.getOrderId());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testMissingNextOrderIdInJson() {
        try {
            // Manually create a JSON file without nextOrderId field
            String jsonWithoutNextOrderId = "{\n" 
                                                +
                    "    \"pendingOrders\": [],\n" 
                                                +
                    "    \"completedOrders\": []\n" 
                                                +
                    "}";

            // Write the JSON without nextOrderId to a test file
            java.nio.file.Files.write(
                    java.nio.file.Paths.get("./data/testMissingNextOrderId.json"),
                    jsonWithoutNextOrderId.getBytes());

            // Get current nextOrderId before reading
            int currentNextOrderId = Order.getNextOrderId();

            // Read the JSON file without nextOrderId field
            JsonReader readerWithoutNextOrderId = new JsonReader("./data/testMissingNextOrderId.json");
            OrderQueue loadedQueue = readerWithoutNextOrderId.read();

            // Verify that nextOrderId was not changed (since it wasn't in the JSON)
            assertEquals(currentNextOrderId, Order.getNextOrderId());

            // Verify the queue was loaded correctly (empty in this case)
            assertEquals(0, loadedQueue.getPendingOrderCount());
            assertEquals(0, loadedQueue.getCompletedOrderCount());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}