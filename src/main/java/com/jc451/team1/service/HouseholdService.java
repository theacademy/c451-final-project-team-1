package com.jc451.team1.service;

import com.jc451.team1.dto.Household;
import com.jc451.team1.dto.InventoryItem;
import com.jc451.team1.dto.User;

import java.util.List;

public interface HouseholdService {
    Household createHousehold(Household household);

    Household getHousehold(int id);

    Household getHouseholdFromUser(int userId);

    boolean addUserToHousehold(int userId, int householdId);

    boolean removeUserFromHousehold(int userId, int householdId);

    List<InventoryItem> getInventoryItems(int householdId);

    List<User> getMembers(int householdId);
}
