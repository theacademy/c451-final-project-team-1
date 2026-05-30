package com.jc451.team1.dao;


import com.jc451.team1.dto.Household;
import com.jc451.team1.dto.InventoryItem;
import com.jc451.team1.dto.User;

import java.util.List;

interface HouseholdDao {

    Household createHousehold(Household household);

    Household getHousehold(int id);

    void addUserToHousehold(User user);

    User removeUserFromHousehold(int userId);

    List<InventoryItem> getInventoryItems();

    List<User> getAllUsers();
}
