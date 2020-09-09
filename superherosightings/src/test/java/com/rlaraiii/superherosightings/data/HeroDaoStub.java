/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rlaraiii.superherosightings.data;

import com.rlaraiii.superherosightings.models.Hero;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author R Lara
 */
public class HeroDaoStub implements HeroDao{
    
    private Hero onlyHero;

    public HeroDaoStub() {
        this.onlyHero = new Hero();
        this.onlyHero.setId(1);
        this.onlyHero.setName("Batman");
        this.onlyHero.setPowerId(1);
        this.onlyHero.setDescription("Lives in a batcave");
    }
    
    @Override
    public Hero getHeroById(int id) {
        if (onlyHero.getId() == id) {
            return onlyHero;
        } else {
            return null;
        }
    }

    @Override
    public List<Hero> getAllHeroes() {
        List<Hero> heroes = new ArrayList<>();
        heroes.add(onlyHero);
        return heroes;
    }

    @Override
    public Hero addHero(Hero hero) {
        if (onlyHero.equals(hero)) {
            return onlyHero;
        } else {
            return null;
        }
    }

    @Override
    public void updateHero(Hero hero) {
        // Do nothing
    }

    @Override
    public void deleteHeroById(int id) {
        // Do nothing
    }

    @Override
    public List<Hero> getHeroesForOrganization(int orgId) {
        List<Hero> heroes = new ArrayList<>();
        
        if (orgId == 1) {
            heroes.add(onlyHero);
        }
        
        return heroes;
    }

    @Override
    public List<Hero> getHeroesAtLocation(int locId) {
        List<Hero> heroes = new ArrayList<>();
        
        if (locId == 1) {
            heroes.add(onlyHero);
        }
        
        return heroes;
    }

    @Override
    public List<Hero> getHeroesWithPowerId(int powerId) {
        List<Hero> heroes = new ArrayList<>();
        
        if (powerId == onlyHero.getPowerId()) {
            heroes.add(onlyHero);
        } 
        
        return heroes;
    }

    @Override
    public void addHeroToOrg(int heroId, int orgId) {
        // Do nothing
    }

    @Override
    public void clearMembership(int heroId) {
        // Do nothing
    }
    
}
