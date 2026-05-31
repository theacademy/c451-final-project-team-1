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

        // Assert - Matching row #1 from your data.sql script
        assertNotNull(recipe, "Recipe with ID 1 should exist");
        assertEquals(1, recipe.getRecipeId()); // Matches your setRecipeId/getRecipeId naming
        assertEquals("Chicken Stir Fry", recipe.getTitle());
        assertEquals(35, recipe.getPrepTime()); // Matches your getPrepTime()
        assertTrue(recipe.getInstruction().contains("Marinate chicken")); // Matches your getInstruction()
        assertEquals("https://example.com/images/chicken-stir-fry.jpg", recipe.getImage()); // Matches your getImage()
    }

    @Test
    @DisplayName("Get Saved Recipes By User ID: 1")
    public void getSavedRecipesByUserIdTest() {
        // Act - Alice (User 1) has 3 saved recipes in your data.sql (recipes 1, 3, and 8)
        List<Recipe> savedRecipes = recipeDao.getSavedRecipesByUserId(1);

        // Assert
        assertNotNull(savedRecipes, "The saved recipe list should not be null");
        assertEquals(3, savedRecipes.size(), "Alice (User 1) should have exactly 3 saved recipes");

        // Quick verification of one of the items inside her list
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

        int userIdForTest = 2; // Let's link it to Bob (User 2)

        // Act
        Recipe savedRecipe = recipeDao.saveRecipe(newRecipe, userIdForTest);
        List<Recipe> bobsRecipes = recipeDao.getSavedRecipesByUserId(userIdForTest);

        // Assert
        assertNotNull(savedRecipe, "The saved recipe instance should not be null");
        assertTrue(savedRecipe.getRecipeId() > 0, "The database should generate an auto-increment ID");
        assertEquals("Homemade Tacos", savedRecipe.getTitle());

        // Bob started with 2 saved recipes (5 and 6). Adding this should bring his count to 3.
        assertEquals(3, bobsRecipes.size(), "Bob's saved recipe count should increase to 3");
    }

    @Test
    @DisplayName("Remove Saved Recipe Connection Link")
    public void removeSavedRecipeTest() {
        // Arrange - User 5 (Emma) has recipe 4 saved in your seed data.sql script
        List<Recipe> emmasRecipesBefore = recipeDao.getSavedRecipesByUserId(5);
        assertEquals(3, emmasRecipesBefore.size(), "Emma should start with 3 saved recipes");

        // Act - Remove recipe 4 from Emma's saved list
        recipeDao.removeSavedRecipe(5, 4);
        List<Recipe> emmasRecipesAfter = recipeDao.getSavedRecipesByUserId(5);

        // Assert
        assertEquals(2, emmasRecipesAfter.size(), "Emma's saved recipe list should drop down to 2 items");

        // Make sure recipe 4 is no longer anywhere in her remaining list
        for (Recipe r : emmasRecipesAfter) {
            assertNotEquals(4, r.getRecipeId(), "Recipe 4 should no longer be present in Emma's list");
        }
    }
}