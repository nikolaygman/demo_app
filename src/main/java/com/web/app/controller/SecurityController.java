package com.web.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SecurityController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(required = false, name = "error") String error,
							  @RequestParam(required = false, name = "logout") String logout, ModelAndView modelAndView) {
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
	LinkedHashMap<String, String> register(ModelAndView modelAndView, @Valid User user, BindingResult bindingResult, String passwordConfirmatin) {
		User dbUser = null;
		LinkedHashMap<String, String> response = new LinkedHashMap<>();
		if (!user.getPassword().equals(passwordConfirmatin) && !bindingResult.hasFieldErrors("password")) {
			response.put("passwordConfirmation", "Password should match password confirmation");
		}
		if (bindingResult.hasErrors() ) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				response.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return response;
		} else if (response.isEmpty()){
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
