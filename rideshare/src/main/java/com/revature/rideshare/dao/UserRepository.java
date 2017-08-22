package com.revature.rideshare.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.rideshare.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findBySlackId(String slackId);

}
