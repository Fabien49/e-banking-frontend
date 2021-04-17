package com.fabienIT.friendsEscalade.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResultsController {

    /*@GetMapping("/hello")
    public String hello(@RequestParam(value = "message", defaultValue = "Bienvenue") String message, Model model) {
        model.addAttribute("message", message);
        return "hello";
    }*/

@GetMapping("/results")
    public String sites() {

        return "results";
    }

}
