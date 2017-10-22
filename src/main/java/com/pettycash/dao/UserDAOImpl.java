package com.pettycash.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.pettycash.exception.ObjectNotExistsInDBException;
import com.pettycash.model.User;

@Repository
public class UserDAOImpl implements UserDAO
{
	
	@PersistenceContext
	   private EntityManager em;

	@Override
	public void add(User user) {
		em.persist(user);
		
	}

	@Override
	public User findByName(String name) throws ObjectNotExistsInDBException 
	{		
		Query query = em.createQuery("SELECT u FROM User u WHERE u.name = ?1");
		query.setParameter(1, name);

		query.setMaxResults(1).setFirstResult(0);
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) query.getResultList();

		if (users == null || users.isEmpty()) 
		{
			throw new ObjectNotExistsInDBException("User with name:"+name+" not exists!");
		}
		User user = (User) users.get(0);

		return user;
	}
	
	@Override
	public User findById(Integer id) throws ObjectNotExistsInDBException {
		User user = em.find(User.class, id);

		if (user == null) 
		{
			throw new ObjectNotExistsInDBException("User with id:"+id+" not exists!");
		}
		return user;
	}


	@Override
	public boolean existsUserWithName(String name) {
		Query query = em.createQuery("FROM User u WHERE u.name = ?1");
		query.setParameter(1, name);

		query.setMaxResults(1).setFirstResult(0);
		@SuppressWarnings("unchecked")
		List<User> users = (List<User>) query.getResultList();

		if (users == null || users.isEmpty()) 
		{
			return false;
		}
		return true;
	}


}
