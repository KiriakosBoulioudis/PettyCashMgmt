package com.pettycash.dao;

import com.pettycash.exception.ObjectNotExistsInDBException;
import com.pettycash.model.CashBalance;

public interface CashBalanceDAO {
	
	void add(CashBalance balance);

	CashBalance findById(long id);
	
	CashBalance findByUserId(Integer id) throws ObjectNotExistsInDBException;

	CashBalance updateBalance(CashBalance balance);

}
