package com.hulkStore.service;

import com.hulkStore.model.Hero;
import com.hulkStore.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeroService {
    @Autowired
    private HeroRepository heroRepository;

    public void addHero(Hero hero) {
        heroRepository.save(hero);
    }

    public boolean checkNameAvailability(String name) {
        List<Hero> heroes = heroRepository.findByName(name);
        return heroes == null || heroes.isEmpty();

    }

    public Iterable<Hero> getHeroes(){return heroRepository.findAll();};

    public  Hero findByName(String heroName) {
        List<Hero> heroes = heroRepository.findByName(heroName);
        if (heroes == null || heroes.isEmpty()) {
            // TO DO
        }
        return heroes.get(0);
    }

}
