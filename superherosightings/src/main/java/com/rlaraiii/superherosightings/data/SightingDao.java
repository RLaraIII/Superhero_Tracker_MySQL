/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.data;

import com.rlaraiii.superherosightings.models.Sighting;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author R Lara
 */
public interface SightingDao {
    /**
     * Returns a sighting object for a given id
     * Will return null if no sightings found 
     * @param id id of sighting object
     * @return sighting object for a hero at a location, null otherwise
     */
    Sighting getSightingById(int id);
    
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
     * Will also remove the entry in the bridge connecting hero to sighting
     * @param id id of sighting 
     */
    void deleteSightingById(int id);
    
    /**
     * Lists all sightings for a given date
     * NOTE: Time is not accounted, only date 
     * @param date
     * @return 
     */
    List<Sighting> getSightingsForDate(LocalDateTime date);
}
