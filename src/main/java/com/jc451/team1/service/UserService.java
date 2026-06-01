package com.jc451.team1.service;

import com.jc451.team1.dto.Recipe;
import com.jc451.team1.dto.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User createUser(User user);

    User getUserById(int id);

    User getUserByUsername(String username);

    void updateUser(User user);

    void addIntolerance(User user, int intoleranceId);

    void deleteIntolerance(User user, String intolerance);

    void addDietaryRestriction(User user,  int dietId);

    void deleteDietaryRestriction(User user, String diet);

    void addRecipe(User user, Recipe recipe);

    void deleteRecipe(User user, Recipe recipe);

    User authenticate(String username, String password);

    List<Map<String, Object>> getAllDiets();

    List<Map<String, Object>> getAllIntolerances();

    Integer getHouseholdIdByUserId(int userId);
}
