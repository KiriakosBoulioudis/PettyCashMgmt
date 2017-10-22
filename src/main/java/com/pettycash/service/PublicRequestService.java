package com.pettycash.service;

import java.util.List;

import com.pettycash.exception.CashBalanceException;
import com.pettycash.exception.ObjectNotExistsInDBException;
import com.pettycash.model.PublicRequest;

public interface PublicRequestService {
   
	List<PublicRequest> listDeliveredRequests();
	
	List<PublicRequest> listNotDeliveredRequests();
	
	void approveRequest(Long idRequest, Integer idCustodian) 
			throws ObjectNotExistsInDBException, CashBalanceException;
	
	void orderAmount(String name, Double amount) throws ObjectNotExistsInDBException;

}


