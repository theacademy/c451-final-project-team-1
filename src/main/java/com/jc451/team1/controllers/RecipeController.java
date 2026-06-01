package com.jc451.team1.controllers;

import com.jc451.team1.client.SpoonacularClient;
import com.jc451.team1.dto.Recipe;
import com.jc451.team1.dto.User;
import com.jc451.team1.service.RecipeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class RecipeController {

    private final SpoonacularClient spoonacularClient;
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(SpoonacularClient spoonacularClient, RecipeService recipeService) {
        this.spoonacularClient = spoonacularClient;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes")
    public String recipesPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("savedRecipes",
                recipeService.getSavedRecipesByUserId(user.getUserId()));

        return "recipes";
    }

    // GET /recipes/search — call Spoonacular, show results in recipes.html
    @GetMapping("/recipes/search")
    public String searchRecipes(@RequestParam("ingredients") String ingredients,
                                HttpSession session,
                                Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        // build diet and intolerance strings from the user object
        String diet = user.getDietaryRestrictions() != null
                ? String.join(",", user.getDietaryRestrictions())
                : "";

        String intolerances = user.getIntolerances() != null
                ? String.join(",", user.getIntolerances())
                : "";

        // call the API
        List<Map<String, Object>> apiResults =
                spoonacularClient.searchRecipes(ingredients, diet, intolerances);

        // map raw API maps → Recipe DTOs so Thymeleaf can use the same object
        List<Recipe> recipes = new ArrayList<>();
        List<String> sourceUrls = new ArrayList<>(); // should maybe add a field for sourceUrl on Recipe object instead of doing this
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
            sourceUrls.add((String) r.get("sourceUrl")); // should maybe add a field for sourceUrl on Recipe object instead of doing this
            //Saving last recipe search
            session.setAttribute("lastIngredients", ingredients);
        }

        model.addAttribute("recipes", recipes);

        // recipe source urls
        model.addAttribute("sourceUrls", sourceUrls);

        // keep the search terms populated in the form
        model.addAttribute("ingredients", ingredients);

        // reload saved recipes so section stays visible after search
        model.addAttribute("savedRecipes", recipeService.getSavedRecipesByUserId(user.getUserId()));

        return "recipes";
    }

    @PostMapping("/recipes/save")
    public String saveRecipe(@RequestParam("title") String title,
                             @RequestParam("image") String image,
                             @RequestParam("prepTime") int prepTime,
                             @RequestParam("instruction") String instruction,
                             HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setImage(image);
        recipe.setPrepTime(prepTime);
        recipe.setInstruction(instruction);

        recipeService.saveRecipe(recipe, user.getUserId());

        String lastIngredients = (String) session.getAttribute("lastIngredients");
        if (lastIngredients != null) {
            return "redirect:/recipes/search?ingredients=" + lastIngredients;
        }

        return "redirect:/recipes";
    }

    @PostMapping("/recipes/remove")
    public String removeRecipe(@RequestParam("recipeId") int recipeId,
                               HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        recipeService.unsaveRecipe(user.getUserId(), recipeId);

        String lastIngredients = (String) session.getAttribute("lastIngredients");
        if (lastIngredients != null) {
            return "redirect:/recipes/search?ingredients=" + lastIngredients;
        }
        return "redirect:/recipes";
    }
}
