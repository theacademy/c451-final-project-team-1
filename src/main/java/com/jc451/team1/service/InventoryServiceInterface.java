package com.jc451.team1.service;

import com.jc451.team1.dto.InventoryItem;

import java.util.List;

public interface InventoryServiceInterface {
    void addInventoryItem(InventoryItem item);

    InventoryItem getInventoryItem(int id);

    void updateInventoryItem(InventoryItem item);

    List<InventoryItem> getExpiringInventoryItem();

    void removeInventoryItem(int id);
}
