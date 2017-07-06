package com.web.app.controller.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.web.app.service.UserService;

@ControllerAdvice(basePackages= "com.web.app.controller")
public class BasicAdvice {
	
	@Autowired
	UserService userService;
	
	@ModelAttribute
	public void currentUser(ModelMap model) {
		model.addAttribute("currentUser", userService.currentUser());
	}
}
