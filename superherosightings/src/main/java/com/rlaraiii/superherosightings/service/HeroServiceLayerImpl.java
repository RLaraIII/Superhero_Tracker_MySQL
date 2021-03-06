/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.service;

import com.rlaraiii.superherosightings.data.HeroDao;
import com.rlaraiii.superherosightings.data.LocationDao;
import com.rlaraiii.superherosightings.data.OrganizationDao;
import com.rlaraiii.superherosightings.data.SuperpowerDao;
import com.rlaraiii.superherosightings.models.Hero;
import com.rlaraiii.superherosightings.models.Location;
import com.rlaraiii.superherosightings.models.Organization;
import com.rlaraiii.superherosightings.models.Superpower;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.validation.FieldError;

/**
 *
 * @author R Lara
 */
@Repository
public class HeroServiceLayerImpl implements HeroServiceLayer {

    @Autowired
    HeroDao heroDao;

    @Autowired
    OrganizationDao orgDao;

    @Autowired
    SuperpowerDao powerDao;

    @Autowired
    LocationDao locDao;

    @Autowired
    public HeroServiceLayerImpl(HeroDao heroDao, OrganizationDao orgDao, SuperpowerDao powerDao, LocationDao locDao) {
        this.heroDao = heroDao;
        this.orgDao = orgDao;
        this.powerDao = powerDao;
        this.locDao = locDao;
    }

    @Override
    public List<Superpower> getAllPowers() {
        return powerDao.getAllPowers();
    }

    @Override
    public List<Hero> getAllHeroes() {
        return heroDao.getAllHeroes();
    }

    @Override
    public List<Organization> getAllOrgs() {
        return orgDao.getAllOrganizations();
    }

    @Override
    public Hero getHero(int id) {
        return heroDao.getHeroById(id);
    }

    @Override
    public Superpower getPower(int id) {
        return powerDao.getPowerById(id);
    }

    @Override
    public Set<ConstraintViolation<Hero>> addHero(Hero aHero, String[] orgIds) {
        Set<ConstraintViolation<Hero>> violations = new HashSet<>();
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        
        violations = validate.validate(aHero);

        if (violations.isEmpty()) {
            heroDao.addHero(aHero);

            for (String orgId : orgIds) {
                heroDao.addHeroToOrg(aHero.getId(), Integer.parseInt(orgId));
            }
        }

        return violations;
    }
    
    @Override
    public FieldError validateHeroName(Hero aHero) {
        List<Hero> heroes = heroDao.getAllHeroes();
        boolean flag = false;
        
        for (Hero hero : heroes) {
            if (hero.getName().equals(aHero.getName())) {
                flag = true;
            }
        }
        
        if (flag) {
            return new FieldError("hero", "name", "Name already taken");
        } else {
            return null;
        }
    }

    @Override
    public void deleteHero(Integer id) {
        heroDao.deleteHeroById(id);
    }

    @Override
    public void updateHero(Hero aHero, String[] orgIds) {
        heroDao.updateHero(aHero);

        heroDao.clearMembership(aHero.getId());

        if (orgIds != null) {
            for (String orgId : orgIds) {
                heroDao.addHeroToOrg(aHero.getId(), Integer.parseInt(orgId));
            }
        }
    }

    @Override
    public List<Organization> listOrgsForHero(Integer id) {
        return orgDao.getOrganizationsForHero(id);
    }

    @Override
    public List<Location> listLocsForHero(Integer id) {
        return locDao.getLocationsForHero(id);
    }

}
