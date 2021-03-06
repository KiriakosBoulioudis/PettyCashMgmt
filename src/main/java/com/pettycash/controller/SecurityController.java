package com.pettycash.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SecurityController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error){

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		model.setViewName("login");

		return model;

	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesDenied() {
		ModelAndView model = new ModelAndView();

		model.setViewName("403");

		return model;

	}

	@RequestMapping(value = "/logedout", method = RequestMethod.GET)
	public ModelAndView logedOutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}

		ModelAndView model = new ModelAndView();
		
		model.addObject("msg", "You've been logged out successfully.");

		model.setViewName("main");

		return model;
	}

}