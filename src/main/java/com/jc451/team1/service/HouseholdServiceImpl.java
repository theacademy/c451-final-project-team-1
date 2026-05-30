package com.jc451.team1.service;

import com.jc451.team1.dto.Household;
import com.jc451.team1.dto.InventoryItem;
import com.jc451.team1.dto.User;

import java.util.List;

public class HouseholdServiceImpl implements HouseholdServiceInterface {
    @Override
    public void createHousehold(Household household) {

    }

    @Override
    public Household getHousehold(int id) {
        return null;
    }

    @Override
    public void addUserToHousehold(int userId, int householdId) {

    }

    @Override
    public void removeUserFromHousehold(int userId, int householdId) {

    }

    @Override
    public List<InventoryItem> getInventoryItems(int householdId) {
        return List.of();
    }

    @Override
    public List<User> getAllUsers(int householdId) {
        return List.of();
    }
}
