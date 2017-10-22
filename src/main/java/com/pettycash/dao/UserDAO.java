package com.pettycash.dao;

import com.pettycash.exception.ObjectNotExistsInDBException;
import com.pettycash.model.User;


public interface UserDAO 
{
	   void add(User user);
	   
	   //List<User> listUsers();
	   
	   User findById(Integer id) throws ObjectNotExistsInDBException;
	   
	   User findByName(String name) throws ObjectNotExistsInDBException;
	   	   
	   //List<Role> findRolesOfUser(Integer userId);
	   
	   boolean existsUserWithName(String name);
}