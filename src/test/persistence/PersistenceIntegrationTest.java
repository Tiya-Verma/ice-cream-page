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
public class PersistenceIntegrationTest {
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
    void testSaveAndLoadIntegration() {
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
            writer.write(orderQueue);

            // load order queue from the file
            OrderQueue loadedOrderQueue = reader.read();

            // verify the data integrity
            assertEquals(1, loadedOrderQueue.getPendingOrderCount());
            assertEquals(1, loadedOrderQueue.getCompletedOrderCount());

            // check the pending order details
            List<Order> pendingOrders = loadedOrderQueue.getPendingOrders();
            Order loadedOrder1 = pendingOrders.get(0);
            assertEquals("Tiya", loadedOrder1.getCustomerName());
            assertEquals("chocolate", loadedOrder1.getFlavor().getName());
            assertEquals("Medium", loadedOrder1.getSize().getName());
            assertEquals(1, loadedOrder1.getToppings().size());
            assertEquals("Sprinkles", loadedOrder1.getToppings().get(0).getName());
            assertFalse(loadedOrder1.isCompleted());

            // check the completed order details
            List<Order> completedOrders = loadedOrderQueue.getCompletedOrders();
            Order loadedOrder2 = completedOrders.get(0);
            assertEquals("Mochi", loadedOrder2.getCustomerName());
            assertTrue(loadedOrder2.isCompleted());
        }
        
        catch (IOException e) {
            fail("IOException should not have been thrown:");
        }
    }

    @Test
    void testEmptySaveAndLoad() {
        try {
            // save an empty order queue
            writer.write(orderQueue);

            // load the empty order queue from file
            OrderQueue loadedOrderQueue = reader.read();

            // verify that loaded order queue is empty
            assertEquals(0, loadedOrderQueue.getPendingOrderCount());
            assertEquals(0, loadedOrderQueue.getCompletedOrderCount());
            assertTrue(loadedOrderQueue.isEmpty());
        }

        catch (IOException e) {
            fail("IOException should not have been thrown:");
        }
    }
}

