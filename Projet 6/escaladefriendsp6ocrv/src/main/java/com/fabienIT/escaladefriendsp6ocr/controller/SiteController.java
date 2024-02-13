package com.fabienIT.escaladefriendsp6ocr.controller;


import com.fabienIT.escaladefriendsp6ocr.form.CommentaireForm;
import com.fabienIT.escaladefriendsp6ocr.model.*;
import com.fabienIT.escaladefriendsp6ocr.repository.SiteRepository;
import com.fabienIT.escaladefriendsp6ocr.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Set;


@Controller
public class SiteController {

    private static final Logger log = LoggerFactory.getLogger(TopoController.class);

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    SiteService siteService;

    @Autowired
    TopoService topoService;

    @Autowired
    CommentaireService commentaireService;

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserController userController;

    @Autowired
    SiteController siteController;

/*   @Autowired
    RoleService roleService;

    @Autowired
    RoleRepository roleRepository;*/

    /*@GetMapping("/hello")
    public String hello(@RequestParam(value = "message", defaultValue = "Bienvenue") String message, Model model) {
        model.addAttribute("message", message);
        return "hello";
    }*/



    @GetMapping("/siteCo")
    public String utilisateur (Model model, Authentication authentication ) {

        String userName = authentication . getName ();
        String authorities =  authentication.getAuthorities().toString();

        System.out.println("**************************** Name : "+userName);
        System.out.println("**************************** Authorities : "+authorities);

        model.addAttribute("userName", userName);

        log.info("L'utlisateur connecté est : " + userName);

        return "siteCo";
    }


    @GetMapping("/visiteur/sites")
    public String sites (Model model,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "5") int size,
                         @RequestParam(name = "keyword", defaultValue = "") String mc,
                         @RequestParam(name="region", defaultValue = "")String region,
                         Authentication authentication ) {
       // Role roleUser = roleService.findByRole(role);
        Page<Site> sitePage = siteRepository.findByNomContainsAndRegionContains(mc, region, PageRequest.of(page, size));
        model.addAttribute("sitePage", sitePage.getContent());
        model.addAttribute("pages", new int[sitePage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("keyword", mc);
        model.addAttribute("region", region);
        /*Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        model.addAttribute("role", roles.toString());
        System.out.println("Le role est : " + roles.toString());*/
        {

            try {
                Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
                model.addAttribute("role", roles.toString());
                System.out.println("Le role est : " + roles.toString());
            } catch (NullPointerException e) {
                log.error("Pas de role");
            }
            log.info("Le nombre de site est : " + sitePage.getTotalElements());
           // log.info("Le role de l'utilisateur connecté est : " + role);

            return "sites";
        }
    }

    @GetMapping("/siteListe")
    public String siteListe (Model model,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "5") int size,
                         @RequestParam(name = "keyword", defaultValue = "") String mc,
                         @RequestParam(name="region", defaultValue = "")String region) {
        Page<Site> sitePage = siteRepository.findByNomContainsAndRegionContains(mc, region, PageRequest.of(page, size));
        model.addAttribute("siteListe", sitePage.getContent());
        model.addAttribute("pages", new int[sitePage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("keyword", mc);
        {
            log.info("Le nombre de site est : " + sitePage.getTotalElements());

            return "siteListe";
        }
    }

    @GetMapping("/siteForm")
    public String topo(Model model) {
        model.addAttribute("site", new Site());
        return "siteForm";
    }


    @PostMapping("/ajouterSite")
    public String siteEnregistrer(Site site){
        siteService.ajouter(site);
        log.info("Le site que l'on ajoute est : " + site);
        return "redirect:/siteListe";
    }

    @GetMapping("/editerSite")
    public String modifier (Model model, Long id){
        Optional<Site> s = siteService.findById(id);
        model.addAttribute("siteModif", s);
        log.info("Le site que l'on souhaite modifier est : " + s);
        return "siteModif";
    }

    @PostMapping("/saveUpdateSite")
    public String saveUpdateSite (Model model, Site site){
        siteService.updateSite(site);
        model.addAttribute("update", site);
        log.info("Le site que l'on édite est : " + site);
        return "redirect:/siteListe";
    }

    @GetMapping("/effacerSite")
    public String effacer (Long id) {
        siteService.effacer(id);
        log.info("Le site que l'on vient d'effacer est : " + id);
        return "redirect:/siteListe";
    }


    @GetMapping("/admin/sitesMe")
    public String membre (Model model, Authentication authentication ) {

        String userName = authentication . getName ();
        String authorities =  authentication.getAuthorities().toString();

        System.out.println("**************************** Authorities : "+authorities);

        model.addAttribute("userName", userName);

        log.info("Le membre connecté est : " + userName);

        return "sitesMe";
    }


/*    @GetMapping("/commentaire")
    public String commentaire(Model model) {
        model.addAttribute("commentaireForm", new Commentaire());
        return "redirect:/sitePageEscalade";
    }*/


    @GetMapping("/visiteur/pageEscalade")
    public String pageEscalade (Model model, Long id, Authentication authentication, HttpSession session, @ModelAttribute("siteId") Site site){

                if (id != null) {
                    site = siteService.findSiteById(id);
                    session.setAttribute("siteId", site);
                    System.out.println("L'Id du site est : " + site.getId());
                    model.addAttribute("siteId", site.getId());
                    model.addAttribute("sitePageEscalade", site);
                    Set<Topo> toposite = siteService.findById(id).get().getTopo();
                    model.addAttribute("toposite", toposite);
                    Set<Commentaire> comSite = siteService.findById(id).get().getCommentaire();
//                    model.addAttribute("localDateTime", LocalDateTime.now());
                    model.addAttribute("comSite", comSite);
                    model.addAttribute("commentaireForm", new Commentaire());

                    log.info("La page d'escalade est : " + site);
                    log.info("Le topo est : " + toposite);
                    log.info("La liste des commentaires est : " +comSite);
                }else {
                    session.getId();
                    session.setAttribute("siteId", site);
                    System.out.println("L'Id du site est : " + site.getId());
                    Long siteId = site.getId();
                    model.addAttribute("siteId",siteId);
                    model.addAttribute("sitePageEscalade", site);
                    Set<Topo> toposite = siteService.findById(siteId).get().getTopo();
                    model.addAttribute("toposite", toposite);
                    Set<Commentaire> comSite = siteService.findById(siteId).get().getCommentaire();
                    model.addAttribute("comSite", comSite);
                    model.addAttribute("commentaireForm", new Commentaire());

                    log.info("La page d'escalade est : " + site);
                    log.info("Le topo est : " + toposite);
                    log.info("La liste des commentaires est : " +comSite);
                }


        /*Long siteId = site.getId();
        session.setAttribute("siteId", site);
        System.out.println("L'Id du site est : " + site.getId());
        model.addAttribute("siteId", site.getId());
        model.addAttribute("sitePageEscalade", site);
        Set<Topo> toposite= siteService.findById(id).get().getTopo();
        model.addAttribute("toposite", toposite);
        Set<Commentaire> comSite = siteService.findById(id).get().getCommentaire();
        model.addAttribute("comSite", comSite);
        model.addAttribute("commentaireForm", new Commentaire());*/

        try {
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            model.addAttribute("role", roles.toString());
            System.out.println("Le role est : " + roles.toString());
        } catch (NullPointerException e) {
            log.error("Pas de role");
        }

        /*log.info("La page d'escalade est : " + site);
        log.info("Le topo est : " + toposite);
        log.info("La liste des commentaires est : " +comSite);*/
        return "sitePageEscalade";
    }


    @PostMapping("/saveCommentaireSite")
    public String saveCommentaireSite (Model model, Site site, Commentaire commentaire){
        siteService.updateSite(site);
        commentaireService.ajouterCom(commentaire);
        model.addAttribute(commentaire);
        model.addAttribute("update", site);
        log.info("Le site que l'on édite est : " + site);
        return "redirect:/visiteur/pageEscalade";
    }



    @PostMapping("/visiteur/pageEscalade")
    public String ajouterCom (User user , Long id, Model model,Commentaire commentaire, HttpSession session,
                              Authentication authentication, Site site, Long siteId, RedirectAttributes redirectAttributes){
        try {
            user = userController.userCo(model, authentication);
            int userId = user.getId();
            model.addAttribute("userId",userId);
            System.out.println("L'ID de l'utilisateur connecté pour mettre un commentaire est : " + userId);
            site = (Site) session.getAttribute("siteId");
            System.out.println("L'ID du site est : " + site.getId());
        } catch (NullPointerException e) {
            log.error("Pas d'ID d'utilisateur connecté");
        }
        try {

            commentaire.setId();
            commentaire.getCom();
            System.out.println("***********Le commentaire est :" + commentaire.getCom());
            commentaire.setComDate(LocalDateTime.now());
            System.out.println("***********La date du jour est : " + commentaire.getComDate());
            user = userController.userCo(model, authentication);
            commentaire.setUser(user);
            System.out.println("************L'id de l'utilisateur est : " + commentaire.getUser().getId());
//            siteId = (Long) session.getAttribute("siteId");
            session.getAttribute("siteId");
            commentaire.setSite(site);
            System.out.println("***********L'id du site est : " + commentaire.getSite().getId());
            commentaireService.ajouterCom(commentaire);
            model.addAttribute("commentaire", commentaire);
            log.info("Le commentaire que l'on ajoute est : " + commentaire);
        } catch (NullPointerException e) {
            log.error("Pas d'utilisateur connecté");
        }
        redirectAttributes.addFlashAttribute("siteId", site);
        return "redirect:/visiteur/pageEscalade";
    }

    @GetMapping("/effacerCommentaire")
    public String effacerCommentaire (Long id) {
        commentaireService.effacer(id);
        log.info("Le commentaire que l'on vient d'effacer est : " + id);
        return "redirect:/visiteur/pageEscalade";
    }

/*    @GetMapping("/editerCommentaire")
    public String modifierCom (Model model, Long id){
        Commentaire c = commentaireService.findCommentaireById(id);
        model.addAttribute("commentaireModif", c );
        log.info("Le commentaire que l'on souhaite modifier est : " + c);
        return "commentaireModif";
    }*/

/*    @PostMapping("/saveUpdateCommentaire")
    public String saveUpdateTopo (Model model, Commentaire commentaire){
        commentaireService.updateCommentaire(commentaire);
        model.addAttribute("commentaireModif", commentaire);
        log.info("Le commentaire que l'on édite est : " + commentaire);
        return "redirect:/visiteur/pageEscalade";
    }*/

}

