package com.rs.fer.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rs.fer.bean.Expense;
import com.rs.fer.bean.User;
import com.rs.fer.repository.ExpenseRepository;
import com.rs.fer.service.FERService;

@RestController
public class FERController {

	@Autowired
	private FERService ferService;

	@Autowired
	ExpenseRepository expenseRepository;

	// Registration
	@PostMapping(value = "/registration")
	public ResponseEntity<User> registration(@RequestBody User user) throws IOException {

		// business logic
		boolean isRegister = ferService.registration(user);

		// Display the input
		if (isRegister) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	// AddExpense
	@PostMapping(value = "/addExpense")
	public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) throws IOException {

		// call the service for business logic
		boolean isAddExpense = ferService.addExpense(expense);

		// display the out put
		if (isAddExpense) {
			return new ResponseEntity<>(expense, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(expense, HttpStatus.NOT_FOUND);
		}
	}

	// EditExpense
	@PostMapping(value = "/editExpense")
	public ResponseEntity<Expense> editExpense(@RequestBody Expense expense) throws IOException {

		// call the service for business logic
		boolean isEditExpense = ferService.editExpense(expense);

		// display the out put
		if (isEditExpense) {
			return new ResponseEntity<Expense>(expense, HttpStatus.OK);

		} else {
			return new ResponseEntity<Expense>(expense, HttpStatus.NOT_FOUND);
		}

	}

	// UpdateProfile
	@PutMapping(value = "/updateProfile/{id}")
	public ResponseEntity<User> updateProfile(@PathVariable("id") int id,@RequestBody User user)
			throws IOException {

		// call the service for business logic
		User user1=ferService.getUser(id);
		boolean isUpdateProfile = ferService.updateUser(user1);

		// display the out put
		if (isUpdateProfile) {
			return new ResponseEntity<User>(user1, HttpStatus.OK);

		} else {
			return new ResponseEntity<User>(user1, HttpStatus.NOT_FOUND);
		}

	}

	// getExpense
	@GetMapping("/getExpense/{id}")
	public ResponseEntity<Expense> getExpense(@PathVariable("id") int id) {
		Expense expense = ferService.getExpense(id);

		if (expense.getId() != 0) {
			return new ResponseEntity<Expense>(expense, HttpStatus.OK);
		} else {
			return new ResponseEntity<Expense>(HttpStatus.NOT_FOUND);
		}
	}

	// getExpenseOptions
	@GetMapping("/getExpenseOptions/{userid}")
	public ResponseEntity<List<Expense>> getExpenseOptions(@PathVariable("userid") int userid) {
		List<Expense> expenses = (List<Expense>) expenseRepository.findByUserid(userid);

		if (expenses.isEmpty()) {
			return new ResponseEntity<List<Expense>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Expense>>((List<Expense>) expenses, HttpStatus.OK);
		}
	}

	// getUser
	@GetMapping("/getUser/{id}")
	public ResponseEntity<User> getuser(@PathVariable("id") int id) {
		User user = ferService.getUser(id);

		if (user.getId() != 0) {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}

	// deleteExpense
	@DeleteMapping("/deleteExpense/{id}")
	public ResponseEntity<String> deleteExpense(@PathVariable("id") int id) {
		boolean expense = ferService.deleteExpense(id);

		String a = "deleted";
		if (expense) {
			return new ResponseEntity<String>(a, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("not found", HttpStatus.NOT_FOUND);
		}
	}

	// login
	@GetMapping("/login/{username}/{password}")
	public ResponseEntity<String> login(@PathVariable("username") String username,
			@PathVariable("password") String password) {
		int user = ferService.login(username, password);

		if (user > 0) {
			return new ResponseEntity<String>("login success", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("login failed", HttpStatus.NOT_FOUND);
		}
	}

	// getExpenseReport
	@GetMapping("/getExpenseReport/{userid}/{type}/{fromDate}/{toDate}")
	public ResponseEntity<List<Expense>> getExpenseReport(@PathVariable("userid") int userid,
			@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			@PathVariable("type") String type) {
		List<Expense> expenses = (List<Expense>) expenseRepository.findByUseridAndTypeAndDateBetween(userid, type,
				fromDate, toDate);

		if (expenses.isEmpty()) {
			return new ResponseEntity<List<Expense>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Expense>>((List<Expense>) expenses, HttpStatus.OK);
		}
	}

	// resetPassword
	@PutMapping("/resetPassword/{id}/{newPassword}/{oldPassword}")
	public ResponseEntity<String> resetPassword(@PathVariable("id") int id,
			@PathVariable("newPassword") String newPassword, @PathVariable("oldPassword") String oldPassword)
			throws IOException {

		// call the service for business logic
		/*
		 * String newPassword = "moo"; String oldPassword = "mo";
		 */
		boolean resetPassword = ferService.resetPassword(id, newPassword, oldPassword);

		// display the out put
		if (resetPassword) {
			return new ResponseEntity<String>("password changed", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("password change failed", HttpStatus.NOT_FOUND);
		}
	}

}
