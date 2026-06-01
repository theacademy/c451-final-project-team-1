package com.jc451.team1.dao;

import com.jc451.team1.App;
import com.jc451.team1.dto.Recipe;
import com.jc451.team1.dto.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = App.class)
public class UserDaoImplTests {

    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;

    @Autowired
    public void UserDaoImplTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = new UserDaoImpl(jdbcTemplate);
    }

    @Test
    @DisplayName("Get User By ID: 1 (Alice) with Hydrated Profiles")
    public void getUserByIdTest() {

        User user = userDao.getUserById(1);

        assertNotNull(user, "User #1 should exist");
        assertEquals(1, user.getUserId());
        assertEquals("alice", user.getUserName());
        assertEquals("password123", user.getPassword());
        assertEquals("alice@example.com", user.getEmail());

        assertEquals(2, user.getDietaryRestrictions().size());
        assertTrue(user.getDietaryRestrictions().contains("Vegetarian"));

        assertEquals(2, user.getIntolerances().size());
        assertTrue(user.getIntolerances().contains("Milk"));

        assertEquals(3, user.getRecipes().size());
        assertEquals("Chicken Stir Fry", user.getRecipes().get(0).getTitle());
    }

    @Test
    @DisplayName("Create New User Profile Test")
    public void createUserTest() {

        User newUser = new User();
        newUser.setUserName("frank");
        newUser.setPassword("pass2026");
        newUser.setEmail("frank@example.com");

        User savedUser = userDao.createUser(newUser);
        User retrieved = userDao.getUserById(savedUser.getUserId());

        assertNotNull(savedUser, "Saved user should not be null");
        assertTrue(savedUser.getUserId() > 0, "Database should generate an auto-increment ID");
        assertNotNull(retrieved);
        assertEquals("frank", retrieved.getUserName());
        assertEquals("frank@example.com", retrieved.getEmail());
    }

    @Test
    @DisplayName("Get User By Username Profiles")
    public void getUserByUsernameTest() {
        User user = userDao.getUserByUsername("bob");
        User missingUser = userDao.getUserByUsername("nonexistent");

        assertNotNull(user);
        assertEquals(2, user.getUserId());
        assertEquals("bob", user.getUserName());
        assertNull(missingUser, "Should return null gracefully on missing username strings");
    }

    @Test
    @DisplayName("Authenticate User Credentials Match")
    public void authenticateTest() {

        User validAuth = userDao.authenticate("alice", "password123");
        User invalidAuth = userDao.authenticate("alice", "wrong_pass");


        assertNotNull(validAuth, "Valid credentials should return a user reference");
        assertEquals(1, validAuth.getUserId());
        assertNull(invalidAuth, "Invalid credentials should return null safely");
    }

    @Test
    @DisplayName("Update Base User Core Fields")
    public void updateUserTest() {

        User user = userDao.getUserById(2); // Bob
        user.setUserName("robert");
        user.setEmail("robert@example.com");

        userDao.updateUser(user);
        User updated = userDao.getUserById(2);

        assertNotNull(updated);
        assertEquals("robert", updated.getUserName());
        assertEquals("robert@example.com", updated.getEmail());
    }

    @Test
    @DisplayName("Add and Delete Intolerance Records")
    public void manageIntolerancesTest() {

        User user = userDao.getUserById(2);
        int initialCount = user.getIntolerances().size();

        userDao.addIntolerance(user, 4);
        assertTrue(user.getIntolerances().contains("Chicken Breast"), "DTO list should update dynamically");

        User updatedUserAfterAdd = userDao.getUserById(2);
        assertEquals(initialCount + 1, updatedUserAfterAdd.getIntolerances().size(), "DB should reflect additions");

        userDao.deleteIntolerance(user, "Chicken Breast");
        assertFalse(user.getIntolerances().contains("Chicken Breast"), "DTO list should clear item");

        User updatedUserAfterDelete = userDao.getUserById(2);
        assertEquals(initialCount, updatedUserAfterDelete.getIntolerances().size(), "DB should drop row");
    }

    @Test
    @DisplayName("Add and Delete Dietary Restrictions")
    public void manageDietaryRestrictionsTest() {
        User user = userDao.getUserById(1);

        userDao.addDietaryRestriction(user, 2);
        assertTrue(user.getDietaryRestrictions().contains("Vegan"));

        User updatedUserAfterAdd = userDao.getUserById(1);
        assertEquals(3, updatedUserAfterAdd.getDietaryRestrictions().size());

        userDao.deleteDietaryRestriction(user, "Vegetarian");
        assertFalse(user.getDietaryRestrictions().contains("Vegetarian"));

        User updatedUserAfterDelete = userDao.getUserById(1);
        assertEquals(2, updatedUserAfterDelete.getDietaryRestrictions().size());
    }

    @Test
    @DisplayName("Save and Remove Shared Recipes Metadata")
    public void manageSavedRecipesTest() {
        User user = userDao.getUserById(2);
        assertEquals(2, user.getRecipes().size());

        Recipe newRecipeToSave = new Recipe();
        newRecipeToSave.setRecipeId(1); // Chicken Stir Fry

        userDao.addRecipe(user, newRecipeToSave);
        User afterAdd = userDao.getUserById(2);
        assertEquals(3, afterAdd.getRecipes().size());

        userDao.deleteRecipe(user, newRecipeToSave);
        User afterDelete = userDao.getUserById(2);
        assertEquals(2, afterDelete.getRecipes().size());
    }

    @Test
    @DisplayName("Fetch Static List Matrix Data maps")
    public void getStaticMetadataListsTest() {

        List<Map<String, Object>> diets = userDao.getAllDiets();
        List<Map<String, Object>> items = userDao.getAllIntolerances();

        assertEquals(6, diets.size(), "Diets seed layout contains exactly 6 catalog options");
        assertEquals(12, items.size(), "Items catalog seed layout contains exactly 12 row listings");

        assertEquals("Vegetarian", diets.get(0).get("name"));
        assertEquals("Milk", items.get(0).get("name"));
    }
}