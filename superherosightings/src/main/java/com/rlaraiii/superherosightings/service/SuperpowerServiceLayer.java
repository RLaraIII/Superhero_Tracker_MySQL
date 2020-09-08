/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.service;

import com.rlaraiii.superherosightings.models.*;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;

/**
 *
 * @author R Lara
 */
public interface SuperpowerServiceLayer {
    public List<Superpower> getAllPowers();
    
    public Superpower getPower(int id);
    
    public Set<ConstraintViolation<Superpower>> addPower(Superpower power);
    
    public void deletePower(Integer id);
    
    public void updatePower(Superpower power);
    
    public List<Hero> listHeroesWithPower(Integer locId);
}
