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
import java.time.LocalDate;
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
                INSERT INTO inventory (household_id, ingredient_id, quantity, unit, expiration)
                VALUES(?, ?, ?, ?, ?)""";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    INSERT_INVENTORY, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, inventoryItem.getHouseHoldId());
            ps.setInt(2, inventoryItem.getIngredientId());
            ps.setFloat(3, inventoryItem.getQuantity());
            ps.setString(4, inventoryItem.getUnit());
            LocalDate expirationDate = inventoryItem.getExpirationDate();
            if (expirationDate != null) ps.setDate(5,
                    java.sql.Date.valueOf(inventoryItem.getExpirationDate()));
            else ps.setNull(5, java.sql.Types.DATE);
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            inventoryItem.setId(key.intValue());
        }
        return inventoryItem;
    }

    @Override
    public List<InventoryItem> getInventory(int householdId) {

        final String SELECT_HOUSEHOLD_INVENTORY = """
                SELECT *, `name` as ingredient_name
                FROM inventory
                JOIN ingredients ON inventory.ingredient_id = ingredients.id
                WHERE household_id = ?""";
        return jdbcTemplate.query(SELECT_HOUSEHOLD_INVENTORY,
                new InventoryItemMapper(), householdId);
    }

    @Override
    public InventoryItem findInventoryItemById(int id) {
        final String SELECT_BY_ID = """
                SELECT inventory.*, ingredients.name as ingredient_name
                FROM inventory
                JOIN ingredients ON inventory.ingredient_id = ingredients.id
                WHERE item_id = ?""";
        return jdbcTemplate.queryForObject(SELECT_BY_ID, new InventoryItemMapper(), id);
    }

    @Override
    public void updateInventoryItem(InventoryItem inventoryItem) {

        final String UPDATE_INVENTORY = """
                UPDATE inventory
                SET household_id = ?, ingredient_id = ?,
                quantity = ?, unit = ?, expiration = ?
                WHERE item_id = ?""";

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

        final String DELETE_INVENTORY = "DELETE FROM inventory WHERE item_id = ?";
        jdbcTemplate.update(DELETE_INVENTORY,id);
    }

    @Override
    public String getItemNameByItemId(int itemId) {

        final String SELECT_NAME_BY_ITEM_ID =
                "SELECT name FROM ingredients WHERE id = ?";
        return jdbcTemplate.queryForObject(
                SELECT_NAME_BY_ITEM_ID, String.class, itemId);
    }

    @Override
    public int getOrCreateIngredientId(String name) {
        // check if ingredient already exists
        List<Integer> ids = jdbcTemplate.query(
                "SELECT id FROM ingredients WHERE name = ?",
                (rs, rowNum) -> rs.getInt("id"), name);

        // if it exists return the id
        if (!ids.isEmpty()) {
            return ids.get(0);
        }

        // otherwise insert it and return the generated id
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO ingredients (name) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }
}
