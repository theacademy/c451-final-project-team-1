package com.jc451.team1.service;

import com.jc451.team1.dto.Household;
import com.jc451.team1.dto.InventoryItem;
import com.jc451.team1.dto.User;

import java.util.List;

public interface HouseholdServiceInterface {
    void createHousehold(Household household);

    Household getHousehold(int id);

    void addUserToHousehold(int userId, int householdId);

    void removeUserFromHousehold(int userId, int householdId);

    List<InventoryItem> getInventoryItems(int householdId);

    List<User> getAllUsers(int householdId);
}
