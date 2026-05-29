package DTO;

import java.util.List;
import java.util.Objects;


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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(diet, user.diet) && Objects.equals(intolerance, user.intolerance) && Objects.equals(recipe, user.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, password, email, diet, intolerance, recipe);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", diet=" + diet +
                ", intolerance=" + intolerance +
                ", recipe=" + recipe +
                '}';
    }
}
