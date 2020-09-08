/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.rlaraiii.superherosightings.service.HomeServiceLayer;

/**
 *
 * @author R Lara
 */
@Controller
public class HomeController {
    @Autowired
    HomeServiceLayer serviceLayer;
    
    @GetMapping("home")
    public String displayRecentSightings(Model model) {
        List sightings = serviceLayer.getLatestSightings();
        List locations = serviceLayer.getAllLocs();
        List heroes = serviceLayer.getAllHeroes();
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        model.addAttribute("heroes", heroes);
        return "home";
    }
}
