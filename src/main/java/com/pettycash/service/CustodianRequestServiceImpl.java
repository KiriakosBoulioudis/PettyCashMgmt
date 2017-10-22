package com.pettycash.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pettycash.dao.CustodianRequestDAO;
import com.pettycash.dao.UserDAO;
import com.pettycash.exception.ObjectNotExistsInDBException;
import com.pettycash.model.CustodianRequest;
import com.pettycash.model.User;


@Service
public class CustodianRequestServiceImpl implements CustodianRequestService {

   @Autowired
   private CustodianRequestDAO custodianRequestDao;
   
   @Autowired
   CashBalanceService cashBalanceService; 
   
   @Autowired
   private UserDAO userDao;
   
   
   @Transactional
   @Override
   public void registerGivenAmount(Integer custodianUserId, Double amount) throws ObjectNotExistsInDBException {
	   CustodianRequest request = new CustodianRequest();
	   User user = userDao.findById(custodianUserId);
	   if(user != null)
	   {
		   request.setRequestUser(user);
		   request.setAmount(amount);
		   request.setRequestDate(new Date(System.currentTimeMillis()));
		   
		   custodianRequestDao.add(request);
		   
		   cashBalanceService.updateCash(user.getId(), amount, true);
		   
	   }
	   
	
   }
   
   
  
}


