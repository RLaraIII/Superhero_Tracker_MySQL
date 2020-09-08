/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.service;

import com.rlaraiii.superherosightings.data.HeroDao;
import com.rlaraiii.superherosightings.data.OrganizationDao;
import com.rlaraiii.superherosightings.models.Hero;
import com.rlaraiii.superherosightings.models.Organization;
import java.util.ArrayList;
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
public class OrganizationServiceLayerImpl implements OrganizationServiceLayer {
    @Autowired
    OrganizationDao orgDao;
    
    @Autowired
    HeroDao heroDao;

    @Autowired
    public OrganizationServiceLayerImpl(OrganizationDao orgDao, HeroDao heroDao) {
        this.orgDao = orgDao;
        this.heroDao = heroDao;
    }
    
    @Override
    public List<Organization> getAllOrgs() {
        return orgDao.getAllOrganizations();
    }
    
    @Override
    public List<Hero> getAllHeroes() {
        return heroDao.getAllHeroes();
    }

    @Override
    public Organization getOrg(int id) {
        return orgDao.getOrganizationById(id);
    }
    
    @Override
    public Hero getHero(int id) {
        return heroDao.getHeroById(id);
    }

    @Override
    public Set<ConstraintViolation<Organization>> addOrg(Organization org, String[] heroIds) {
        Set<ConstraintViolation<Organization>> violations = new HashSet<>();
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        
        violations = validate.validate(org);

        if (violations.isEmpty()) {
            List<Hero> heroes = new ArrayList<>();
            for (String heroId : heroIds) {
                heroes.add(heroDao.getHeroById(Integer.parseInt(heroId)));
            }
            
            org.setMembers(heroes);
            org = orgDao.addOrganization(org);
        }
        
        return violations;
    }

    @Override
    public void deleteOrg(Integer id) {
        orgDao.deleteOrganizationById(id);
    }

    @Override
    public void updateOrg(Organization org, String[] heroIds) {
        List<Hero> members = new ArrayList<>();
        
        for (String heroId : heroIds) {
            members.add(heroDao.getHeroById(Integer.parseInt(heroId)));
        }
        org.setMembers(members);
        orgDao.updateOrganization(org);
    }

    @Override
    public List<Hero> listHeroesForOrg(Integer id) {
        return heroDao.getHeroesForOrganization(id);
    }

    
}
