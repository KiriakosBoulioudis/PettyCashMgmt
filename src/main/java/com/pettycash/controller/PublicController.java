package com.pettycash.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.pettycash.service.PublicRequestService;
import com.pettycash.service.UserService;

@RestController
@RequestMapping(value = "public")
public class PublicController {

	private final Logger logger = Logger.getLogger(PublicController.class);

	@Autowired
	PublicRequestService publicRequestService;

	@Autowired
	CashBalanceService cashBalanceService;

	@Autowired
	UserService userService;

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public ModelAndView orderRequest(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "amount", required = true) Double amount) {

		ModelAndView model = new ModelAndView();
		String errors = "";
		if(name.isEmpty())
		{
			errors =errors+ "Please enter a name"+"<br/>";
		}

		try {
			publicRequestService.orderAmount(name, amount);
		} catch (ObjectNotExistsInDBException one) {
			logger.error(one);
			errors = errors+ one.getMessage()+"<br/>";
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
			model.addObject("msg", "Request Successfully ordered!");
		}

		model.setViewName("public");
		return model;

	}

	@RequestMapping(value = "/approve/{id}", method = RequestMethod.POST)
	public ModelAndView aproveRequest(@PathVariable("id") long id) {

		ModelAndView model = new ModelAndView();
		String errors = "";

		try {
			User user = null;
			Double cashBalance = 0D;

			try {

				user = userService.getOrCreate("custodio");
				publicRequestService.approveRequest(id, user.getId());
			} catch (ObjectNotExistsInDBException e) {
				logger.info(e);
				errors = errors+ e.getMessage()+"<br/>";
			} catch (CashBalanceException cbe) {
				logger.error(cbe);
				errors = errors+ cbe.getMessage()+"<br/>";
			}

			if (user != null) {
				try {
					cashBalance = cashBalanceService.getAvailableCash(user.getId());
				} catch (ObjectNotExistsInDBException e) {
					logger.error(e);
					errors = errors+ e.getMessage()+"<br/>";
				}
			}

			model.addObject("cashbalance", cashBalance);

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
			model.addObject("msg", "Request Successfully approved!");
		}

		model.setViewName("custodio");
		return model;

	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView viewPage() {

		ModelAndView model = new ModelAndView();

		model.setViewName("public");

		return model;

	}

}
