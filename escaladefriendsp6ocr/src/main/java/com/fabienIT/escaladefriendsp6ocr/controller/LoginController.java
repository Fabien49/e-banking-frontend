package com.fabienIT.escaladefriendsp6ocr.controller;

import javax.validation.Valid;

import com.fabienIT.escaladefriendsp6ocr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fabienIT.escaladefriendsp6ocr.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value={"/login"}, method = RequestMethod.GET)
	public ModelAndView login(Model model){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@RequestMapping(value="/inscription", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("inscription");
		return modelAndView;
	}

	@RequestMapping(value="/inscriptionAdmin", method = RequestMethod.GET)
	public ModelAndView registrationMembre(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("inscriptionAdmin");
		return modelAndView;
	}



	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("inscription");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "Votre inscription a bien été prise en compte");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("/login");

		}
		return modelAndView;
	}

/*	@RequestMapping(value = "/saveMembre", method = RequestMethod.POST)
	public ModelAndView createNewMembre(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("inscriptionAdmin");
		} else {
			userService.saveMembre(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("inscriptionAdmin");
		}
		return modelAndView;
	}*/
	
/*	@RequestMapping(value="/membre", method = RequestMethod.GET)
	public ModelAndView home(Long id){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	*//*	User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");*//*
		//modelAndView.addObject("adminMessage","Content Available Only for users with Admin Role");
		String user =
		userService.findUser(id).getCommune();
		modelAndView.addObject("UserIdentity", user);
		modelAndView.setViewName("membre");
		return modelAndView;
	}*/
	

}
