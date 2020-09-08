/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.data;

import com.rlaraiii.superherosightings.models.Superpower;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author R Lara
 */
public class SuperpowerDaoStub implements SuperpowerDao {

    private Superpower onlyPower;
    
    public SuperpowerDaoStub() {
        this.onlyPower = new Superpower();
        this.onlyPower.setId(1);
        this.onlyPower.setPower("None");
    }

    @Override
    public Superpower getPowerById(int id) {
        if (onlyPower.getId() == id) {
            return onlyPower;
        } else {
            return null;
        }
    }

    @Override
    public List<Superpower> getAllPowers() {
        List<Superpower> powerList = new ArrayList<>();
        powerList.add(onlyPower);
        return powerList;        
    }

    @Override
    public Superpower addPower(Superpower power) {
        if (onlyPower.equals(power)) {
            return onlyPower;
        } else {
            return null;
        }
    }

    @Override
    public void updatePower(Superpower power) {
        // Do nothing
    }

    @Override
    public void deletePowerById(int id) {
        // Do nothing
    }
    
}
