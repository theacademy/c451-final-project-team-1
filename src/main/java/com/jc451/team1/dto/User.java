package com.jc451.team1.dto;

import java.util.List;
import java.util.Map;
import java.util.Objects;


public class User {
    private int userId;
    private String userName;
    private String password;
    private String email;
    private List<String> diets;
    private List<String> intolerances;
    private List<Recipe> recipes;

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

    public List<String> getDietaryRestrictions() {
        return diets;
    }

    public void setDietaryRestrictions(List<String> dietaryRestriction) {
        this.diets = dietaryRestriction;
    }

    public List<String> getIntolerances() {
        return intolerances;
    }

    public void setIntolerances(List<String> intolerance) {
        this.intolerances = intolerance;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(diets, user.diets) && Objects.equals(intolerances, user.intolerances) && Objects.equals(recipes, user.recipes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, password, email, diets, intolerances, recipes);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", diet=" + diets +
                ", intolerance=" + intolerances +
                ", recipe=" + recipes +
                '}';
    }
}
