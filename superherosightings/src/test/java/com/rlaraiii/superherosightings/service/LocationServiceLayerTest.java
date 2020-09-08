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
import com.rlaraiii.superherosightings.models.Location;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author R Lara
 */
public class LocationServiceLayerTest {

    private LocationServiceLayer service;
    private LocationDao locDao;
    private SightingDao sightingDao;
    private HeroDao heroDao;

    public LocationServiceLayerTest() {
        this.locDao = new LocationDaoStub();
        this.heroDao = new HeroDaoStub();
        this.sightingDao = new SightingDaoStub();
        this.service = new LocationServiceLayerImpl(locDao, sightingDao, heroDao);
    }

    /**
     * Test of addLoc method, of class LocationServiceLayerImpl.
     */
    @Test
    public void testAddLoc() {
        Location loc = new Location();

        loc.setId(1);
        loc.setLatitude("123.000 N");
        loc.setLongitude("100.000 W");
        loc.setName("Gotham City");
        loc.setAddress("Gotham City");
        loc.setDescription("Big city");
        
        Set<ConstraintViolation<Location>> violations = new HashSet<>();
        
        violations = service.addLoc(loc);
        
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testAddInvalidLoc() {
        Location loc = new Location();

        loc.setId(1);
        
        Set<ConstraintViolation<Location>> violations = new HashSet<>();
        
        violations = service.addLoc(loc);
        
        assertFalse(violations.isEmpty());
    }

}
