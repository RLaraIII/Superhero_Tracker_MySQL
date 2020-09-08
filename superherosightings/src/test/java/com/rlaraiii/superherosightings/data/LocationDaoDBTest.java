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
import java.time.LocalDateTime;
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
public class LocationDaoDBTest {
    
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
    public void testAddGetLocation() {
        Location loc = new Location();
        
        loc.setName("A place");
        loc.setLatitude("456.4100 N");
        loc.setLongitude("12.3300 W");
        loc.setDescription("Just a test place");
        loc.setAddress("123 Fake place, 12345");
        
        loc = locDao.addLocation(loc);
        
        Location fromDao = locDao.getLocationById(loc.getId());
        
        assertEquals(fromDao, loc);
    }

    @Test
    public void testGetAllLocations() {
        Location locOne = new Location();
        locOne.setName("Place one");
        locOne.setLatitude("1234 N");
        locOne.setLongitude("9876 E");
        
        locOne = locDao.addLocation(locOne);
        
        Location locTwo = new Location();
        locTwo.setName("Place two");
        locTwo.setLatitude("145 S");
        locTwo.setLongitude("100 W");
        
        locTwo = locDao.addLocation(locTwo);
        
        List<Location> locs = locDao.getAllLocations();
        
        assertEquals(2, locs.size());
        assertTrue(locs.contains(locOne));
        assertTrue(locs.contains(locTwo));
    }

    @Test
    public void testDeleteLocationById() {
        Location locOne = new Location();
        locOne.setName("Place one");
        locOne.setLatitude("1234 N");
        locOne.setLongitude("9876 E");
        
        locOne = locDao.addLocation(locOne);
        
        locDao.deleteLocationById(locOne.getId());
        
        Location fromDao = locDao.getLocationById(locOne.getId());
        
        assertNull(fromDao);
    }

    @Test
    public void testGetLocationsForHero() {
        Location locOne = new Location();
        locOne.setName("Gotham");
        locOne.setLatitude("456.4100 N");
        locOne.setLongitude("12.3300 W");
        locOne = locDao.addLocation(locOne);
        
        Location locTwo = new Location();
        locTwo.setName("North Pole");
        locTwo.setLatitude("145 S");
        locTwo.setLongitude("100 W");
        locTwo = locDao.addLocation(locTwo);
        
        Superpower human = new Superpower();
        human.setPower("Just a person");        
        human = powerDao.addPower(human);
        
        Superpower alien = new Superpower();
        alien.setPower("Alien being");
        alien = powerDao.addPower(alien);
        
        Hero heroOne = new Hero();
        heroOne.setName("Batman");
        heroOne.setDescription("Has a lot of money");
        heroOne.setPowerId(human.getId());
        heroOne = heroDao.addHero(heroOne);
        
        Hero heroTwo = new Hero();
        heroTwo.setName("Superman");
        heroTwo.setDescription("Basically an alien");
        heroTwo.setPowerId(alien.getId());
        heroTwo = heroDao.addHero(heroTwo);
        
        Sighting sighting = new Sighting();
        sighting.setHeroId(heroOne.getId());
        sighting.setLocationId(locOne.getId());
        sighting.setDate(LocalDateTime.parse("2020-09-01T12:00:00"));
        sighting = sightingDao.addSighting(sighting);
        
        List<Location> locs = locDao.getLocationsForHero(heroOne.getId());
        
        assertEquals(1, locs.size());
        assertTrue(locs.contains(locOne));
    }
}
