/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.data;

import com.rlaraiii.superherosightings.models.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting getSightingByIdsAndDate(int heroId, int locationId, LocalDateTime date) {
        try {
            final String SELECT_SIGHTING_BY_IDS_AND_DATE = "SELECT * FROM sighting "
                    + "WHERE hero_HeroId = ? AND "
                    + "location_LocationId = ? AND "
                    + "Date = ?";
            return jdbc.queryForObject(SELECT_SIGHTING_BY_IDS_AND_DATE, new SightingMapper(), 
                    heroId, locationId, date);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public Sighting getSightingBySightingId(int sightingId) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE SightingId = ?";
            return jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), sightingId);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting ORDER BY sighting.date DESC";
        return jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());    
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO sighting(hero_HeroId, location_LocationId, Date) "
                + "VALUES(?, ?, ?)";
        jdbc.update(INSERT_SIGHTING,
                sighting.getHeroId(),
                sighting.getLocationId(),
                sighting.getDate());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        return sighting;
    }

    @Override
    @Transactional
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sighting "
                + "SET SightingId = ?, hero_HeroId = ?, location_LocationId = ?, Date = ? "
                + "WHERE SightingId = ?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getId(),
                sighting.getHeroId(),
                sighting.getLocationId(),
                sighting.getDate(),
                sighting.getId());
    }

    @Override
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE SightingId = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }

    @Override
    public List<Sighting> getSightingsForLocation(int locId) {
        final String SELECT_ALL_SIGHTINGS_FOR_DATE = "SELECT * FROM sighting "
                + "WHERE location_LocationId = ? "
                + "ORDER BY sighting.Date";
        return jdbc.query(SELECT_ALL_SIGHTINGS_FOR_DATE, new SightingMapper(), locId);  
    }
    
    public static final class SightingMapper implements RowMapper<Sighting> {
        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("SightingId"));
            sighting.setHeroId(rs.getInt("hero_HeroId"));
            sighting.setLocationId(rs.getInt("location_LocationId"));
            sighting.setDate(rs.getTimestamp("Date").toLocalDateTime());
            return sighting;
        }
    }
}
