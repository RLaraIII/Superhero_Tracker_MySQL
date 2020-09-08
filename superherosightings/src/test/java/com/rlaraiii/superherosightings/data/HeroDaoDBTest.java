/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.data;

import com.rlaraiii.superherosightings.TestApplicationConfiguration;
import com.rlaraiii.superherosightings.models.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
public class HeroDaoDBTest {

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
    public void testAddGetHero() {
        Superpower human = new Superpower();
        human.setPower("Just a person");        
        human = powerDao.addPower(human);
        
        Hero aHero = new Hero();

        aHero.setName("Batman");
        aHero.setDescription("Has a lot of money");
        aHero.setPowerId(human.getId());
       
        aHero = heroDao.addHero(aHero);

        Hero fromDao = heroDao.getHeroById(aHero.getId());

        assertEquals(aHero, fromDao);
    }

    @Test
    public void testGetAllHeros() {
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
        
        Hero heroTwo = new Hero();
        heroTwo.setName("Superman");
        heroTwo.setDescription("Basically an alien");
        heroTwo.setPowerId(alien.getId());
        
        heroOne = heroDao.addHero(heroOne);
        heroTwo = heroDao.addHero(heroTwo);

        List<Hero> fromDao = heroDao.getAllHeroes();

        assertEquals(2, fromDao.size());
        assertTrue(fromDao.contains(heroOne));
        assertTrue(fromDao.contains(heroTwo));
    }

    @Test
    public void testDeleteHeroById() {
        Superpower human = new Superpower();
        human.setPower("Just a person");        
        human = powerDao.addPower(human);
        
        Hero aHero = new Hero();
        aHero.setName("Batman");
        aHero.setDescription("More money");
        aHero.setPowerId(human.getId());
        
        aHero = heroDao.addHero(aHero);

        heroDao.deleteHeroById(aHero.getId());

        Hero fromDao = heroDao.getHeroById(aHero.getId());

        assertNull(fromDao);
    }

    @Test
    public void testGetHeroesForOrganization() {
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
        
        Organization aOrg = new Organization();
        
        aOrg.setName("Batman club");
        
        List<Hero> membersList = new ArrayList<>();
        membersList.add(heroOne);
        
        aOrg.setMembers(membersList);
        aOrg = orgDao.addOrganization(aOrg);
        
        
        List<Hero> herosList = heroDao.getHeroesForOrganization(aOrg.getId());
        
        assertEquals(1, herosList.size());
        assertTrue(herosList.contains(heroOne));
    }

    @Test
    public void testGetHerosAtLocation() {
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
        
        Location aLoc = new Location();
        aLoc.setName("North Pole");
        aLoc.setLatitude("90.0000 N");
        aLoc.setLongitude("135.0000 W");
        aLoc = locDao.addLocation(aLoc);
        
        Sighting aSighting = new Sighting();
        
        aSighting.setHeroId(heroTwo.getId());
        aSighting.setLocationId(aLoc.getId());
        aSighting.setDate(LocalDateTime.parse("2020-01-05 12:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        
        aSighting = sightingDao.addSighting(aSighting);
        
        List<Hero> herosList = heroDao.getHeroesAtLocation(aLoc.getId());
        
        assertEquals(1, herosList.size());
        assertTrue(herosList.contains(heroTwo));
    }
}
