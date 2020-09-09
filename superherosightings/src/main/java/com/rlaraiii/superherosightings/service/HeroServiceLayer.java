/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.service;

import com.rlaraiii.superherosightings.models.Hero;
import com.rlaraiii.superherosightings.models.Location;
import com.rlaraiii.superherosightings.models.Organization;
import com.rlaraiii.superherosightings.models.Superpower;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import org.springframework.validation.FieldError;

/**
 *
 * @author R Lara
 */
public interface HeroServiceLayer {
    public List<Superpower> getAllPowers();
    
    public List<Hero> getAllHeroes();
    
    public List<Organization> getAllOrgs();
    
    public Hero getHero(int id);
    
    public Superpower getPower(int id);
    
    public Set<ConstraintViolation<Hero>> addHero(Hero aHero, String[] orgIds);
    
    public void deleteHero(Integer id);
    
    public void updateHero(Hero aHero, String[] orgIds);
    
    public List<Organization> listOrgsForHero(Integer id);
        
    public List<Location> listLocsForHero(Integer id);
    
    public FieldError validateHeroName(Hero aHero);
}
