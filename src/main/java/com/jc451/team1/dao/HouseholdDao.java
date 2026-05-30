package com.jc451.team1.dao;


import com.jc451.team1.dto.Household;
import com.jc451.team1.dto.InventoryItem;
import com.jc451.team1.dto.User;

import java.util.List;

public interface HouseholdDao {

    public Household createHousehold(Household household);

    public Household getHousehold(int id);

    public void addUserToHousehold(User user);

    public User removeUserFromHousehold(int userId);

    public List<InventoryItem> getInventoryItems();

    public List<User> getAllUsers();
}
