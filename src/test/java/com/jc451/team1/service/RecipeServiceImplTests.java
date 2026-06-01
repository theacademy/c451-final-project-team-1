package com.jc451.team1.service;

import com.jc451.team1.dao.RecipeDao;
import com.jc451.team1.dto.Recipe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RecipeServiceImplTests {

    private RecipeService recipeService;

    public RecipeServiceImplTests() {
        RecipeDao stub = new RecipeDaoStub();
        recipeService = new RecipeServiceImpl(stub);
    }

    @Test
    @DisplayName("Save Recipe Service Test")
    public void saveRecipeTest() {
        Recipe recipe = new Recipe();
        recipe.setTitle("Jollof Rice");
        recipe.setPrepTime(35);
        recipe.setInstruction("Cook the rice");
        Recipe result = recipeService.saveRecipe(recipe, 1);
        assertNotNull(result);
        assertEquals("Jollof Rice", result.getTitle());
        assertTrue(result.getRecipeId() > 0);
    }

    @Test
    @DisplayName("Find Recipe By ID Service Test")
    public void findRecipeByIdTest() {
        Recipe result = recipeService.findRecipeById(1);
        assertNotNull(result);
        assertEquals("Jollof Rice", result.getTitle());
    }

    @Test
    @DisplayName("Find Recipe By Invalid ID Returns Null")
    public void findRecipeByInvalidIdTest() {
        Recipe result = recipeService.findRecipeById(99);
        assertNull(result);
    }

    @Test
    @DisplayName("Get Saved Recipes By User ID Service Test")
    public void getSavedRecipesByUserIdTest() {
        List<Recipe> result = recipeService.getSavedRecipesByUserId(1);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Fettuccine", result.get(0).getTitle());
    }

    @Test
    @DisplayName("Remove Saved Recipe Service Test")
    public void removeSavedRecipeTest() {
        assertDoesNotThrow(() -> recipeService.unsaveRecipe(1, 1));
    }
}