package com.fabienIT.escaladefriendsp6ocr.controller;


import com.fabienIT.escaladefriendsp6ocr.service.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class InscriptionController {


    @Autowired
    InscriptionService inscriptionService;
    // mettre USERsERVICEiMPL

/*   @GetMapping("/inscription")
    public String resultsForm (Model model) {
        model.addAttribute("user", new User());
        return "inscription";
    }*/


    /*@PostMapping("/ajouterUser")
    public String userSubmit(@ModelAttribute userOld user, Model model) {
        model.addAttribute("user", user);
        System.out.println("*****************Le nom d'user est : " + user);
        inscriptionService.ajouter(user);
        return "user";
    }*/

}
