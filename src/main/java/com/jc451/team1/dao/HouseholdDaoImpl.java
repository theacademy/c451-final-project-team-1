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
    public void addUserToHousehold(User user) {

    }

    @Override
    public User removeUserFromHousehold(int userId) {
        return null;
    }

    @Override
    public List<InventoryItem> getInventoryItems() {
        return List.of();
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }
}
