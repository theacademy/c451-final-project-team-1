package com.jc451.team1.service;

import com.jc451.team1.dto.User;

import java.util.List;

public interface UserServiceInterface {
    void createNewUser(User user);

    User getUser(int id);

    List<User> getUsersPerHousehold(int householdId);

    List<User> getAllUsers();

    void updateUser(User user);
}
