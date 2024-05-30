package cse430;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {

    private ShoppingCart cart;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void setUp() {
        cart = new ShoppingCart();
        product1 = new Product(1, "Product 1", 10.0, 100, "Type1", LocalDate.of(2023, 12, 31));
        product2 = new Product(2, "Product 2", 20.0, 200, "Type2", LocalDate.of(2024, 12, 31));
    }

    @Test
    public void testAddItem() {
        cart.addItem(product1, 2);
        assertTrue(cart.containsProduct(product1));
        assertEquals(2, cart.getItems().get(product1).intValue());

        cart.addItem(product1, 3);
        assertEquals(5, cart.getItems().get(product1).intValue());
    }

    @Test
    public void testRemoveItem() {
        cart.addItem(product1, 5);
        cart.removeItem(product1, 2);
        assertEquals(3, cart.getItems().get(product1).intValue());

        cart.removeItem(product1, 3);
        assertFalse(cart.containsProduct(product1));
    }

    @Test
    public void testGetItems() {
        cart.addItem(product1, 2);
        cart.addItem(product2, 3);
        Map<Product, Integer> items = cart.getItems();
        assertEquals(2, items.size());
        assertEquals(2, items.get(product1).intValue());
        assertEquals(3, items.get(product2).intValue());
    }

    @Test
    public void testCalculateTotalPrice() {
        cart.addItem(product1, 2);
        cart.addItem(product2, 3);
        double expectedTotalPrice = (product1.getPrice() * 2) + (product2.getPrice() * 3);
        assertEquals(expectedTotalPrice, cart.calculateTotalPrice());
    }

    @Test
    public void testClear() {
        cart.addItem(product1, 2);
        cart.clear();
        assertTrue(cart.isEmpty());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(cart.isEmpty());
        cart.addItem(product1, 2);
        assertFalse(cart.isEmpty());
    }

    @Test
    public void testApplyDiscount() {
        cart.addItem(product1, 2);
        cart.applyDiscount(10); // 10% discount
        double expectedPrice = product1.getPrice() * 0.9 * 2;
        assertEquals(expectedPrice, cart.calculateTotalPrice());
    }

    @Test
    public void testGetTotalQuantity() {
        cart.addItem(product1, 2);
        cart.addItem(product2, 3);
        assertEquals(5, cart.getTotalQuantity());
    }

    @Test
    public void testContainsProduct() {
        assertFalse(cart.containsProduct(product1));
        cart.addItem(product1, 2);
        assertTrue(cart.containsProduct(product1));
    }

    @Test
    public void testGetItemsAboveThreshold() {
        cart.addItem(product1, 2);
        cart.addItem(product2, 5);
        Map<Product, Integer> itemsAboveThreshold = cart.getItemsAboveThreshold(3);
        assertEquals(1, itemsAboveThreshold.size());
        assertTrue(itemsAboveThreshold.containsKey(product2));
    }

    @Test
    public void testUpdateQuantity() {
        cart.addItem(product1, 2);
        cart.updateQuantity(product1, 5);
        assertEquals(5, cart.getItems().get(product1).intValue());
    }
    
    @Test
    public void testEdgeCases() {
        // Test adding and removing items with quantities of zero
        cart.addItem(product1, 0);
        assertFalse(cart.containsProduct(product1));

        // Test adding and removing items with negative quantities
        cart.addItem(product1, -1);
        assertFalse(cart.containsProduct(product1));

        // Test adding and removing items when the cart is empty
        cart.removeItem(product1, 1);
        assertFalse(cart.containsProduct(product1));
    }

    @Test
    public void testBoundaryCases() {
        // Test adding items with maximum quantities allowed
        cart.addItem(product1, Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, cart.getItems().get(product1).intValue());

        // Test removing items when the quantity becomes zero
        cart.removeItem(product1, Integer.MAX_VALUE);
        assertFalse(cart.containsProduct(product1));

        // Test applying discount when the cart is empty
        cart.applyDiscount(10);
        assertEquals(0.0, cart.calculateTotalPrice());
    }

    @Test
    public void testExpiredProductsHandling() {
        Product expiredProduct = new Product(3, "Expired Product", 15.0, 50, "Type3", LocalDate.of(2020, 12, 31));

        // Test adding expired products and verify that they are not added to the cart
        cart.addItem(expiredProduct, 1);
        assertFalse(cart.containsProduct(expiredProduct));

        // Test adding non-expired products and verify that they are added correctly
        cart.addItem(product1, 1);
        assertTrue(cart.containsProduct(product1));
    }

}
