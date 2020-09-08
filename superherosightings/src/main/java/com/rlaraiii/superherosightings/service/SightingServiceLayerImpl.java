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
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.validation.FieldError;

/**
 *
 * @author R Lara
 */
@Repository
public class SightingServiceLayerImpl implements SightingServiceLayer {

    @Autowired
    SightingDao sightingDao;

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locDao;

    @Override
    public List<Sighting> getAllSightings() {
        return sightingDao.getAllSightings();
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

    @Override
    public Set<ConstraintViolation<Sighting>> addSighting(Sighting sighting, String[] heroIds, String dateString) {
        Set<ConstraintViolation<Sighting>> violations = new HashSet<>();
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

        if (!dateString.isBlank()) {
            sighting.setDate(LocalDateTime.parse(dateString));
        }

        if (heroIds != null) {
            sighting.setHeroId(1);
        }
        
        violations = validate.validate(sighting);

        if (violations.isEmpty()) {
            for (String heroId : heroIds) {
                sighting.setHeroId(Integer.parseInt(heroId));
                sightingDao.addSighting(sighting);
            }
        }
        
        return violations;
    }

    @Override
    public void deleteSighting(Integer id) {
        sightingDao.deleteSightingById(id);
    }

    @Override
    public void updateSighting(Sighting sighting, LocalDateTime date) {
        sighting.setDate(date);

        sightingDao.updateSighting(sighting);

    }

    @Override
    public FieldError validateSightingDate(Sighting sighting, String dateString) {
        Set<ConstraintViolation<Sighting>> violations = new HashSet<>();
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

        if (!dateString.isBlank()) {
            LocalDateTime date = LocalDateTime.parse(dateString);
            sighting.setDate(date);
        } 
        
        violations = validate.validate(sighting);
        
        if (violations.isEmpty()) {
            return null;
        } else {
            return new FieldError("sighting", "date", "Date cannot be in the future");
        }
    }

}
