package cse430;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Map;

class OrderTest {

    private Order order;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void setUp() {
        // Initialize the Order object before each test
        order = new Order(1, 123, "2023-05-19", "Credit Card");

        // Initialize some Product objects
        product1 = new Product(1, "Product 1", 10.0, 100, "Type1", LocalDate.of(2023, 12, 31));
        product2 = new Product(2, "Product 2", 20.0, 200, "Type2", LocalDate.of(2024, 12, 31));
    }

    @Test
    public void testGetOrderId() {
        assertEquals(1, order.getOrderId());
    }

    @Test
    public void testSetOrderId() {
        order.setOrderId(2);
        assertEquals(2, order.getOrderId());
    }

    @Test
    public void testGetCustomerId() {
        assertEquals(123, order.getCustomerId());
    }

    @Test
    public void testSetCustomerId() {
        order.setCustomerId(456);
        assertEquals(456, order.getCustomerId());
    }

    @Test
    public void testGetOrderDate() {
        assertEquals("2023-05-19", order.getOrderDate());
    }

    @Test
    public void testSetOrderDate() {
        order.setOrderDate("2023-05-20");
        assertEquals("2023-05-20", order.getOrderDate());
    }

    @Test
    public void testGetPaymentMethod() {
        assertEquals("Credit Card", order.getPaymentMethod());
    }

    @Test
    public void testSetPaymentMethod() {
        order.setPaymentMethod("PayPal");
        assertEquals("PayPal", order.getPaymentMethod());
    }

    @Test
    public void testGetStatus() {
        assertEquals(OrderStatus.PENDING, order.getStatus());
    }

    @Test
    public void testSetStatus() {
        order.setStatus(OrderStatus.SHIPPED);
        assertEquals(OrderStatus.SHIPPED, order.getStatus());
    }

    @Test
    public void testAddProduct() {
        order.addProduct(product1, 2);
        Map<Product, Integer> products = order.getProducts();
        assertTrue(products.containsKey(product1));
        assertEquals(2, products.get(product1));
    }

    @Test
    public void testGetTotalPrice() {
        // Add products to the order
        order.addProduct(product1, 2);
        order.addProduct(product2, 3);

        // Calculate the expected total price
        double expectedTotalPrice = (product1.getPrice() * 2) + (product2.getPrice() * 3);

        // Check if the total price calculation is correct
        assertEquals(expectedTotalPrice, order.getTotalPrice());
    }
    
    @Test
    public void testAddingProductWithNegativeQuantity() {
        // Try adding a product with a negative quantity
        order.addProduct(product1, -2);
        
        // Ensure the product is not added to the order
        assertFalse(order.getProducts().containsKey(product1));
    }
    
    @Test
    public void testAddingProductWithZeroQuantity() {
        // Try adding a product with a zero quantity
        order.addProduct(product1, 0);
        
        // Ensure the product is not added to the order
        assertFalse(order.getProducts().containsKey(product1));
    }
    
    @Test
    public void testRemovingProduct() {
        // Add products to the order
        order.addProduct(product1, 2);
        order.addProduct(product2, 3);
        
        // Remove product1 from the order
        order.getProducts().remove(product1);
        
        // Ensure product1 is removed and total price is updated
        assertFalse(order.getProducts().containsKey(product1));
        assertEquals(product2.getPrice() * 3, order.getTotalPrice());
    }
    
    @Test
    public void testOrderStatusTransitions() {
        // Transition order status from PENDING to SHIPPED
        order.setStatus(OrderStatus.SHIPPED);
        assertEquals(OrderStatus.SHIPPED, order.getStatus());
        
        // Transition order status from SHIPPED to DELIVERED
        order.setStatus(OrderStatus.DELIVERED);
        assertEquals(OrderStatus.DELIVERED, order.getStatus());
        
        // Transition order status from DELIVERED to CANCELLED
        order.setStatus(OrderStatus.CANCELLED);
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }
    
    // Add more test cases as needed...

}
