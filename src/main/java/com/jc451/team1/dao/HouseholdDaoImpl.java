package com.jc451.team1.dao;

import com.jc451.team1.dao.mappers.*;
import com.jc451.team1.dto.Household;
import com.jc451.team1.dto.InventoryItem;
import com.jc451.team1.dto.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.*;
import java.util.List;

public class HouseholdDaoImpl implements HouseholdDao {

    private final JdbcTemplate jdbcTemplate;

    public HouseholdDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Household createHousehold(Household household) {
        final String INSERT_HOUSEHOLD = "INSERT INTO households(code, nickname, address) " +
                "VALUES(?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    INSERT_HOUSEHOLD,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, household.getCode());
            statement.setString(2, household.getHouseholdName());
            statement.setString(3, household.getAddress());
            return statement;
        }, keyHolder);

        household.setHouseholdId(keyHolder.getKey().intValue());

        return household;
    }

    @Override
    public Household getHouseholdById(int id) {
        final String SELECT_HOUSEHOLD_BY_ID = "SELECT * FROM households WHERE id = ?";

        Household household = jdbcTemplate.queryForObject(SELECT_HOUSEHOLD_BY_ID, new HouseholdMapper(), id);

        if (household == null){
            return null;
        }

        // If the household exists, get its users
        household.setUsers(this.getAllUsers(id));

        return household;
    }

    @Override
    public Household getHouseholdByCode(String householdCode) {
        final String SELECT_HOUSEHOLD_BY_CODE = "SELECT * FROM households WHERE code = ?";

        Household household = jdbcTemplate.queryForObject(SELECT_HOUSEHOLD_BY_CODE, new HouseholdMapper(), householdCode);

        if (household == null){
            return null;
        }

        // If the household exists, get its users
        household.setUsers(this.getAllUsers(household.getHouseholdId()));

        return household;
    }

    @Override
    public void addUserToHousehold(int userId, int householdId) {
        final String INSERT_HOUSEHOLD = "INSERT INTO household_memberships(user_id, household_id) " +
                "VALUES(?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    INSERT_HOUSEHOLD,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, userId);
            statement.setInt(2, householdId);
            return statement;
        });
    }

    @Override
    public void removeUserFromHousehold(int userId, int householdId) {
        final String DELETE_USER_FROM_HOUSEHOLD = "DELETE FROM household_memberships " +
                "WHERE user_id = ? AND household_id = ?";
        jdbcTemplate.update(DELETE_USER_FROM_HOUSEHOLD, userId, householdId);
    }

    @Override
    public List<InventoryItem> getInventoryItems(int householdId) {
        final String SELECT_ALL_HOUSEHOLD_INVENTORY = "SELECT * FROM inventory " +
                "WHERE household_id = ?";
        return jdbcTemplate.query(SELECT_ALL_HOUSEHOLD_INVENTORY, new InventoryItemMapper(), householdId);
    }

    @Override
    public List<User> getAllUsers(int householdId) {
        // Get users who are a part of the household
        final String SELECT_HOUSEHOLD_USERS = "SELECT * FROM household_memberships " +
                "JOIN users ON users.id = household_memberships.user_id " +
                "WHERE household_memberships.household_id = ?";
        List<User> users = jdbcTemplate.query(SELECT_HOUSEHOLD_USERS, new UserMapper(), householdId);

        // Get the user's Dietary Restrictions, Intolerances, and Recipes
        final String SELECT_USER_DIETARY_RESTRICTIONS_BY_ID = "SELECT diets.name FROM users " +
                "JOIN dietary_restrictions ON dietary_restrictions.user_id = ? " +
                "JOIN diets ON dietary_restrictions.diet_id = diets.id";
        final String SELECT_USER_INTOLERANCE_RESTRICTIONS_BY_ID = "SELECT items.name FROM users " +
                "JOIN intolerances ON intolerances.user_id = ? " +
                "JOIN items ON intolerances.item_id = items.id";
        final String SELECT_SAVED_RECIPES_BY_USER_ID = "SELECT * FROM users " +
                "JOIN saved_recipes ON saved_recipes.user_id = ? " +
                "JOIN recipes ON recipes.id = saved_recipes.recipe_id";

        for (User user : users){
            user.setDietaryRestrictions(jdbcTemplate.query(SELECT_USER_DIETARY_RESTRICTIONS_BY_ID, new DietsMapper(), user.getUserId()));
            user.setIntolerances(jdbcTemplate.query(SELECT_USER_INTOLERANCE_RESTRICTIONS_BY_ID, new IntoleranceMapper(), user.getUserId()));
            user.setRecipes(jdbcTemplate.query(SELECT_SAVED_RECIPES_BY_USER_ID, new RecipeMapper(), user.getUserId()));
        }

        return users;
    }
}
