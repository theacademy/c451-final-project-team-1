package com.jc451.team1.dao;

import com.jc451.team1.dto.InventoryItem;

import java.util.List;

public interface InventoryItemDao {

    InventoryItem addInventoryItem(InventoryItem inventoryItem);

    List<InventoryItem> getInventory(int householdId);

    InventoryItem findInventoryItemById(int id);

    void updateInventoryItem(InventoryItem inventoryItem);

    void removeInventoryItem(int id);

    String getItemNameByItemId(int itemId);
}
