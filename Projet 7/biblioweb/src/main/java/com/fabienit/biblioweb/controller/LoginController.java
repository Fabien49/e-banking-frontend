package com.fabienit.biblioweb.controller;

import com.fabienit.biblioweb.model.Role;
import com.fabienit.biblioweb.model.Usager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashSet;

@Controller
public class LoginController {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    Logger logger = LoggerFactory.getLogger(LoginController.class);



    @GetMapping("/login")
    public String login() {

        logger.info("Affichage page de login");

        return "login";
    }



    //créer controller backend login
        //Etape1: call api avec paramètres mail + mdp
        //Etape2: ramène les attributs de l'utilisateur dans les paramètres ou session.getAttributes

    @GetMapping("/inscription")
    public String inscription(Model model){
        logger.info("---- START : Action de Login ----");
        Usager usager = new Usager();
        model.addAttribute("usager", usager);
        model.getAttribute("inscription");
        return "inscription";
    }

    @PostMapping(value = "/ajouter")
    public String ajouterUsager(Model model) {
        logger.info("---- START : Action de Login ----");
        // request url
        String url = "http://localhost:8080/api/usagers";

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        restTemplate = restTemplateBuilder.build();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin2021"));
        //restTemplate.getInterceptors().add((ClientHttpRequestInterceptor) new BCryptPasswordEncoder());

// create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

// create a post object
            Usager usager = new Usager(54, "julien@hotmail.com","12345678","Julien", "Chapeau", "1 rue du pigeon", "75000", "Paris",true);
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            usager.setPassword(bCryptPasswordEncoder.encode(usager.getPassword()));
            usager.setActive(true);
            HashSet<Role> roles = new HashSet<Role>();
            Role role = new Role();
            role.setRole("USER");
            roles.add(role);
            System.out.println("L'usager enregistré est : " + usager);


// build the request
        HttpEntity<Usager> request = new HttpEntity<>(usager, headers);

// send POST request
        Usager response = restTemplate.postForObject(url, request, Usager.class);
        System.out.println(response);

            model.addAttribute("successMessage", "Votre inscription a bien été prise en compte");
        return "login";
    }

    @GetMapping (value = "/connexion")
    public String connexion (Usager usager){
        logger.info("---- START : Action de connexion avec : " + usager.toString());
        // request url
        String url = "http://localhost:8080/api/usagers/usagerCo";

// create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        restTemplate = restTemplateBuilder.build();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin2021"));
//        restTemplate.getInterceptors().add((ClientHttpRequestInterceptor) new BCryptPasswordEncoder());

// create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));


        // send POST request
        Usager response = restTemplate.getForObject(url, Usager.class);
        System.out.println(response);

        return "/";
    }
}
