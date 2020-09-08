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
public interface OrganizationDao {
    /**
     * Returns an organization for a given id
     * Will return null if not found
     * @param id id of organization
     * @return organization object if found, null otherwise
     */
    Organization getOrganizationById(int id);
    
    /**
     * Lists all organizations currently in the database
     * @return list of all organizations in database
     */
    List<Organization> getAllOrganizations();
    
    /**
     * Adds an organization object to database
     * @param org organization object to add to database
     * @return organization object with id set by database
     */
    Organization addOrganization(Organization org);
    
    /**
     * Replaces an organization object in database with one given
     * @param org organization object to replace one in database
     */
    void updateOrganization(Organization org);
    
    /**
     * Deletes a organization from database
     * Will also delete the association between organization and heros
     * @param id 
     */
    void deleteOrganizationById(int id);
    
    /**
     * Returns a list of all organizations a given hero is associated with
     * @param heroId id of heroS
     * @return list containing all organizations given hero is a part of
     */
    List<Organization> getOrganizationsForHero(int heroId);
}
