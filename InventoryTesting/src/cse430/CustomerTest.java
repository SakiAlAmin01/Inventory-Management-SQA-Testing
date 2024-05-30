package cse430;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private Customer customer;
    private Product mobile;
    private Product laptop;
    private Product mouse;
    private Product keyboard;

    @BeforeEach
    public void setUp() {
        customer = new Customer(1, "Saki Al amin", "saki@example.com", "123 Street, City");

        mobile = new Product(1, "Mobile", 500.0, 0, null, null);
        laptop = new Product(2, "Laptop", 1000.0, 0, null, null);
        mouse = new Product(3, "Mouse", 20.0, 0, null, null);
        keyboard = new Product(4, "Keyboard", 50.0, 0, null, null);
    }

    @Test
    public void testPurchaseItem() {
        customer.purchaseItem(mobile, 2);
        customer.purchaseItem(laptop, 1);
        Map<Product, Integer> expectedItems = new HashMap<>();
        expectedItems.put(mobile, 2);
        expectedItems.put(laptop, 1);
        assertEquals(expectedItems, customer.getPurchasedItems());
    }

    @Test
    public void testReturnItem() {
        customer.purchaseItem(mouse, 5);
        customer.returnItem(mouse, 2);
        Map<Product, Integer> expectedItems = new HashMap<>();
        expectedItems.put(mouse, 3);
        assertEquals(expectedItems, customer.getPurchasedItems());
    }

    @Test
    public void testTotalItemsPurchased() {
        customer.purchaseItem(keyboard, 3);
        assertEquals(3, customer.getTotalItemsPurchased());
    }

    @Test
    public void testTotalAmountSpent() {
        customer.purchaseItem(mobile, 2);
        customer.purchaseItem(laptop, 1);
        assertEquals(2000.0, customer.getTotalAmountSpent());
    }

    @Test
    public void testHasPurchasedItem() {
        customer.purchaseItem(mobile, 2);
        assertTrue(customer.hasPurchasedItem(mobile));
    }

    @Test
    public void testClearAllPurchasedItems() {
        customer.purchaseItem(mobile, 2);
        customer.purchaseItem(laptop, 1);
        customer.clearAllPurchasedItems();
        assertTrue(customer.getPurchasedItems().isEmpty());
    }

    @Test
    public void testHasPurchasedMultipleItems() {
        customer.purchaseItem(mobile, 2);
        customer.purchaseItem(laptop, 1);
        assertTrue(customer.hasPurchasedMultipleItems());
    }

    @Test
    public void testIsFrequentShopper() {
        customer.purchaseItem(mouse, 1);
        customer.purchaseItem(keyboard, 1);
        assertTrue(customer.isFrequentShopper());
    }

    @Test
    public void testCalculateAveragePurchaseQuantity() {
        customer.purchaseItem(mouse, 1);
        customer.purchaseItem(keyboard, 3);
        assertEquals(2.0, customer.calculateAveragePurchaseQuantity());
    }

    @Test
    public void testHasHighSpending() {
        customer.purchaseItem(mobile, 3);
        customer.purchaseItem(laptop, 2);
        assertTrue(customer.hasHighSpending());
    }

    @Test
    public void testAddAndRemoveBalance() {
        customer.addBalance(100);
        assertEquals(100.0, customer.getBalance());
        customer.removeBalance(50);
        assertEquals(50.0, customer.getBalance());
    }

    @Test
    public void testSetAndGetStatus() {
        customer.setStatus(CustomerStatus.PREMIUM);
        assertEquals(CustomerStatus.PREMIUM, customer.getStatus());
    }

    @Test
    public void testIsActive() {
        customer.setStatus(CustomerStatus.ACTIVE);
        assertTrue(customer.isActive());
    }

    // Edge Cases for Purchasing and Returning Items:

    @Test
    public void testPurchaseItemWithZeroQuantity() {
        customer.purchaseItem(mouse, 0);
        assertTrue(customer.getPurchasedItems().isEmpty());
    }

    @Test
    public void testPurchaseItemWithNegativeQuantity() {
        customer.purchaseItem(mouse, -1);
        assertTrue(customer.getPurchasedItems().isEmpty());
    }

    @Test
    public void testReturnItemNotPurchased() {
        customer.returnItem(keyboard, 1);
        assertTrue(customer.getPurchasedItems().isEmpty());
    }

    // Edge Cases for Balance Management:

    @Test
    public void testAddAndRemoveZeroBalance() {
        customer.addBalance(0);
        assertEquals(0.0, customer.getBalance());
        customer.removeBalance(0);
        assertEquals(0.0, customer.getBalance());
    }

    @Test
    public void testRemoveZeroBalance() {
        customer.removeBalance(0);
        assertEquals(0.0, customer.getBalance());
    }

    @Test
    public void testRemoveBalanceGreaterThanCurrent() {
        customer.removeBalance(100); // Removing more than the current balance
        assertEquals(0.0, customer.getBalance());
    }

    // Testing Status Transitions:

    @Test
    public void testStatusTransitionFromRegularToPremium() {
        customer.addBalance(1000); // Premium status requires a balance of at least $1000
        customer.setStatus(CustomerStatus.PREMIUM);
        assertEquals(CustomerStatus.PREMIUM, customer.getStatus());
    }

    @Test
    public void testStatusTransitionFromActiveToInactive() {
        customer.removeBalance(100); // Set balance to less than $0, making the customer inactive
        assertEquals(CustomerStatus.INACTIVE, customer.getStatus());
    }

    // Testing Total Purchases Calculation:

    @Test
    public void testTotalPurchasesWithNoItemsPurchased() {
        assertEquals(0.0, customer.getTotalPurchases());
    }

    @Test
    public void testTotalPurchasesAfterClearing() {
        customer.purchaseItem(mobile, 2);
        customer.clearAllPurchasedItems();
        assertEquals(0.0, customer.getTotalPurchases());
    }

    // Boundary Testing:

    @Test
    public void testMaxAllowedItemsPurchase() {
    	// Limit the number of iterations to a smaller value, e.g., 1000
        int maxTestIterations = 10; 
        for (int i = 0; i < maxTestIterations; i++) {
            customer.purchaseItem(mouse, 1);
        }
        assertEquals(Integer.MAX_VALUE, customer.getTotalItemsPurchased());
    }

    @Test
    public void testPurchaseMaxPriceItem() {
        // Purchase an item with the maximum allowed price
        Product maxPriceProduct = new Product(5, "Max Price Product", Double.MAX_VALUE, 0, null, null);
        customer.purchaseItem(maxPriceProduct, 1);
        assertEquals(Double.MAX_VALUE, customer.getTotalAmountSpent());
    }
}
