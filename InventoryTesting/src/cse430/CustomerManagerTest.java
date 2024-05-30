package cse430;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerManagerTest {

    private CustomerManager customerManager;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;

    @BeforeEach
    public void setUp() {
        customerManager = new CustomerManager();

        customer1 = new Customer(1, "Saki Al amin", "saki@example.com", "123 Street, City");
        customer2 = new Customer(2, "Mohit", "mohit@example.com", "456 Avenue, Town");
        customer3 = new Customer(3, "Rifat", "rifat@example.com", "789 Road, Village");

        customerManager.addCustomer(customer1);
        customerManager.addCustomer(customer2);
    }

    @Test
    public void testAddCustomer() {
        customerManager.addCustomer(customer3);
        assertEquals(3, customerManager.getTotalCustomers());
    }

    @Test
    public void testRemoveCustomer() {
        assertTrue(customerManager.removeCustomer(1));
        assertFalse(customerManager.removeCustomer(4)); // Non-existent customer
        assertEquals(1, customerManager.getTotalCustomers());
    }

    @Test
    public void testFindCustomerById() {
        assertEquals(customer1, customerManager.findCustomerById(1));
        assertNull(customerManager.findCustomerById(4)); // Non-existent customer
    }

    @Test
    public void testUpdateCustomerEmail() {
        assertTrue(customerManager.updateCustomerEmail(1, "newemail@example.com"));
        assertEquals("newemail@example.com", customerManager.findCustomerById(1).getEmail());
        assertFalse(customerManager.updateCustomerEmail(4, "newemail@example.com")); // Non-existent customer
    }

    @Test
    public void testUpdateCustomerAddress() {
        assertTrue(customerManager.updateCustomerAddress(1, "New Address"));
        assertEquals("New Address", customerManager.findCustomerById(1).getAddress());
        assertFalse(customerManager.updateCustomerAddress(4, "New Address")); // Non-existent customer
    }

    @Test
    public void testIsPreferredCustomer() {
        customer1.addBalance(1500); // Total purchases exceed 1000
        assertTrue(customerManager.isPreferredCustomer(1));
        assertFalse(customerManager.isPreferredCustomer(2));
    }

    // Add more test cases for other methods similarly

    @Test
    public void testGetTotalCustomers() {
        assertEquals(2, customerManager.getTotalCustomers());
    }

    @Test
    public void testAddNullCustomer() {
        customerManager.addCustomer(null);
        assertEquals(2, customerManager.getTotalCustomers());
    }

    @Test
    public void testRemoveNonExistentCustomer() {
        assertFalse(customerManager.removeCustomer(4));
        assertEquals(2, customerManager.getTotalCustomers());
    }

    @Test
    public void testUpdateCustomerWithNullValues() {
        assertFalse(customerManager.updateCustomerEmail(1, null));
        assertFalse(customerManager.updateCustomerAddress(1, null));
    }

    @Test
    public void testIsPreferredCustomerWithZeroBalance() {
        assertFalse(customerManager.isPreferredCustomer(2));
    }

    @Test
    public void testIsPreferredCustomerWithNegativeBalance() {
        customer2.addBalance(-500);
        assertFalse(customerManager.isPreferredCustomer(2));
    }

    
    @Test
    public void testUpdateNonExistentCustomer() {
        assertFalse(customerManager.updateCustomerEmail(4, "newemail@example.com"));
        assertFalse(customerManager.updateCustomerAddress(4, "New Address"));
    }

}
