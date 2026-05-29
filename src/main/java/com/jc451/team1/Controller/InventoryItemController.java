package com.jc451.team1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InventoryItemController {

    //For testing purposes, must be removed
    List<InventoryItem> inventoryItems = new ArrayList<>();

    // this endpoint is needed to display the page when you click on the inventory tab in the header of the page
    @GetMapping("/inventory")
    public String showInventory(Model model) {

        model.addAttribute("inventoryitems", inventoryItems);

        //returning the view name that spring uses to find and load the html file
        return "inventory";
    }

    @PostMapping("/inventory/add")
    public String addIngredient(@RequestParam String name,
                                @RequestParam String quantity,
                                @RequestParam String expirationdate) {
        InventoryItem item = new InventoryItem(name,quantity,LocalDate.parse(expirationdate));

        inventoryItems.add(item);
        return "redirect:/inventory";
    }
}

// for testing purposes, must be removed
class InventoryItem {
    private String name;
    private String quantity;
    private LocalDate expirationdate;

    InventoryItem(String name, String quantity, LocalDate expirationdate) {
        this.name = name;
        this.quantity = quantity;
        this.expirationdate = expirationdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public LocalDate getExpirationdate() {
        return expirationdate;
    }

    public void setExpirationdate(LocalDate expirationdate) {
        this.expirationdate = expirationdate;
    }
}
