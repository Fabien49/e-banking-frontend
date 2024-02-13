package com.fabienit.biblioweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class LoginContoller {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;


    @GetMapping("/login")
    public String Accueil(Model model) {

        RestTemplate restTemplate = new RestTemplate();

        restTemplate = restTemplateBuilder.build();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin2021"));

        final ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/usagers", String.class);
        final String body = responseEntity.getBody();
        System.out.println("body = " + body);
        model.addAttribute("body",body);

        return "login";
    }
}
