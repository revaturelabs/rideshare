package com.revature.rideshare.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.rideshare.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	/**
	 * Finds a user by their Slack Identification.
	 * 
	 * @param slackId the Slack Identification of the user we are searching for
	 * @return the user we were searching for
	 * @see com.revature.rideshare.domain.User The User class
	 */
	User findBySlackId(String slackId);

}
