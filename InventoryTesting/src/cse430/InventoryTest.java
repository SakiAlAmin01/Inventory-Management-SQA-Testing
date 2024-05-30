package cse430;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class InventoryTest {

    private Inventory inventory;

    @Before
    public void setUp() {
        inventory = new Inventory();
        inventory.addProduct(new Product(1, "Laptop", 1000, 10, "Electronics", LocalDate.of(2024, 12, 31)));
        inventory.addProduct(new Product(2, "Phone", 500, 15, "Electronics", LocalDate.of(2024, 12, 31)));
        inventory.addProduct(new Product(3, "Book", 20, 50, "Stationery", LocalDate.of(2025, 6, 30)));
    }

    @Test
    public void testAddProduct() {
        int initialSize = inventory.getProducts().size();
        LocalDate expiryDate = LocalDate.of(2024, 12, 31); 
        inventory.addProduct(new Product(4, "Headphones", 50, 20, "Electronics", expiryDate));
        assertEquals(initialSize + 1, inventory.getProducts().size());
    }

    @Test
    public void testRemoveProduct() {
        assertTrue(inventory.removeProduct(2));
        assertFalse(inventory.removeProduct(5));
    }

    @Test
    public void testFindProductById() {
        assertNotNull(inventory.findProductById(2));
        assertNull(inventory.findProductById(5));
    }

    @Test
    public void testFindProductsByName() {
        List<Product> laptops = inventory.findProductsByName("Laptop");
        assertEquals(1, laptops.size());
        assertEquals("Laptop", laptops.get(0).getName());
    }
    
    @Test
    public void testUpdateProduct() {
        LocalDate expiryDate = LocalDate.now().plusYears(1); // Example expiry date one year from now
        Product updatedProduct = new Product(2, "Smartphone", 600, 20, "Electronics", expiryDate);
        assertTrue(inventory.updateProduct(2, updatedProduct));
        assertEquals("Smartphone", inventory.findProductById(2).getName());
    }

    @Test
    public void testGetProductsByPriceRange() {
        List<Product> productsInRange = inventory.getProductsByPriceRange(20, 1000);
        assertEquals(2, productsInRange.size());
    }

    @Test
    public void testGetTotalInventoryValue() {
        assertEquals(22500.0, inventory.getTotalInventoryValue(), 0.01);
    }

    @Test
    public void testCheckProductAvailability() {
        assertTrue(inventory.checkProductAvailability(1, 5));
        assertFalse(inventory.checkProductAvailability(1, 15));
    }

    @Test
    public void testCalculateAverageProductPrice() {
        assertEquals(506.66, inventory.calculateAverageProductPrice(), 0.01);
    }

    @Test
    public void testHasProductType() {
        assertTrue(inventory.hasProductType("Electronics"));
        assertFalse(inventory.hasProductType("Clothing"));
    }

    @Test
    public void testUpdateProductQuantity() {
        assertTrue(inventory.updateProductQuantity(3, 60));
        assertEquals(60, inventory.findProductById(3).getQuantity());
    }

    @Test
    public void testRemoveExpiredProducts() {
        inventory.removeExpiredProducts();
        assertEquals(3, inventory.getProducts().size());
    }

    @Test
    public void testGetProductsBelowThreshold() {
        List<Product> belowThresholdProducts = inventory.getProductsBelowThreshold(30);
        assertEquals(1, belowThresholdProducts.size());
        assertEquals("Book", belowThresholdProducts.get(0).getName());
    }

    @Test
    public void testAddProductWithExtremeValues() {
        // Test adding a product with the minimum allowed values
        inventory.addProduct(new Product(0, "Test", 0, 0, "Test Type", LocalDate.now()));
        assertEquals(4, inventory.getProducts().size());

        // Test adding a product with the maximum allowed values
        inventory.addProduct(new Product(Integer.MAX_VALUE, "Max Test", Double.MAX_VALUE, Integer.MAX_VALUE, "Max Type", LocalDate.now()));
        assertEquals(5, inventory.getProducts().size());
    }
}