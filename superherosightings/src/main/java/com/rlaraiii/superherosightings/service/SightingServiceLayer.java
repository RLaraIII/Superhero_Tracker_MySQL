/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.service;

import com.rlaraiii.superherosightings.models.Hero;
import com.rlaraiii.superherosightings.models.Location;
import com.rlaraiii.superherosightings.models.Sighting;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import org.springframework.validation.FieldError;

/**
 *
 * @author R Lara
 */
public interface SightingServiceLayer {
    public List<Sighting> getAllSightings();
    
    public List<Hero> getAllHeroes();

    public List<Location> getAllLocs();
    
    public Sighting getSighting(int id);
    
    public Hero getHero(int id);
    
    public Location getLocation(int id);

    public Set<ConstraintViolation<Sighting>> addSighting(Sighting sighting, String[] heroIds, String dateString);
    
    public void deleteSighting(Integer id);
    
    public void updateSighting(Sighting sighting, LocalDateTime date);
    
    public FieldError validateSightingDate(Sighting sighting, String dateString);
}
