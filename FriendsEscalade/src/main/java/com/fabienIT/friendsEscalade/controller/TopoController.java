package com.fabienIT.friendsEscalade.controller;


import com.fabienIT.friendsEscalade.model.*;
import com.fabienIT.friendsEscalade.repository.SiteRepository;
import com.fabienIT.friendsEscalade.service.ReservationService;
import com.fabienIT.friendsEscalade.service.SiteService;
import com.fabienIT.friendsEscalade.service.TopoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;


@Controller
public class TopoController {

    private static final Logger log = LoggerFactory.getLogger(TopoController.class);

    @Autowired
    TopoService topoService;

    @Autowired
    UserController userController;

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    SiteService siteService;

    @Autowired
    ReservationService reservationService;


    @GetMapping("/topoForm")
    public String topo(Model model) {
        model.addAttribute("topo", new Topo());
        return "topoForm";
    }

    @PostMapping("/enregistre")
    public String topoEnregistrer(Topo topo, HttpSession session){
        Site site = (Site) session.getAttribute("site");
        Long siteId = site.getId();

        topoService.ajouter(topo);
        topo.getSite().getId();
        log.info("Le topo que l'on ajoute est : " + topo);
        return "redirect:/topoListe";
    }

    @GetMapping("/monTopoForm")
    public String monTopo(Model model, Authentication authentication, Long id, HttpSession session, Site site) {
        site = siteService.findSiteById(id);
        model.addAttribute("site", site);
        session.setAttribute("site", site);
        System.out.println("Les sites sont : " + site);
        model.addAttribute("topo", new Topo());
        return "monTopoForm";
    }

    @PostMapping("/monTopoEnregistre")
        public String monTopoEnregistrer(Model model, Authentication authentication, Topo topo, String nom, HttpSession session){
        User user = userController.userCo(model, authentication);
        int userId = user.getId();
        System.out.println("L'id de l'utilisateur qui enregistre le topo est : " + userId);
        topo.setUser(user);
        System.out.println("L'Id de l'utilisateur pour la table topo est : " + user.getId());
        /*Site site = siteService.findByNom(nom);
        model.addAttribute("site", site);*/
        Site site = (Site) session.getAttribute("site");
        topo.setSite(site);
        System.out.println("Les sites sont : " + site);
        System.out.println("L'Id du site du topo que l'on enregistre est : " + topo.getSite().getId());
        topoService.ajouter(topo);
        model.addAttribute("topo", topo);
        log.info("Le topo que l'on ajoute est : " + topo);
        return "redirect:/mesTopos";
    }

    public void pasAfficherMesTopos(User user, Model model, Long id) {

        int userId = user.getId();
        Topo topo = topoService.findTopoById(id);
        long topoUserId = topo.getId();
        boolean pasAfficherMesTopos;
        if (userId == topoUserId) {
            pasAfficherMesTopos = true;
            model.addAttribute("pasAfficherMesTopos", pasAfficherMesTopos);
        }
        return;
    }

    @GetMapping("/topoListe")
    public String topoListAfficher (Model model,
                                    @RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "size", defaultValue = "10") int size,
                                    @RequestParam(name = "keyword", defaultValue = "") String keyword, User user, Authentication authentication){
        //List<Topo> topoList = topoService.findAllTopo();
        user = userController.userCo(model, authentication);
        model.addAttribute("role", user.getId());
        System.out.println("Le role esst : " + user.getId());
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        model.addAttribute("role", roles.toString());
        System.out.println("Le role est : " + roles.toString());

        Page<Topo> pageTopo = topoService.findByNameContains(keyword, PageRequest.of(page, size));
        model.addAttribute("TopoListe", pageTopo.getContent());
        model.addAttribute("keyword", keyword);
        model.addAttribute("pages", new int[pageTopo.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);

        log.info("*********Le nombre de topo est : " + pageTopo.getTotalElements());

                return "topoListe";
    }


    @GetMapping("/mesTopos")
    public String mesTopos (Model model, Authentication authentication){

        Set<Topo> pageTopo = userController.userCo(model, authentication).getTopo();
        model.addAttribute("mesTopos", pageTopo);

        try {
            Set<Reservation> reservation = userController.userCo(model, authentication).getReservation();
            model.addAttribute("reservation", reservation);
            System.out.println("Les réservations de l'utilisateur sont : " + reservation);
            model.addAttribute("mesTopos", pageTopo);
        } catch (NullPointerException e) {
            log.error("Vous n'avez pas de de demande de réservation");
        }


        System.out.println("Les topos de l'utilisateur  sont  " + pageTopo);


        return "mesTopos";
    }


    @GetMapping("/sitePageEscalade")
    public String topoListPageEscalade (Model model)
     {
        List<Topo> topoList = topoService.findAllTopo();
        model.addAttribute(topoList);
        log.info("La liste des topos est : " + topoList);

        return "sitePageEscalade";
    }

    @GetMapping("/demandeReservation")
    public String topoAfficher (Model model, Long id){
        Topo t = topoService.findTopoById(id);
        model.addAttribute("topoAfficher", t);
        log.info("Le topo que l'on souhaite réserver est : " + t);
        return "topoDemandeReservation";
    }

    @GetMapping("/validerReservation")
    public String topoValider (Model model, Long id){
        List<Topo> t = topoService.findAllTopo();
        model.addAttribute("valider", t );
        log.info("Le topo que l'on valide est : " + t);
        return "topoValidation";
    }

    @GetMapping("/effacerTopo")
    public String effacer (Long id) {
        topoService.effacer(id);
        log.info("Le topo que l'on vient d'effacer est : " + id);
        return "redirect:/topoListe";
    }

    @GetMapping("/editerTopo")
        public String modifier (Model model, Long id){
        Topo t = topoService.findTopoById(id);
        model.addAttribute("topoModif", t );
        log.info("Le topo que l'on souhaite modifier est : " + t);/*
         int userId = t.getUser().getId();
         model.addAttribute("userId", userId);*/
        return "topoModif";
    }

    @GetMapping("/monTopoListe")
    public String monTopoListAfficher (@Param("model") Model model,
                                       @Param("email") String email,
                                    @RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "size", defaultValue = "10") int size,
                                    @RequestParam(name = "keyword", defaultValue = "") String keyword,
                                    Long id, @Param("user") User user, @Param("authentification") Authentication authentication){
        //List<Topo> topoList = topoService.findAllTopo();
        user = userController.userCo(model, authentication);
        model.addAttribute("role", user);
        System.out.println("Le role est : " + user);
        email = user.getEmail();
        model.addAttribute("email", email);
        System.out.println("L'email de l'utilisateur connecté est : " + email);
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        model.addAttribute("role", roles.toString());
        System.out.println("Le role est : " + roles.toString());
        Page<Topo> pageTopo = topoService.findAllNotId(PageRequest.of(page, size), model, user, authentication, userController);
        model.addAttribute("monTopoListe", pageTopo.getContent());
        model.addAttribute("keyword", keyword);
        model.addAttribute("pages", new int[pageTopo.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        Set<Reservation> reservation = userController.userCo(model, authentication).getReservation();
        model.addAttribute("reservation", reservation);
        System.out.println("L'Id de l'utilisateur qui fait la demande de réservation est : " + user.getReservation());



       /* int userId = user.getId();

        model.addAttribute(userId);
        System.out.println("l'Id de l'utilisateur pour le boolean est : " + userId);
        List<Topo> topo = topoService.findAllTopo();
        Topo topoUserId = topo.get(0);
        model.addAttribute(topoUserId);
        System.out.println("L'Id du topo pour le boolean est : " + topoUserId);

        boolean pasAfficherMesTopos;
        if (userId == topoUserId) {
            pasAfficherMesTopos = true;
            model.addAttribute("pasAfficherMesTopos", pasAfficherMesTopos);
        }*/

        log.info("*********Le nombre de topo est : " + pageTopo.getTotalElements());
        System.out.println("*****Les réservations sont : " + reservation);
        return "monTopoListe";
    }

    @GetMapping("/monTopoReservation")
    public String topoAfficherDemande (Model model, Long id, HttpSession session){
        Topo topo = topoService.findTopoById(id);
        model.addAttribute("topoAfficherDemande", topo);
        session.setAttribute("topo", topo);
        model.addAttribute("reservation", new Reservation());
        log.info("Le topo que l'on souhaite réserver est : " + topo);
        return "monTopoReservation";
    }

    @PostMapping("/saveUpdateMaDemande")
    public String saveUpdateMaDemande (Model model, Topo topo){
        topoService.updateTopo(topo);
        model.addAttribute("update", topo);
        log.info("Le topo que l'on édite est : " + topo);
        return "redirect:/mesDemandes";
    }

    @GetMapping("/mesDemandes")
    public  String  mesDemandes (Model model, HttpSession session, Authentication authentication) {
        User user = userController.userCo(model, authentication);
        int userId = user.getId();
        System.out.println("L'id de l'utilisateur des demandes est : " + userId);
        List<Reservation> reservation = reservationService.mesDemandes(userId);


        model.addAttribute("mesDemandes", reservation);
        /*TODO: trouver le moyen de rammener la liste de topo et non de reservation*/
        /*Set<Topo> topoDemande = userController.userCo(model, authentication).getTopo();
        model.addAttribute("mesDemandes", pageReservation);*/

        System.out.println("Les topos que je demande sont : " + reservation);
        return "mesDemandes";
    }


    @PostMapping("/saveUpdateTopo")
    public String saveUpdateTopo (Model model, Topo topo){
        topoService.updateTopo(topo);
        model.addAttribute("update", topo);
        log.info("Le topo que l'on édite est : " + topo);
        return "redirect:/topoListe";
    }

    @GetMapping("/editerMonTopo")
    public String modifierMonTopo (Model model, Long id){
        Topo t = topoService.findTopoById(id);
        model.addAttribute("topoModif", t );
        log.info("Le topo que l'on souhaite modifier est : " + t);
        return "monTopoModif";
    }



    @PostMapping("/saveReservation")
    public String saveReservation (Model model, Reservation reservation, Long id, HttpSession session, Authentication authentication){
        Topo topo = (Topo) session.getAttribute("topo");
        id = topo.getId();
        reservation.setTopo(topo);
        System.out.println("L'id du topo est : " + reservation.getTopo().getId());
        User user = userController.userCo(model, authentication);
        int userId = user.getId();
        System.out.println("L'id de l'utilisateur qui souhaite réserver est : " + userId);
        reservation.setUser(user);
        reservationService.ajouter(reservation);
        model.addAttribute("reservation", reservation);
        log.info("La réservation est : " + reservation);
        return "redirect:/mesTopos";
    }

/*    @GetMapping("/monTopoReservation")
    public String topoAfficherMonTopo (Model model, Long id){
        Topo t = topoService.findTopoById(id);
        model.addAttribute("topoAfficherMonTopo", t);
        log.info("Le topo que l'on souhaite réserver est : " + t);
        return "monTopoReservation";
    }*/

    @GetMapping("/validerReservationMonTopo")
    public String topoValiderMonTopo (Model model, Long id){
        Topo t = topoService.findTopoById(id);
        model.addAttribute("validerMonTopo", t );
        log.info("Le topo que l'on valide est : " + t);
        return "monTopoValidation";
    }

    @GetMapping("coordonnees")
    public String coordonnees (Model model, Long id){
        Topo topo = topoService.findTopoById(id);
        User user = topo.getUser();
        model.addAttribute("coordonnees", user);
        System.out.println("Les coordonnées de l'utilisateur sont : " +
                "Prénom : " + user.getName() +
                "Nom : " + user.getLastName() +
                "Adresse Mail : " + user.getEmail() +
                "Code Postal : " + user.getCodePostal());
        return "coordonnees";
    }





 /*   @PostMapping("/")
    public String demandeReservation (Model model, Long id){

    return "redirect:/topoListe";
    }*/




}




