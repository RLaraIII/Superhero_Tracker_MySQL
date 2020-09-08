/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.data;

import com.rlaraiii.superherosightings.models.Superpower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author R Lara
 */
@Repository
public class SuperpowerDaoDB implements SuperpowerDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Superpower getPowerById(int id) {
        try {
            final String SELECT_POWER_BY_ID = "SELECT * FROM superpower WHERE SuperpowerId = ?";
            Superpower power = jdbc.queryForObject(SELECT_POWER_BY_ID, new PowerMapper(), id);
            return power;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Superpower> getAllPowers() {
        final String SELECT_ALL_POWERS = "SELECT * from superpower";
        List<Superpower> powers = jdbc.query(SELECT_ALL_POWERS, new PowerMapper());
        
        return powers;
    }

    @Override
    @Transactional
    public Superpower addPower(Superpower power) {
        final String INSERT_POWER = "INSERT INTO superpower(Power) "
                + "VALUES(?)";
        jdbc.update(INSERT_POWER,
                power.getPower());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        power.setId(newId);
        return power;
    }

    @Override
    @Transactional
    public void updatePower(Superpower power) {
        final String UPDATE_POWER = "UPDATE superpower "
                + "SET SuperpowerId = ?, Power = ? "
                + "WHERE SuperpowerId = ?";
        jdbc.update(UPDATE_POWER,
                power.getId(),
                power.getPower(),
                power.getId());
    }

    @Override
    public void deletePowerById(int id) {        
        final String DELETE_POWER = "DELETE FROM superpower "
                + "WHERE SuperpowerId = ?";
        jdbc.update(DELETE_POWER, id);
    }
    
    public static final class PowerMapper implements RowMapper<Superpower> {
        @Override
        public Superpower mapRow(ResultSet rs, int index) throws SQLException {
            Superpower power = new Superpower();
            power.setId(rs.getInt("SuperpowerId"));
            power.setPower(rs.getString("Power"));
            return power;
        }
    }
}
