package com.pettycash.service;

import com.pettycash.exception.ObjectNotExistsInDBException;

/**
 * Interface of the Cash Balance services
 * 
 * @author Kiriakos Boulioudis
 *
 */
public interface CashBalanceService {

	/**
	 * Updates the cash balance of the user with id (userId). If parameter
	 * increase is true then the given amount will be increased to the current
	 * cash balance. If parameter increase is true then the given amount will be
	 * decreased of the current cash balance.
	 * 
	 * @param userId
	 *            The id of the custodian user
	 * @param amount
	 *            The amount to be actualize
	 * @param increase
	 *            The type to actualize true=increase false=decrease
	 * @throws ObjectNotExistsInDBException
	 *             if the user can't be found
	 */
	void updateCash(Integer userId, Double amount, boolean increase) throws ObjectNotExistsInDBException;

	/**
	 * Retrieves the current available cash balance of the given user (userId)
	 * 
	 * @param userId
	 *            The id of the custodian user
	 * @return The current available amount of the custodian
	 * @throws ObjectNotExistsInDBException
	 *             if the user can't be found
	 */
	Double getAvailableCash(Integer userId) throws ObjectNotExistsInDBException;

}
