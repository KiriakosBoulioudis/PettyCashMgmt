package com.pettycash.service;

import com.pettycash.exception.ObjectNotExistsInDBException;
import com.pettycash.model.User;

/**
 * Interface of the User services
 * 
 * @author Kiriakos Boulioudis
 *
 */
public interface UserService {

	/**
	 * Retrieves an existent user or creates a new user if no user with the
	 * given name exists.
	 * 
	 * @param username
	 *            The user name of the user to be found or created
	 * @return Returns the user model of the user
	 * @throws ObjectNotExistsInDBException
	 *             if a error occurs when trying to get an existing user
	 */
	User getOrCreate(String username) throws ObjectNotExistsInDBException;

}
