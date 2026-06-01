package com.jc451.team1.service;

import com.jc451.team1.dao.UserDao;
import com.jc451.team1.dao.UserDaoImpl;
import com.jc451.team1.dto.Recipe;
import com.jc451.team1.dto.User;

import java.util.List;
import java.util.Map;

public class UserDaoStubImpl implements UserDao {

    public User user;

    public UserDaoStubImpl(){
        user.setUserId(1);
        user.setUserName("TestName");
        user.setPassword("TestPasword");
        user.setEmail("Test@Test.com");
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

        return user;
    }

    @Override
    public User getUserById(int id) {
        if (user.getUserId() == id){
            return user;
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        // Unimplemented method
        return null;
    }

    @Override
    public User authenticate(String username, String password) {
        if (user.getUserName().equals(username) && user.getPassword().equals(password)){
            return user;
        }
        return null;
    }

    @Override
    public void changePassword(int userId, String newPassword) {
        if (user.getUserId() == userId){
            user.setPassword(newPassword);
        }
    }

    @Override
    public void updateUser(User user) {
        user.setUserId(user.getUserId());
        user.setUserName(user.getUserName());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
    }

    @Override
    public void addIntolerance(User user, int intoleranceId) {

    }

    @Override
    public void deleteIntolerance(User user, String intolerance) {

    }

    @Override
    public void addDietaryRestriction(User user, int dietaryRestrictionId) {

    }

    @Override
    public void deleteDietaryRestriction(User user, String dietaryRestriction) {

    }

    @Override
    public void addRecipe(User user, Recipe recipe) {

    }

    @Override
    public void deleteRecipe(User user, Recipe recipe) {

    }

    @Override
    public List<Map<String, Object>> getAllDiets() {
        return List.of();
    }

    @Override
    public List<Map<String, Object>> getAllIntolerances() {
        return List.of();
    }
}
