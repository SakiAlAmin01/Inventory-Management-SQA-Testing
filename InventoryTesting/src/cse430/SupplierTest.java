package cse430;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SupplierTest {

    private Supplier supplier;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void setUp() {
        supplier = new Supplier("Supplier 1");
        product1 = new Product(1, "Product 1", 10.0, 100, "Type1", LocalDate.of(2023, 12, 31));
        product2 = new Product(2, "Product 2", 20.0, 200, "Type2", LocalDate.of(2024, 12, 31));
    }

    @Test
    public void testAddProduct() {
        supplier.addProduct(product1);
        assertTrue(supplier.hasProduct(product1));
        assertEquals(1, supplier.getTotalProductsSupplied());

        supplier.addProduct(product2);
        assertTrue(supplier.hasProduct(product2));
        assertEquals(2, supplier.getTotalProductsSupplied());
    }

    @Test
    public void testRemoveProduct() {
        supplier.addProduct(product1);
        supplier.addProduct(product2);
        assertTrue(supplier.removeProduct(product1));
        assertFalse(supplier.hasProduct(product1));
        assertEquals(1, supplier.getTotalProductsSupplied());
    }

    @Test
    public void testUpdateProductPrice() {
        supplier.addProduct(product1);
        supplier.updateProductPrice(product1, 12.0);
        assertEquals(12.0, product1.getPrice());

        supplier.updateProductPrice(product1, 8.0);
        assertEquals(8.0, product1.getPrice());

        supplier.updateProductPrice(product1, 8.0);
        assertEquals(8.0, product1.getPrice());
    }

    @Test
    public void testGetProductsSupplied() {
        supplier.addProduct(product1);
        supplier.addProduct(product2);
        List<Product> products = supplier.getProductsSupplied();
        assertEquals(2, products.size());
        assertTrue(products.contains(product1));
        assertTrue(products.contains(product2));
    }

    @Test
    public void testGetTotalProductsSupplied() {
        supplier.addProduct(product1);
        assertEquals(1, supplier.getTotalProductsSupplied());

        supplier.addProduct(product2);
        assertEquals(2, supplier.getTotalProductsSupplied());
    }

    @Test
    public void testClearAllProducts() {
        supplier.addProduct(product1);
        supplier.addProduct(product2);
        supplier.clearAllProducts();
        assertEquals(0, supplier.getTotalProductsSupplied());
        assertTrue(supplier.getProductsSupplied().isEmpty());
    }

    @Test
    public void testGetTotalInventoryValue() {
        supplier.addProduct(product1);
        supplier.addProduct(product2);
        double expectedValue = (product1.getPrice() * product1.getQuantity()) + (product2.getPrice() * product2.getQuantity());
        assertEquals(expectedValue, supplier.getTotalInventoryValue());
    }

    @Test
    public void testHasProduct() {
        assertFalse(supplier.hasProduct(product1));
        supplier.addProduct(product1);
        assertTrue(supplier.hasProduct(product1));
    }

    @Test
    public void testHasExpiredProducts() {
        Product expiredProduct = new Product(3, "Expired Product", 5.0, 50, "Type3", LocalDate.of(2022, 12, 31));
        supplier.addProduct(expiredProduct);
        assertTrue(supplier.hasExpiredProducts());

        supplier.removeProduct(expiredProduct);
        assertFalse(supplier.hasExpiredProducts());
    }

    @Test
    public void testUpdateProductQuantity() {
        supplier.addProduct(product1);
        supplier.updateProductQuantity(product1, 150);
        assertEquals(150, product1.getQuantity());

        supplier.updateProductQuantity(product1, 50);
        assertEquals(50, product1.getQuantity());

        supplier.updateProductQuantity(product1, 50);
        assertEquals(50, product1.getQuantity());
    }

    @Test
    public void testHasProductsExpiringToday() {
        Product expiringTodayProduct = new Product(4, "Expiring Today Product", 15.0, 30, "Type4", LocalDate.now());
        supplier.addProduct(expiringTodayProduct);
        assertTrue(supplier.hasExpiredProducts());
    }
}
