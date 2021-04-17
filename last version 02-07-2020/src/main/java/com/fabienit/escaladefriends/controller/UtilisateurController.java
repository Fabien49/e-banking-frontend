package com.fabienit.escaladefriends.controller;



import com.fabienit.escaladefriends.entities.Utilisateur;
import com.fabienit.escaladefriends.dao.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UtilisateurController {

   /* @Autowired
    UtilisateurService utilisateurService;*/

    @Autowired
    UtilisateurRepository utilisateurRepository;


    @GetMapping("/utilisateurCo")
    public String admin(Model model, Authentication authentication ) {

        String userName = authentication . getName ();
        String authorities =  authentication.getAuthorities().toString();

        System.out.println("**************************** Name : "+userName);
        System.out.println("**************************** Authorities : "+authorities);

        model.addAttribute("userName", userName);

        return "utilisateurCo";
    }

    /*@GetMapping(value = "/utilisateur")
    public String list(Model model,
            @RequestParam(name = "page", defaultValue = "0")int page,
            @RequestParam(name="size",defaultValue = "5")int size) {
        Page<Utilisateur> utilisateurPage = utilisateurService.getUserList(PageRequest.of(page,size));
        model.addAttribute("utilisateurPage",utilisateurPage.getContent());
        model.addAttribute("pages", new int[utilisateurPage.getTotalPages()]);
         return "utilisateur";

    }*/

  @GetMapping ("/utilisateur")
    public String list (Model model,
            @RequestParam(name = "page", defaultValue = "0")int page,
            @RequestParam(name="size",defaultValue = "5")int size,
            @RequestParam(name="keyword",defaultValue = "")String mc) {
        Page<Utilisateur> utilisateurPage = utilisateurRepository.findByNomContains(mc, PageRequest.of(page, size));
        model.addAttribute("utilisateurPage", utilisateurPage.getContent());
        model.addAttribute("pages", new int[utilisateurPage.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("size", size);
        model.addAttribute("keyword", mc);
        return "utilisateur";
    }

    @GetMapping("/deleteUtilisateur")
    public String delete (Long id, String keyword, int page, int size) {
        utilisateurRepository.deleteById(id);
        return "redirect:/utilisateur?page="+page+"&size="+size+"&keyword="+keyword;
    }

    @GetMapping("/editUtilisateur")
    public String edit (Model model, Long id) {
        Utilisateur u =  utilisateurRepository.findById(id).get();
        model.addAttribute("utilisateur", u);
        System.out.println();
        return "modifier";
    }
}



