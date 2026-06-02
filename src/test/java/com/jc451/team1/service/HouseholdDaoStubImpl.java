package com.jc451.team1.service;

import com.jc451.team1.dao.HouseholdDao;
import com.jc451.team1.dto.Household;
import com.jc451.team1.dto.InventoryItem;
import com.jc451.team1.dto.User;

import java.util.List;

public class HouseholdDaoStubImpl implements HouseholdDao {

    public Household household;

    public HouseholdDaoStubImpl() {
        household = new Household();
        household.setHouseholdId(1);
        household.setHouseholdName("TestName");
        household.setAddress("testAddress");
        household.setCode("TestCode");
    }

    @Override
    public Household createHousehold(Household household) {
        return null;
    }

    @Override
    public Household getHouseholdById(int id) {
        return null;
    }

    @Override
    public Household getHouseholdByCode(String householdCode) {
        return null;
    }

    @Override
    public Household getHouseholdFromUser(int userId) {
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
