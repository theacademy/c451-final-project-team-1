package com.jc451.team1.controllers;

import com.jc451.team1.client.SpoonacularClient;
import com.jc451.team1.dto.InventoryItem;
import com.jc451.team1.dto.Recipe;
import com.jc451.team1.dto.User;
import com.jc451.team1.service.InventoryItemService;
import com.jc451.team1.service.InventoryItemServiceImpl;
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
    private final InventoryItemService inventoryItemService;

    @Autowired
    public RecipeController(SpoonacularClient spoonacularClient, RecipeService recipeService, InventoryItemService inventoryItemService) {
        this.spoonacularClient = spoonacularClient;
        this.recipeService = recipeService;
        this.inventoryItemService = inventoryItemService;
    }

    @GetMapping("/recipes")
    public String recipesPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        int householdId = (int) session.getAttribute("householdId");

        // load inventory so user can see what ingredients will be used
        List<InventoryItem> inventoryItems = inventoryItemService.getAllInventoryItems(householdId);
        model.addAttribute("inventoryItems", inventoryItems);

        model.addAttribute("savedRecipes",
                recipeService.getSavedRecipesByUserId(user.getUserId()));

        return "recipes";
    }

    // GET /recipes/search — call Spoonacular, show results in recipes.html
    @GetMapping("/recipes/search")
    public String searchRecipes(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        int householdId = (int) session.getAttribute("householdId");

        // build ingredient string from household inventory
        List<InventoryItem> inventoryItems =
                inventoryItemService.getAllInventoryItems(householdId);

        String ingredients = inventoryItems.stream()
                .map(InventoryItem::getIngredientName)
                .collect(java.util.stream.Collectors.joining(","));

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
        // Recipes objects do not store issing ingredients, therefore, make a List here
        List<List<String>> missingIngredientsListPerRecipe = new ArrayList<>();
        List<String> sourceUrls = new ArrayList<>(); // should maybe add a field for sourceUrl on Recipe object instead of doing this
        for (Map<String, Object> result : apiResults) {
            Recipe recipe = new Recipe();

            recipe.setTitle((String) result.get("title"));
            recipe.setImage((String) result.get("image"));

            // readyInMinutes comes back when addRecipeInformation=true
            Object mins = result.get("readyInMinutes");
            if (mins instanceof Integer) {
                recipe.setPrepTime((Integer) mins);
            }

            // pull steps from analyzedInstructions into one string
            StringBuilder steps = new StringBuilder();
            Object analyzedRaw = result.get("analyzedInstructions");
            if (analyzedRaw instanceof List<?> analyzedList && !analyzedList.isEmpty()) {
                Map<String, Object> firstBlock = (Map<String, Object>) analyzedList.get(0);
                Object stepsRaw = firstBlock.get("steps");
                // append recipe steps to the recipe object
                if (stepsRaw instanceof List<?> stepList) {
                    for (Object stepObj : stepList) {
                        Map<String, Object> step = (Map<String, Object>) stepObj;
                        steps.append("Step ").append(step.get("number"))
                                .append(": ").append(step.get("step"))
                                .append("<br><br>");
                    }
                }
            }
            // store missing ingredients in an unordered list
            List<String> missingIngredients = new ArrayList<>();
            // append ingredients not in the inventory into a String
            Object missedIngredientsRaw = result.get("missedIngredients");
            if (missedIngredientsRaw instanceof List<?> missingIngredientsList && !missingIngredientsList.isEmpty()){
                Map<String, Object> firstIngredient = (Map<String, Object>) missingIngredientsList.get(0);
                // append missing ingredients to unordered list of ingredients
                for (Object ingredientObj : missingIngredientsList) {
                    Map<String, Object> ingredient = (Map<String, Object>) ingredientObj;
                    missingIngredients.add((String) ingredient.get("name"));
                }
            }
            // Add the instructions to the Recipe object
            recipe.setInstruction(steps.toString());
            // Now add the recipe to the list of all recipes now that it has been queried for its properties
            recipes.add(recipe);
            // Missing ingredients are only displayed in the recipes page after queried, so they are not stored.
            missingIngredientsListPerRecipe.add(missingIngredients);
            sourceUrls.add((String) result.get("sourceUrl")); // should maybe add a field for sourceUrl on Recipe object instead of doing this
        }

        model.addAttribute("recipes", recipes);
        model.addAttribute("inventoryItems", inventoryItems);

        // recipe source urls
        model.addAttribute("sourceUrls", sourceUrls);
        // add missing ingredients
        model.addAttribute("missingIngredients", missingIngredientsListPerRecipe);

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

        return "redirect:/recipes/search";
    }

    @PostMapping("/recipes/remove")
    public String removeRecipe(@RequestParam("recipeId") int recipeId,
                               HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        recipeService.unsaveRecipe(user.getUserId(), recipeId);

        return "redirect:/recipes/search";
    }
}
