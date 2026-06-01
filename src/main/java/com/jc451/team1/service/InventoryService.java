package com.jc451.team1.service;

import com.jc451.team1.dto.InventoryItem;
import java.util.List;

public interface InventoryService {

    InventoryItem addInventoryItem(InventoryItem inventoryItem);

    List<InventoryItem> getAllInventoryItems();

    InventoryItem findInventoryItemById(int id);

    boolean updateInventoryItem(InventoryItem inventoryItem);

    void removeInventoryItem(int id);

    List<InventoryItem> getExpiringItems(int daysThreshold);
}