package com.jc451.team1.dao;

import com.jc451.team1.App;
import com.jc451.team1.dto.Household;
import com.jc451.team1.dto.InventoryItem;
import com.jc451.team1.dto.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = App.class)
public class HouseholdDaoImplTests {

    private JdbcTemplate jdbcTemplate;
    private HouseholdDao householdDao;

    @Autowired
    public void HouseholdDaoImplTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.householdDao = new HouseholdDaoImpl(jdbcTemplate);
    }

    @Test
    @DisplayName("Get Household By ID: 1 (Smith Family)")
    public void getHouseholdByIdTest() {

        Household household = householdDao.getHouseholdById(1);

        assertNotNull(household, "Household #1 should exist");
        assertEquals(1, household.getHouseholdId());
        assertEquals("A1B2C3D4", household.getCode());
        assertEquals("Smith Family", household.getHouseholdName());
        assertEquals("123 Maple Lane", household.getAddress());

        List<User> members = household.getUsers();
        assertNotNull(members, "Members list should not be null");
        assertEquals(2, members.size(), "Smith Family should have exactly 2 members");
        assertEquals("alice", members.get(0).getUserName());
        assertEquals("bob", members.get(1).getUserName());
    }

    @Test
    @DisplayName("Create New Household Test")
    public void createHouseholdTest() {

        Household newHousehold = new Household();
        newHousehold.setCode("XYZ98765");
        newHousehold.setHouseholdName("Tech Suite");
        newHousehold.setAddress("456 Innovation Way");

        Household savedHousehold = householdDao.createHousehold(newHousehold);
        Household retrieved = householdDao.getHouseholdById(savedHousehold.getHouseholdId());

        assertNotNull(savedHousehold, "Saved household instance should not be null");
        assertTrue(savedHousehold.getHouseholdId() > 0, "Database should generate an auto-increment ID");
        assertNotNull(retrieved, "Should be able to fetch the new household from DB");
        assertEquals("Tech Suite", retrieved.getHouseholdName());
    }

    @Test
    @DisplayName("Add User To Household Membership Test")
    public void addUserToHouseholdTest() {
        Household householdBefore = householdDao.getHouseholdById(2);
        assertEquals(1, householdBefore.getUsers().size(), "Household 2 should start with 1 user");

        householdDao.addUserToHousehold(4, 2);
        Household householdAfter = householdDao.getHouseholdById(2);

        assertEquals(2, householdAfter.getUsers().size(), "Household 2 should now have 2 users");
    }

    @Test
    @DisplayName("Remove User From Household Membership Test")
    public void removeUserFromHouseholdTest() {
        Household householdBefore = householdDao.getHouseholdById(3);
        assertEquals(2, householdBefore.getUsers().size(), "Household 3 should start with 2 users");

        householdDao.removeUserFromHousehold(4, 3);
        Household householdAfter = householdDao.getHouseholdById(3);

        assertEquals(1, householdAfter.getUsers().size(), "Household 3 count should drop to 1");
        assertEquals("emma", householdAfter.getUsers().get(0).getUserName(), "Remaining member should be emma");
    }

    @Test
    @DisplayName("Get Inventory Items By Household ID: 1")
    public void getInventoryItemsTest() {
        List<InventoryItem> inventory = householdDao.getInventoryItems(1);

        assertNotNull(inventory, "Inventory list should not be null");
        assertEquals(5, inventory.size(), "Household 1 should have exactly 5 inventory line items");
    }

    @Test
    @DisplayName("Verify Deep Nested Mapping Properties For Household Users")
    public void verifyNestedUserMetadataTest() {
        List<User> members = householdDao.getAllUsers(1);

        User alice = members.stream()
                .filter(u -> u.getUserId() == 1)
                .findFirst()
                .orElse(null);

        assertNotNull(alice, "Alice should be found in household 1 members list");

        // Dietary Restrictions mapping validation (Alice has Vegetarian & Gluten-Free)
        assertNotNull(alice.getDietaryRestrictions());
        assertEquals(2, alice.getDietaryRestrictions().size());

        assertNotNull(alice.getIntolerances());
        assertEquals(2, alice.getIntolerances().size());

        assertNotNull(alice.getRecipes());
        assertEquals(3, alice.getRecipes().size());
    }
}