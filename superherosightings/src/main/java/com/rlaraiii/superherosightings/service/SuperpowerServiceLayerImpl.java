/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.service;

import com.rlaraiii.superherosightings.data.HeroDao;
import com.rlaraiii.superherosightings.data.SuperpowerDao;
import com.rlaraiii.superherosightings.models.Hero;
import com.rlaraiii.superherosightings.models.Superpower;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author R Lara
 */
@Repository
public class SuperpowerServiceLayerImpl implements SuperpowerServiceLayer {

    @Autowired
    SuperpowerDao powerDao;
    
    @Autowired
    HeroDao heroDao;

    @Autowired
    public SuperpowerServiceLayerImpl(SuperpowerDao powerDao, HeroDao heroDao) {
        this.powerDao = powerDao;
        this.heroDao = heroDao;
    }
    
    @Override
    public Superpower getPower(int id) {
        return powerDao.getPowerById(id);
    }

    @Override
    public List<Superpower> getAllPowers() {
        return powerDao.getAllPowers();
    }

    @Override
    public Set<ConstraintViolation<Superpower>> addPower(Superpower aPower) {
        Set<ConstraintViolation<Superpower>> violations = new HashSet<>();
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

        violations = validate.validate(aPower);

        if (violations.isEmpty()) {
            powerDao.addPower(aPower);
        }

        return violations;
    }

    @Override
    public void deletePower(Integer id) {
        List<Superpower> powers = powerDao.getAllPowers();
        
        Superpower defaultPower = new Superpower();
        
        for (Superpower power : powers) {
            if (power.getPower().equalsIgnoreCase("None")) {
                defaultPower = power;
            }
        }
        
        if (defaultPower.getPower().isEmpty()) {
            defaultPower.setPower("None");
            defaultPower = powerDao.addPower(defaultPower);
        }
        
        List<Hero> heroes = heroDao.getHeroesWithPowerId(id);
        
        for (Hero aHero : heroes) {
            if (aHero.getPowerId() == id) {
                aHero.setPowerId(defaultPower.getId());
                heroDao.updateHero(aHero);
            }
        }
        
        powerDao.deletePowerById(id);
    }

    @Override
    public void updatePower(Superpower power) {
        powerDao.updatePower(power);
    }

    @Override
    public List<Hero> listHeroesWithPower(Integer powerId) {
        return heroDao.getHeroesWithPowerId(powerId);
    }
}
