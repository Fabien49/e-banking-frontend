package com.fabienit.biblioweb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApi {

    @GetMapping("/hello")
    public String accueil() {
        return "Bonjour Fabien";
    }
}
