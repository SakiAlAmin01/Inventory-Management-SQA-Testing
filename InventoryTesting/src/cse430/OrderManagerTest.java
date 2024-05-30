package cse430;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderManagerTest {

    private OrderManager orderManager;
    private Order order1;
    private Order order2;

    @BeforeEach
    public void setUp() {
        orderManager = new OrderManager();

        // Initialize some Product objects
        Product product1 = new Product(1, "Product 1", 10.0, 100, "Type1", LocalDate.of(2023, 12, 31));
        Product product2 = new Product(2, "Product 2", 20.0, 200, "Type2", LocalDate.of(2024, 12, 31));

        // Initialize some Order objects
        order1 = new Order(1, 123, "2023-05-19", "Credit Card");
        order1.addProduct(product1, 2);
        order1.addProduct(product2, 3);

        order2 = new Order(2, 456, "2023-06-19", "PayPal");
        order2.addProduct(product1, 1);
        order2.addProduct(product2, 1);

        // Add orders to OrderManager
        orderManager.addOrder(order1);
        orderManager.addOrder(order2);
    }

    @Test
    public void testAddOrder() {
        Order order3 = new Order(3, 789, "2023-07-19", "Debit Card");
        orderManager.addOrder(order3);
        assertEquals(3, orderManager.getTotalOrders());
    }

    @Test
    public void testRemoveOrder() {
        assertTrue(orderManager.removeOrder(1));
        assertEquals(1, orderManager.getTotalOrders());
        assertFalse(orderManager.removeOrder(3));
    }

    @Test
    public void testGetOrders() {
        List<Order> orders = orderManager.getOrders();
        assertEquals(2, orders.size());
        assertTrue(orders.contains(order1));
        assertTrue(orders.contains(order2));
    }

    @Test
    public void testCalculateTotalRevenue() {
        double expectedRevenue = order1.getTotalPrice() + order2.getTotalPrice();
        assertEquals(expectedRevenue, orderManager.calculateTotalRevenue());
    }

    @Test
    public void testGetOrdersByCustomerId() {
        List<Order> customerOrders = orderManager.getOrdersByCustomerId(123);
        assertEquals(1, customerOrders.size());
        assertEquals(order1, customerOrders.get(0));

        customerOrders = orderManager.getOrdersByCustomerId(999);
        assertTrue(customerOrders.isEmpty());
    }

    @Test
    public void testGetTotalOrders() {
        assertEquals(2, orderManager.getTotalOrders());
    }

    @Test
    public void testClearAllOrders() {
        orderManager.clearAllOrders();
        assertEquals(0, orderManager.getTotalOrders());
    }

    @Test
    public void testFindOrderById() {
        assertEquals(order1, orderManager.findOrderById(1));
        assertNull(orderManager.findOrderById(3));
    }

    @Test
    public void testUpdateOrderStatus() {
        assertTrue(orderManager.updateOrderStatus(1, OrderStatus.SHIPPED));
        assertEquals(OrderStatus.SHIPPED, order1.getStatus());
        assertFalse(orderManager.updateOrderStatus(3, OrderStatus.CANCELLED));
    }

    @Test
    public void testGetOrdersWithStatus() {
        orderManager.updateOrderStatus(1, OrderStatus.SHIPPED);
        List<Order> shippedOrders = orderManager.getOrdersWithStatus(OrderStatus.SHIPPED);
        assertEquals(1, shippedOrders.size());
        assertEquals(order1, shippedOrders.get(0));
    }

    @Test
    public void testGetOrdersByPaymentMethod() {
        List<Order> creditCardOrders = orderManager.getOrdersByPaymentMethod("Credit Card");
        assertEquals(1, creditCardOrders.size());
        assertEquals(order1, creditCardOrders.get(0));

        List<Order> paypalOrders = orderManager.getOrdersByPaymentMethod("PayPal");
        assertEquals(1, paypalOrders.size());
        assertEquals(order2, paypalOrders.get(0));
    }

    @Test
    public void testGetOrdersByDateRange() {
        List<Order> ordersInRange = orderManager.getOrdersByDateRange("2023-05-01", "2023-05-31");
        assertEquals(1, ordersInRange.size());
        assertEquals(order1, ordersInRange.get(0));

        ordersInRange = orderManager.getOrdersByDateRange("2023-06-01", "2023-06-30");
        assertEquals(1, ordersInRange.size());
        assertEquals(order2, ordersInRange.get(0));

        ordersInRange = orderManager.getOrdersByDateRange("2023-01-01", "2023-12-31");
        assertEquals(2, ordersInRange.size());
    }
    @Test
    public void testAddOrderWithExistingOrderId() {
        Order order = new Order(1, 789, "2023-07-19", "Debit Card");
        orderManager.addOrder(order);
        assertEquals(order, orderManager.findOrderById(1));

        // Try adding another order with the same order ID
        Order newOrder = new Order(1, 999, "2023-07-20", "Credit Card");
        orderManager.addOrder(newOrder);
        assertEquals(newOrder, orderManager.findOrderById(1));
        assertNotEquals(order, orderManager.findOrderById(1));
    }

    @Test
    public void testRemoveNonExistentOrder() {
        assertFalse(orderManager.removeOrder(999));
    }

    @Test
    public void testGetOrdersWhenListIsEmpty() {
        orderManager.clearAllOrders();
        assertTrue(orderManager.getOrders().isEmpty());
    }

    @Test
    public void testUpdateStatusOfNonExistentOrder() {
        assertFalse(orderManager.updateOrderStatus(999, OrderStatus.CANCELLED));
    }

    @Test
    public void testAddingOrderWithMaximumValues() {
        // Define order with maximum values for attributes
        Order order = new Order(Integer.MAX_VALUE, Integer.MAX_VALUE, "9999-12-31", "Cash");

        // Add the order
        orderManager.addOrder(order);

        // Check if the order was added successfully
        assertEquals(order, orderManager.findOrderById(Integer.MAX_VALUE));
    }

    @Test
    public void testAddingOrderWithLargeQuantitiesOfProducts() {
        // Initialize some Product objects
        Product product1 = new Product(1, "Product 1", 10.0, Integer.MAX_VALUE, "Type1", LocalDate.of(2023, 12, 31));
        Product product2 = new Product(2, "Product 2", 20.0, Integer.MAX_VALUE, "Type2", LocalDate.of(2024, 12, 31));

        // Initialize an Order object with large quantities of products
        Order order = new Order(3, 123, "2023-05-19", "Credit Card");
        order.addProduct(product1, Integer.MAX_VALUE);
        order.addProduct(product2, Integer.MAX_VALUE);

        // Add the order
        orderManager.addOrder(order);

        // Check if the order was added successfully
        assertEquals(order, orderManager.findOrderById(3));
    }

    @Test
    public void testAddingOrderWithFutureAndPastDates() {
        // Define orders with future and past order dates
        Order futureOrder = new Order(4, 123, "2999-12-31", "Credit Card");
        Order pastOrder = new Order(5, 123, "2000-01-01", "Credit Card");

        // Add the orders
        orderManager.addOrder(futureOrder);
        orderManager.addOrder(pastOrder);

        // Check if the orders were added successfully
        assertEquals(futureOrder, orderManager.findOrderById(4));
        assertEquals(pastOrder, orderManager.findOrderById(5));
    }

    @Test
    public void testAddingOrderWithVeryLargeTotalPrice() {
        // Define an order with a very large total price
        Order order = new Order(6, 123, "2023-05-19", "Credit Card");
        Product product = new Product(3, "Product 3", Double.MAX_VALUE, 1, "Type3", LocalDate.of(2023, 12, 31));
        order.addProduct(product, 1);

        // Add the order
        orderManager.addOrder(order);

        // Check if the order was added successfully
        assertEquals(order, orderManager.findOrderById(6));
    }

    @Test
    public void testPassingNullValuesToMethods() {
        assertThrows(NullPointerException.class, () -> orderManager.addOrder(null));
    }

    @Test
    public void testInvalidDateFormatsWhenSearchingByDateRange() {
        // Test invalid start date format
        assertThrows(IllegalArgumentException.class, () -> orderManager.getOrdersByDateRange("2023-05-32", "2023-06-01"));

        // Test invalid end date format
        assertThrows(IllegalArgumentException.class, () -> orderManager.getOrdersByDateRange("2023-05-01", "2023-06-32"));
    }

    @Test
    public void testInvalidPaymentMethodValues() {
        // Test invalid payment method value
        assertTrue(orderManager.getOrdersByPaymentMethod("Invalid Method").isEmpty());
    }
}
