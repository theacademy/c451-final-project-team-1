package com.jc451.team1.dao;

import com.jc451.team1.dao.mappers.InventoryItemMapper;
import com.jc451.team1.dto.InventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class InventoryItemDaoImpl implements InventoryItemDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public InventoryItemDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override @Transactional
    public InventoryItem addInventoryItem(InventoryItem inventoryItem) {

        final String INSERT_INVENTORY = """
                INSERT INTO inventory (household_id, item_id, quantity, unit, expiration)
                VALUES(?, ?, ?, ?, ?)""";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    INSERT_INVENTORY, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, inventoryItem.getHouseHoldId());
            ps.setInt(2, inventoryItem.getId());
            ps.setFloat(3, inventoryItem.getQuantity());
            ps.setString(4, inventoryItem.getUnit());
            ps.setDate(5, java.sql.Date.valueOf(inventoryItem.getExpirationDate()));
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            inventoryItem.setId(key.intValue());
        }
        return inventoryItem;
    }

    // TODO: Check if intended, or going for a specific household inventory
    @Override
    public List<InventoryItem> getInventory(int householdId) {

        final String SELECT_ALL_INVENTORY = "SELECT * FROM inventory";
        return jdbcTemplate.query(SELECT_ALL_INVENTORY, new InventoryItemMapper());
    }

    @Override
    public InventoryItem findInventoryItemById(int id) {

        final String SELECT_BY_ID = "SELECT * FROM inventory WHERE id = ?";
        return jdbcTemplate.queryForObject(
                SELECT_BY_ID, new InventoryItemMapper(), id);
    }

    @Override
    public void updateInventoryItem(InventoryItem inventoryItem) {

        final String UPDATE_INVENTORY = """
                UPDATE inventory
                SET household_id = ?, item_id = ?,
                quantity = ?, unit = ?, expiration = ?
                WHERE id = ?""";

        jdbcTemplate.update(UPDATE_INVENTORY,
                inventoryItem.getHouseHoldId(),
                inventoryItem.getIngredientId(),
                inventoryItem.getQuantity(),
                inventoryItem.getUnit(),
                java.sql.Date.valueOf(inventoryItem.getExpirationDate()),
                inventoryItem.getId());
    }

    @Override
    public void removeInventoryItem(int id) {

        final String DELETE_INVENTORY = "DELETE FROM inventory WHERE id = ?";
        jdbcTemplate.update(DELETE_INVENTORY,id);
    }

    @Override
    public String getItemNameByItemId(int itemId) {

        final String SELECT_NAME_BY_ITEM_ID =
                "SELECT name FROM items WHERE id = ?";
        return jdbcTemplate.queryForObject(
                SELECT_NAME_BY_ITEM_ID, String.class, itemId);
    }
}
