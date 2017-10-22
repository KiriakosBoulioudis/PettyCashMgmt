package com.pettycash.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import com.pettycash.exception.ObjectNotExistsInDBException;
import com.pettycash.model.PublicRequest;


@Repository
public class PublicReuestDAOImpl implements PublicRequestDAO {

   @PersistenceContext
   private EntityManager em;

   @Override
   public void add(PublicRequest request) {
      em.persist(request);
   }

   @Override
   public List<PublicRequest> listDeliveredRequests() {
	   Query query = em.createQuery("FROM PublicRequest pr WHERE pr.delivered = ?1");
		query.setParameter(1, true);
		@SuppressWarnings("unchecked")
		List<PublicRequest> requests = (List<PublicRequest>) query.getResultList();

		return requests;
   }
   
 
   @Override
	public List<PublicRequest> listNotDeliveredRequests() {
		Query query = em.createQuery("FROM PublicRequest pr WHERE pr.delivered = ?1");
		query.setParameter(1, false);
		@SuppressWarnings("unchecked")
		List<PublicRequest> requests = (List<PublicRequest>) query.getResultList();

		return requests;
	}
	

   @Override
   public PublicRequest findById(long id) throws ObjectNotExistsInDBException 
   {	   
	   PublicRequest request = em.find(PublicRequest.class, id, LockModeType.PESSIMISTIC_WRITE);

		if (request == null) 
		{
			throw new ObjectNotExistsInDBException("Public Request with id:"+id+" not exists!");
		}
		return request;
	   
   }
   
   @Override
   public PublicRequest updateRequest(PublicRequest request) throws ObjectNotExistsInDBException 
   {
	   PublicRequest requestObj = this.findById(request.getId());
	   if (requestObj != null)
	   {
		   em.merge(requestObj);
	   }
	   return requestObj;
   }

}

