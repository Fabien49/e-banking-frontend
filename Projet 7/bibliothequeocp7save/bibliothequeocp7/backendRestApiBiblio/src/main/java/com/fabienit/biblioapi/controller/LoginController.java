package com.fabienit.biblioapi.controller;

import com.fabienit.biblioapi.service.UsagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/login")
public class LoginController {
	
	@Autowired
	private UsagerService usagerService;

	@GetMapping
	public String login(){
		return "login";
	}

	/*@RequestMapping(value="/inscription", method = RequestMethod.GET)
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
	}*/
}
