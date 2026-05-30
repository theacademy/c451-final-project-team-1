package com.jc451.team1.dao.mappers;

import com.jc451.team1.dto.Recipe;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeMapper implements RowMapper<Recipe> {

    @Override
    public Recipe mapRow(ResultSet rs, int rowNum) throws SQLException{

        Recipe recipe = new Recipe();
        recipe.setRecipeId(rs.getInt("id"));
        recipe.setTitle(rs.getString("title"));
        recipe.setPrepTime(rs.getInt("prep_time_in_mins"));
        recipe.setInstruction(rs.getString("instructions"));
        recipe.setImage(rs.getString("image_link"));

        return recipe;
    }
}
