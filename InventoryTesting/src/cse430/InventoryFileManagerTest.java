package cse430;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class InventoryFileManagerTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private InventoryFileManager inventoryFileManager;
    private String testFileName;

    @Before
    public void setUp() throws IOException {
        File tempFile = tempFolder.newFile("testInventory.txt");
        testFileName = tempFile.getAbsolutePath();
        inventoryFileManager = new InventoryFileManager(testFileName);
    }

    @Test
    public void testWriteAndReadInventoryToFile() throws IOException {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Product1", 10.0, 20, "Type1", LocalDate.of(2024, 5, 1)));
        products.add(new Product(2, "Product2", 15.0, 15, "Type2", LocalDate.of(2024, 5, 2)));

        inventoryFileManager.writeInventoryToFile(products);

        List<Product> readProducts = inventoryFileManager.readInventoryFromFile();
        assertEquals(products.size(), readProducts.size());

        for (int i = 0; i < products.size(); i++) {
            Product expected = products.get(i);
            Product actual = readProducts.get(i);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getPrice(), actual.getPrice(), 0.01);
            assertEquals(expected.getQuantity(), actual.getQuantity());
            assertEquals(expected.getType(), actual.getType());
            assertEquals(expected.getExpiryDate(), actual.getExpiryDate());
        }
    }

    @Test
    public void testBackupInventory() throws IOException {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Product1", 10.0, 20, "Type1", LocalDate.of(2024, 5, 1)));
        products.add(new Product(2, "Product2", 15.0, 15, "Type2", LocalDate.of(2024, 5, 2)));

        inventoryFileManager.writeInventoryToFile(products);

        String backupFileName = tempFolder.newFile("testBackup.txt").getAbsolutePath();
        inventoryFileManager.backupInventory(backupFileName);

        File backupFile = new File(backupFileName);
        assertTrue(backupFile.exists());
        assertTrue(backupFile.length() > 0);
    }
}
