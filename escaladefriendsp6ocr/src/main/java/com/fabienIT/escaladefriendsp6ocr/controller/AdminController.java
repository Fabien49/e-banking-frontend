package com.fabienIT.escaladefriendsp6ocr.controller;


import com.fabienIT.escaladefriendsp6ocr.model.User;
import com.fabienIT.escaladefriendsp6ocr.repository.SiteRepository;
import com.fabienIT.escaladefriendsp6ocr.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(TopoController.class);

    @Autowired
    UserService userService;

    @Autowired
    SiteRepository siteRepository;

/*
    @GetMapping("/admin/accueilAdmin")
    public String accueilAdmin(Model model, String userName, Authentication authentication, Long id, String user) {
    */
/*   User u = userService.findUser(id);
       model.addAttribute(u);*//*

         userName = authentication . getName();
//         userName = userService.findByName(userName).getName();
        String authorities =  authentication.getAuthorities().toString();
        System.out.println("**************************** Name : "+userName);
        System.out.println("**************************** Authorities : "+authorities);

        model.addAttribute("userName", userName);
        user = userService.findUser(id).getName();
        model.addAttribute("userIdentity", user);
//       System.out.println("L'admin connecté est : " + u);

        return "accueilAdmin";
    }
*/

    @RequestMapping(value="/admin/accueilAdmin", method = RequestMethod.GET)
    public ModelAndView accueilAdmin(Authentication authentication){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Bonjour " + " : " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for users with Admin Role");
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        modelAndView.addObject("role", roles.toString());
        System.out.println("Le role est : " + roles.toString());
        System.out.println("*********************L'Id est : " + user.getId()+ "*****************");
        modelAndView.setViewName("accueilAdmin");
        return modelAndView;
    }

}
