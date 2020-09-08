/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.controllers;

import com.rlaraiii.superherosightings.models.Location;
import com.rlaraiii.superherosightings.service.LocationServiceLayer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author R Lara
 */
@Controller
public class LocationController {

    @Autowired
    LocationServiceLayer serviceLayer;

    Set<ConstraintViolation<Location>> violations = new HashSet<>();

    @GetMapping("locations")
    public String displayLocations(Model model) {
        List locs = serviceLayer.getAllLocs();
        model.addAttribute("locations", locs);
        model.addAttribute("errors", violations);
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request) {
        String name = request.getParameter("name");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        String address = request.getParameter("address");
        String description = request.getParameter("description");

        Location loc = new Location();

        loc.setName(name);
        loc.setLatitude(latitude);
        loc.setLongitude(longitude);
        loc.setAddress(address);
        loc.setDescription(description);

        violations = serviceLayer.addLoc(loc);

        return "redirect:/locations";
    }

    @GetMapping("deleteLocation")
    public String deleteLoc(Integer id, Model model) {
        Location loc = serviceLayer.getLoc(id);
        model.addAttribute("location", loc);

        return "deleteLocation";
    }

    @PostMapping("deleteLocation")
    public String performDeleteLoc(Location loc) {
        serviceLayer.deleteLoc(loc.getId());
        return "redirect:/locations";
    }

    @GetMapping("cancelDeleteLocation")
    public String cancelDeleteLoc() {
        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(Integer id, Model model) {
        Location loc = serviceLayer.getLoc(id);
        model.addAttribute("location", loc);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(@Valid Location loc, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("location", loc);
            return "editLocation";
        }
        serviceLayer.updateLoc(loc);
        return "redirect:/locations";
    }

    @GetMapping("cancelEditLocation")
    public String cancelEditLocation() {
        return "redirect:/locations";
    }

    @GetMapping("locDetails")
    public String displayLocInfo(Integer id, Model model) {
        Location loc = serviceLayer.getLoc(id);
        List sightings = serviceLayer.listSightingsForLoc(id);
        List heroes = serviceLayer.listHeroesAtLoc(id);
        
        model.addAttribute("location", loc);
        model.addAttribute("sightings", sightings);
        model.addAttribute("heroes", heroes);
        return "locationDetail";
    }
}
