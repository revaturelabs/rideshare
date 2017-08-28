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
	
	/**
	 * Called from AdminController
	 * Toggles ban boolean on given user
	 * @param user - user to toggle ban boolean on 
	 */
	void toggleUserBanStatus(User user);
}
