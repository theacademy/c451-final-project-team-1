package com.jc451.team1.dao;

import com.jc451.team1.dao.mappers.RecipeMapper;
import com.jc451.team1.dao.mappers.UserMapper;
import com.jc451.team1.dto.Recipe;
import com.jc451.team1.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User createUser(User user) {
        final String INSERT_USER = """
                INSERT INTO users(username, password, email)
                VALUES(?, ?, ?)""";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            return statement;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if(key != null){
            user.setUserId(key.intValue());
        }
        return user;
    }

    @Override
    public User getUserById(int id) {

        // Query for the user by id
        final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
        User user = jdbcTemplate.queryForObject(
                SELECT_USER_BY_ID, new UserMapper(), id);

        if (user == null) {
            return null;
        }

        // Get user's dietary restrictions, intolerances, and saved recipes
        final String SELECT_USER_DIETARY_RESTRICTIONS_BY_ID = """
                SELECT diets.name
                FROM dietary_restrictions
                JOIN diets ON dietary_restrictions.diet_id = diets.id
                WHERE dietary_restrictions.user_id = ?""";

        user.setDietaryRestrictions(jdbcTemplate.query(
                SELECT_USER_DIETARY_RESTRICTIONS_BY_ID,
                (rs, rowNum) -> rs.getString("name"), id));

        final String SELECT_USER_INTOLERANCES_BY_ID = """
                SELECT items.name
                FROM intolerances
                JOIN items ON intolerances.item_id = items.id
                WHERE intolerances.user_id = ?""";

        user.setIntolerances(jdbcTemplate.query(
                SELECT_USER_INTOLERANCES_BY_ID,
                (rs, rowNum) -> rs.getString("name"), id));

        final String SELECT_SAVED_RECIPES_BY_USER_ID = """
                SELECT recipes.*
                FROM saved_recipes
                JOIN recipes ON saved_recipes.recipe_id = recipes.id
                WHERE saved_recipes.user_id = ?""";

        user.setRecipes(jdbcTemplate.query(
                SELECT_SAVED_RECIPES_BY_USER_ID,
                new RecipeMapper(), id));

        return user;
    }

    @Override
    // keeping the method but won't be using for authentication,
    // might find a use case for this
    public User getUserByUsername(String username) {
        final String SELECT =
                "SELECT * FROM users WHERE username = ?";
        try {
            User user = jdbcTemplate.queryForObject(SELECT, new UserMapper(), username);
            if (user != null) {
                // load their full profile the same way as getUserById
                return getUserById(user.getUserId());
            }
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null; // username not found
        }
        return null;
    }

    @Override
    public void updateUser(User user) {
        final String UPDATE_USER = """
                UPDATE users
                SET username = ?, password = ?, email = ?
                WHERE id = ?""";

        jdbcTemplate.update(UPDATE_USER,
                user.getUserName(),
                user.getPassword(),
                user.getEmail(),
                user.getUserId());
    }

    @Override
    public User authenticate(String username, String password) {
        final String AUTHENTICATE_USER =
                "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            User user = jdbcTemplate.queryForObject(
                    AUTHENTICATE_USER, new UserMapper(), username, password);
            // Load the full data with getUserById
            return getUserById(user.getUserId());

        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null; // username not found
        }
    }

    @Override
    public void changePassword(int userId, String newPassword) {
        final String CHANGE_PASSWORD =
                "UPDATE users SET password = ? WHERE id = ?";
        jdbcTemplate.update(CHANGE_PASSWORD, newPassword, userId);
    }

    @Override
    public void addIntolerance(User user, int ingredientId) {
        // Get the intolerance name
        /* TODO: Remove commented code?
        final String SELECT_ITEM_INTOLERANCE = "SELECT items.name FROM intolerances " +
                "JOIN items ON intolerances.item_id = items.id " +
                "WHERE item_id = ?";
        String intolerance = jdbcTemplate.queryForObject(SELECT_ITEM_INTOLERANCE,
            new IntoleranceMapper(), ingredientId);
         */

        final String SELECT_ITEM_NAME = "SELECT name FROM items WHERE id = ?";
        String intolerance = jdbcTemplate.queryForObject(
                SELECT_ITEM_NAME, String.class, ingredientId);
        user.getIntolerances().add(intolerance);

        // Insert user's intolerance to the intolerance table
        final String INSERT_USER_INTOLERANCE = """
                INSERT INTO intolerances (user_id, item_id)
                VALUES (?, ?)""";
        jdbcTemplate.update(INSERT_USER_INTOLERANCE,
                user.getUserId(), ingredientId);
    }

    @Override
    public void deleteIntolerance(User user, String intolerance) {
        // Delete from User
        user.getIntolerances().remove(intolerance);

//        TODO: Remove commented code?
//        """
//        DELETE intolerances
//        FROM intolerances
//        JOIN items ON items.id = intolerances.item_id
//        WHERE intolerances.user_id = ? AND items.name = ?"""

        // Update the database
        final String DELETE_USER_INTOLERANCE = """
                DELETE FROM intolerances
                WHERE user_id = ?
                AND item_id = (SELECT id FROM items WHERE name = ?)""";

        jdbcTemplate.update(DELETE_USER_INTOLERANCE,
                user.getUserId(), intolerance);
    }

    @Override
    public void addDietaryRestriction(User user, int dietaryRestrictionId) {
        /*
        // Get the diet name
        // TODO: Remove commented code?
        final String SELECT_DIET_RESTRICTION = "SELECT items.name FROM intolerances " +
                "JOIN items ON intolerances.item_id = items.id " +
                "WHERE intolerances.item_id = ?";
        String dietaryRestriction = jdbcTemplate.queryForObject(SELECT_DIET_RESTRICTION,
            new IntoleranceMapper(), dietaryRestrictionId);
         */

        final String SELECT_DIET_NAME = "SELECT name FROM diets WHERE id = ?";
        String dietaryRestriction = jdbcTemplate.queryForObject(
                SELECT_DIET_NAME, String.class, dietaryRestrictionId);

        user.getDietaryRestrictions().add(dietaryRestriction);

        // Insert user's diet to the diet table
        final String INSERT_USER_DIET = """
                INSERT INTO dietary_restrictions (user_id, diet_id)
                VALUES (?, ?)""";
        jdbcTemplate.update(INSERT_USER_DIET,
                user.getUserId(), dietaryRestrictionId);
    }

    @Override
    public void deleteDietaryRestriction(User user, String dietaryRestriction) {
        // Delete from the user
        user.getDietaryRestrictions().remove(dietaryRestriction);

        // TODO: Remove commented code
//        """
//                DELETE dietary_restrictions
//                FROM dietary_restrictions
//                JOIN diets ON diets.id = dietary_restrictions.diet_id
//                WHERE dietary_restrictions.user_id = ? AND diets.name = ?"""

        // Update the database
        final String DELETE_USER_DIETARY_RESTRICTION = """
                DELETE FROM dietary_restrictions
                WHERE user_id = ?
                AND diet_id = (SELECT id FROM diets WHERE name = ?)""";
        jdbcTemplate.update(DELETE_USER_DIETARY_RESTRICTION,
                user.getUserId(), dietaryRestriction);
    }

    @Override
    public void addRecipe(User user, Recipe recipe) {
        // Add to the user's recipes
        user.getRecipes().add(recipe);

        // Insert the recipe into the database
        final String INSERT_USER_RECIPE = """
                INSERT INTO saved_recipes (user_id, recipe_id)
                VALUES (?, ?)""";

        jdbcTemplate.update(INSERT_USER_RECIPE,
                user.getUserId(), recipe.getRecipeId());
    }

    @Override
    public void deleteRecipe(User user, Recipe recipe) {
        // Delete from the User
        user.getRecipes().remove(recipe);

        // Delete from the database
        final String DELETE_USER_RECIPE = """
                DELETE FROM saved_recipes
                WHERE user_id = ? AND recipe_id = ?""";

        jdbcTemplate.update(DELETE_USER_RECIPE,
                user.getUserId(), recipe.getRecipeId());
    }

    @Override
    public List<Map<String, Object>> getAllDiets() {
        return jdbcTemplate.queryForList("SELECT id, name FROM diets");
    }

    @Override
    public List<Map<String, Object>> getAllIntolerances() {
        return jdbcTemplate.queryForList("SELECT id, name FROM items");
    }
}
