package com.fabienIT.escaladefriendsp6ocr.controller;


import com.fabienIT.escaladefriendsp6ocr.model.Site;
import com.fabienIT.escaladefriendsp6ocr.model.Topo;
import com.fabienIT.escaladefriendsp6ocr.repository.PageEscaladeRepository;
import com.fabienIT.escaladefriendsp6ocr.repository.TopoRepository;
import com.fabienIT.escaladefriendsp6ocr.service.TopoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PageEscaladeController {


    @Autowired
    PageEscaladeRepository pageEscaladeRepository;

    @Autowired
    TopoService topoService;


    /*@GetMapping("/hello")
    public String hello(@RequestParam(value = "message", defaultValue = "Bienvenue") String message, Model model) {
        model.addAttribute("message", message);
        return "hello";
    }*/

    @GetMapping("/sitePageEscaladeCo")
    public String user (Model model, Authentication authentication ) {

        String userName = authentication . getName ();
        String authorities =  authentication.getAuthorities().toString();

        System.out.println("**************************** Name : "+userName);
        System.out.println("**************************** Authorities : "+authorities);

        model.addAttribute("userName", userName);

        return "pageEscaladeCo";
    }

   /* @GetMapping("/pageEscaladeMe")
    public String membre (Model model, Authentication authentication ) {

        String userName = authentication . getName ();
        String authorities =  authentication.getAuthorities().toString();

        System.out.println("**************************** Name : "+userName);
        System.out.println("**************************** Authorities : "+authorities);

        model.addAttribute("userName", userName);

        return "pageEscaladeMe";
    }*/

   /* @GetMapping ("/pageEscalade")
    public String pageEscalade (Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String mc) {
        Page<Site> sitePage = pageEscaladeRepository.findByNomContains(mc, PageRequest.of(page, size));
        model.addAttribute("sitePage", sitePage.getContent());
        model.addAttribute("pages", new int[sitePage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("keyword", mc);
        model.addAttribute("template", "test html paragraphe");
        List<Topo> maListDesTopo = findAllTopo();
        model.addAttribute("TopoList", maListDesTopo);

            return "pageEscalade";

    }*/

    /*@GetMapping ("/pageEscalade")
    public String topo(Model model, Topo topo){
       model.addAttribute("topo", topo);
       return "pageEscalade";
    }*/

    /*private List<Topo> findAllTopo (){
        return topoService.findAllTopo();
    }*/


}

