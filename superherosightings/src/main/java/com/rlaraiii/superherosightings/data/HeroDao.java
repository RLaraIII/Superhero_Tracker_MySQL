/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.data;

import com.rlaraiii.superherosightings.models.Location;
import com.rlaraiiii.superherosightings.entities.Hero;
import com.rlaraiiii.superherosightings.entities.Organization;
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
     * Returns a list of all heros
     * @return list containing all heros in database
     */
    List<Hero> getAllHeros();
    
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
     * Lists all heros for a given organization
     * @param org organization object to find all heros for
     * @return list containing all heros connected to organization
     */
    List<Hero> getHeroesForOrganization(Organization org);
    
    /**
     * Lists all heros that appeared at a given location
     * @param loc location object
     * @return list containing all heros for a given location
     */
    List<Hero> getHerosAtLocation(Location loc);
}
