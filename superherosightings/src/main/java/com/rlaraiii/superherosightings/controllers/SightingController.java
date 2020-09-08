/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.controllers;

import com.rlaraiii.superherosightings.models.Hero;
import com.rlaraiii.superherosightings.models.Location;
import com.rlaraiii.superherosightings.models.Sighting;
import com.rlaraiii.superherosightings.service.SightingServiceLayer;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author R Lara
 */
@Controller
public class SightingController {

    @Autowired
    SightingServiceLayer serviceLayer;

    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List sightings = serviceLayer.getAllSightings();
        List locations = serviceLayer.getAllLocs();
        List heroes = serviceLayer.getAllHeroes();
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        model.addAttribute("heroes", heroes);
        model.addAttribute("errors", violations);
        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {
        int locationId = Integer.parseInt(request.getParameter("locationId"));
        String[] heroIds = request.getParameterValues("heroId");
        String dateString = request.getParameter("date");

        Sighting sighting = new Sighting();
        sighting.setLocationId(locationId);

        violations = serviceLayer.addSighting(sighting, heroIds, dateString);
        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id, Model model) {
        Sighting sighting = serviceLayer.getSighting(id);
        Hero hero = serviceLayer.getHero(sighting.getHeroId());
        Location loc = serviceLayer.getLocation(sighting.getLocationId());
        
        model.addAttribute("sighting", sighting);
        model.addAttribute("hero", hero);
        model.addAttribute("location", loc);
        return "deleteSighting";
    }
    
    @PostMapping("deleteSighting")
    public String performDeleteSighting(Integer id) {        
        serviceLayer.deleteSighting(id);
        return "redirect:/sightings";
    }

    @GetMapping("cancelDeleteSighting")
    public String cancelDeleteSighting() {
        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = serviceLayer.getSighting(id);
        model.addAttribute("sighting", sighting);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(Sighting sighting, BindingResult result, HttpServletRequest request, Model model) {
        String dateString = request.getParameter("dateString");
        LocalDateTime date = null;

        FieldError error = serviceLayer.validateSightingDate(sighting, dateString);

        if (error != null) {
            result.addError(error);
        }
        
        if (result.hasErrors()) {
            model.addAttribute("sighting", sighting);
            return "editSighting";
        }

        date = LocalDateTime.parse(dateString);
        sighting.setDate(date);

        serviceLayer.updateSighting(sighting, date);
        return "redirect:/sightings";
    }

    @GetMapping("cancelEditSighting")
    public String cancelEditSighting() {
        return "redirect:/sightings";
    }

    @GetMapping("sightingDetails")
    public String displaySightingInfo(Integer id, Model model) {
        Sighting sighting = serviceLayer.getSighting(id);
        Location loc = serviceLayer.getLocation(sighting.getLocationId());
        Hero hero = serviceLayer.getHero(sighting.getHeroId());
        model.addAttribute("sighting", sighting);
        model.addAttribute("location", loc);
        model.addAttribute("hero", hero);
        return "sightingDetail";
    }
}
