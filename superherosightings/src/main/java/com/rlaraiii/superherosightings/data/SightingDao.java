/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.data;

import com.rlaraiii.superherosightings.models.Hero;
import com.rlaraiii.superherosightings.models.Sighting;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author R Lara
 */
public interface SightingDao {
    /**
     * Returns a sighting object for the given ids and date
     * Will return null if no sightings found 
     * @param heroId
     * @param locationId
     * @param date
     * @return sighting object for a hero at a location, null otherwise
     */
    Sighting getSightingByIdsAndDate(int heroId, int locationId, LocalDateTime date);
    
    /**
     * Returns a sighting object for a given id
     * @param sightingId Id of sighting 
     * @return sighting object for a hero at a location, null otherwise
     */
    Sighting getSightingBySightingId(int sightingId);
    
    /**
     * Lists all sightings currently in database
     * @return 
     */
    List<Sighting> getAllSightings();
    
    /**
     * Adds a sighting object to database
     * @param sighting
     * @return 
     */
    Sighting addSighting(Sighting sighting);
    
    /**
     * Updates a sighting object on database with one given
     * @param sighting object to replace one on database
     */
    void updateSighting(Sighting sighting);
    
    /**
     * Deletes a sighting from database 
     * @param id sighting id
     */
    void deleteSightingById(int id);
    
    /**
     * Lists all sightings for a given location
     * NOTE: Time should not be accounted, only the date 
     * @param date
     * @return 
     */
    List<Sighting> getSightingsForLocation(int locId);
}
