package com.jc451.team1.dao;

import com.jc451.team1.dto.InventoryItem;
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
        item.setId(1);

        inventoryItemDao.addInventoryItem(item);
        // TODO: include the household id
        List<InventoryItem> newList = inventoryItemDao.getInventory(1);

        assertNotNull(newList);
        assertEquals(6, newList.size());
    }

    @Test
    @DisplayName("Get All Inventory Items Test")
    public void getAllInventoryItemsTest() {
        // TODO: include the household id
        List<InventoryItem> newList = inventoryItemDao.getInventory(1);
        assertNotNull(newList);
        assertEquals(5, newList.size());
    }

    @Test
    @DisplayName("Find An Inventory Item By ID: 5")
    public void findInventoryItemById5Test() {
        InventoryItem item = inventoryItemDao.findInventoryItemById(5);
        assertNotNull(item);
        assertEquals(0.50f, item.getQuantity());
        assertEquals("kg", item.getUnit());
    }

    @Test
    @DisplayName("Update Inventory Item Info")
    public void updateInventoryItemTest() {
        InventoryItem item = new InventoryItem();
        item.setIngredientId(11);
        item.setQuantity(10.0f);
        item.setUnit("Grams");
        item.setExpirationDate(LocalDate.of(2026, 12, 25));
        item.setHouseHoldId(1);
        item.setId(2);

        inventoryItemDao.updateInventoryItem(item);
        // TODO: include the household id
        List<InventoryItem> newList = inventoryItemDao.getInventory(1);
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
        inventoryItemDao.removeInventoryItem(1);
        // TODO: include the household id
        List<InventoryItem> items = inventoryItemDao.getInventory(1);

        assertNotNull(items);
        assertEquals(4, items.size());
    }

    @Test
    @DisplayName("Verify Item Name Retrieval Separately")
    public void getItemNameByItemIdTest() {

        String itemName = inventoryItemDao.getItemNameByItemId(1);
        assertNotNull(itemName);
        assertEquals("Milk", itemName);
    }
}