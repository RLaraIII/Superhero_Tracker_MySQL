/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.service;

import com.rlaraiii.superherosightings.data.HeroDao;
import com.rlaraiii.superherosightings.data.HeroDaoStub;
import com.rlaraiii.superherosightings.data.SuperpowerDao;
import com.rlaraiii.superherosightings.data.SuperpowerDaoStub;
import com.rlaraiii.superherosightings.models.Superpower;
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
public class SuperpowerServiceLayerTest {
    
    private SuperpowerServiceLayer service;
    
    private SuperpowerDao powerDao;
    private HeroDao heroDao;

    public SuperpowerServiceLayerTest() {
        this.powerDao = new SuperpowerDaoStub();
        this.heroDao = new HeroDaoStub();
        this.service = new SuperpowerServiceLayerImpl(powerDao, heroDao);
    }

    /**
     * Checks that a valid power will have no errors
     */
    @Test
    public void testAddPower() {
        Superpower power = new Superpower();
        
        power.setPower("None");
        power.setId(1);
        
        Set<ConstraintViolation<Superpower>> violations = new HashSet<>();
        
        violations = service.addPower(power);
        
        assertTrue(violations.isEmpty());
    }
    
    /**
     * Checks that a power without a power set will cause an error
     */
    @Test
    public void testAddGetInvalidPower() {
        Superpower power = new Superpower();

        power.setId(1);
        
        Set<ConstraintViolation<Superpower>> violations = new HashSet<>();
        
        violations = service.addPower(power);
        
        assertFalse(violations.isEmpty());
    }

}
