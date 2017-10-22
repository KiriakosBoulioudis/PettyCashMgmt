package com.pettycash.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pettycash.dao.PublicRequestDAO;
import com.pettycash.dao.UserDAO;
import com.pettycash.exception.CashBalanceException;
import com.pettycash.exception.ObjectNotExistsInDBException;
import com.pettycash.model.PublicRequest;
import com.pettycash.model.User;


@Service
public class PublicRequestServiceIml implements PublicRequestService {

   @Autowired
   private PublicRequestDAO publicRequestDao;
   
   @Autowired
   CashBalanceService cashBalanceService; 
   
   @Autowired
   UserService userService; 
   
   @Autowired
   private UserDAO userDao;

   
   @Override
   public List<PublicRequest> listDeliveredRequests(Integer userId, Date from, Date to) {
      return publicRequestDao.listDeliveredRequests(userId, from, to);
   }
   
   @Override
   public List<PublicRequest> listNotDeliveredRequests() {
       return publicRequestDao.listNotDeliveredRequests();
   }   
   
   
   @Transactional
   @Override
   public void orderAmount(String name, Double amount) throws ObjectNotExistsInDBException
   {
	   PublicRequest request = new PublicRequest();
	   User user = userService.getOrCreate(name);
	   if(user != null)
	   {
		   request.setRequestUser(user);
		   request.setAmount(amount);
		   request.setRequestDate(new Date(System.currentTimeMillis()));
		   request.setDelivered(false);
		   
		   publicRequestDao.add(request);
		   
	   }
   }
   
   
   @Transactional
   @Override
   public void approveRequest(Long idRequest, Integer idCustodian) 
		   throws ObjectNotExistsInDBException, CashBalanceException
   {
	   PublicRequest request = publicRequestDao.findById(idRequest);
	   User user = userDao.findById(idCustodian);
	   if((request != null)&&(user != null))
	   {
		   Double availableAmount = cashBalanceService.getAvailableCash(idCustodian);
		   if(availableAmount.doubleValue() < request.getAmount().doubleValue())
		   {
			   throw new CashBalanceException("Not enough money for this request!");
		   }
		   
		   request.setDelivered(true);
		   request.setDeliveryDate(new Date(System.currentTimeMillis()));
		   request.setDeliveryUser(user);
		   
		   publicRequestDao.updateRequest(request);
		   
		   cashBalanceService.updateCash(user.getId(), request.getAmount(), false);
	   }
   }

  
}

