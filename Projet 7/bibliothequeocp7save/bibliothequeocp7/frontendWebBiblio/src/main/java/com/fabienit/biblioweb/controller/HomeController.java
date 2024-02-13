package com.fabienit.biblioweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;


    @GetMapping("/")
    public String Accueil(Model model) {

            RestTemplate restTemplate = new RestTemplate();

            restTemplate = restTemplateBuilder.build();
            restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin2021"));

            final ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/usagers", String.class);
            final String body = responseEntity.getBody();
            System.out.println("body = " + body);
            model.addAttribute("body",body);

        return "home";
    }

/*    @GetMapping("/")
    public String accueil() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplateBuilder.basicAuthentication("root", "admin").build();
//        restTemplate.getForObject("http://localhost:8080/api/usagers",String.class);
        String fooResourceUrl
                = "http://localhost:8080/api/usagers";
        ResponseEntity<String> toto
                = restTemplateBuilder.build().getForEntity(fooResourceUrl + "/4", String.class);
       // assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        System.out.println("la réponse de l'appel de l'API est" + toto);
        return "home";
    }*/
}