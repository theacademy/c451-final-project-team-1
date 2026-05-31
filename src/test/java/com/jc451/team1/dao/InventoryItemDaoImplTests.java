package com.jc451.team1.dao;

import com.jc451.team1.DAO.InventoryItemDao;
import com.jc451.team1.DAO.InventoryItemDaoImpl;
import com.jc451.team1.DTO.InventoryItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InventoryItemDaoImplTests {

    private JdbcTemplate jdbcTemplate;
    private InventoryItemDao inventoryItemDao;

    @Autowired
    public void InventoryItemDaoImplTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        inventoryItemDao = new InventoryItemDaoImpl(jdbcTemplate);
    }

    @Test
    @DisplayName("Add New Inventory Item Test")
    public void addInventoryItemTest() {
        InventoryItem item = new InventoryItem();
        item.setQuantity(2.5f);
        item.setUnit("Liters");
        item.setExpirationDate(LocalDate.of(2026, 6, 30));
        item.setHouseHoldId(1);
        item.setItemId(1); // Assuming item identity references an existing master item record

        inventoryItemDao.addInventoryItem(item);
        List<InventoryItem> newList = inventoryItemDao.getAllInventoryItems();

        assertNotNull(newList);
        // Assuming database starts with a fixed number (e.g., 14), expect count to increment by 1
        assertEquals(15, newList.size());
    }

    @Test
    @DisplayName("Get All Inventory Items Test")
    public void getAllInventoryItemsTest() {
        List<InventoryItem> newList = inventoryItemDao.getAllInventoryItems();
        assertNotNull(newList);
        // Assuming your seeded database state contains exactly 14 records initially
        assertEquals(14, newList.size());
    }

    @Test
    @DisplayName("Find An Inventory Item By ID: 5")
    public void findInventoryItemById5Test() {
        InventoryItem item = inventoryItemDao.findInventoryItemById(5);
        assertNotNull(item);
        // Validate against whatever quantity or unit value is pre-seeded at index ID 5
        assertEquals(5.0f, item.getQuantity());
    }

    @Test
    @DisplayName("Update Inventory Item Info")
    public void updateInventoryItemTest() {
        InventoryItem item = new InventoryItem();
        item.setIngredientId(11); // Targeting row primary key id = 11
        item.setQuantity(10.0f);
        item.setUnit("Grams");
        item.setExpirationDate(LocalDate.of(2026, 12, 25));
        item.setHouseHoldId(1);
        item.setItemId(2);

        inventoryItemDao.updateInventoryItem(item);
        List<InventoryItem> newList = inventoryItemDao.getAllInventoryItems();
        assertNotNull(newList);

        int matchCount = 0;
        for (InventoryItem inv : newList) {
            if (inv.getIngredientId() == 11 && inv.getQuantity() == 10.0f && "Grams".equals(inv.getUnit())) {
                matchCount++;
            }
        }
        assertTrue(matchCount != 0);
    }

    @Test
    @DisplayName("Delete an Inventory Item")
    public void removeInventoryItemTest() {
        inventoryItemDao.removeInventoryItem(14);
        List<InventoryItem> items = inventoryItemDao.getAllInventoryItems();

        assertNotNull(items);
        assertEquals(13, items.size());
    }

    @Test
    @DisplayName("Verify Item Name Retrieval Separately")
    public void getItemNameByItemIdTest() {
        // Asserting using your preferred "slow way" separate helper lookup query
        // Checks that item relationship reference #1 maps properly to its name string descriptor
        String itemName = inventoryItemDao.getItemNameByItemId(1);
        assertNotNull(itemName);
        assertEquals("Milk", itemName); // Update string literal to match your seed records
    }
}