package com.jc451.team1.controllers;

import com.jc451.team1.dto.User;
import com.jc451.team1.service.HouseholdService;
import com.jc451.team1.service.InventoryItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.jc451.team1.dto.InventoryItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InventoryItemController {
    private final InventoryItemService inventoryItemService;
    private final HouseholdService householdService;

    @Autowired
    public InventoryItemController(InventoryItemService inventoryItemService,
                                   HouseholdService householdService) {
        this.inventoryItemService = inventoryItemService;
        this.householdService = householdService;
    }

    @GetMapping("/inventory")
    public String showInventory(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) return "redirect:/login";

        int householdId = (int) session.getAttribute("householdId");

        if (householdId == 0) {
            model.addAttribute("noHousehold", true);
            return "inventory";
        }

        model.addAttribute("inventoryItems",
                inventoryItemService.getAllInventoryItems(householdId));
        return "inventory";
    }

    @PostMapping("/inventory/add")
    public String addIngredient(@RequestParam("name") String name,
                                @RequestParam("quantity") float quantity,
                                @RequestParam("unit") String unit,
                                @RequestParam(value = "expirationDate", required = false) String expirationDate,
                                HttpSession session) {

        if (session.getAttribute("user") == null) return "redirect:/login";

        int householdId = (int) session.getAttribute("householdId");
        int ingredientId = inventoryItemService.getOrCreateIngredientId(name);

        InventoryItem item = new InventoryItem();
        item.setHouseHoldId(householdId);
        item.setIngredientId(ingredientId);
        item.setIngredientName(name);
        item.setQuantity(quantity);
        item.setUnit(unit);
        item.setExpirationDate(
                expirationDate != null && !expirationDate.isBlank()
                        ? LocalDate.parse(expirationDate) : null);

        inventoryItemService.addInventoryItem(item);
        return "redirect:/inventory";
    }

    @PostMapping("/inventory/update/{id}")
    public String updateIngredient(@PathVariable("id") int id,
                                   @RequestParam("quantity") float quantity,
                                   @RequestParam("unit") String unit,
                                   @RequestParam(value = "expirationDate", required = false) String expirationDate,
                                   HttpSession session) {

        if (session.getAttribute("user") == null) return "redirect:/login";

        int householdId = (int) session.getAttribute("householdId");

        InventoryItem item = inventoryItemService.findInventoryItemById(id);
        item.setId(id);
        item.setHouseHoldId(householdId);
        item.setQuantity(quantity);
        item.setUnit(unit);
        item.setExpirationDate(
                expirationDate != null && !expirationDate.isBlank()
                        ? LocalDate.parse(expirationDate) : null);

        inventoryItemService.updateInventoryItem(item);
        return "redirect:/inventory";
    }

    @PostMapping("/inventory/delete/{id}")
    public String deleteIngredient(@PathVariable("id") int id,
                                   HttpSession session) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        inventoryItemService.removeInventoryItem(id);
        return "redirect:/inventory";
    }
}
