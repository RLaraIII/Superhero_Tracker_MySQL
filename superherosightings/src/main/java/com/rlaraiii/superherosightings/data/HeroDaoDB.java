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
public class HeroDaoDB implements HeroDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Hero getHeroById(int id) {
        try {
            final String SELECT_HERO_BY_ID = "SELECT * FROM hero WHERE HeroId = ?";
            Hero hero = jdbc.queryForObject(SELECT_HERO_BY_ID, new HeroMapper(), id);
            return hero;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Hero> getAllHeroes() {
        final String SELECT_ALL_HEROES = "SELECT * from hero";
        List <Hero> heroes = jdbc.query(SELECT_ALL_HEROES, new HeroMapper());
        
        return heroes;
    }

    @Override
    @Transactional
    public Hero addHero(Hero hero) {
        final String INSERT_HERO = "INSERT INTO hero(Name, Description, superpower_SuperpowerId) "
                + "VALUES (?, ?, ?)";
        
        jdbc.update(INSERT_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.getPowerId());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setId(newId);        
        return hero;
    }

    @Override
    @Transactional
    public void updateHero(Hero hero) {
        final String UPDATE_HERO = "UPDATE hero "
                + "SET HeroId = ?, Name = ?, Description = ?, superpower_SuperpowerId = ? "
                + "WHERE HeroId = ?";
        jdbc.update(UPDATE_HERO,
                hero.getId(),
                hero.getName(),
                hero.getDescription(),
                hero.getPowerId(),
                hero.getId()); 
    }

    @Override
    public void deleteHeroById(int id) {
        final String DELETE_HERO_ORGANIZATION = "DELETE FROM organization_has_hero WHERE organization_has_hero.hero_HeroId = ?";
        jdbc.update(DELETE_HERO_ORGANIZATION, id);
        
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE hero_HeroId = ?";
        jdbc.update(DELETE_SIGHTING, id);
        
        final String DELETE_HERO = "DELETE FROM hero WHERE HeroId = ?";
        jdbc.update(DELETE_HERO, id);
    }

    @Override
    public List<Hero> getHeroesForOrganization(int orgId) {
        final String SELECT_HEROES_FROM_ORGANIZATION = "SELECT h.* FROM hero h "
                + "JOIN organization_has_hero ohh ON ohh.hero_HeroId = h.HeroId "
                + "JOIN organization o ON ohh.organization_OrganizationID = o.OrganizationId "
                + "WHERE o.OrganizationId = ? "
                + "ORDER BY h.Name";
        return jdbc.query(SELECT_HEROES_FROM_ORGANIZATION, new HeroMapper(), orgId);
    }

    @Override
    public List<Hero> getHeroesAtLocation(int locId) {
        final String SELECT_HEROES_AT_LOC = "SELECT h.* FROM hero h "
                + "JOIN sighting s ON h.HeroId = s.hero_HeroId "
                + "JOIN location loc ON s.location_LocationId = loc.LocationId "
                + "WHERE loc.LocationId = ? "
                + "GROUP BY h.HeroId "
                + "ORDER BY h.HeroId";
        return jdbc.query(SELECT_HEROES_AT_LOC, new HeroMapper(), locId);
    }

    @Override
    public List<Hero> getHeroesWithPowerId(int powerId) {
        final String SELECT_HEROES_WITH_POWER = "SELECT h.* from hero h "
                + "WHERE h.superpower_SuperpowerId = ?";
        return jdbc.query(SELECT_HEROES_WITH_POWER, new HeroMapper(), powerId);
    }
    
    public static final class HeroMapper implements RowMapper<Hero> {
        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            Hero hero = new Hero();
            hero.setId(rs.getInt("HeroId"));
            hero.setName(rs.getString("Name"));
            hero.setDescription(rs.getString("Description"));
            hero.setPowerId(rs.getInt("superpower_SuperpowerId"));
            return hero;
        }
    }
}
