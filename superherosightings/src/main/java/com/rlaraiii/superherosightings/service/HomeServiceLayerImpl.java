/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.service;

import com.rlaraiii.superherosightings.data.HeroDao;
import com.rlaraiii.superherosightings.data.LocationDao;
import com.rlaraiii.superherosightings.data.SightingDao;
import com.rlaraiii.superherosightings.models.Hero;
import com.rlaraiii.superherosightings.models.Location;
import com.rlaraiii.superherosightings.models.Sighting;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author R Lara
 */
@Repository
public class HomeServiceLayerImpl implements HomeServiceLayer {

    @Autowired
    SightingDao sightingDao;

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locDao;
    
    @Override
    public List<Sighting> getLatestSightings() {
        List sightings = sightingDao.getAllSightings();
        
        if (sightings.size() <= 10) {
            return sightings;
        } else {
            return sightings.subList(0, 10);
        }
    }

    @Override
    public List<Hero> getAllHeroes() {
        return heroDao.getAllHeroes();
    }

    @Override
    public List<Location> getAllLocs() {
        return locDao.getAllLocations();
    }

    @Override
    public Sighting getSighting(int id) {
        return sightingDao.getSightingBySightingId(id);
    }

    @Override
    public Hero getHero(int id) {
        return heroDao.getHeroById(id);
    }

    @Override
    public Location getLocation(int id) {
        return locDao.getLocationById(id);
    }
    
}
