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
        boolean error = household.getCode().length() != 8
                || household.getHouseholdName().isBlank();

        if (error) {
            household.setHouseholdId(-1);
        } else {
            household = householdDao.createHousehold(household);
        }

        return household;
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
    public boolean addUserToHousehold(int userId, int householdId) {
        if (getHousehold(householdId) == null) {
            return false;
        }

        householdDao.addUserToHousehold(userId, householdId);
        return true;
    }

    @Override
    public boolean removeUserFromHousehold(int userId, int householdId) {
        if (getHousehold(householdId) == null) return false;

        if (getMembers(householdId).stream()
                .noneMatch(user -> user.getUserId() == userId))
            return false;

        householdDao.removeUserFromHousehold(userId, householdId);
        return true;
    }

    @Override
    public List<InventoryItem> getInventoryItems(int householdId) {
        return householdDao.getInventoryItems(householdId);
    }

    @Override
    public List<User> getMembers(int householdId) {
        return householdDao.getAllUsers(householdId);
    }
}
