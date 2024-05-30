package cse430;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {

    private Warehouse warehouse;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void setUp() {
        warehouse = new Warehouse();
        product1 = new Product(1, "Product 1", 10.0, 100, "Type1", LocalDate.of(2023, 12, 31));
        product2 = new Product(2, "Product 2", 20.0, 200, "Type2", LocalDate.of(2024, 12, 31));
    }

    @Test
    public void testAddProduct() {
        warehouse.addProduct(product1, 50);
        assertEquals(50, warehouse.getAvailableQuantity(product1));

        warehouse.addProduct(product2, 100);
        assertEquals(100, warehouse.getAvailableQuantity(product2));
    }

    @Test
    public void testRemoveProduct() {
        warehouse.addProduct(product1, 100);
        assertTrue(warehouse.removeProduct(product1, 50));
        assertEquals(50, warehouse.getAvailableQuantity(product1));

        assertFalse(warehouse.removeProduct(product2, 50));
    }

    @Test
    public void testGetAvailableQuantity() {
        warehouse.addProduct(product1, 100);
        assertEquals(100, warehouse.getAvailableQuantity(product1));
        assertEquals(0, warehouse.getAvailableQuantity(product2));
    }

    @Test
    public void testCalculateInventoryValue() {
        warehouse.addProduct(product1, 100);
        warehouse.addProduct(product2, 200);
        double expectedValue = (product1.getPrice() * 100) + (product2.getPrice() * 200);
        assertEquals(expectedValue, warehouse.calculateInventoryValue());
    }

    @Test
    public void testContainsExpensiveProducts() {
        assertFalse(warehouse.containsExpensiveProducts(25.0));
        warehouse.addProduct(product1, 100);
        assertTrue(warehouse.containsExpensiveProducts(5.0));
    }

    @Test
    public void testUpdateProductPrice() {
        warehouse.addProduct(product1, 100);
        assertTrue(warehouse.updateProductPrice(1, 15.0));
        assertEquals(15.0, product1.getPrice());
        assertFalse(warehouse.updateProductPrice(2, 25.0));
    }

    @Test
    public void testGetTotalProductCategories() {
        assertEquals(0, warehouse.getTotalProductCategories());
        warehouse.addProduct(product1, 100);
        assertEquals(1, warehouse.getTotalProductCategories());
        warehouse.addProduct(product2, 200);
        assertEquals(2, warehouse.getTotalProductCategories());
    }

    @Test
    public void testProductWithFutureExpiryDate() {
        LocalDate futureDate = LocalDate.now().plusYears(1);
        Product product3 = new Product(3, "Product 3", 15.0, 150, "Type3", futureDate);
        warehouse.addProduct(product3, 150);
        assertTrue(warehouse.getInventory().containsKey(product3.getId()));
        assertEquals(150, warehouse.getAvailableQuantity(product3));
    }
}
