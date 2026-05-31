package com.jc451.team1.service;

import com.jc451.team1.dto.Recipe;

import java.util.List;

public interface RecipeService {

    Recipe saveRecipe(Recipe recipe, int userId);

    Recipe findRecipeById(int id);

    List<Recipe> getSavedRecipesByUserId(int userId);

    void removeSavedRecipe(int userId, int recipeId);

}
