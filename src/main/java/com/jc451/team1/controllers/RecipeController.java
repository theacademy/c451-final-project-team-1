package com.jc451.team1.controllers;

import com.jc451.team1.client.SpoonacularClient;
import com.jc451.team1.dto.Recipe;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class RecipeController {

    private final SpoonacularClient spoonacularClient;

    @Autowired
    public RecipeController(SpoonacularClient spoonacularClient) {
        this.spoonacularClient = spoonacularClient;
    }

    @GetMapping("/recipes")
    public String recipesPage() {
        return "recipes";
    }

    // GET /recipes/search — call Spoonacular, show results in recipes.html
    @GetMapping("/recipes/search")
    public String searchRecipes(@RequestParam(required = false) String ingredients,
                                @RequestParam(required = false) String intolerances,
                                @RequestParam(required = false) String diet,
                                HttpSession session,
                                Model model) {

        // call the API
        List<Map<String, Object>> apiResults =
                spoonacularClient.searchRecipes(ingredients, diet, intolerances);

        // map raw API maps → Recipe DTOs so Thymeleaf can use the same object
        List<Recipe> recipes = new ArrayList<>();
        for (Map<String, Object> r : apiResults) {
            Recipe recipe = new Recipe();

            recipe.setTitle((String) r.get("title"));
            recipe.setImage((String) r.get("image"));

            // readyInMinutes comes back when addRecipeInformation=true
            Object mins = r.get("readyInMinutes");
            if (mins instanceof Integer) {
                recipe.setPrepTime((Integer) mins);
            }

            // pull steps from analyzedInstructions into one string
            StringBuilder steps = new StringBuilder();
            Object analyzedRaw = r.get("analyzedInstructions");
            if (analyzedRaw instanceof List<?> analyzedList && !analyzedList.isEmpty()) {
                Map<String, Object> firstBlock = (Map<String, Object>) analyzedList.get(0);
                Object stepsRaw = firstBlock.get("steps");
                if (stepsRaw instanceof List<?> stepList) {
                    for (Object stepObj : stepList) {
                        Map<String, Object> step = (Map<String, Object>) stepObj;
                        steps.append("Step ").append(step.get("number"))
                                .append(": ").append(step.get("step"))
                                .append("<br><br>");
                    }
                }
            }
            recipe.setInstruction(steps.toString());

            recipes.add(recipe);
        }

        model.addAttribute("recipes", recipes);

        // keep the search terms populated in the form
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("intolerances", intolerances);
        model.addAttribute("diet", diet);

        return "recipes";
    }
}
