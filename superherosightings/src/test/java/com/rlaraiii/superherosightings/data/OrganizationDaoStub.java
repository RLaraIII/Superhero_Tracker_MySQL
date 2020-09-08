/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.data;

import com.rlaraiii.superherosightings.models.Hero;
import com.rlaraiii.superherosightings.models.Organization;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author R Lara
 */
public class OrganizationDaoStub implements OrganizationDao {

    private Organization onlyOrg;

    public OrganizationDaoStub() {
        List members = new ArrayList<>();
        
        Hero onlyHero = new Hero();
        onlyHero.setId(1);
        onlyHero.setName("Batman");
        onlyHero.setPowerId(1);
        onlyHero.setDescription("Lives in a batcave");
        
        members.add(onlyHero);
        
        onlyOrg = new Organization();
        onlyOrg.setId(1);
        onlyOrg.setName("Justice League");
        onlyOrg.setDescription("Team of heroes");
        onlyOrg.setAddress("123 Main St");
        onlyOrg.setContact_info("911");
        onlyOrg.setMembers(members);
    }

    @Override
    public Organization getOrganizationById(int id) {
        if (id == onlyOrg.getId()) {
            return onlyOrg;
        } else {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        List<Organization> orgs = new ArrayList<>();
        orgs.add(onlyOrg);
        return orgs;
    }

    @Override
    public Organization addOrganization(Organization org) {
        if (onlyOrg.equals(org)) {
            return onlyOrg;
        } else {
            return null;
        }
    }

    @Override
    public void updateOrganization(Organization org) {
        // Do nothing
    }

    @Override
    public void deleteOrganizationById(int id) {
        // Do nothing
    }

    @Override
    public List<Organization> getOrganizationsForHero(int heroId) {
        List<Organization> orgs = new ArrayList<>();

        List<Hero> heroes = onlyOrg.getMembers();
        
        for (Hero hero : heroes) {
            if (hero.getId() == heroId) {
                orgs.add(onlyOrg);
            }
        }
        
        return orgs;
    }
    
}
