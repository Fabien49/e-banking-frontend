package com.fabienIT.friendsEscalade.controller;


import com.fabienIT.friendsEscalade.model.Commentaire;
import com.fabienIT.friendsEscalade.service.CommentaireService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CommentaireController {

    private static final Logger log = LoggerFactory.getLogger(TopoController.class);

    @Autowired
    CommentaireService commentaireService;


/*    @GetMapping("/commentaire")
    public String commentaire(Model model) {
        model.addAttribute("commentaireForm", new Commentaire());
        return "commentaire";
    }*/

/*    @PostMapping("/commentaireAjouter")
    public String commentaireAjouter(Commentaire commentaire){
        commentaireService.ajouterCom(commentaire);
        log.info("Le commentaire que l'on ajoute est : " + commentaire);
        return "redirect:/commentaire";
    }*/

    @GetMapping("/commentaireAfficher")
    public  String commentaireAfficher (Model model){
        List<Commentaire> commentaireList = commentaireService.findAllCom();
        model.addAttribute("commentaireList", commentaireList);
        return "redirect:/commentaire";
    }

/*
    @GetMapping("/effacerCommentaire")
    public String effacer (Long id) {
        commentaireService.effacer(id);
        log.info("Le topo que l'on vient d'effacer est : " + id);
        return "sitePageEscalade";
    }

    @GetMapping("/editerCommentaire")
    public String modifier (Model model, Long id){
        Commentaire c = commentaireService.findCommentaireById(id);
        model.addAttribute("commentaireModif", c );
        log.info("Le topo que l'on souhaite modifier est : " + c);
        return "commentaireModif";
    }

    @PostMapping("/saveUpdateCommentaire")
    public String saveUpdateTopo (Model model, Commentaire commentaire){
        commentaireService.updateCommentaire(commentaire);
        model.addAttribute("update", commentaire);
        log.info("Le topo que l'on édite est : " + commentaire);
        return "redirect:/sitePageEscalade";
    }
*/


}
