package com.jc451.team1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HouseholdController {
    // Bare minimum to show html page
    @GetMapping("/household")
    public String householdPage() {
        return "household";
    }

    @PostMapping("household/create")
    public String createHousehold() {
        return "redirect:/household";
    }
}
