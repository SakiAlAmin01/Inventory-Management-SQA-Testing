package cse430;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    public void testGetDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 19, 12, 30);
        Transaction transaction = new Transaction("Type", 100.0, dateTime);
        assertEquals(dateTime, transaction.getDateTime());
    }

    @Test
    public void testGetType() {
        Transaction transaction = new Transaction("Type", 100.0);
        assertEquals("Type", transaction.getType());
    }

    @Test
    public void testGetAmount() {
        Transaction transaction = new Transaction("Type", 100.0);
        assertEquals(100.0, transaction.getAmount());
    }

    @Test
    public void testSetAmount() {
        Transaction transaction = new Transaction("Type", 100.0);
        transaction.setAmount(200.0);
        assertEquals(200.0, transaction.getAmount());
    }

    @Test
    public void testIsPositiveAmount() {
        Transaction transaction = new Transaction("Type", 100.0);
        assertTrue(transaction.isPositiveAmount());
    }

    @Test
    public void testIsNegativeAmount() {
        Transaction transaction = new Transaction("Type", -100.0);
        assertTrue(transaction.isNegativeAmount());
    }

    @Test
    public void testIsOfType() {
        Transaction transaction = new Transaction("Type", 100.0);
        assertTrue(transaction.isOfType("Type"));
        assertFalse(transaction.isOfType("OtherType"));
    }

    @Test
    public void testIsRecentTransaction() {
        LocalDateTime now = LocalDateTime.now();
        Transaction transaction = new Transaction("Type", 100.0, now.minusDays(5));
        assertTrue(transaction.isRecentTransaction());
    }

    @Test
    public void testIsPastTransaction() {
        LocalDateTime now = LocalDateTime.now();
        Transaction transaction = new Transaction("Type", 100.0, now.minusDays(10));
        assertTrue(transaction.isPastTransaction());
    }

    @Test
    public void testExceedsThreshold() {
        Transaction transaction = new Transaction("Type", 100.0);
        assertTrue(transaction.exceedsThreshold(50.0));
        assertFalse(transaction.exceedsThreshold(150.0));
    }

    @Test
    public void testIsFutureTransaction() {
        LocalDateTime now = LocalDateTime.now();
        Transaction transaction = new Transaction("Type", 100.0, now.plusDays(1));
        assertTrue(transaction.isFutureTransaction());
    }

    @Test
    public void testIsRefundTransaction() {
        Transaction transaction = new Transaction("refund", 100.0);
        assertTrue(transaction.isRefundTransaction());
    }

    @Test
    public void testIsSpecificTransaction() {
        Transaction transaction = new Transaction("Type", 100.0);
        assertTrue(transaction.isSpecificTransaction("Type", 100.0));
        assertFalse(transaction.isSpecificTransaction("OtherType", 100.0));
        assertFalse(transaction.isSpecificTransaction("Type", 200.0));
    }

    @Test
    public void testConcurrency() {
        // Simulate concurrent access
        Transaction transaction1 = new Transaction("Type", 100.0);
        Transaction transaction2 = new Transaction("Type", 200.0);

        // Check if transactions are independent
        assertNotEquals(transaction1.getDateTime(), transaction2.getDateTime());
        assertNotEquals(transaction1.getAmount(), transaction2.getAmount());
        assertNotEquals(transaction1.getType(), transaction2.getType());
    }
}
