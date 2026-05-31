package com.jc451.team1.service;

import com.jc451.team1.dao.HouseholdDao;
import com.jc451.team1.dto.Household;
import com.jc451.team1.dto.InventoryItem;
import com.jc451.team1.dto.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HouseholdServiceImpl implements HouseholdService {

    private final HouseholdDao householdDao;

    @Autowired
    public HouseholdServiceImpl(HouseholdDao householdDao) {
        this.householdDao = householdDao;
    }

    @Override
    public Household createHousehold(Household household) {
        boolean error = false;

        if (household.getCode().length() != 8) {
            household.setCode("Invalid code format.");
            error = true;
        }

        return error? household : householdDao.createHousehold(household);
    }

    @Override
    public Household getHousehold(int id) {
        return householdDao.getHouseholdById(id);
    }

    @Override
    public Household getHouseholdFromUser(int userId) {
        return null;
    }

    @Override
    public void addUserToHousehold(int userId, int householdId) {
        householdDao.addUserToHousehold(userId, householdId);
    }

    @Override
    public void removeUserFromHousehold(int userId, int householdId) {
        householdDao.removeUserFromHousehold(userId, householdId);
    }

    @Override
    public List<InventoryItem> getInventoryItems(int householdId) {
        return householdDao.getInventoryItems(householdId);
    }

    @Override
    public List<User> getAllUsers(int householdId) {
        return List.of();
    }
}
