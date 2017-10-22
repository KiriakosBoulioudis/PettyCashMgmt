package com.pettycash.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import com.pettycash.model.CustodianRequest;


@Repository
public class CustodianRequestDAOImpl implements CustodianRequestDAO {

   @PersistenceContext
   private EntityManager em;

   @Override
   public void add(CustodianRequest request) {
      em.persist(request);
   }

}

