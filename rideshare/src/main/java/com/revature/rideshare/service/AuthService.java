package com.revature.rideshare.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.revature.rideshare.dao.PointOfInterestRepository;
import com.revature.rideshare.domain.User;
import com.revature.rideshare.exception.BannedUserException;
import com.revature.rideshare.exception.SlackApiException;

public interface AuthService {

	void setUserService(UserService userService);

	void setPoiRepo(PointOfInterestRepository poiRepo);

	/*
	 * use this when dealing with requesting the identity scopes to authenticate a user
	 */
	String getSlackAccessToken(String code) throws SlackApiException;

	/*
	 * use this when requesting the incoming-webhook and commands scopes to integrate with slack
	 */
	JsonNode getSlackAccessResponse(String code) throws SlackApiException;

	JsonNode getUserIdentity(String token) throws SlackApiException;

	JsonNode getUserProfile(String token, String slackId) throws SlackApiException;

	JsonNode getUserInfo(String token, String slackId) throws SlackApiException;

	User getUserAccount(JsonNode userIdentity, JsonNode userInfo) throws BannedUserException;

	User integrateUser(User u, JsonNode accessResponse);

	String createJsonWebToken(User u);

	User verifyJsonWebToken(String token);

	User getUserFromToken(String token);

}