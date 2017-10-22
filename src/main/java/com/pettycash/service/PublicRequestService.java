package com.pettycash.service;

import java.util.Date;
import java.util.List;

import com.pettycash.exception.CashBalanceException;
import com.pettycash.exception.ObjectNotExistsInDBException;
import com.pettycash.model.PublicRequest;

/**
 * Interface of the Public Request services
 * 
 * @author Kiriakos Boulioudis
 *
 */
public interface PublicRequestService {

	/**
	 * Retrieves the list of the delivered public requests
	 * 
	 * @return The delivered public requests
	 */
	/**
	 * Retrieves the list of the delivered public requests from the given custodian user
	 * for the given period (from - to)
	 * 
	 * @param userId The custodian user id
	 * @param from the date where the period starts
	 * @param to the date where the period ends
	 * @return the list of the delivered requests
	 */
	List<PublicRequest> listDeliveredRequests(Integer userId, Date from, Date to);

	/**
	 * Retrieves the list of the requested but still not delivered public
	 * requests
	 * 
	 * @return The list of the not delivered public requests
	 */
	List<PublicRequest> listNotDeliveredRequests();

	/**
	 * Approves the given public request.
	 * 
	 * @param idRequest
	 *            The id of the public request to be approved
	 * @param idCustodian
	 *            The id of the custodian user who approves the request
	 * @throws ObjectNotExistsInDBException
	 *             if the request or the user doesn't exists in DB
	 * @throws CashBalanceException
	 *             if the cash balance of the custodian user is less than the
	 *             amount of the request
	 */
	void approveRequest(Long idRequest, Integer idCustodian) throws ObjectNotExistsInDBException, CashBalanceException;

	/**
	 * Orders a public request with the given amount (amount) and for the given
	 * person (name)
	 * 
	 * @param name
	 *            The name of the user, person who orders the request
	 * @param amount
	 *            The amount of the request
	 * @throws ObjectNotExistsInDBException
	 *             if a error occurs when trying to get an existing user
	 */
	void orderAmount(String name, Double amount) throws ObjectNotExistsInDBException;

}
