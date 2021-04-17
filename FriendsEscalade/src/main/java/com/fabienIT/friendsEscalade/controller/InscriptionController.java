package com.fabienIT.friendsEscalade.controller;


import com.fabienIT.friendsEscalade.service.InscriptionService;
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


    /*@PostMapping("/ajouterUtilisateur")
    public String utilisateurSubmit(@ModelAttribute Utilisateur utilisateur, Model model) {
        model.addAttribute("utilisateur", utilisateur);
        System.out.println("*****************Le nom d'utilisateur est : " + utilisateur);
        inscriptionService.ajouter(utilisateur);
        return "utilisateur";
    }*/

}
