package com.jc451.team1.DAO;

import com.jc451.team1.DTO.Recipe;

import java.util.List;

public interface RecipeDao {

    Recipe saveRecipe(Recipe recipe, int userId);

    Recipe findRecipeById(int id);

    List<Recipe> getSavedRecipesByUserId(int userId);

    void removeSavedRecipe(int userId, int recipeId);

}
