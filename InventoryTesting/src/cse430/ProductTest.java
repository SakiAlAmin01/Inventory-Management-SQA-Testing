package cse430;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product(1, "Test Product", 10.0, 5, "Test Type", LocalDate.now().plusDays(10));
    }

    @Test
    public void testGetters() {
        assertEquals(1, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals(10.0, product.getPrice());
        assertEquals(5, product.getQuantity());
        assertEquals("Test Type", product.getType());
        assertEquals(LocalDate.now().plusDays(10), product.getExpiryDate());
    }

    @Test
    public void testSetters() {
        product.setPrice(20.0);
        assertEquals(20.0, product.getPrice());

        product.setQuantity(10);
        assertEquals(10, product.getQuantity());
    }

    @Test
    public void testIsExpired() {
        assertFalse(product.isExpired());

        Product expiredProduct = new Product(2, "Expired Product", 15.0, 3, "Expired Type", LocalDate.now().minusDays(1));
        assertTrue(expiredProduct.isExpired());
    }

    @Test
    public void testCalculateTotalValue() {
        assertEquals(50.0, product.calculateTotalValue());
        
        // Test with zero quantity
        Product zeroQuantityProduct = new Product(3, "Zero Quantity", 5.0, 0, "Zero Type", LocalDate.now().plusDays(5));
        assertEquals(0.0, zeroQuantityProduct.calculateTotalValue());
        
        // Test with negative price
        Product negativePriceProduct = new Product(4, "Negative Price", -8.0, 2, "Negative Type", LocalDate.now().plusDays(3));
        assertEquals(-16.0, negativePriceProduct.calculateTotalValue());
    }

    // Additional Test Cases
    
    @Test
    public void testBoundaryValues() {
        // Test with minimum and maximum allowed values
        Product minIdProduct = new Product(Integer.MIN_VALUE, "Min ID", 10.0, 1, "Type", LocalDate.now());
        assertEquals(Integer.MIN_VALUE, minIdProduct.getId());

        Product maxIdProduct = new Product(Integer.MAX_VALUE, "Max ID", 10.0, 1, "Type", LocalDate.now());
        assertEquals(Integer.MAX_VALUE, maxIdProduct.getId());

        // Test with empty and null values for name and type
        Product emptyNameProduct = new Product(5, "", 10.0, 1, "Type", LocalDate.now());
        assertEquals("", emptyNameProduct.getName());

        Product nullTypeProduct = new Product(6, "Null Type", 10.0, 1, null, LocalDate.now());
        assertNull(nullTypeProduct.getType());
    }

    @Test
    public void testEdgeCases() {
        // Test with very large values for price and quantity
        Product largePriceProduct = new Product(7, "Large Price", Double.MAX_VALUE, 1, "Type", LocalDate.now());
        assertEquals(Double.MAX_VALUE, largePriceProduct.getPrice());

        Product largeQuantityProduct = new Product(8, "Large Quantity", 10.0, Integer.MAX_VALUE, "Type", LocalDate.now());
        assertEquals(Integer.MAX_VALUE, largeQuantityProduct.getQuantity());

        // Test with a very distant future expiry date
        LocalDate distantFuture = LocalDate.now().plusYears(1000);
        Product distantFutureProduct = new Product(9, "Distant Future", 10.0, 1, "Type", distantFuture);
        assertEquals(distantFuture, distantFutureProduct.getExpiryDate());

        // Test with a past expiry date
        Product pastExpiryProduct = new Product(10, "Past Expiry", 10.0, 1, "Type", LocalDate.now().minusDays(1));
        assertTrue(pastExpiryProduct.isExpired());
    }

    @Test
    public void testInvalidInputs() {
        // Test setting negative values for price and quantity
        Product negativePriceProduct = new Product(11, "Negative Price", -10.0, 1, "Type", LocalDate.now());
        assertEquals(-10.0, negativePriceProduct.getPrice());

        Product negativeQuantityProduct = new Product(12, "Negative Quantity", 10.0, -1, "Type", LocalDate.now());
        assertEquals(-1, negativeQuantityProduct.getQuantity());

        // Test setting null for expiryDate
        Product nullExpiryProduct = new Product(13, "Null Expiry", 10.0, 1, "Type", null);
        assertNull(nullExpiryProduct.getExpiryDate());
    }

    // Add more test cases as needed

}
