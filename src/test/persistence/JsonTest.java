package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkTopping(String name, double price, boolean isAvailable, Topping topping) {
        assertEquals(name, topping.getName());
        assertEquals(price, topping.getPrice());
        assertEquals(isAvailable, topping.isAvailable());
    }

    protected void checkOrder(String customerName, String flavorName, String sizeName, int toppingCount, boolean isCompleted, Order order) {
        assertEquals(customerName, order.getCustomerName());
        assertEquals(flavorName, order.getFlavor().getName());
        assertEquals(sizeName, order.getSize().getName());
        assertEquals(toppingCount, order.getToppings().size());
        assertEquals(isCompleted, order.isCompleted());
    }

    protected void checkOrderQueue(int pendingCount, int completedCount, model.OrderQueue queue) {
        assertEquals(pendingCount, queue.getPendingOrderCount());
        assertEquals(completedCount, queue.getCompletedOrderCount());
    }
}
