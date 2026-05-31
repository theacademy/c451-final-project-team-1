package com.jc451.team1.dao.mappers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntoleranceMapper implements RowMapper<String> {
    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        String intolerance = "";

        intolerance = rs.getString("name");

        return intolerance;
    }
}
