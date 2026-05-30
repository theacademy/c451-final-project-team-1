package com.jc451.team1.service;

import com.jc451.team1.dto.Recipe;

import java.util.List;

public interface RecipeServiceInterface {
    List<Recipe> searchRecipe(String query);

    Recipe findRecipe(int id);

    int getPrepTime(int recipeId); // NECESSARY?

    List<String> getInstructions(int recipeId);

    void saveRecipe(Recipe recipe);

    void unsaveRecipe(Recipe recipe);
}
