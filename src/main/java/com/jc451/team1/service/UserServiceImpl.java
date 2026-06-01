package com.jc451.team1.service;

import com.jc451.team1.dao.UserDao;
import com.jc451.team1.dto.Recipe;
import com.jc451.team1.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User createUser(User user) {
        final String EMAIL_REGEX_EXPRESSION = "[a-zA-Z0-9]+@[a-zA-z]+.[a-zA-z]+";

        if (user.getUserName().isBlank()){
            user.setUserName("Invalid Username, user was not created");
            user.setUserId(-1);
            return user;
        }

        // If it's not blank, use regex to check for proper email format
        if (user.getPassword().isBlank() && user.getPassword().matches(EMAIL_REGEX_EXPRESSION)){
            user.setUserName("Invalid Password, user was not created");
            user.setUserId(-1);
            return user;
        }

        if (user.getEmail().isBlank()){
            user.setUserName("Invalid Email, user was not created");
            user.setUserId(-1);
            return user;
        }

        return userDao.createUser(user);
    }

    @Override
    public User getUserById(int id) {
        User user = new User();

        try {
            user = userDao.getUserById(id);
        }
        catch(DataAccessException e){
            user.setUserName("User not found");
            user.setPassword("User not found");
            user.setEmail("User not found");
        }

        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
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

    @Override
    public List<Map<String, Object>> getAllDiets() {
        return userDao.getAllDiets();
    }

    @Override
    public List<Map<String, Object>> getAllIntolerances() {
        return userDao.getAllIntolerances();
    }
}
