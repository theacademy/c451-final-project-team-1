package com.jc451.team1.dao.mappers;

import com.jc451.team1.dto.InventoryItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryItemMapper implements RowMapper<InventoryItem> {

    @Override
    public InventoryItem mapRow(ResultSet rs, int rowNum) throws SQLException{

        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setId(rs.getInt("item_id"));
        inventoryItem.setHouseHoldId(rs.getInt("household_id"));
        inventoryItem.setIngredientId(rs.getInt("ingredient_id"));
        inventoryItem.setIngredientName(rs.getString("ingredient_name"));
        inventoryItem.setQuantity(rs.getFloat("quantity"));
        inventoryItem.setUnit(rs.getString("unit"));
        inventoryItem.setExpirationDate(rs.getObject("expiration", java.time.LocalDate.class));

        return inventoryItem;
    }
}
