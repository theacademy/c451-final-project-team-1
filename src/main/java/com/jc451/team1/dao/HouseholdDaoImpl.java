package com.jc451.team1.dao;

import com.jc451.team1.dto.Household;
import com.jc451.team1.dto.InventoryItem;
import com.jc451.team1.dto.User;

import java.util.List;

public class HouseholdDaoImpl implements HouseholdDao {
    @Override
    public Household createHousehold(Household household) {
        return null;
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
    public List<User> getAllUsers() {
        return List.of();
    }
}
