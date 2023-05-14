package com.rs.fer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rs.fer.bean.Expense;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Integer> {

	List<Expense> findByUserid(int userid);

	List<Expense> findByUseridAndTypeAndDateBetween(int userid, String type, String fromDate, String toDate);
}
