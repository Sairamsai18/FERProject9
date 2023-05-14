package com.rs.fer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rs.fer.bean.Expense;
import com.rs.fer.bean.User;
import com.rs.fer.repository.ExpenseRepository;
import com.rs.fer.repository.UserRepository;
import com.rs.fer.service.FERService;

@Service
public class FERSrviceImpl implements FERService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ExpenseRepository expenseRepository;

	// RegistrationMain
	@Transactional
	@Override
	public boolean registration(User user) {
		return userRepository.save(user).getId() > 0;
	}

	// LoginMain
	@Override
	public int login(String username, String password) {
		List<User> users = userRepository.findByUsernameAndPassword(username, password);
		if (users == null || users.isEmpty()) {
			return 0;
		} else {
			return users.get(0).getId();
		}

	}

	// AddExpenseMain
	@Transactional
	@Override
	public boolean addExpense(Expense expense) {
		return expenseRepository.save(expense).getId() > 0;
	}

	// EditExpenseMain
	@Override
	public boolean editExpense(Expense expense) {
		return addExpense(expense);
	}

	// DeleteExpanseMain
	@Transactional
	@Override
	public boolean deleteExpense(int expenseId) {
		try {
			expenseRepository.deleteById(expenseId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// ResetPassword
	@Override
	public boolean resetPassword(int id, String newPassword, String oldPassword) {
		User user = getUser(id);
		if (oldPassword.equals(user.getPassword())) {
			user.setPassword(newPassword);
			return registration(user);
		}
		return false;
	}

	// GetExpenseMain
	@Override
	public Expense getExpense(int expenseId) {
		return expenseRepository.findById(expenseId).get();
	}

	// GetExpenseOptions
	@Override
	public List<Expense> getExpenseOptions(int userId) {
		return expenseRepository.findByUserid(userId);
	}

	// GetExpenseReport
	@Override
	public List<Expense> getExpenseReport(int userId, String expenseType, String fromDate, String toDate) {
		return expenseRepository.findByUseridAndTypeAndDateBetween(userId, expenseType, fromDate, toDate);
	}

	// GetUserMain
	@Override
	public User getUser(int userId) {
		return userRepository.findById(userId).get();
	}

	// UpdateUserMain
	@Override
	public boolean updateUser(User user) {
		return registration(user);
	}
}