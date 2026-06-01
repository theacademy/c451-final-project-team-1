package com.jc451.team1.service;

import com.jc451.team1.dao.InventoryItemDao;
import com.jc451.team1.dto.InventoryItem;
import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InventoryItemDaoStub implements InventoryItemDao {

    @Override
    public InventoryItem addInventoryItem(InventoryItem inventoryItem) {
        inventoryItem.setId(2);
        return inventoryItem;
    }

    @Override
    public List<InventoryItem> getInventory(int householdId) {
        InventoryItem item1 = new InventoryItem();
        item1.setId(1);
        item1.setIngredientName("Milk");
        item1.setQuantity(2);
        item1.setUnit("Liters");
        item1.setExpirationDate(LocalDate.of(2026, 6, 7));

        InventoryItem item2 = new InventoryItem();
        item2.setId(2);
        item2.setIngredientName("Corn Starch");
        item2.setQuantity(1);
        item2.setUnit("kg");
        item2.setExpirationDate(LocalDate.of(2027, 1, 1));

        return new ArrayList<>(List.of(item1, item2));
    }

    @Override
    public InventoryItem findInventoryItemById(int id) {
        if (id == 1) {
            InventoryItem item = new InventoryItem();
            item.setId(1);
            item.setIngredientName("Milk");
            item.setQuantity(2);
            item.setUnit("Liters");
            item.setExpirationDate(LocalDate.of(2026, 6, 7));
            return item;
        }
        throw new DataAccessException("Item not found") {};
    }

    @Override
    public void updateInventoryItem(InventoryItem inventoryItem) {
        // stub, does nothing
    }

    @Override
    public void removeInventoryItem(int id) {
        // stub, does nothing
    }

    @Override
    public String getItemNameByItemId(int itemId) {
        return null;
    }
}
