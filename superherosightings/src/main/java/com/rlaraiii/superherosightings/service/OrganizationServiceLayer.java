/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.service;

import com.rlaraiii.superherosightings.models.Hero;
import com.rlaraiii.superherosightings.models.Organization;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;

/**
 *
 * @author R Lara
 */
public interface OrganizationServiceLayer {
    public List<Organization> getAllOrgs();
    
    public List<Hero> getAllHeroes();
    
    public Organization getOrg(int id);
    
    public Hero getHero(int id);
    
    public Set<ConstraintViolation<Organization>> addOrg(Organization org, String[] heroIds);
    
    public void deleteOrg(Integer id);
    
    public void updateOrg(Organization org, String[] heroIds);
    
    public List<Hero> listHeroesForOrg(Integer id);
}
