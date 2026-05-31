package com.jc451.team1.dao.mappers;

import com.jc451.team1.dto.Household;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HouseholdMapper implements RowMapper<Household> {
    @Override
    public Household mapRow(ResultSet rs, int rowNum) throws SQLException {
        Household household = new Household();

        household.setHouseholdId(rs.getInt("id"));
        household.setCode(rs.getString("code"));
        household.setHouseholdName(rs.getString("nickname"));
        household.setAddress(rs.getString("address"));

        return household;
    }
}
