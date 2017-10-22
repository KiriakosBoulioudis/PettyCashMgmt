package com.pettycash.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import com.pettycash.exception.ObjectNotExistsInDBException;
import com.pettycash.model.CashBalance;


@Repository
public class CashBalanceDAOImpl implements CashBalanceDAO {

   @PersistenceContext
   private EntityManager em;

   @Override
   public void add(CashBalance request) {
      em.persist(request);
   }
  
   @Override
   public CashBalance findById(long id) 
   {	   
	   CashBalance balance = em.find(CashBalance.class, id, LockModeType.PESSIMISTIC_WRITE);
	   return balance;
   }
   
   @Override
   public CashBalance updateBalance(CashBalance balance) 
   {
	   CashBalance balanceObj = this.findById(balance.getId());
	   if (balanceObj != null)
	   {
		   em.merge(balanceObj);
	   }
	   return balanceObj;
   }
   
   
   @Override
   public CashBalance findByUserId(Integer userId) throws ObjectNotExistsInDBException {
	   Query query = em.createQuery("FROM CashBalance cb WHERE cb.user.id = ?1 ");
	   query.setParameter(1, userId);
	   
		@SuppressWarnings("unchecked")
		List<CashBalance> balances = (List<CashBalance>) query.getResultList();
		
		if (balances == null || balances.isEmpty()) 
		{
			throw new ObjectNotExistsInDBException("Cash Balance for user with id:"+userId+" not found!");
		}

		return balances.get(0);

   }

}