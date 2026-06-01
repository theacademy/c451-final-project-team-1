package com.jc451.team1.dao;

import com.jc451.team1.App;
import com.jc451.team1.dto.Recipe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = App.class)
public class RecipeDaoImplTests {

    private JdbcTemplate jdbcTemplate;
    private RecipeDao recipeDao;

    @Autowired
    public void RecipeDaoImplTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.recipeDao = new RecipeDaoImpl(jdbcTemplate);
    }

    @Test
    @DisplayName("Find A Recipe By ID: 1 (Chicken Stir Fry)")
    public void findRecipeById1Test() {

        Recipe recipe = recipeDao.findRecipeById(1);
        assertNotNull(recipe, "Recipe with ID 1 should exist");
        assertEquals(1, recipe.getRecipeId());
        assertEquals("Chicken Stir Fry", recipe.getTitle());
        assertEquals(35, recipe.getPrepTime());
        assertTrue(recipe.getInstruction().contains("Marinate chicken"));
        assertEquals("https://example.com/images/chicken-stir-fry.jpg", recipe.getImage());
    }

    @Test
    @DisplayName("Get Saved Recipes By User ID: 1")
    public void getSavedRecipesByUserIdTest() {

        List<Recipe> savedRecipes = recipeDao.getSavedRecipesByUserId(1);

        assertNotNull(savedRecipes, "The saved recipe list should not be null");
        assertEquals(3, savedRecipes.size(), "Alice (User 1) should have exactly 3 saved recipes");

        assertEquals("Chicken Stir Fry", savedRecipes.get(0).getTitle());
    }

    @Test
    @DisplayName("Save New Recipe and Link to User Test")
    public void saveRecipeTest() {
        // Arrange
        Recipe newRecipe = new Recipe();
        newRecipe.setTitle("Homemade Tacos");
        newRecipe.setPrepTime(20);
        newRecipe.setInstruction("Season ground beef, warm up tortillas, assemble with cheese.");
        newRecipe.setImage("https://example.com/images/tacos.jpg");

        int userIdForTest = 2;

        Recipe savedRecipe = recipeDao.saveRecipe(newRecipe, userIdForTest);
        List<Recipe> bobsRecipes = recipeDao.getSavedRecipesByUserId(userIdForTest);

        assertNotNull(savedRecipe, "The saved recipe instance should not be null");
        assertTrue(savedRecipe.getRecipeId() > 0, "The database should generate an auto-increment ID");
        assertEquals("Homemade Tacos", savedRecipe.getTitle());

        assertEquals(3, bobsRecipes.size(), "Bob's saved recipe count should increase to 3");
    }

    @Test
    @DisplayName("Remove Saved Recipe Connection Link")
    public void removeSavedRecipeTest() {
        List<Recipe> emmasRecipesBefore = recipeDao.getSavedRecipesByUserId(5);
        assertEquals(3, emmasRecipesBefore.size(), "Emma should start with 3 saved recipes");

        recipeDao.removeSavedRecipe(5, 4);
        List<Recipe> emmasRecipesAfter = recipeDao.getSavedRecipesByUserId(5);

        assertEquals(2, emmasRecipesAfter.size(), "Emma's saved recipe list should drop down to 2 items");

        for (Recipe r : emmasRecipesAfter) {
            assertNotEquals(4, r.getRecipeId(), "Recipe 4 should no longer be present in Emma's list");
        }
    }
}