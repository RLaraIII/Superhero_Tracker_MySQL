/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.data;

import com.rlaraiii.superherosightings.models.Location;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author R Lara
 */
public class LocationDaoStub implements LocationDao {

    public Location onlyLoc;

    public LocationDaoStub() {
        this.onlyLoc = new Location();
        this.onlyLoc.setId(1);
        this.onlyLoc.setLatitude("123.000 N");
        this.onlyLoc.setLongitude("100.000 W");
        this.onlyLoc.setName("Gotham City");
        this.onlyLoc.setAddress("Gotham City");
        this.onlyLoc.setDescription("Big city");
    }

    @Override
    public Location getLocationById(int id) {
        if (onlyLoc.getId() == id) {
            return onlyLoc;
        } else {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        List<Location> locs = new ArrayList<>();
        locs.add(onlyLoc);
        return locs;
    }

    @Override
    public Location addLocation(Location loc) {
        if (onlyLoc.equals(loc)) {
            return onlyLoc;
        } else {
            return null;
        }
    }

    @Override
    public void updateLocation(Location loc) {
        // Do nothing
    }

    @Override
    public void deleteLocationById(int id) {
        // Do nothing
    }

    @Override
    public List<Location> getLocationsForHero(int heroId) {
        List<Location> locs = new ArrayList<>();
        if (heroId == 1) {
            locs.add(onlyLoc);
        }
        
        return locs;
    }

}
