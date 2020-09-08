/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.controllers;

import com.rlaraiii.superherosightings.models.Superpower;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.rlaraiii.superherosightings.service.SuperpowerServiceLayer;

/**
 *
 * @author R Lara
 */
@Controller
public class SuperpowerController {

    @Autowired
    SuperpowerServiceLayer serviceLayer;

    Set<ConstraintViolation<Superpower>> violations = new HashSet<>();

    @GetMapping("superpowers")
    public String displayPowers(Model model) {
        List powers = serviceLayer.getAllPowers();
        model.addAttribute("superpowers", powers);
        model.addAttribute("errors", violations);
        return "superpowers";
    }

    @PostMapping("addSuperpower")
    public String addPower(String power) {
        Superpower aPower = new Superpower();
        aPower.setPower(power);

        violations = serviceLayer.addPower(aPower);

        return "redirect:/superpowers";
    }

    @GetMapping("deleteSuperpower")
    public String deletePower(Integer id, Model model) {
        Superpower power = serviceLayer.getPower(id);

        model.addAttribute("superpower", power);

        return "deleteSuperpower";
    }

    @PostMapping("deleteSuperpower")
    public String performDeletePower(Superpower power) {
        serviceLayer.deletePower(power.getId());
        return "redirect:/superpowers";
    }

    @GetMapping("cancelDeleteSuperpower")
    public String cancelDeleteSuperpower() {
        return "redirect:/superpowers";
    }

    @GetMapping("editSuperpower")
    public String editSuperpower(Integer id, Model model) {
        Superpower power = serviceLayer.getPower(id);

        model.addAttribute("superpower", power);
        return "editSuperpower";
    }

    @PostMapping("editSuperpower")
    public String performEditPower(@Valid Superpower power, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("superpower", power);
            return "editSuperpower";
        }
        serviceLayer.updatePower(power);
        return "redirect:/superpowers";
    }

    @GetMapping("cancelEditSuperpower")
    public String cancelEditSuperpower() {
        return "redirect:/superpowers";
    }

    @GetMapping("powerDetails")
    public String displayPowerInfo(Integer id, Model model) {
        Superpower power = serviceLayer.getPower(id);
        List heroes = serviceLayer.listHeroesWithPower(id);
        model.addAttribute("superpower", power);
        model.addAttribute("heroes", heroes);
        return "superpowerDetail";
    }
}
