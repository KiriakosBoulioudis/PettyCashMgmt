package com.pettycash.exception;

public class ObjectNotExistsInDBException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ObjectNotExistsInDBException(String message) {
        super(message);
    }
	
}