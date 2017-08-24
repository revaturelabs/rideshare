package com.revature.rideshare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.revature.rideshare.dao.UserRepository;
import com.revature.rideshare.domain.User;

@Component("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	public UserServiceImpl() {
	}

	public void setUserRepo(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public List<User> getAll() {
		return userRepo.findAll();
	}

	@Override
	public void addUser(User u) {
		userRepo.saveAndFlush(u);
	}

	@Override
	public User getUser(long id) {
		return userRepo.getOne(id);
	}

	@Override
	public User getUserBySlackId(String slackId) {
		return userRepo.findBySlackId(slackId);
	}

	@Override
	public void removeUser(User user) {
		userRepo.delete(user);
	}

	@Override
	public void updateUser(User user) {
		userRepo.saveAndFlush(user);
	}

	// Implementations for the methods of the UserDetailsManager interface
	// (For this implementation, slackId acts as a user's username)
	
	/*
	 * User's do not currently have passwords
	 */
	@Override
	public void changePassword(String oldPassword, String newPassword) {}

	@Override
	public void createUser(UserDetails u) {
		userRepo.saveAndFlush((User) u);
	}

	@Override
	public void deleteUser(String slackId) {
		userRepo.delete(userRepo.findBySlackId(slackId));
	}

	@Override
	public void updateUser(UserDetails u) {
		userRepo.saveAndFlush((User) u);
	}

	@Override
	public boolean userExists(String slackId) {
		return userRepo.findBySlackId(slackId) != null;
	}

	@Override
	public UserDetails loadUserByUsername(String slackId) throws UsernameNotFoundException {
		return userRepo.findBySlackId(slackId);
	}
	
}
