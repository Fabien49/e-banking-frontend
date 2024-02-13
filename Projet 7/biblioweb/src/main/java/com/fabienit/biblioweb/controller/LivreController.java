package com.fabienit.biblioweb.controller;

import com.fabienit.biblioweb.model.Livre;
import com.fabienit.biblioweb.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LivreController {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;


    @GetMapping("/livres")
    public String livres(Model model, Livre livre) {


        RestTemplate restTemplate = new RestTemplate();

        restTemplate = restTemplateBuilder.build();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin2021"));


        ResponseEntity<Livre[]> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/livres", Livre[].class);
        Livre[] livreArray = responseEntity.getBody();
        Arrays.stream(livreArray).map(Livre::getTitre).collect(Collectors.toList());
        List<Livre> listeLivre = new ArrayList<Livre>(Arrays.asList(livreArray));
        model.addAttribute("listeLivre", listeLivre);
        System.out.println("**************************** livreArray = " + livreArray + "*********************");
        System.out.println("+++++++++++++++++++++++++ listeLivre = " + listeLivre + "++++++++++++++++++++");

        return "livres";
    }


    @GetMapping("/livrePage")
    public String livrePage(Model model, HttpSession session, @Valid Livre livre, @RequestParam(name = "id") int id) {

        System.out.println("l'ID est : " + id);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate = restTemplateBuilder.build();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin2021"));

        final ResponseEntity<Livre> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/livres/ " + id, Livre.class);
        Livre body = responseEntity.getBody();
        System.out.println("body = " + body);
        model.addAttribute("body", body);
        if (id == 0) {
            livre = body;
            session.setAttribute("livre", livre);
            model.addAttribute("livreId", livre.getId());
            System.out.println("****** l'id du livre est :" + livre.getId());
            model.addAttribute("livrePage", livre);
            System.out.println("5555555555555555555" + livre);
        } else {
            livre = body;
            session.getId();
            session.setAttribute("livreId", livre);
            System.out.println("L'Id du livre est : " + livre.getId());
            int livreId = livre.getId();
            model.addAttribute("livreId", livreId);
            model.addAttribute("livrePage", livre);
            System.out.println("5555555555555555555" + livre);
        }

        return "livrePage";
    }

    @GetMapping("/demandeReservation")
    public String livreReservation(Model model, @RequestParam(name = "id") int id,
                                   HttpSession session, Livre livre) {

        RestTemplate restTemplate = new RestTemplate();

        restTemplate = restTemplateBuilder.build();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin2021"));

        final ResponseEntity<Livre> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/livres/ " + id, Livre.class);
        Livre body = responseEntity.getBody();
        System.out.println("body = " + body);
        model.addAttribute("body", body);
        if (id == 0) {
            livre = body;
            session.setAttribute("livre", livre);
            model.addAttribute("livreId", livre.getId());
            System.out.println("****** l'id du livre est :" + livre.getId());
            model.addAttribute("livrePage", livre);
            System.out.println("5555555555555555555" + livre);
        } else {
            livre = body;
            session.getId();
            session.setAttribute("livre", livre);
            System.out.println("L'Id du livre est : " + livre.getId());
            int livreId = livre.getId();
            model.addAttribute("livreId", livreId);
            model.addAttribute("livrePage", livre);
            System.out.println("5555555555555555555" + livre);
        }
        model.addAttribute("reservation", new Reservation());
        System.out.println("le livre que l'on souhaite réserver est : " + livre);
        return "livreValiderReservation";
    }
}




