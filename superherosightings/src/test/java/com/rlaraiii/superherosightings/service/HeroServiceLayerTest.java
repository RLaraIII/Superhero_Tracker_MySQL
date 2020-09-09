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
import com.rlaraiii.superherosightings.data.OrganizationDao;
import com.rlaraiii.superherosightings.data.OrganizationDaoStub;
import com.rlaraiii.superherosightings.data.SuperpowerDao;
import com.rlaraiii.superherosightings.data.SuperpowerDaoStub;
import com.rlaraiii.superherosightings.models.Hero;
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
public class HeroServiceLayerTest {
    
    private HeroServiceLayer service;
    private HeroDao heroDao;
    private OrganizationDao orgDao;
    private SuperpowerDao powerDao;
    private LocationDao locDao;
    
    public HeroServiceLayerTest() {
        this.heroDao = new HeroDaoStub();
        this.orgDao = new OrganizationDaoStub();
        this.powerDao = new SuperpowerDaoStub();
        this.locDao = new LocationDaoStub();
        this.service = new HeroServiceLayerImpl(heroDao, orgDao, powerDao, locDao);
    }
    
    @Test
    public void testAddGetHero() {
        Hero hero = new Hero();
        
        hero.setName("Batman");
        hero.setDescription("Lives in a batcave");
        hero.setPowerId(1);
        
        String[] orgIds = {};
        
        Set<ConstraintViolation<Hero>> violations = new HashSet<>();
        
        violations = service.addHero(hero, orgIds);
        
        assertTrue(violations.isEmpty());
    }
    
    @Test
    public void testAddGetInvalidHero() {
        Hero hero = new Hero();

        hero.setDescription("Lives in a batcave");
        hero.setPowerId(1);
        String[] orgIds = {};
        
        Set<ConstraintViolation<Hero>> violations = new HashSet<>();
        
        violations = service.addHero(hero, orgIds);
        
        assertFalse(violations.isEmpty());
    }
}
