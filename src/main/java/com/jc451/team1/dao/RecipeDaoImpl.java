package com.jc451.team1.dao;

import com.jc451.team1.dao.mappers.RecipeMapper;
import com.jc451.team1.dto.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class RecipeDaoImpl implements RecipeDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RecipeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Recipe saveRecipe(Recipe recipe, int userId) {
        final String INSERT_RECIPE = "INSERT INTO recipes(title, prep_time_in_mins, instructions, image_link) VALUES(?,?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_RECIPE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, recipe.getTitle());
            ps.setInt(2, recipe.getPrepTime());
            ps.setString(3, recipe.getInstruction());
            ps.setString(4, recipe.getImage());
            return ps;
        },keyHolder);

        Number key = keyHolder.getKey();
        if (key != null){
            recipe.setRecipeId(key.intValue());
        }

        final String INSERT_USER_LINK = "INSERT IGNORE INTO saved_recipes(user_id, recipe_id) VALUES(?,?)";
        jdbcTemplate.update(INSERT_USER_LINK, userId, recipe.getRecipeId());

        return recipe;
    }

    @Override
    public Recipe findRecipeById(int id) {
        final String SELECT_BY_ID = "SELECT * FROM recipes WHERE id = ?";
        return jdbcTemplate.queryForObject(SELECT_BY_ID, new RecipeMapper(), id);
    }

    @Override
    public List<Recipe> getSavedRecipesByUserId(int userId) {
        final String SELECT_SAVED_BY_USER =
                "SELECT r.* FROM recipes r " +
                "INNER JOIN saved_recipes sr ON r.id = sr.recipe_id " +
                "WHERE sr.user_id = ?";

        return jdbcTemplate.query(SELECT_SAVED_BY_USER, new RecipeMapper(), userId);
    }

    @Override
    public void removeSavedRecipe(int userId, int recipeId) {
        final String DELETE_SAVED = "DELETE FROM saved_recipes WHERE user_id = ? AND recipe_id = ?";
        jdbcTemplate.update(DELETE_SAVED, userId, recipeId);
    }
}
