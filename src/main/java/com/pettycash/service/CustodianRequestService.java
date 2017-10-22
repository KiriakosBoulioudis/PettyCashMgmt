package com.pettycash.service;

import com.pettycash.exception.ObjectNotExistsInDBException;

public interface CustodianRequestService {
	
	void registerGivenAmount(Integer custodianUserId, Double amount) throws ObjectNotExistsInDBException;

}
