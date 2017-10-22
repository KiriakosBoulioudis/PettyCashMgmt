package com.pettycash.service;

import com.pettycash.exception.ObjectNotExistsInDBException;

public interface CashBalanceService {
	
	void updateCash(Integer userId, Double amount, boolean increase) throws ObjectNotExistsInDBException;
	
	Double getAvailableCash(Integer userId) throws ObjectNotExistsInDBException;

}
