package com.jc451.team1.service;

import com.jc451.team1.dto.Recipe;
import com.jc451.team1.dto.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User getUserById(int id);

    User getUserByUsername(String username); // for login

    List<User> getUsersPerHousehold(int householdId);

    List<User> getAllUsers();

    void updateUser(User user);

    void addIntolerance(User user, int intoleranceId);

    void deleteIntolerance(User user, String intolerance);

    void addDietaryRestriction(User user,  int dietId);

    void deleteDietaryRestriction(User user, String diet);

    void addRecipe(User user, Recipe recipe);

    void deleteRecipe(User user, Recipe recipe);
}
