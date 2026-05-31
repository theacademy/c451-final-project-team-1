package com.jc451.team1.DAO;

import com.jc451.team1.DTO.InventoryItem;

import java.util.List;

public interface InventoryItemDao {

    InventoryItem addInventoryItem(InventoryItem inventoryItem);

    List<InventoryItem> getAllInventoryItems();

    InventoryItem findInventoryItemById(int id);

    void updateInventoryItem(InventoryItem inventoryItem);

    void removeInventoryItem(int id);

    String getItemNameByItemId(int itemId);
}
