package com.fabienIT.escaladefriendsp6ocr.controller;


import com.fabienIT.escaladefriendsp6ocr.model.User;
import com.fabienIT.escaladefriendsp6ocr.model.Utilisateur;
import com.fabienIT.escaladefriendsp6ocr.service.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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


    /*@PostMapping("/ajouterUtilisateur")
    public String utilisateurSubmit(@ModelAttribute Utilisateur utilisateur, Model model) {
        model.addAttribute("utilisateur", utilisateur);
        System.out.println("*****************Le nom d'utilisateur est : " + utilisateur);
        inscriptionService.ajouter(utilisateur);
        return "utilisateur";
    }*/

}
