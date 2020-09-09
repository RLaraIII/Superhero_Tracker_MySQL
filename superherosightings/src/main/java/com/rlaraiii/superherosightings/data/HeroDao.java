/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.data;

import com.rlaraiii.superherosightings.models.*;
import java.util.List;

/**
 *
 * @author R Lara
 */
public interface HeroDao {
    
    /**
     * Returns a hero object for given id
     * Will return null if no object found
     * @param id id value of hero object
     * @return Hero object if found, null otherwise
     */
    Hero getHeroById(int id);
    
    /**
     * Returns a list of all heroes
     * @return list containing all heroes in database
     */
    List<Hero> getAllHeroes();
    
    /**
     * Adds a hero object to database
     * @param hero hero object to add to database
     * @return hero object with id assigned by database
     */
    Hero addHero(Hero hero);
    
    /**
     * Updates hero object in database
     * @param hero hero object to be replace one on database
     */
    void updateHero(Hero hero);
    
    /**
     * Deletes a hero from database
     * Removes from organization and sightings as well
     * @param id 
     */
    void deleteHeroById(int id);
    
    /**
     * Lists all heroes for a given organization
     * @param orgId id of organization to find all heroes for
     * @return list containing all heroes connected to organization
     */
    List<Hero> getHeroesForOrganization(int orgId);
    
    /**
     * Lists all heroes that appeared at a given location
     * @param locId location id 
     * @return list containing all heroes for a given location
     */
    List<Hero> getHeroesAtLocation(int locId);
    
    /**
     * Lists all heroes with a given power
     * @param powerId power id
     * @return list containing all heroes with given power
     */
    List<Hero> getHeroesWithPowerId(int powerId);
    
    /**
     * Connects a hero object to a org object using a bridge
     * @param heroId id of hero
     * @param orgId id of organization
     */
    void addHeroToOrg(int heroId, int orgId);
    
    /**
     * Deletes all relationships between a hero and organizations
     * @param heroId id of hero
     */
    void clearMembership(int heroId);
}
