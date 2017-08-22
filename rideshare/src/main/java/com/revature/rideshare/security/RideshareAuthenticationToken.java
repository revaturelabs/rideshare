package com.revature.rideshare.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.revature.rideshare.domain.User;

public class RideshareAuthenticationToken extends PreAuthenticatedAuthenticationToken {
	
	private static final long serialVersionUID = 3885076944142687221L;
	
	private String principal;
	private String credentials;
	
	public RideshareAuthenticationToken(String slackId, String credentials, UserDetails user, Collection<? extends GrantedAuthority> authorities) {
		super(slackId, credentials, authorities);
		super.setDetails(user);
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		this.credentials = null;
	}

	@Override
	public String getName() {
		return super.getName();
	}
	
	public User getUser() {
		return (User) super.getDetails();
	}
}
