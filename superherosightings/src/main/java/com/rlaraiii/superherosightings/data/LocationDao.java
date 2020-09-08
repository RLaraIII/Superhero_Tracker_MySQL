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
public interface LocationDao {
    /**
     * Returns a location object for a given id
     * Will return null if not found
     * @param id id of location 
     * @return location object if found, null otherwise
     */
    Location getLocationById(int id);
    
    /**
     * Returns a list of all locations in database
     * @return list of all locations in database
     */
    List<Location> getAllLocations();
    
    /**
     * Adds a location object to database
     * @param loc object to add to database
     * @return location object with id assigned by database
     */
    Location addLocation(Location loc);
    
    /**
     * Updates a location object in database with one given
     * @param loc location object to replace one in database
     */
    void updateLocation(Location loc);
    
    /**
     * Deletes a location object from database based on id
     * Will also remove any sightings at location
     * @param id id of location object to delete
     */
    void deleteLocationById(int id);
    
    /**
     * Returns a list of all locations a given hero was sighted at
     * @param heroId id of hero
     * @return list of all locations a given hero was sighted at
     */
    List<Location> getLocationsForHero(int heroId);
    
    
}
