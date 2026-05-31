package com.jc451.team1.service;

import com.jc451.team1.dao.UserDao;
import com.jc451.team1.dto.Recipe;
import com.jc451.team1.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserServiceInterface {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User createUser(User user) {
        return userDao.createUser(user);
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username); // for login
    }

    @Override
    public List<User> getUsersPerHousehold(int householdId) { // TODO move this to HouseHoldServiceImpl
        return List.of();
    }

    @Override
    public List<User> getAllUsers() { // TODO move this to HouseHoldServiceImpl
        return List.of();
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void addIntolerance(User user, String intolerance) {
        userDao.addIntolerance(user, intolerance);
    }

    @Override
    public void deleteIntolerance(User user, String intolerance) {
        userDao.deleteIntolerance(user, intolerance);
    }

    @Override
    public void addDietaryRestriction(User user, String diet) {
        userDao.addDietaryRestriction(user, diet);
    }

    @Override
    public void deleteDietaryRestriction(User user, String diet) {
        userDao.deleteDietaryRestriction(user, diet);
    }

    @Override
    public void addRecipe(User user, Recipe recipe) {
        userDao.addRecipe(user, recipe);
    }

    @Override
    public void deleteRecipe(User user, Recipe recipe) {
        userDao.deleteRecipe(user, recipe);
    }
}
