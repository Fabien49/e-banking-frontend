package com.example.servingwebcontent;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class EtudiantController {

    @GetMapping("/etudiant")
    public String etudiant(Model model,
                            @RequestParam(name="nom", required=false, defaultValue="") String nom,
                            @RequestParam(name="nom", required=false, defaultValue="") String prenom,
                            @RequestParam(name="age", required=false, defaultValue="") int age,
                            @RequestParam(name="photoUrl", required=false, defaultValue="") String photoUrl) {
        model.addAttribute("nom", nom);
        model.addAttribute("prenom", prenom);
        model.addAttribute("age", age);
        model.addAttribute("photoUrl", photoUrl);
        return "etudiant";
    }
}
