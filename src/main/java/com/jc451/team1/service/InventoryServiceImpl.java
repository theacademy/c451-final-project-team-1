package com.jc451.team1.service;

import com.jc451.team1.dao.InventoryItemDao;
import com.jc451.team1.dto.InventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryItemDao inventoryItemDao;

    @Autowired
    public InventoryServiceImpl(InventoryItemDao inventoryItemDao) {
        this.inventoryItemDao = inventoryItemDao;
    }

    @Override
    public InventoryItem addInventoryItem(InventoryItem inventoryItem) {
        if (inventoryItem.getIngredientName() == null || inventoryItem.getIngredientName().isBlank()) {
            throw new IllegalArgumentException("Ingredient name cannot be blank");
        }
        if (inventoryItem.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (inventoryItem.getExpirationDate() == null) {
            throw new IllegalArgumentException("Expiration date cannot be null");
        }
        if (inventoryItem.getExpirationDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Cannot add an expired ingredient");
        }
        return inventoryItemDao.addInventoryItem(inventoryItem);
    }

    @Override
    public List<InventoryItem> getAllInventoryItems() {
        return inventoryItemDao.getAllInventoryItems();
    }

    @Override
    public InventoryItem findInventoryItemById(int id) {
        try {
            return inventoryItemDao.findInventoryItemById(id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public void updateInventoryItem(InventoryItem inventoryItem) {
        if (inventoryItem.getIngredientName() == null || inventoryItem.getIngredientName().isBlank()) {
            throw new IllegalArgumentException("Ingredient name cannot be blank");
        }
        if (inventoryItem.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        inventoryItemDao.updateInventoryItem(inventoryItem);
    }

    @Override
    public void removeInventoryItem(int id) {
        inventoryItemDao.removeInventoryItem(id);
    }

    @Override
    public List<InventoryItem> getExpiringItems(int daysThreshold) {
        LocalDate cutoff = LocalDate.now().plusDays(daysThreshold);
        List<InventoryItem> expiring = new ArrayList<>();
        for (InventoryItem item : inventoryItemDao.getAllInventoryItems()) {
            if (item.getExpirationDate() != null
                    && item.getExpirationDate().isBefore(cutoff)) {
                expiring.add(item);
            }
        }
        return expiring;
    }

}
