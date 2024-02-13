package com.fabienIT.escaladefriendsp6ocr.controller;

import com.fabienIT.escaladefriendsp6ocr.model.*;
import com.fabienIT.escaladefriendsp6ocr.repository.UserRepository;
import com.fabienIT.escaladefriendsp6ocr.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Set;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(TopoController.class);

/*    @Autowired
    UtilisateurService utilisateurService;*/

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;



/*    @GetMapping("/inscription")
    public String utilisateur(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "/inscription";
    }*/

/*    @PostMapping("/utilisateurAjouter")
    public String ajouter (Utilisateur utilisateur) {
        utilisateurService.ajouter(utilisateur);
        log.info("Le nom de l'utilisateur est : " +utilisateur);
        return "/home";
    }*/


/*    @GetMapping("/utilisateurCo")
    public String admin(Model model, Authentication authentication ) {

        String userName = authentication . getName ();
        String authorities =  authentication.getAuthorities().toString();

        System.out.println("**************************** Name : "+userName);
        System.out.println("**************************** Authorities : "+authorities);

        model.addAttribute("userName", userName);

        return "utilisateurCo";
    }*/


    @RequestMapping(value="/utilisateurCo", method = RequestMethod.GET)
    public ModelAndView accueilUtilisateurCo(Authentication authentication){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Bonjour " + " : " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        modelAndView.addObject("role", roles.toString());
        System.out.println("Le role est : " + roles.toString());
        modelAndView.addObject("userId", user.getId());
        System.out.println("L'Id de l'utilisateur connecté est : " + user.getId());
        modelAndView.setViewName("utilisateurCo");
        return modelAndView;
    }

    public User userCo(Model model, Authentication authentication){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        int userId = user.getId();
        model.addAttribute("userId ",userId);
        System.out.println("L'Id de l'utilisateur connecté sur la page mon compte est : " + user.getId());
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        model.addAttribute("role", roles.toString());
        System.out.println("Le role est : " + roles.toString());
        return user;

    }


    @RequestMapping(value="/accueilMembre", method = RequestMethod.GET)
    public ModelAndView accueilMembre(Authentication authentication){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Bonjour " + " : " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        modelAndView.addObject("role", roles.toString());
        System.out.println("Le role est : " + roles.toString());
        modelAndView.setViewName("accueilMembre");
        return modelAndView;
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

  @GetMapping("/userListe")
    public String userListe (Model model, Long id, String email,
                        @RequestParam(name = "page", defaultValue = "0")int page,
                        @RequestParam(name="size",defaultValue = "5")int size,
                        @RequestParam(name="keyword",defaultValue = "")String mc) {
        Page<User> userListe = userRepository.findByNameContains(mc, PageRequest.of(page, size));
        /*Set<Role> roleUser= userService.findUser(id).getRoles();
        model.addAttribute("roleUser", roleUser);*/
        model.addAttribute("userListe", userListe.getContent());
        model.addAttribute("pages", new int[userListe.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("keyword", mc);

      {
//        log.info("Le role est : " + roleUser);
          log.info("Le nombre d'utilisateur est : " + userListe.getTotalElements());
          return "userListe";
      }
    }

/*    @GetMapping("/deleteUser")
    public String delete (Long id, String keyword, int page, int size) {
        userService.deleteById(id);
        log.info("L'id de l'utilisateur supprimé est : " + id);
        return "redirect:/utilisateur?page="+page+"&size="+size+"&keyword="+keyword;
    }*/

/*    @GetMapping("/editUser")
    public String edit (Model model, Long id) {
        Utilisateur u =  utilisateurService.findUtilisateurById(id);
        model.addAttribute("utilisateur", u);
        log.info("L'utilisateur modifié est : " + u);
        return "modifier";
    }*/

    @GetMapping("/userForm")
    public String user(Model model) {
        model.addAttribute("user", new User());
        return "userForm";
    }

    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("userForm");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "Votre inscription a bien été prise en compte");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("/userListe");
        }
        return modelAndView;
    }

    @GetMapping("/membreForm")
    public String membre(Model model) {
        model.addAttribute("user", new User());
        return "membreForm";
    }

    @RequestMapping(value = "/saveMembre", method = RequestMethod.POST)
    public ModelAndView createNewMembre(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("userForm");
        } else {
            userService.saveMembre(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("/userListe");
        }
        return modelAndView;
    }

    @GetMapping("/monCompte")
    public String monCompte(Model model, Authentication authentication, Long id) {

        try {
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            model.addAttribute("role", roles.toString());
            System.out.println("Le role est : " + roles.toString());
        } catch (NullPointerException e) {
            log.error("Pas de role");
        }
        try {
            User user = userCo(model, authentication);
            int userId = user.getId();
            System.out.println("L'id de l'utilisateur de la page mon compte est : " + userId);
        } catch (NullPointerException e) {
            log.error("Pas de role");
        }


        /*user = userService.findUser(id);
        model.addAttribute("monCompte", user.getId());
        System.out.println("L'id de l'utilisateur de la page mon compte vers la page mes topos est : " + user.getId());*/

        return "monCompte";
    }

/*    @GetMapping("/monCompte")
    public String mesTopos(Model model, Authentication authentication) {

        try {
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            model.addAttribute("role", roles.toString());
            System.out.println("Le role est : " + roles.toString());
        } catch (NullPointerException e) {
            log.error("Pas de role");
        }

        return "monCompte";
    }*/


}
