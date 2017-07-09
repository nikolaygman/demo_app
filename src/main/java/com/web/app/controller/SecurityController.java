package com.web.app.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.app.model.User;
import com.web.app.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class SecurityController {


	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(required = false, name = "error") String error,
							  ModelAndView modelAndView) {
		modelAndView.setViewName("login");
		modelAndView.addObject("error", error != null);
		return modelAndView;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(ModelAndView modelAndView) {
		modelAndView.setViewName("register");
		return modelAndView;
	}


	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Map<String, String> register(@Autowired UserService userService, @Valid User user, BindingResult bindingResult, String passwordConfirmation) {
		HashMap<String, String> response = new HashMap<>();
		try {
			userService.loadUserByUsername(user.getUsername());
			response.put("username", "User name already exists");
		} catch (UsernameNotFoundException exception) {
			exception.printStackTrace();
		}
		if (!user.getPassword().equals(passwordConfirmation) && !bindingResult.hasFieldErrors("password")) {
			response.put("passwordConfirmation", "Password should match password confirmation");
		}
		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				response.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return response;
		} else if (response.isEmpty()) {
			response.put("success", "");
			userService.createUser(user);
			authenticateUser(user);
			return response;
		}
		return response;
	}

	private void authenticateUser(User user) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
