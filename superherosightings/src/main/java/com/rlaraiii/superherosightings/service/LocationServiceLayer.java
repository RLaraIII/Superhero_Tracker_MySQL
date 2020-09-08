/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.service;

import com.rlaraiii.superherosightings.models.Hero;
import com.rlaraiii.superherosightings.models.Location;
import com.rlaraiii.superherosightings.models.Sighting;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;

/**
 *
 * @author R Lara
 */
public interface LocationServiceLayer {
    public List<Location> getAllLocs();
    
    public Location getLoc(int id);
    
    public Set<ConstraintViolation<Location>> addLoc(Location loc);
    
    public void deleteLoc(Integer id);
    
    public void updateLoc(Location loc);
    
    public List<Sighting> listSightingsForLoc(Integer locId);
    
    public List<Hero> listHeroesAtLoc(Integer locId);
}
