/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.data;

import com.rlaraiii.superherosightings.data.HeroDaoDB.HeroMapper;
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
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String SELECT_ORG_BY_ID = "SELECT * FROM organization WHERE OrganizationId = ?";
            Organization org = jdbc.queryForObject(SELECT_ORG_BY_ID, new OrgMapper(), id);
            org.setMembers(getHeroesForOrg(id));
            return org;            
        } catch (DataAccessException ex) {
            return null;
        }
    }
    
    private List<Hero> getHeroesForOrg(int id) {
        final String SELECT_HERO_FOR_ORG = "SELECT h.* FROM hero h "
                + "JOIN organization_has_hero ohh ON h.HeroId = ohh.hero_HeroId "
                + "WHERE ohh.organization_OrganizationId = ?";
        return jdbc.query(SELECT_HERO_FOR_ORG, new HeroMapper(), id);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGS = "SELECT * from organization";
        List<Organization> orgs = jdbc.query(SELECT_ALL_ORGS, new OrgMapper());
        associateHeroesWithOrgs(orgs);
        return orgs;
    }
    
    private void associateHeroesWithOrgs(List<Organization> orgs) {
        for (Organization org : orgs) {
            org.setMembers(getHeroesForOrg(org.getId()));
        }
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization org) {
        final String INSERT_ORG = "INSERT INTO organization(Name, Description, Address, Contact_info) "
                + "VALUES (?, ?, ?, ?)";
        jdbc.update(INSERT_ORG,
                org.getName(),
                org.getDescription(),
                org.getAddress(),
                org.getContact_info());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setId(newId);
        insertOrgHero(org);
        return org;                
    }
    
    private void insertOrgHero(Organization org) {
        final String INSERT_ORG_HERO = "INSERT INTO organization_has_hero(organization_OrganizationId, hero_HeroId) "
                + "VALUES(?, ?)";
        for (Hero hero : org.getMembers()) {
            jdbc.update(INSERT_ORG_HERO,
                    org.getId(),
                    hero.getId());
        }
    }

    @Override
    @Transactional
    public void updateOrganization(Organization org) {
        final String UPDATE_ORG = "UPDATE organization "
                + "SET OrganizationId = ?, Name = ?, Description = ?, Address = ?, Contact_info = ? "
                + "WHERE OrganizationId = ?";
        jdbc.update(UPDATE_ORG, 
                org.getId(),
                org.getName(),
                org.getDescription(),
                org.getAddress(),
                org.getContact_info(),
                org.getId());
        
        final String DELETE_ORG_HERO = "DELETE FROM organization_has_hero WHERE organization_OrganizationId = ?";
        jdbc.update(DELETE_ORG_HERO, org.getId());
        insertOrgHero(org);
    }

    @Override
    public void deleteOrganizationById(int id) {
        final String DELETE_HERO_ORGANIZATION = "DELETE FROM organization_has_hero WHERE organization_has_hero.organization_OrganizationId = ?";
        jdbc.update(DELETE_HERO_ORGANIZATION, id);
        
        final String DELETE_ORG = "DELETE FROM organization WHERE OrganizationId = ?";
        jdbc.update(DELETE_ORG, id);
    }

    @Override
    public List<Organization> getOrganizationsForHero(int heroId) {
        final String SELECT_ORG_FROM_HERO = "SELECT o.* FROM organization o "
                + "JOIN organization_has_hero ohh ON ohh.organization_OrganizationId = o.OrganizationId "
                + "JOIN hero h ON ohh.hero_HeroId = h.HeroId "
                + "WHERE h.HeroId = ? "
                + "ORDER BY o.Name";
        List<Organization> orgs = jdbc.query(SELECT_ORG_FROM_HERO, new OrgMapper(), heroId);
        associateHeroesWithOrgs(orgs);
        return orgs;
    }
    
    public static final class OrgMapper implements RowMapper<Organization> {
        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization org = new Organization();
            org.setId(rs.getInt("OrganizationId"));
            org.setName(rs.getString("Name"));
            org.setDescription(rs.getString("Description"));
            org.setAddress(rs.getString("Address"));
            org.setContact_info(rs.getString("Contact_info"));
            return org;
        }
    }
}
