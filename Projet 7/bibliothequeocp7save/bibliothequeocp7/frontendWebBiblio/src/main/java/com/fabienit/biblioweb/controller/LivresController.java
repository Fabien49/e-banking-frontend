package com.fabienit.biblioweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LivresController {

    @GetMapping("/livres")
    public String livres() {
        return "livres";
    }
}
