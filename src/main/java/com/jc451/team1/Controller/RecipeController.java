package com.jc451.team1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecipeController {
    @GetMapping("/recipes")
    public String recipesPage() {
        return "recipes";
    }
}
