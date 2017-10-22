package com.pettycash.service;

import com.pettycash.exception.ObjectNotExistsInDBException;

/**
 * Interface of the Custodian Request services
 * 
 * @author Kiriakos Boulioudis
 *
 */
public interface CustodianRequestService {

	/**
	 * Registers the given amount to the custodian user (custodianUserId). This
	 * amount will be available in the cash balance of the user
	 * 
	 * @param custodianUserId
	 *            The user id of the custodian user
	 * @param amount
	 *            The amount to be registered
	 * @throws ObjectNotExistsInDBException
	 *             if the user with the given id doesn't exists
	 */
	void registerGivenAmount(Integer custodianUserId, Double amount) throws ObjectNotExistsInDBException;

}
