package com.revature.rideshare.service;

import java.util.List;

import org.springframework.security.provisioning.UserDetailsManager;

import com.revature.rideshare.domain.User;

public interface UserService extends UserDetailsManager {

	List<User> getAll();

	void addUser(User u);

	void updateUser(User user);

	User getUser(long id);

	User getUserBySlackId(String slackId);

	void removeUser(User user);
}
