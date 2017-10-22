package com.pettycash.service;

import com.pettycash.exception.ObjectNotExistsInDBException;
import com.pettycash.model.User;

public interface UserService {
     
    User getOrCreate(String username) throws ObjectNotExistsInDBException;    
     
}


