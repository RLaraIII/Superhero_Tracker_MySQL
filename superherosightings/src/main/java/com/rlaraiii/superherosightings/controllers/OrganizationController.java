/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.controllers;

import com.rlaraiii.superherosightings.models.Organization;
import com.rlaraiii.superherosightings.service.OrganizationServiceLayer;
import java.util.ArrayList;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author R Lara
 */
@Controller
public class OrganizationController {

    @Autowired
    OrganizationServiceLayer serviceLayer;

    Set<ConstraintViolation<Organization>> violations = new HashSet<>();

    @GetMapping("organizations")
    public String displayOrgs(Model model) {
        List orgs = serviceLayer.getAllOrgs();
        List heroes = serviceLayer.getAllHeroes();
        model.addAttribute("organizations", orgs);
        model.addAttribute("heroes", heroes);
        model.addAttribute("errors", violations);
        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrg(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String contact_info = request.getParameter("contact_info");
        String[] heroIds = request.getParameterValues("heroId");

        Organization org = new Organization();

        org.setName(name);
        org.setDescription(description);
        org.setAddress(address);
        org.setContact_info(contact_info);

        violations = serviceLayer.addOrg(org, heroIds);

        return "redirect:/organizations";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrg(Integer id, Model model) {
        Organization org = serviceLayer.getOrg(id);

        model.addAttribute("organization", org);

        return "deleteOrganization";
    }

    @PostMapping("deleteOrganization")
    public String performDeleteOrg(Organization org) {
        serviceLayer.deleteOrg(org.getId());
        return "redirect:/organizations";
    }

    @GetMapping("cancelDeleteOrganization")
    public String cancelDeleteOrg() {
        return "redirect:/organizations";
    }

    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organization org = serviceLayer.getOrg(id);
        List heroes = serviceLayer.getAllHeroes();
        List members = serviceLayer.listHeroesForOrg(id);
        model.addAttribute("organization", org);
        model.addAttribute("heroes", heroes);
        model.addAttribute("members", members);
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(@Valid Organization org, BindingResult result, HttpServletRequest request, Model model) {
        String[] heroIds = request.getParameterValues("heroId");

        List heroes = serviceLayer.getAllHeroes();
        
        List members = new ArrayList<>();
        if (heroIds != null) {
            for (String heroId : heroIds) {
                members.add(serviceLayer.getHero(Integer.parseInt(heroId)));
            }
        } else {
            FieldError error = new FieldError("organization", "members", "Must include one hero");
            result.addError(error);
        }
        
        org.setMembers(members);
        
        if (result.hasErrors()) {
            model.addAttribute("organization", org);
            model.addAttribute("heroes", heroes);
            return "editOrganization";
        }

        serviceLayer.updateOrg(org, heroIds);
        return "redirect:/organizations";
    }

    @GetMapping("cancelEditOrganization")
    public String cancelEditOganization() {
        return "redirect:/organizations";
    }

    @GetMapping("orgDetails")
    public String displayOrgInfo(Integer id, Model model) {
        Organization org = serviceLayer.getOrg(id);
        List heroes = serviceLayer.listHeroesForOrg(id);
        model.addAttribute("organization", org);
        model.addAttribute("heroes", heroes);
        return "organizationDetail";
    }
}
