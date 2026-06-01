package com.jc451.team1.service;

import com.jc451.team1.dao.InventoryItemDao;
import com.jc451.team1.dto.InventoryItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryItemServiceImplTests {

    private InventoryService inventoryService;

    public InventoryItemServiceImplTests() {
        InventoryItemDao stub = new InventoryItemDaoStub();
        inventoryService = new InventoryServiceImpl(stub);
    }

    @Test
    @DisplayName("Add Inventory Item Service Test")
    public void addInventoryItemTest() {
        InventoryItem item = new InventoryItem();
        item.setIngredientName("Eggs");
        item.setQuantity(12);
        item.setUnit("pieces");
        item.setExpirationDate(LocalDate.of(2027, 1, 1));
        item.setHouseHoldId(1);
        InventoryItem result = inventoryService.addInventoryItem(item);
        assertNotNull(result);
        assertEquals("Eggs", result.getIngredientName());
    }

    @Test
    @DisplayName("Add Item Blank Name Returns Invalid")
    public void addItemBlankNameTest() {
        InventoryItem item = new InventoryItem();
        item.setIngredientName("");
        item.setQuantity(12);
        item.setUnit("pieces");
        item.setExpirationDate(LocalDate.of(2027, 1, 1));
        InventoryItem result = inventoryService.addInventoryItem(item);
        assertEquals(-1, result.getId());
    }

    @Test
    @DisplayName("Add Item Zero Quantity Returns Invalid")
    public void addItemZeroQuantityTest() {
        InventoryItem item = new InventoryItem();
        item.setIngredientName("Eggs");
        item.setQuantity(0);
        item.setUnit("pieces");
        item.setExpirationDate(LocalDate.of(2027, 1, 1));
        InventoryItem result = inventoryService.addInventoryItem(item);
        assertEquals(-1, result.getId());
    }

    @Test
    @DisplayName("Add Item Expired Date Returns Invalid")
    public void addItemExpiredDateTest() {
        InventoryItem item = new InventoryItem();
        item.setIngredientName("Eggs");
        item.setQuantity(12);
        item.setUnit("pieces");
        item.setExpirationDate(LocalDate.of(2020, 1, 1));
        InventoryItem result = inventoryService.addInventoryItem(item);
        assertEquals(-1, result.getId());
    }

    @Test
    @DisplayName("Find Item By ID Service Test")
    public void findItemByIdTest() {
        InventoryItem result = inventoryService.findInventoryItemById(1);
        assertNotNull(result);
        assertEquals("Milk", result.getIngredientName());
    }

    @Test
    @DisplayName("Find Item By Invalid ID Returns Null")
    public void findItemByInvalidIdTest() {
        InventoryItem result = inventoryService.findInventoryItemById(99);
        assertNull(result);
    }

    @Test
    @DisplayName("Get All Inventory Items Service Test")
    public void getAllInventoryItemsTest() {
        List<InventoryItem> result = inventoryService.getAllInventoryItems();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Get Expiring Items Service Test")
    public void getExpiringItemsTest() {
        List<InventoryItem> result = inventoryService.getExpiringItems(7);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Milk", result.get(0).getIngredientName());
    }
}