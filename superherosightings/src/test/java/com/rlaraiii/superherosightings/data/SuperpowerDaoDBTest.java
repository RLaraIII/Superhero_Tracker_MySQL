/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.data;

import com.rlaraiii.superherosightings.TestApplicationConfiguration;
import com.rlaraiii.superherosightings.models.Hero;
import com.rlaraiii.superherosightings.models.Location;
import com.rlaraiii.superherosightings.models.Organization;
import com.rlaraiii.superherosightings.models.Sighting;
import com.rlaraiii.superherosightings.models.Superpower;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author R Lara
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class SuperpowerDaoDBTest {
    
    @Autowired
    HeroDao heroDao;
    
    @Autowired
    LocationDao locDao;
    
    @Autowired
    OrganizationDao orgDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    SuperpowerDao powerDao;
  
    @Before
    public void setUp() {
        // Clear the test database of any data from previous tests

        List<Organization> orgs = orgDao.getAllOrganizations();

        for (Organization org : orgs) {
            orgDao.deleteOrganizationById(org.getId());
        }

        List<Hero> heros = heroDao.getAllHeroes();

        for (Hero hero : heros) {
            heroDao.deleteHeroById(hero.getId());
        }
        
        List<Superpower> powers = powerDao.getAllPowers();

        for (Superpower power : powers) {
            powerDao.deletePowerById(power.getId());
        }

        List<Sighting> sightings = sightingDao.getAllSightings();

        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getId());
        }

        List<Location> locs = locDao.getAllLocations();

        for (Location loc : locs) {
            locDao.deleteLocationById(loc.getId());
        }
    }

    @Test
    public void testAddGetPower() {
        Superpower aPower = new Superpower();
        
        aPower.setPower("Flying");
        
        aPower = powerDao.addPower(aPower);
        
        Superpower fromDao = powerDao.getPowerById(aPower.getId());
        
        assertEquals(aPower, fromDao);
    }

    @Test
    public void testGetAllPowers() {
        Superpower powerOne = new Superpower();
        powerOne.setPower("X-ray vision");
        
        powerOne = powerDao.addPower(powerOne);
        
        Superpower powerTwo = new Superpower();
        powerTwo.setPower("Flying");
        
        powerTwo = powerDao.addPower(powerTwo);
        
        List<Superpower> powers = powerDao.getAllPowers();
        
        assertEquals(2, powers.size());
        assertTrue(powers.contains(powerOne));
        assertTrue(powers.contains(powerTwo));
    }

    @Test
    public void testDeletePowerById() {
        Superpower aPower = new Superpower();
        
        aPower.setPower("Flying");
        
        aPower = powerDao.addPower(aPower);
        
        powerDao.deletePowerById(aPower.getId());
        
        Superpower fromDao = powerDao.getPowerById(aPower.getId());
        
        assertNull(fromDao);
    }
    
}
