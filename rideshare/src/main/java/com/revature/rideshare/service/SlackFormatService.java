package com.revature.rideshare.service;

import org.codehaus.jackson.JsonNode;

import com.revature.rideshare.domain.User;

public interface SlackFormatService {

	void setSlackMessageService(SlackMessageService slackMessageService);

	void setUserService(UserService userService);

	/**
	 * Checks if a date is a valid date.  A valid date is one that is not prior to today's date
	 * @param String date which is being validated
	 * @return boolean True if the date is valid, false otherwise
	 */
	boolean acceptDate(String date);

	/**
	 * Checks if the user is in the database.
	 * @param String slackId
	 * @return User If user exists with slackId, otherwise null.
	 */
	User isValidUser(String slackId);

	String isValidUserAndDate(String slackId, String text);

	/**
	 * Checks if the time the user chose has already passed.
	 * @param JsonNode payload
	 * @return boolean True if time has passed, false otherwise
	 */
	boolean isPreviousTime(JsonNode payload);

}