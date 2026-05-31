package com.jc451.team1.dao;

import com.jc451.team1.dao.mappers.DietsMapper;
import com.jc451.team1.dao.mappers.IntoleranceMapper;
import com.jc451.team1.dao.mappers.RecipeMapper;
import com.jc451.team1.dao.mappers.UserMapper;
import com.jc451.team1.dto.Recipe;
import com.jc451.team1.dto.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User createUser(User user) {
        final String INSERT_USER = "INSERT INTO users(username, password, email) " +
                "VALUES(?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    INSERT_USER,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            return statement;
        }, keyHolder);

        user.setUserId(keyHolder.getKey().intValue());

        return user;
    }

    @Override
    public User getUserById(int id) {
        User user;

        // Query for the user by id
        final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
        user = jdbcTemplate.queryForObject(SELECT_USER_BY_ID, new UserMapper(), id);

        // Return null if user is not found by id
        if (user == null) {
            return null;
        }

        // Now get their dietary restriction list, intolerances list, and recipes list
        final String SELECT_USER_DIETARY_RESTRICTIONS_BY_ID = "SELECT diets.name FROM users " +
                "JOIN dietary_restrictions ON dietary_restrictions.user_id = ? " +
                "JOIN diets ON dietary_restrictions.diet_id = diets.id";
        user.setDietaryRestrictions(jdbcTemplate.query(SELECT_USER_DIETARY_RESTRICTIONS_BY_ID, new DietsMapper(), id));

        final String SELECT_USER_INTOLERANCE_RESTRICTIONS_BY_ID = "SELECT items.name FROM users " +
                "JOIN intolerances ON intolerances.user_id = ? " +
                "JOIN items ON intolerances.item_id = items.id";
        user.setIntolerances(jdbcTemplate.query(SELECT_USER_INTOLERANCE_RESTRICTIONS_BY_ID, new IntoleranceMapper(), id));

        // For Recipes, call its respective mapper
        final String SELECT_SAVED_RECIPES_BY_USER_ID = "SELECT * FROM users " +
                "JOIN saved_recipes ON saved_recipes.user_id = ? " +
                "JOIN recipes ON recipes.id = saved_recipes.recipe_id";
        user.setRecipes(jdbcTemplate.query(SELECT_SAVED_RECIPES_BY_USER_ID, new RecipeMapper(), id));

        return user;
    }

    @Override
    public void updateUser(User user) {
        final String UPDATE_USER = "UPDATE users " +
                "SET username = ?, password = ?, email = ?" +
                "WHERE id = ?";
        jdbcTemplate.update(UPDATE_USER, user.getUserName(), user.getPassword(), user.getEmail(), user.getIntolerances());
    }

    @Override
    public void addIntolerance(User user, String intolerance) {

    }

    @Override
    public void deleteIntolerance(User user, String intolerance) {
        // Delete from User
        user.getIntolerances().remove(intolerance);

        // Update the database
        final String DELETE_USER_INTOLERANCE = "DELETE intolerances " +
                "FROM intolerances " +
                "JOIN items ON items.id = intolerances.item_id " +
                "WHERE intolerances.user_id = ? AND WHERE `items.name` = ? ";
        jdbcTemplate.update(DELETE_USER_INTOLERANCE, user.getUserId(), intolerance);
    }

    @Override
    public void addDietaryRestriction(User user, String dietaryRestriction) {

    }

    @Override
    public void deleteDietaryRestriction(User user, String dietaryRestriction) {
        // Delete from the user
        user.getDietaryRestrictions().remove(dietaryRestriction);

        // Update the database
        final String DELETE_USER_DIETARY_RESTRICTION = "DELETE dietary_restrictions " +
                "FROM dietary_restrictions " +
                "JOIN diets ON diets.id = dietary_restrictions.diet_id " +
                "WHERE dietary_restrictions.user_id = ? AND WHERE `diets.name` = ? ";
        jdbcTemplate.update(DELETE_USER_DIETARY_RESTRICTION, user.getUserId(), dietaryRestriction);
    }

    @Override
    public void addRecipe(User user, Recipe recipe) {
        // Add to the user's recipes
        user.getRecipes().add(recipe);

        // Insert the recipe into the database
        final String INSERT_USER_RECIPE = "INSERT INTO saved_recipes(user_id, recipe_id) " +
                "VALUES(?, ?)";
        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    INSERT_USER_RECIPE,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, user.getUserId());
            statement.setInt(2, recipe.getRecipeId());
            return statement;
        });
    }

    @Override
    public void deleteRecipe(User user, Recipe recipe) {
        // Delete from the User
        user.getRecipes().remove(recipe);

        // Delete from the database
        final String DELETE_USER_RECIPE = "DELETE FROM saved_recipes " +
                "WHERE user_id = ? AND recipe_id = ?";
        jdbcTemplate.update(DELETE_USER_RECIPE, user.getIntolerances(), recipe.getRecipeId());
    }
}
