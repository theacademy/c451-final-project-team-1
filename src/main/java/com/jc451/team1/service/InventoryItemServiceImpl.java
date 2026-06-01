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
public class InventoryItemServiceImpl implements InventoryItemService {

    private final InventoryItemDao inventoryItemDao;

    @Autowired
    public InventoryItemServiceImpl(InventoryItemDao inventoryItemDao) {
        this.inventoryItemDao = inventoryItemDao;
    }

    @Override
    public InventoryItem addInventoryItem(InventoryItem item) {
        boolean error = item.getIngredientName().isBlank()
                || item.getQuantity() <= 0
                || item.getExpirationDate().isBefore(LocalDate.now());

        if (error) item.setId(-1);
        else item = inventoryItemDao.addInventoryItem(item);

        return item;
    }

    @Override
    public List<InventoryItem> getAllInventoryItems(int householdId) {
        return inventoryItemDao.getInventory(householdId);
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
    public boolean updateInventoryItem(InventoryItem item) {
        InventoryItem inventoryItem =
                inventoryItemDao.findInventoryItemById(item.getId());
        if (inventoryItem == null) return false;

        boolean error = item.getIngredientName().isBlank()
                || item.getQuantity() <= 0;
        if (error) return false;

        inventoryItemDao.updateInventoryItem(item);
        return true;
    }

    @Override
    public void removeInventoryItem(int id) {
        inventoryItemDao.removeInventoryItem(id);
    }

    @Override
    public List<InventoryItem> getExpiringItems(int householdId, int daysThreshold) {
        LocalDate cutoff = LocalDate.now().plusDays(daysThreshold);
        List<InventoryItem> expiring = new ArrayList<>();
        for (InventoryItem item : inventoryItemDao.getInventory(householdId)) {
            if (item.getExpirationDate() != null
                    && item.getExpirationDate().isBefore(cutoff)) {
                expiring.add(item);
            }
        }
        return expiring;
    }

}
