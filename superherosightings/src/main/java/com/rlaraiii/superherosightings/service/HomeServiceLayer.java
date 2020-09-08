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

/**
 *
 * @author R Lara
 */
public interface HomeServiceLayer {
    public List<Sighting> getLatestSightings();
    
    public List<Hero> getAllHeroes();

    public List<Location> getAllLocs();
    
    public Sighting getSighting(int id);
    
    public Hero getHero(int id);
    
    public Location getLocation(int id);
}
