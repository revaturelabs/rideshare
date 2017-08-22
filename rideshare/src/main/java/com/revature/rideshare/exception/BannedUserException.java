package com.revature.rideshare.exception;

import org.springframework.security.authentication.DisabledException;

public class BannedUserException extends DisabledException {
	
	private static final long serialVersionUID = -4484882365747664904L;

	public BannedUserException(String msg) {
		super(msg);
	}

	public BannedUserException(String msg, Throwable t) {
		super(msg, t);
	}

}
