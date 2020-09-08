/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.data;

import com.rlaraiii.superherosightings.TestApplicationConfiguration;
import com.rlaraiii.superherosightings.models.*;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
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
public class SightingDaoDBTest {

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
    public void testGetSightingByIdsAndDate() {
        Superpower human = new Superpower();
        human.setPower("Just a person");        
        human = powerDao.addPower(human);
        
        Hero aHero = new Hero();

        aHero.setName("Batman");
        aHero.setDescription("Has a lot of money");
        aHero.setPowerId(human.getId());
        aHero = heroDao.addHero(aHero);
        
        Location loc = new Location();
        
        loc.setName("Gotham");
        loc.setLatitude("456.4100 N");
        loc.setLongitude("12.3300 W");
        loc = locDao.addLocation(loc);

        String dateString = "2020-09-03T12:30:00";
        LocalDateTime date = LocalDateTime.parse(dateString);
        
        Sighting sighting = new Sighting();
        sighting.setHeroId(aHero.getId());
        sighting.setLocationId(loc.getId());
        sighting.setDate(date);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingByIdsAndDate(
                aHero.getId(), loc.getId(), date);
        
        assertEquals(fromDao, sighting);
    }

    @Test
    public void testGetSightingBySightingId() {
        Superpower human = new Superpower();
        human.setPower("Just a person");        
        human = powerDao.addPower(human);
        
        Hero aHero = new Hero();

        aHero.setName("Batman");
        aHero.setDescription("Has a lot of money");
        aHero.setPowerId(human.getId());
        aHero = heroDao.addHero(aHero);
        
        Location loc = new Location();
        
        loc.setName("Gotham");
        loc.setLatitude("456.4100 N");
        loc.setLongitude("12.3300 W");
        loc = locDao.addLocation(loc);
        
        String dateString = "2020-09-03T12:30:00";
        LocalDateTime date = LocalDateTime.parse(dateString);
        
        Sighting sighting = new Sighting();
        sighting.setHeroId(aHero.getId());
        sighting.setLocationId(loc.getId());
        sighting.setDate(date);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingBySightingId(sighting.getId());
        
        assertEquals(fromDao, sighting);
    }

    @Test
    public void testGetAllSightings() {
        Superpower human = new Superpower();
        human.setPower("Just a person");        
        human = powerDao.addPower(human);
        
        Hero aHero = new Hero();

        aHero.setName("Batman");
        aHero.setDescription("Has a lot of money");
        aHero.setPowerId(human.getId());
        aHero = heroDao.addHero(aHero);
        
        Location loc = new Location();
        
        loc.setName("Gotham");
        loc.setLatitude("456.4100 N");
        loc.setLongitude("12.3300 W");
        loc = locDao.addLocation(loc);
        
        
        String dateString = "2020-09-03T12:30:00";
        LocalDateTime date = LocalDateTime.parse(dateString);
        
        Sighting sightingOne = new Sighting();
        sightingOne.setHeroId(aHero.getId());
        sightingOne.setLocationId(loc.getId());
        sightingOne.setDate(date);
        sightingOne = sightingDao.addSighting(sightingOne);
        
        dateString = "2020-12-03T12:30:00";
        date = LocalDateTime.parse(dateString);
        
        Sighting sightingTwo = new Sighting();
        sightingTwo.setHeroId(aHero.getId());
        sightingTwo.setLocationId(loc.getId());
        sightingTwo.setDate(date);
        sightingTwo = sightingDao.addSighting(sightingTwo);
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sightingOne));
        assertTrue(sightings.contains(sightingTwo));
    }

    @Test
    public void testDeleteSightingById() {
        Superpower human = new Superpower();
        human.setPower("Just a person");        
        human = powerDao.addPower(human);
        
        Hero aHero = new Hero();

        aHero.setName("Batman");
        aHero.setDescription("Has a lot of money");
        aHero.setPowerId(human.getId());
        aHero = heroDao.addHero(aHero);
        
        Location loc = new Location();
        
        loc.setName("Gotham");
        loc.setLatitude("456.4100 N");
        loc.setLongitude("12.3300 W");
        loc = locDao.addLocation(loc);
        
        String dateString = "2020-09-03T12:30:00";
        LocalDateTime date = LocalDateTime.parse(dateString);
        
        Sighting sighting = new Sighting();
        sighting.setHeroId(aHero.getId());
        sighting.setLocationId(loc.getId());
        sighting.setDate(date);
        sighting = sightingDao.addSighting(sighting);
        
        sightingDao.deleteSightingById(sighting.getId());
        
        Sighting fromDao = sightingDao.getSightingBySightingId(sighting.getId());
        
        assertNull(fromDao);   
    }
}
