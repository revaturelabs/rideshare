package com.revature.rideshare.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

public class SlackApiException extends OAuth2Exception {

	private static final long serialVersionUID = -1834743565521771198L;

	public SlackApiException(String msg) {
		super(msg);
	}

}
