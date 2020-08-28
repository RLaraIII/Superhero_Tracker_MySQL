/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.data;

import com.rlaraiiii.superherosightings.entities.Superpower;
import java.util.List;

/**
 *
 * @author R Lara
 */
public interface SuperpowerDao {
    /**
     * Returns a superpower based on a given id
     * Will return null if not found
     * @param id id for wanted superpower
     * @return 
     */
    Superpower getPowerById(int id);
    
    /**
     * Returns a list containing all superpowers in the database
     * @return list containing all superpowers in database
     */
    List<Superpower> getAllPowers();
    
    /**
     * Adds a superpower to database
     * @param power superpower object to add to database
     * @return superpower object with id assigned by the database
     */
    Superpower addPower(Superpower power);
    
    /**
     * Updates a superpower in the database
     * with the one given 
     * @param power superpower object used to update an existing power 
     * in the database
     */
    void updatePower(Superpower power);
    
    /**
     * Deletes a superpower from database
     * Will also remove the power from any superheros who have it
     * @param id Id of superpower to remove
     */
    void deletePowerById(int id);
    
    /**
     * Lists all superpowers for a given superhero
     * @param heroId id of superhero
     * @return list containing all powers hero currently has
     */
    List<Superpower> getAllSuperpowersForHero(int heroId);
}
