package com.jc451.team1.DAO.mappers;

import com.jc451.team1.DTO.InventoryItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryItemMapper implements RowMapper<InventoryItem> {

    @Override
    public InventoryItem mapRow(ResultSet rs, int rowNum) throws SQLException{

        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setIngredientId(rs.getInt("id"));
        inventoryItem.setQuantity(rs.getFloat("quantity"));
        inventoryItem.setUnit(rs.getString("unit"));
        inventoryItem.setExpirationDate(rs.getObject("expiration", java.time.LocalDate.class));
        inventoryItem.setHouseHoldId(rs.getInt("household_id"));
        inventoryItem.setItemId(rs.getInt("item_id"));

        return inventoryItem;
    }
}
