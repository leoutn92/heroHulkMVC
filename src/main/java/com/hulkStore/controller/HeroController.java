package com.hulkStore.controller;

import com.hulkStore.model.Hero;
import com.hulkStore.service.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import static com.hulkStore.model.Hero.*;

@Controller
@RequestMapping("/hero")

//Este controller maneja la ui que te permite crear nuevos heroes (spiderman, superman, etc)
public class HeroController {

    @Autowired
    private HeroService heroService;

    @RequestMapping(method = RequestMethod.GET)
    ModelAndView hero() {
        ModelAndView modelAndView = new ModelAndView("hero");
        modelAndView.addObject("heroes", heroService.getHeroes());
        return modelAndView;
    };

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    ModelAndView addHero(@RequestParam String name, @RequestParam String trade) {
        ModelAndView modelAndView = new ModelAndView("hero");
        if (!name.isEmpty()) {
            if (!heroService.checkNameAvailability(name)) {
                modelAndView.addObject("error", name + " already exists");
            } else {
                Hero hero = builder().name(name).trade(trade).build();
                heroService.addHero(hero);
            }
        }
        modelAndView.addObject("heroes", heroService.getHeroes());
        return modelAndView;
    }
}
