/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.service;

import com.rlaraiii.superherosightings.data.HeroDao;
import com.rlaraiii.superherosightings.data.HeroDaoStub;
import com.rlaraiii.superherosightings.data.LocationDao;
import com.rlaraiii.superherosightings.data.LocationDaoStub;
import com.rlaraiii.superherosightings.data.SightingDao;
import com.rlaraiii.superherosightings.data.SightingDaoStub;
import com.rlaraiii.superherosightings.models.Sighting;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.springframework.validation.FieldError;

/**
 *
 * @author R Lara
 */
public class SightingServiceLayerTest {
    
    private SightingServiceLayer service;
    private HeroDao heroDao;
    private LocationDao locDao;
    private SightingDao sightingDao;
    
    public SightingServiceLayerTest() {
        this.sightingDao = new SightingDaoStub();
        this.heroDao = new HeroDaoStub();
        this.locDao = new LocationDaoStub();
        this.service = new SightingServiceLayerImpl(sightingDao, heroDao, locDao);
    }


    /**
     * Test of addSighting method, of class SightingServiceLayerImpl.
     */
    @Test
    public void testAddSighting() {
        Sighting sighting = new Sighting();
        sighting.setId(1);
        sighting.setLocationId(1);
        
        String[] heroIds = {"1"};
        String dateString = "2020-01-01T12:00:00";
        
        Set<ConstraintViolation<Sighting>> violations = new HashSet<>();
        
        violations = service.addSighting(sighting, heroIds, dateString);
        
        assertTrue(violations.isEmpty());
    }
    
    @Test
    public void testAddInvalidSighting() {
        Sighting sighting = new Sighting();
        sighting.setId(1);
        sighting.setLocationId(1);
        
        Set<ConstraintViolation<Sighting>> violations = new HashSet<>();
        
        String[] heroIds = {"1"};
        String dateString = "";
        
        violations = service.addSighting(sighting, heroIds, dateString);
        
        assertFalse(violations.isEmpty());
    }

    /**
     * Test of validateSightingDate method, of class SightingServiceLayerImpl.
     */
    @Test
    public void testValidateSightingDate() {
        Set<ConstraintViolation<Sighting>> violations = new HashSet<>();
        
        Sighting sighting = new Sighting();
        sighting.setId(1);
        sighting.setLocationId(1);
        sighting.setHeroId(1);
        
        String dateString = "2020-01-01T12:00:00";
        
        FieldError error = service.validateSightingDate(sighting, dateString);
        
        assertNull(error);
    }
    
}
