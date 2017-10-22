package com.pettycash.dao;

import java.util.Date;
import java.util.List;

import com.pettycash.exception.ObjectNotExistsInDBException;
import com.pettycash.model.PublicRequest;

public interface PublicRequestDAO 
{
	   void add(PublicRequest request);
	   
	   List<PublicRequest> listDeliveredRequests(Integer userId, Date from, Date to);
	   
	   List<PublicRequest> listNotDeliveredRequests();
	   
	   PublicRequest findById(long id) throws ObjectNotExistsInDBException;
	   
	   PublicRequest updateRequest(PublicRequest request) throws ObjectNotExistsInDBException;
}
