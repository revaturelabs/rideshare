package com.revature.rideshare.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.rideshare.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	/**
	 * Finds a User by their SlackId.
	 * 
	 * @param slackId the SlackId string of the User we are searching for
	 * @return the User we were searching for
	 * @see com.revature.rideshare.domain.User The User class
	 */
	User findBySlackId(String slackId);

}
