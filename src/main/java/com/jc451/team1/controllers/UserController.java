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

            // store householdId separately in session
            Integer householdId = userService.getHouseholdIdByUserId(user.getUserId());
            session.setAttribute("householdId", householdId != null ? householdId : 0);

            return "redirect:/homepage";
        }

        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/homepage")
    public String homepage(HttpSession session, Model model) {

        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        // load all available diets and intolerances for the dropdowns
        model.addAttribute("allDiets", userService.getAllDiets());
        model.addAttribute("allIntolerances", userService.getAllIntolerances());

        return "homepage";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }

    @PostMapping("/homepage/addIntolerance")
    public String addIntolerance(@RequestParam("intoleranceId") int intoleranceId,
                                 HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        userService.addIntolerance(user, intoleranceId);
        session.setAttribute("user", user); // update session with new intolerance
        return "redirect:/homepage";
    }

    @PostMapping("/homepage/removeIntolerance")
    public String removeIntolerance(@RequestParam("intolerance") String intolerance,
                                    HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        userService.deleteIntolerance(user, intolerance);
        session.setAttribute("user", user);
        return "redirect:/homepage";
    }

    @PostMapping("/homepage/addDiet")
    public String addDiet(@RequestParam("dietId") int dietId,
                          HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        userService.addDietaryRestriction(user, dietId);
        session.setAttribute("user", user);
        return "redirect:/homepage";
    }

    @PostMapping("/homepage/removeDiet")
    public String removeDiet(@RequestParam("diet") String diet,
                             HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        userService.deleteDietaryRestriction(user, diet);
        session.setAttribute("user", user);
        return "redirect:/homepage";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("email") String email,
            HttpSession session,
            Model model) {

        // passwords don't match
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }

        // username already taken
        if (userService.getUserByUsername(username) != null) {
            model.addAttribute("error", "Username already taken");
            return "register";
        }

        // username is blank
        if (username.isBlank() || username.equals(null)) {
            model.addAttribute("error", "Please input a username");
            return "register";
        }

        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setEmail(email);

        User created = userService.createUser(user);

        // createUser returns userId -1 if validation failed in the service
        if (created.getUserId() == -1) {
            model.addAttribute("error", created.getUserName()); // service sets error msg as username
            return "register";
        }

        // log them in right away
        session.setAttribute("user", created);
        // new user has no household
        session.setAttribute("householdId", 0);
        return "redirect:/homepage";
    }
}
