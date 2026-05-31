package com.jc451.team1.dao;

import com.jc451.team1.dto.Recipe;
import com.jc451.team1.dto.User;

public interface UserDao {

    User createUser(User user);

    User getUserById(int id);

    User getUserByUsername(String username);

    User authenticate(String username, String password);

    void changePassword(int userId, String newPassword);

    void updateUser(User user);

    void addIntolerance(User user, int intoleranceId);

    void deleteIntolerance(User user, String intolerance);

    void addDietaryRestriction(User user, int dietaryRestrictionId);

    void deleteDietaryRestriction(User user, String dietaryRestriction);

    void addRecipe(User user, Recipe recipe);

    void deleteRecipe(User user, Recipe recipe);
}
