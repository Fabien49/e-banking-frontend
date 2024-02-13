package com.fabienit.biblioweb.controller;


import com.fabienit.biblioweb.model.Usager;
import com.fabienit.biblioweb.service.UsagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        logger.info("---- START : Action d'inscription' ----");
        Usager usager = new Usager();
        model.addAttribute("usager", usager);
        model.getAttribute("inscription");
        return "inscription";
    }

/*    @PostMapping(value = "/ajouter")
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
    }*/

    @GetMapping (value = "/usager")
    public String listeDesUsagers(Model model, Usager usagers, Usager usager){
        RestTemplate restTemplate = new RestTemplate();

        restTemplate = restTemplateBuilder.build();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin2021"));

        ResponseEntity<Usager[]> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/usagers/liste_usagers", Usager[].class);
        Usager[] usagerArray = responseEntity.getBody();
        Arrays.stream(usagerArray).map(Usager::getName).collect(Collectors.toList());
        List<Usager> listeUsager = new ArrayList<Usager>(Arrays.asList(usagerArray));
        model.addAttribute("listeLivre", listeUsager);
        System.out.println("**************************** livreArray = " + usagerArray + "*********************");
        System.out.println("+++++++++++++++++++++++++ listeLivre = " + listeUsager + "++++++++++++++++++++");

        return "usager";
    }

    @GetMapping("/usageremail/{email}")
    public String usagerPage(Model model, HttpSession session, Usager usager, @PathVariable(name = "email") String email) {

        System.out.println("l'email est : " + email);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate = restTemplateBuilder.build();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin2021"));

        final ResponseEntity<Usager> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/usagers/usageremail/ " + email, Usager.class);
        Usager body = responseEntity.getBody();
        System.out.println("body = " + body);
        model.addAttribute("body", body);
        usager = body;
        session.getId();
        session.setAttribute("usager", usager);
        System.out.println("L'email de l'usager est : " + usager.getEmail());
        String usagerEmail = usager.getEmail();
        model.addAttribute("usagerEmail", usagerEmail);
        model.addAttribute("usagerPage", usager);
        System.out.println("5555555555555555555" + usager);
/*        if (email == null) {
            usager = body;
            session.setAttribute("usager", usager);
            model.addAttribute("usagerEmail", usager.getEmail());
            System.out.println("****** l'email de l'usager est :" + usager.getEmail());
            model.addAttribute("usagerPage", usager);
            System.out.println("5555555555555555555" + usager);
        } else {
            usager = body;
            session.getId();
            session.setAttribute("usager", usager);
            System.out.println("L'email de l'usager est : " + usager.getEmail());
            String usagerEmail = usager.getEmail();
            model.addAttribute("usagerEmail", usagerEmail);
            model.addAttribute("usagerPage", usager);
            System.out.println("5555555555555555555" + usager);
        }*/

        return "usagerPage";
    }





    @GetMapping (value = "/connexion")
    public String connexion (@RequestParam(name = "email") String email, @RequestParam(name = "password") String password){
        logger.info("---- START : Action de connexion : " + email + " et " + password);
        // request url
        String url = "http://localhost:8080/api/login/login";

// create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        restTemplate = restTemplateBuilder.build();
//        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin2021"));
        restTemplate.getInterceptors().add((ClientHttpRequestInterceptor) new BCryptPasswordEncoder());

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
