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
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
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
public class OrganizationDaoDBTest {
    
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
    public void testAddGetOrganization() {
        Superpower human = new Superpower();
        human.setPower("Alien");        
        human = powerDao.addPower(human);
        
        Hero hero = new Hero();
        hero.setName("Superman");
        hero.setPowerId(human.getId());
        hero = heroDao.addHero(hero);
        
        List<Hero> members = new ArrayList<>();
        members.add(hero);
        
        Organization aOrg = new Organization();

        aOrg.setName("Justice League");
        aOrg.setAddress("Space");
        aOrg.setMembers(members);
        aOrg = orgDao.addOrganization(aOrg);

        Organization fromDao = orgDao.getOrganizationById(aOrg.getId());

        assertEquals(aOrg, fromDao);
    }

    @Test
    public void testGetAllOrganizations() {
        Superpower human = new Superpower();
        human.setPower("Alien");        
        human = powerDao.addPower(human);
        
        Hero hero = new Hero();
        hero.setName("Superman");
        hero.setPowerId(human.getId());
        hero = heroDao.addHero(hero);
        
        List<Hero> members = new ArrayList<>();
        members.add(hero);
        
        Organization orgOne = new Organization();
        orgOne.setName("First org");
        orgOne.setMembers(members);
        orgOne = orgDao.addOrganization(orgOne);
        
        Organization orgTwo = new Organization();
        orgTwo.setName("Second org");
        orgTwo.setMembers(members);
        orgTwo = orgDao.addOrganization(orgTwo);
        
        List<Organization> orgs = orgDao.getAllOrganizations();
        
        assertEquals(2, orgs.size());
        assertTrue(orgs.contains(orgOne));
        assertTrue(orgs.contains(orgTwo));
    }

    @Test
    public void testDeleteOrganizationById() {
        Superpower human = new Superpower();
        human.setPower("Alien");        
        human = powerDao.addPower(human);
        
        Hero hero = new Hero();
        hero.setName("Superman");
        hero.setPowerId(human.getId());
        hero = heroDao.addHero(hero);
        
        List<Hero> members = new ArrayList<>();
        members.add(hero);
        
        Organization aOrg = new Organization();

        aOrg.setName("Justice League");
        aOrg.setAddress("Space");
        aOrg.setMembers(members);
        aOrg = orgDao.addOrganization(aOrg);
        
        orgDao.deleteOrganizationById(aOrg.getId());
        
        Organization fromDao = orgDao.getOrganizationById(aOrg.getId());
        
        assertNull(fromDao);
    }

    @Test
    public void testGetOrganizationsForHero() {
        Superpower human = new Superpower();
        human.setPower("Just a person");        
        human = powerDao.addPower(human);
        
        Hero heroOne = new Hero();
        heroOne.setName("Civi");
        heroOne.setPowerId(human.getId());
        heroOne = heroDao.addHero(heroOne);
        
        List<Hero> membersForOrgOne = new ArrayList<>();
        membersForOrgOne.add(heroOne);
        
        Organization orgOne = new Organization();
        orgOne.setName("First org");
        orgOne.setMembers(membersForOrgOne);

        orgOne = orgDao.addOrganization(orgOne);
        
        Superpower goodPower = new Superpower();
        goodPower.setPower("Proper power");
        goodPower = powerDao.addPower(goodPower);

        Hero heroTwo = new Hero();
        heroTwo.setName("Hero Man");
        heroTwo.setPowerId(goodPower.getId());
        heroTwo = heroDao.addHero(heroTwo);

        List<Hero> membersForOrgTwo = new ArrayList<>();
        membersForOrgTwo.add(heroTwo);

        Organization orgTwo = new Organization();
        orgTwo.setName("Second org");
        orgTwo.setMembers(membersForOrgTwo);
        
        orgTwo = orgDao.addOrganization(orgTwo);        
        
        List<Organization> orgList = orgDao.getOrganizationsForHero(heroOne.getId());
        
        assertEquals(1, orgList.size());
        assertTrue(orgList.contains(orgOne));
    }
}
