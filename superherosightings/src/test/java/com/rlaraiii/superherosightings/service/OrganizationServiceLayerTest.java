/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.service;

import com.rlaraiii.superherosightings.data.HeroDao;
import com.rlaraiii.superherosightings.data.HeroDaoStub;
import com.rlaraiii.superherosightings.data.OrganizationDao;
import com.rlaraiii.superherosightings.data.OrganizationDaoStub;
import com.rlaraiii.superherosightings.models.Hero;
import com.rlaraiii.superherosightings.models.Organization;
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
public class OrganizationServiceLayerTest {
    
    private OrganizationServiceLayer service;
    private OrganizationDao orgDao;
    private HeroDao heroDao;
    
    public OrganizationServiceLayerTest() {
        this.orgDao = new OrganizationDaoStub();
        this.heroDao = new HeroDaoStub();
        this.service = new OrganizationServiceLayerImpl(orgDao, heroDao);
    }

    @Test
    public void testAddOrg() {
        String[] members = new String[1];
        
        Hero hero = new Hero();
        hero.setId(1);
        hero.setName("Batman");
        hero.setPowerId(1);
        hero.setDescription("Lives in a batcave");
        
        members[0] = Integer.toString(hero.getId());
        
        Organization org = new Organization();
        org.setId(1);
        org.setName("Justice League");
        org.setDescription("Team of heroes");
        org.setAddress("123 Main St");
        org.setContact_info("911");
        
        Set<ConstraintViolation<Organization>> violations = new HashSet<>();
        
        violations = service.addOrg(org, members);
        
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testAddInvalidOrg() {
        String[] members = new String[1];
        
        Hero hero = new Hero();
        hero.setId(1);
        hero.setName("Batman");
        hero.setPowerId(1);
        hero.setDescription("Lives in a batcave");
        
        members[0] = Integer.toString(hero.getId());
        
        Organization org = new Organization();
        org.setId(1);
        org.setDescription("Team of heroes");
        org.setAddress("123 Main St");
        org.setContact_info("911");
        
        Set<ConstraintViolation<Organization>> violations = new HashSet<>();
        
        violations = service.addOrg(org, members);
        
        assertFalse(violations.isEmpty());
    }
    
}
