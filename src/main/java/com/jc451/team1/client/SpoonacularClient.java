package com.jc451.team1.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SpoonacularClient {

    @Value("${spoonacular.api.key}")
    private String apiKey;

    @Value("${spoonacular.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public SpoonacularClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    /**
     * Searches Spoonacular by ingredients, diet, and intolerances.
     * Calls: GET /recipes/complexSearch
     *
     * Returns a list of maps — each map has the raw JSON fields:
     *   id, title, image
     * We map these onto our Recipe DTO in the controller.
     */
    public List<Map<String, Object>> searchRecipes(String ingredients,
                                                   String diet,
                                                   String intolerances) {
        StringBuilder url = new StringBuilder(baseUrl);
        url.append("/recipes/complexSearch?apiKey=").append(apiKey);
        url.append("&number=5");
        url.append("&addRecipeInformation=true"); // gives us prepTime, instructions
        url.append("&addRecipeInstructions=true"); // gives us recipeSteps
        url.append("&fillIngredients=true");

        if (ingredients != null && !ingredients.isBlank()) {
            url.append("&includeIngredients=").append(ingredients.trim());
        }
        if (diet != null && !diet.isBlank()) {
            url.append("&diet=").append(diet.trim());
        }
        if (intolerances != null && !intolerances.isBlank()) {
            url.append("&intolerances=").append(intolerances.trim());
        }

        try {
            Map<String, Object> response = restTemplate.getForObject(url.toString(), Map.class);
            if (response != null && response.containsKey("results")) {
                return (List<Map<String, Object>>) response.get("results");
            }
        } catch (Exception e) {
            // log and return empty so the page doesn't crash
            System.err.println("Spoonacular API error: " + e.getMessage());
        }

        return new ArrayList<>();
    }
}
