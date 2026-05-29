package DTO;

import java.util.List;


public class User {
    private int userId;
    private String userName;
    private String password;
    private String email;
    private List<String> diet;
    private List<String> intolerance;
    private List<Recipe> recipe;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getDietaryRestriction() {
        return diet;
    }

    public void setDietaryRestriction(List<String> dietaryRestriction) {
        this.diet = dietaryRestriction;
    }

    public List<String> getIntolerance() {
        return intolerance;
    }

    public void setIntolerance(List<String> intolerance) {
        this.intolerance = intolerance;
    }

    public List<Recipe> getRecipe() {
        return recipe;
    }

    public void setRecipe(List<Recipe> recipe) {
        this.recipe = recipe;
    }
}
