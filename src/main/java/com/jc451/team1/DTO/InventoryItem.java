package com.jc451.team1.DTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class InventoryItem {
    private int ingredientId;
    private String ingredientName;
    private String unit;
    private int quantity;
    private LocalDate expirationDate;

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getExpirationDate() { return expirationDate; }

    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        InventoryItem that = (InventoryItem) o;
        return ingredientId == that.ingredientId && quantity == that.quantity && Objects.equals(ingredientName, that.ingredientName) && Objects.equals(unit, that.unit) && Objects.equals(expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId, ingredientName, unit, quantity, expirationDate);
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "ingredientId=" + ingredientId +
                ", ingredientName='" + ingredientName + '\'' +
                ", unit='" + unit + '\'' +
                ", quantity=" + quantity +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
