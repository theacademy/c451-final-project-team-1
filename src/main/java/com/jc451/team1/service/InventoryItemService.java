package com.jc451.team1.service;

import com.jc451.team1.dto.InventoryItem;
import java.util.List;

public interface InventoryItemService {

    InventoryItem addInventoryItem(InventoryItem inventoryItem);

    List<InventoryItem> getAllInventoryItems(int householdId);

    InventoryItem findInventoryItemById(int id);

    boolean updateInventoryItem(InventoryItem inventoryItem);

    void removeInventoryItem(int id);

    List<InventoryItem> getExpiringItems(int householdId, int daysThreshold);

    int getOrCreateIngredientId(String name);
}