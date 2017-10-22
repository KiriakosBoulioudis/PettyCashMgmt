package com.pettycash.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pettycash.dao.UserDAO;
import com.pettycash.exception.ObjectNotExistsInDBException;
import com.pettycash.model.User;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDao;

	@Override
	public User getOrCreate(String name) throws ObjectNotExistsInDBException {

		User user = null;

		if (userDao.existsUserWithName(name)) {
			user = userDao.findByName(name);
		} else {
			user = new User();
			user.setName(name);

			userDao.add(user);
			
		}

		return user;
	}

}
