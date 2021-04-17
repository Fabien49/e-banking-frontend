package com.fabienit.escaladefriends.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SitesController {

    /*@GetMapping("/hello")
    public String hello(@RequestParam(value = "message", defaultValue = "Bienvenue") String message, Model model) {
        model.addAttribute("message", message);
        return "hello";
    }*/

@GetMapping("/sites")
    public String sites() {

        return "sites";
    }

}
