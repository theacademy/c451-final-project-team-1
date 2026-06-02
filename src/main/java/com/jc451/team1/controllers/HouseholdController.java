package com.jc451.team1.controllers;

import com.jc451.team1.dto.Household;
import com.jc451.team1.dto.User;
import com.jc451.team1.service.HouseholdService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HouseholdController {

    private final HouseholdService householdService;

    @Autowired
    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    @GetMapping("/household")
    public String householdPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Household household = householdService
                .getHouseholdFromUser(user.getUserId());

        model.addAttribute("household", household);
        if (household != null) {
            model.addAttribute("memberCount", household.getUsers().size());
            model.addAttribute("members", household.getUsers());
        }

        return "household";
    }

    @PostMapping("household/create")
    public String createHousehold(@RequestParam("code") String code,
                                  @RequestParam("name") String name,
                                  @RequestParam("address") String address,
                                  HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Household household = new  Household();
        household.setCode(code);
        household.setHouseholdName(name);
        household.setAddress(address);

        household = householdService.createHousehold(household);
        int id = household.getHouseholdId();

        // remove from current household first if they already belong to one
        int currentHouseholdId = (int) session.getAttribute("householdId");
        if (currentHouseholdId != 0) {
            householdService.removeUserFromHousehold(user.getUserId(), currentHouseholdId);
        }

        householdService.addUserToHousehold(user.getUserId(), id);

        session.setAttribute("householdId", id);
        household = householdService.getHousehold(id);

        model.addAttribute("household", household);
        model.addAttribute("membersCount", household.getUsers().size());
        model.addAttribute("members", household.getUsers());
        return "redirect:/household";
    }

    @PostMapping("/household/join")
    public String joinHousehold(@RequestParam("code") String code,
                                HttpSession session,
                                Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        try {
            // look up the household by invite code
            Household household = householdService.getHouseholdByCode(code);

            if (household == null) {
                model.addAttribute("error", "Invalid invite code");
                return "household";
            }

            // remove from current household first if they already belong to one
            int currentHouseholdId = (int) session.getAttribute("householdId");
            if (currentHouseholdId != 0) {
                householdService.removeUserFromHousehold(user.getUserId(), currentHouseholdId);
            }

            // add the user to it
            householdService.addUserToHousehold(user.getUserId(), household.getHouseholdId());

            // update session so inventory tab knows right away
            session.setAttribute("householdId", household.getHouseholdId());

            return "redirect:/household";

        } catch (Exception e) {
            model.addAttribute("error", "Invalid invite code");
            return "household";
        }
    }
}
