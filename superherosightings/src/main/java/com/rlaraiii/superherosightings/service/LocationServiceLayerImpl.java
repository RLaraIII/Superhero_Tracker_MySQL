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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author R Lara
 */
@Repository
public class LocationServiceLayerImpl implements LocationServiceLayer {
    @Autowired
    LocationDao locDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    HeroDao heroDao;

    @Override
    public List<Location> getAllLocs() {
        return locDao.getAllLocations();
    }

    @Override
    public Location getLoc(int id) {
        return locDao.getLocationById(id);
    }

    @Override
    public Set<ConstraintViolation<Location>> addLoc(Location loc) {
        Set<ConstraintViolation<Location>> violations = new HashSet<>();
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

        violations = validate.validate(loc);
        
        if (violations.isEmpty()) {
            locDao.addLocation(loc);
        }
        
        return violations;
    }

    @Override
    public void deleteLoc(Integer id) {
        locDao.deleteLocationById(id);
    }

    @Override
    public void updateLoc(Location loc) {
        locDao.updateLocation(loc);
    }

    @Override
    public List<Sighting> listSightingsForLoc(Integer locId) {
        return sightingDao.getSightingsForLocation(locId);
    }

    @Override
    public List<Hero> listHeroesAtLoc(Integer locId) {
        return heroDao.getHeroesAtLocation(locId);
    }
    
}
