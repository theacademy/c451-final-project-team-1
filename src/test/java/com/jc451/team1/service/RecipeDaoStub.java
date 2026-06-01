package com.jc451.team1.service;

import com.jc451.team1.dao.RecipeDao;
import com.jc451.team1.dto.Recipe;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;

public class RecipeDaoStub implements RecipeDao {

    @Override
    public Recipe saveRecipe(Recipe recipe, int userId) {
        recipe.setRecipeId(1);
        return recipe;
    }

    @Override
    public Recipe findRecipeById(int id) {
        if (id == 1) {
            Recipe recipe = new Recipe();
            recipe.setRecipeId(1);
            recipe.setTitle("Jollof Rice");
            recipe.setPrepTime(35);
            return recipe;
        }
        throw new DataAccessException("Recipe not found") {};
    }

    @Override
    public List<Recipe> getSavedRecipesByUserId(int userId) {
        Recipe recipe1 = new Recipe();
        recipe1.setRecipeId(1);
        recipe1.setImage("Img 1");
        recipe1.setInstruction("Instruction 1");
        recipe1.setTitle("Fettuccine");


        Recipe recipe2 = new Recipe();
        recipe2.setRecipeId(2);
        recipe2.setImage("Img 2");
        recipe2.setInstruction("Instruction 2");
        recipe2.setTitle("Linguine");


        return new ArrayList<>(List.of(recipe1, recipe2));
    }

    @Override
    public void removeSavedRecipe(int userId, int recipeId) {
        // stub, does nothing
    }


}


