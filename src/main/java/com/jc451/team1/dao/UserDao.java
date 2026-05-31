package com.jc451.team1.dao;

import com.jc451.team1.dto.Recipe;
import com.jc451.team1.dto.User;

public interface UserDao {

    public User createUser(User user);

    public User getUserById(int id);

    public User getUserByUsername(String username);

    public void updateUser(User user);

    public void addIntolerance(User user, String intolerance);

    public void deleteIntolerance(User user, String intolerance);

    public void addDietaryRestriction(User user, String dietaryRestriction);

    public void deleteDietaryRestriction(User user, String dietaryRestriction);

    public void addRecipe(User user, Recipe recipe);

    public void deleteRecipe(User user, Recipe recipe);
}
