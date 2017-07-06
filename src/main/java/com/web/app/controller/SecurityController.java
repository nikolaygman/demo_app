package com.web.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.web.app.model.User;
import com.web.app.service.UserService;

@Controller
public class SecurityController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(required = false, name = "error") String error,
			@RequestParam(required = false, name = "logout") String logout, ModelAndView modelAndView) {
		modelAndView.setViewName("login");
		if (error != null) {
			modelAndView.addObject("error", error != null);
		} else {

		}
		return modelAndView;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(ModelAndView modelAndView) {
		modelAndView.setViewName("register");
		return modelAndView;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(ModelAndView modelAndView, @Valid User user, BindingResult bindingResult) {
		User dbUser = null;
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("errorMessages", bindingResult.getAllErrors());
			modelAndView.addObject("userName", user.getUsername());
			modelAndView.setViewName("register");
		} else {
			userService.createUser(user);
			modelAndView.setViewName("redirect:/");
		}

		return modelAndView;
	}
}
