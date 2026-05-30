package com.jc451.team1.service;

import com.jc451.team1.dto.Recipe;

import java.util.List;

public class RecipeServiceImpl implements RecipeServiceInterface {
    @Override
    public List<Recipe> searchRecipe(String query) {
        return List.of();
    }

    @Override
    public Recipe findRecipe(int id) {
        return null;
    }

    @Override
    public int getPrepTime(int recipeId) {
        return 0;
    }

    @Override
    public List<String> getInstructions(int recipeId) {
        return List.of();
    }

    @Override
    public void saveRecipe(Recipe recipe) {

    }

    @Override
    public void unsaveRecipe(Recipe recipe) {

    }
}
