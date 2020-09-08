/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.data;

import com.rlaraiii.superherosightings.models.Sighting;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author R Lara
 */
public class SightingDaoStub implements SightingDao {
    
    private Sighting onlySighting;

    public SightingDaoStub() {
        this.onlySighting = new Sighting();
        this.onlySighting.setId(1);
        this.onlySighting.setHeroId(1);
        this.onlySighting.setLocationId(1);
        this.onlySighting.setDate(LocalDateTime.parse("2020-01-01T12:00:00"));
    }

    @Override
    public Sighting getSightingByIdsAndDate(int heroId, int locationId, LocalDateTime date) {
        if (onlySighting.getHeroId() == heroId && 
                onlySighting.getLocationId() == locationId &&
                onlySighting.getDate() == date) {
            return onlySighting;
        } else {
            return null;
        }
    }

    @Override
    public Sighting getSightingBySightingId(int sightingId) {
        if (onlySighting.getId() == sightingId) {
            return onlySighting;
        } else {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        List<Sighting> sightings = new ArrayList<>();
        sightings.add(onlySighting);
        return sightings;
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        if (onlySighting.equals(sighting)) {
            return onlySighting;
        } else {
            return null;
        }
    }

    @Override
    public void updateSighting(Sighting sighting) {
        // Do nothing
    }

    @Override
    public void deleteSightingById(int id) {
        // Do nothing
    }

    @Override
    public List<Sighting> getSightingsForLocation(int locId) {
        List<Sighting> sightings = new ArrayList<>();
        
        if (locId == 1) {
            sightings.add(onlySighting);
        }
        
        return sightings;
    }
    
}
