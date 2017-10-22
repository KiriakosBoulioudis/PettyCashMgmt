package com.pettycash.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pettycash.dao.CashBalanceDAO;
import com.pettycash.dao.UserDAO;
import com.pettycash.exception.ObjectNotExistsInDBException;
import com.pettycash.model.CashBalance;
import com.pettycash.model.User;

/**
 * Service class for the Cash Balance
 * 
 * @author Kiriakos Boulioudis
 *
 */
@Service
public class CashBalanceServiceImpl implements CashBalanceService {

	@Autowired
	private CashBalanceDAO cashBalanceDao;

	@Autowired
	private UserDAO userDao;

	@Override
	public void updateCash(Integer userId, Double amount, boolean increase) throws ObjectNotExistsInDBException {
		User user = userDao.findById(userId);
		CashBalance cashBalance = null;
		try {
			cashBalance = cashBalanceDao.findByUserId(userId);
		} catch (ObjectNotExistsInDBException e) {
			cashBalance = new CashBalance();
			cashBalance.setUser(user);
			cashBalance.setAmount(0D);
			cashBalance.setChangeDate(new Date(System.currentTimeMillis()));

			cashBalanceDao.add(cashBalance);
		}
		if (increase) {
			cashBalance.setAmount(cashBalance.getAmount() + amount);
		} else {
			cashBalance.setAmount(cashBalance.getAmount() - amount);
		}
		cashBalanceDao.updateBalance(cashBalance);
	}

	@Transactional
	@Override
	public Double getAvailableCash(Integer userId) throws ObjectNotExistsInDBException {
		CashBalance cashBalance = null;
		User user = userDao.findById(userId);
		try {
			cashBalance = cashBalanceDao.findByUserId(userId);
		} catch (ObjectNotExistsInDBException e) {
			cashBalance = new CashBalance();
			cashBalance.setUser(user);
			cashBalance.setAmount(0D);
			cashBalance.setChangeDate(new Date(System.currentTimeMillis()));

			cashBalanceDao.add(cashBalance);
		}

		return cashBalance.getAmount();
	}

}