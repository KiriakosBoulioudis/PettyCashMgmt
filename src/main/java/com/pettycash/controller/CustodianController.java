package com.pettycash.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
		try {
			Double cashBalance = 0D;
			User user = null;

			try {
				user = userService.getOrCreate("custodio");

				if ((amount != null) && (user != null)) {
					custodianRequestService.registerGivenAmount(user.getId(), amount);
				}
			} catch (ObjectNotExistsInDBException e) {
				logger.error(e);
				errors = errors+ e.getMessage()+"<br/>";
			}

			try {

				if (user != null) {
					cashBalance = cashBalanceService.getAvailableCash(user.getId());
				}

				model.addObject("cashbalance", cashBalance);

			} catch (ObjectNotExistsInDBException one) {
				logger.error(one);
				errors = errors+ one.getMessage()+"<br/>";
			}

			model.addObject("requests", publicRequestService.listNotDeliveredRequests());

		} catch (Exception e) {
			logger.error(e);
			errors = errors+ e.getMessage()+"<br/>";
		}
		if(!errors.isEmpty())
		{
			model.addObject("error", errors);
		}
		else
		{
			model.addObject("msg", "Amount Successfully registered");
		}
		model.setViewName("custodio");
		return model;

	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView viewPage() {

		ModelAndView model = new ModelAndView();
		try {
			Double cashBalance = 0D;
			try {

				User user = userService.getOrCreate("custodio");
				cashBalance = cashBalanceService.getAvailableCash(user.getId());
			} catch (ObjectNotExistsInDBException one) {
				logger.error(one);
				model.addObject("error", one.getMessage());
			}

			model.addObject("cashbalance", cashBalance);
			model.addObject("requests", publicRequestService.listNotDeliveredRequests());
			
		} catch (Exception e) {
			logger.error(e);
			model.addObject("error", e.getMessage());
		}

		model.setViewName("custodio");
		return model;

	}

}
