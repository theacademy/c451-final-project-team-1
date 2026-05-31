package com.jc451.team1.controllers;

import com.jc451.team1.dto.User;
import com.jc451.team1.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        User user = userService.authenticate(username, password);

        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/homepage";
        }

        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/homepage")
    public String homepage(HttpSession session) {

        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        return "homepage";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}
