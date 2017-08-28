package com.revature.rideshare.service;

import java.util.List;

import com.revature.rideshare.domain.User;

public interface AdminService {
	/**
	 * Called from AdminController
	 * Gets list of all users
	 * @return - list of all users
	 */
	List<User> getAll();
}
