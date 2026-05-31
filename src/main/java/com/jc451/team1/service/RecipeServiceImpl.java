package com.jc451.team1.service;

import com.jc451.team1.DAO.RecipeDao;
import com.jc451.team1.DTO.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    RecipeDao recipeDao;

    public RecipeServiceImpl(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    @Override
    public Recipe saveRecipe(Recipe recipe, int userId) {
       return recipeDao.saveRecipe(recipe,userId);
    }

    @Override
    public Recipe findRecipeById(int id) {
        try {
            return recipeDao.findRecipeById(id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Recipe> getSavedRecipesByUserId(int userId) {
        return recipeDao.getSavedRecipesByUserId(userId);
    }

    @Override
    public void removeSavedRecipe(int userId, int recipeId) {
        recipeDao.removeSavedRecipe(userId,recipeId);
    }
}
