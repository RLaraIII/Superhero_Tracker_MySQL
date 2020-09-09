/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.controllers;

import com.rlaraiii.superherosightings.models.Hero;
import com.rlaraiii.superherosightings.models.Superpower;
import com.rlaraiii.superherosightings.service.HeroServiceLayer;
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
public class HeroController {

    @Autowired
    HeroServiceLayer serviceLayer;

    Set<ConstraintViolation<Hero>> violations = new HashSet<>();

    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        List powers = serviceLayer.getAllPowers();
        List heroes = serviceLayer.getAllHeroes();
        List orgs = serviceLayer.getAllOrgs();
        model.addAttribute("superpowers", powers);
        model.addAttribute("heroes", heroes);
        model.addAttribute("organizations", orgs);
        model.addAttribute("errors", violations);
        return "heroes";
    }

    @PostMapping("addHero")
    public String addHero(String name, String description, Integer superpowerId, String[] organizationId) {
        Hero hero = new Hero();

        hero.setName(name);
        hero.setDescription(description);
        hero.setPowerId(superpowerId);

        violations = serviceLayer.addHero(hero, organizationId);

        return "redirect:/heroes";
    }

    @GetMapping("deleteHero")
    public String deleteHero(Integer id, Model model) {
        Hero hero = serviceLayer.getHero(id);
        model.addAttribute("hero", hero);
        return "deleteHero";
    }

    @PostMapping("deleteHero")
    public String performDeleteHero(Hero hero) {
        serviceLayer.deleteHero(hero.getId());
        return "redirect:/heroes";
    }

    @GetMapping("cancelDeleteHero")
    public String cancelDeleteHero() {
        return "redirect:/heroes";
    }

    @GetMapping("editHero")
    public String editHero(Integer id, Model model) {
        Hero hero = serviceLayer.getHero(id);
        List powers = serviceLayer.getAllPowers();
        List orgs = serviceLayer.getAllOrgs();
        List membership = serviceLayer.listOrgsForHero(id);
        model.addAttribute("superpowers", powers);
        model.addAttribute("hero", hero);
        model.addAttribute("organizations", orgs);
        model.addAttribute("membership", membership);
        return "editHero";
    }

    @PostMapping("editHero")
    public String performEditHero(@Valid Hero hero, BindingResult result, HttpServletRequest request, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("superpowers", serviceLayer.getAllPowers());
            model.addAttribute("hero", hero);
            model.addAttribute("organizations", serviceLayer.getAllOrgs());
            model.addAttribute("membership", serviceLayer.listOrgsForHero(hero.getId()));
            return "editHero";
        }
        String[] orgIds = request.getParameterValues("organizationId");
        serviceLayer.updateHero(hero, orgIds);
        return "redirect:/heroes";
    }

    @GetMapping("cancelEditHero")
    public String cancelEditHero() {
        return "redirect:/heroes";
    }

    @GetMapping("heroDetails")
    public String displayHeroDetails(Integer id, Model model) {
        Hero hero = serviceLayer.getHero(id);
        List orgs = serviceLayer.listOrgsForHero(id);
        List locs = serviceLayer.listLocsForHero(id);
        Superpower power = serviceLayer.getPower(hero.getPowerId());
        model.addAttribute("hero", hero);
        model.addAttribute("superpower", power);
        model.addAttribute("locations", locs);
        model.addAttribute("organizations", orgs);
        return "heroDetail";
    }
}
