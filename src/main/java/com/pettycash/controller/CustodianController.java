package com.pettycash.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pettycash.exception.CashBalanceException;
import com.pettycash.exception.ObjectNotExistsInDBException;
import com.pettycash.model.User;
import com.pettycash.service.CashBalanceService;
import com.pettycash.service.CustodianRequestService;
import com.pettycash.service.PublicRequestService;
import com.pettycash.service.UserService;

@RestController
@RequestMapping(value = "custodio")
public class CustodianController {

	private final Logger logger = Logger.getLogger(CustodianController.class);

	@Autowired
	PublicRequestService publicRequestService;

	@Autowired
	CustodianRequestService custodianRequestService;

	@Autowired
	CashBalanceService cashBalanceService;

	@Autowired
	UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerAmount(@RequestParam(value = "amount", required = false) Double amount) {

		ModelAndView model = new ModelAndView();
		String errors = "";
		String username = "";
		try {
			Double cashBalance = 0D;
			User user = null;

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetails = (UserDetails) auth.getPrincipal();
				username = userDetails.getUsername();
			}

			if ((username == null) || (username.isEmpty())) {
				errors = errors + "Information of current USER not found" + "<br/>";
			} else {
				model.addObject("username", username);

				try {
					user = userService.getOrCreate(username);

					if ((amount != null) && (user != null)) {
						custodianRequestService.registerGivenAmount(user.getId(), amount);
					}
				} catch (ObjectNotExistsInDBException e) {
					logger.error(e);
					errors = errors + e.getMessage() + "<br/>";
				}

				try {

					if (user != null) {
						cashBalance = cashBalanceService.getAvailableCash(user.getId());
					}

					model.addObject("cashbalance", cashBalance);

				} catch (ObjectNotExistsInDBException one) {
					logger.error(one);
					errors = errors + one.getMessage() + "<br/>";
				}
			}

			model.addObject("requests", publicRequestService.listNotDeliveredRequests());

		} catch (Exception e) {
			logger.error(e);
			errors = errors + e.getMessage() + "<br/>";
		}
		if (!errors.isEmpty()) {
			model.addObject("error", errors);
		} else {
			model.addObject("msg", "Amount Successfully registered");
		}
		model.setViewName("custodio");
		return model;

	}

	@RequestMapping(value = "/approve/{id}", method = RequestMethod.POST)
	public ModelAndView aproveRequest(@PathVariable("id") long id) {

		ModelAndView model = new ModelAndView();
		String errors = "";
		String username = "";

		try {
			User user = null;
			Double cashBalance = 0D;

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetails = (UserDetails) auth.getPrincipal();
				username = userDetails.getUsername();
			}

			if ((username == null) || (username.isEmpty())) {
				errors = errors + "Information of current USER not found" + "<br/>";
			} else {
				model.addObject("username", username);

				try {

					user = userService.getOrCreate(username);
					publicRequestService.approveRequest(id, user.getId());
				} catch (ObjectNotExistsInDBException e) {
					logger.info(e);
					errors = errors + e.getMessage() + "<br/>";
				} catch (CashBalanceException cbe) {
					logger.error(cbe);
					errors = errors + cbe.getMessage() + "<br/>";
				}

				if (user != null) {
					try {
						cashBalance = cashBalanceService.getAvailableCash(user.getId());
					} catch (ObjectNotExistsInDBException e) {
						logger.error(e);
						errors = errors + e.getMessage() + "<br/>";
					}
				}

				model.addObject("cashbalance", cashBalance);
			}

			model.addObject("requests", publicRequestService.listNotDeliveredRequests());

		} catch (Exception e) {
			logger.error(e);
			errors = errors + e.getMessage() + "<br/>";
		}
		if (!errors.isEmpty()) {
			model.addObject("error", errors);
		} else {
			model.addObject("msg", "Request Successfully approved!");
		}

		model.setViewName("custodio");
		return model;

	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView viewPage() {

		ModelAndView model = new ModelAndView();
		String errors = "";
		String username = "";
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetails = (UserDetails) auth.getPrincipal();
				username = userDetails.getUsername();
			}

			if ((username == null) || (username.isEmpty())) {
				errors = errors + "Information of current USER not found" + "<br/>";
			} else {
				
				model.addObject("username", username);

				Double cashBalance = 0D;
				try {

					User user = userService.getOrCreate(username);
					cashBalance = cashBalanceService.getAvailableCash(user.getId());
				} catch (ObjectNotExistsInDBException one) {
					logger.error(one);
					errors = errors + one.getMessage() + "<br/>";
				}

				model.addObject("cashbalance", cashBalance);
			}
			model.addObject("requests", publicRequestService.listNotDeliveredRequests());

		} catch (Exception e) {
			logger.error(e);
			errors = errors + e.getMessage() + "<br/>";
		}
		if (!errors.isEmpty()) {
			model.addObject("error", errors);
		}

		model.setViewName("custodio");
		return model;

	}

}
