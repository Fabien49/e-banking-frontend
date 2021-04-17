package com.fabienIT.friendsEscalade.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(TopoController.class);


    /*@GetMapping("/hello")
    public String hello(@RequestParam(value = "message", defaultValue = "Bienvenue") String message, Model model) {
        model.addAttribute("message", message);
        return "hello";
    }*/

    @GetMapping("/")
    public String accueil(Model model, Authentication authentication) {

        try {
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            model.addAttribute("role", roles.toString());
            System.out.println("Le role est : " + roles.toString());
        } catch (NullPointerException e) {
            log.error("Pas de role");
        }

        return "home";
    }

}
