package cse430;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionManagerTest {

    private TransactionManager transactionManager;
    private Transaction transaction1;
    private Transaction transaction2;

    @BeforeEach
    public void setUp() {
        transactionManager = new TransactionManager();
        transaction1 = new Transaction("Type1", 100.0);
        transaction2 = new Transaction("Type2", -50.0);
        transactionManager.addTransaction(transaction1);
        transactionManager.addTransaction(transaction2);
    }

    @Test
    public void testAddTransaction() {
        Transaction transaction3 = new Transaction("Type3", 75.0);
        transactionManager.addTransaction(transaction3);
        assertTrue(transactionManager.hasTransaction(transaction3));
    }

    @Test
    public void testRemoveTransaction() {
        assertTrue(transactionManager.removeTransaction(transaction1));
        assertFalse(transactionManager.hasTransaction(transaction1));
        assertFalse(transactionManager.removeTransaction(new Transaction("Type4", 25.0)));
    }

    @Test
    public void testGetTransactions() {
        assertEquals(2, transactionManager.getTransactions().size());
    }

    @Test
    public void testGetTotalRevenue() {
        assertEquals(100.0, transactionManager.getTotalRevenue());
    }

    @Test
    public void testGetTotalExpenses() {
        assertEquals(50.0, transactionManager.getTotalExpenses());
    }

    @Test
    public void testGetTotalTransactions() {
        assertEquals(2, transactionManager.getTotalTransactions());
    }

    @Test
    public void testGetTransactionsByType() {
        assertEquals(1, transactionManager.getTransactionsByType("Type1").size());
        assertEquals(1, transactionManager.getTransactionsByType("Type2").size());
        assertEquals(0, transactionManager.getTransactionsByType("Type3").size());
    }

    @Test
    public void testClearAllTransactions() {
        transactionManager.clearAllTransactions();
        assertTrue(transactionManager.getTransactions().isEmpty());
    }

    @Test
    public void testHasRefundTransactions() {
        assertFalse(transactionManager.hasRefundTransactions());
        Transaction refundTransaction = new Transaction("Refund", -20.0);
        transactionManager.addTransaction(refundTransaction);
        assertTrue(transactionManager.hasRefundTransactions());
    }
    
    @Test
    public void testAddNullTransaction() {
        assertThrows(IllegalArgumentException.class, () -> {
            transactionManager.addTransaction(null);
        });
    }
}
