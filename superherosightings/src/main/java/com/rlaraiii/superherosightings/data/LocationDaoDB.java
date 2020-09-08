/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.data;

import com.rlaraiii.superherosightings.models.*;
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
public class LocationDaoDB implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) {
        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM location WHERE LocationId = ?";
            Location loc = jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), id);
            return loc;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String SELECT_ALL_LOCATIONS = "SELECT * from location";
        List<Location> locs = jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());

        return locs;
    }

    @Override
    @Transactional
    public Location addLocation(Location loc) {
        final String INSERT_LOC = "INSERT INTO location(Name, Latitude, Longitude, Address, Description) "
                + "VALUES(?, ?, ?, ?, ?)";

        jdbc.update(INSERT_LOC,
                loc.getName(),
                loc.getLatitude(),
                loc.getLongitude(),
                loc.getAddress(),
                loc.getDescription());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        loc.setId(newId);
        return loc;
    }

    @Override
    public void updateLocation(Location loc) {
        final String UPDATE_LOC = "UPDATE location "
                + "SET LocationId = ?, Name = ?, Latitude = ?, Longitude = ?, Address = ?, Description = ? "
                + "WHERE LocationId = ?";
        jdbc.update(UPDATE_LOC,
                loc.getId(),
                loc.getName(),
                loc.getLatitude(),
                loc.getLongitude(),
                loc.getAddress(),
                loc.getDescription(),
                loc.getId());
    }

    @Override
    public void deleteLocationById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE location_LocationId = ?";
        jdbc.update(DELETE_SIGHTING, id);

        final String DELETE_LOC = "DELETE FROM location WHERE LocationId = ?";
        jdbc.update(DELETE_LOC, id);
    }

    @Override
    public List<Location> getLocationsForHero(int heroId) {
        final String SELECT_LOC_FOR_HERO = "SELECT loc.* FROM location loc "
                + "JOIN sighting s on loc.LocationId = s.location_LocationId "
                + "JOIN hero h on s.hero_HeroId = h.HeroId "
                + "WHERE h.HeroId = ? "
                + "ORDER BY loc.LocationId";
        return jdbc.query(SELECT_LOC_FOR_HERO, new LocationMapper(), heroId);
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location loc = new Location();
            loc.setId(rs.getInt("locationId"));
            loc.setName(rs.getString("Name"));
            loc.setLatitude(rs.getString("Latitude"));
            loc.setLongitude(rs.getString("Longitude"));
            loc.setAddress(rs.getString("Address"));
            loc.setDescription(rs.getString("Description"));
            return loc;
        }
    }

}
