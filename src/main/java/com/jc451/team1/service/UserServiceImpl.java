package com.jc451.team1.service;

import com.jc451.team1.dao.UserDao;
import com.jc451.team1.dto.Recipe;
import com.jc451.team1.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

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
        return userDao.getUserByUsername(username);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void addIntolerance(User user, int intoleranceId) {
        userDao.addIntolerance(user, intoleranceId);
    }

    @Override
    public void deleteIntolerance(User user, String intolerance) {
        userDao.deleteIntolerance(user, intolerance);
    }

    @Override
    public void addDietaryRestriction(User user, int dietId) {
        userDao.addDietaryRestriction(user, dietId);
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

    @Override
    public User authenticate(String username, String password) {
        return userDao.authenticate(username, password);
    }
}
