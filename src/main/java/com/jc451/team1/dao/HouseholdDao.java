package com.jc451.team1.dao;


import com.jc451.team1.dto.Household;
import com.jc451.team1.dto.InventoryItem;
import com.jc451.team1.dto.User;

import java.util.List;

public interface HouseholdDao {

    Household createHousehold(Household household);

    Household getHouseholdById(int id);

    Household getHouseholdByCode(String householdCode);

    Household getHouseholdFromUser(int userId);

    void addUserToHousehold(int userId, int householdId);

    void removeUserFromHousehold(int userId, int householdId);

    List<InventoryItem> getInventoryItems(int householdId);

    List<User> getAllUsers(int householdId);
}
