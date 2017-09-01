package com.revature.rideshare.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The representation of a User, which could be a rider or a driver.<br>
 * <br>
 * Drivers are referenced by {@link Cars}, while riders are referenced by
 * {@link RideRequest Ride Requests}.<br>
 * <br>
 * <b>Notable Fields:</b><br>
 * {@link #userId}<br>
 * {@link #fullName}, {@link #firstName}, {@link #lastName}<br>
 * {@link #mainPoi}, {@link #workPoi}<br>
 * {@link #email}<br>
 * {@link #slackId}<br>
 * {@link #isAdmin}<br>
 * {@link #isBanned}<br>
 * {@link #slackUrl}<br>
 */
@Entity
@Table(name = "USERS")
public class User implements Serializable, UserDetails {

	private static final long serialVersionUID = -2923889374579038772L;

	/**
	 * The unique ID identifying this user in the database.
	 */
	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_SEQUENCE")
	@SequenceGenerator(name = "USER_ID_SEQUENCE", sequenceName = "USER_ID_SEQUENCE")
	private long userId;

	/**
	 * The user's first name.
	 */

	@Column(name = "FIRST_NAME")
	@JsonIgnore
	private String firstName;

	/**
	 * The user's last name.
	 */

	@Column(name = "LAST_NAME")
	@JsonIgnore
	private String lastName;

	/**
	 * The user's full name which should contain their first name and last name.
	 */
	@Column(name = "FULL_NAME", nullable = false)
	private String fullName;

	/**
	 * The {@link PointOfInterest point of interest} representing the user's
	 * primary place of residence.
	 */
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private PointOfInterest mainPOI;

	/**
	 * The {@link PointOfInterest point of interest} representing the user's
	 * workplace.
	 * 
	 */
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private PointOfInterest workPOI;

	/**
	 * The user's Email Address
	 */
	@Column(name = "EMAIL")
	private String email;

	/**
	 * The user's Slack ID
	 */
	@Column(name = "SLACK_ID", nullable = false)
	private String slackId;

	/**
	 * Flag marking the user as an admin. True if admin, false if not.
	 */
	@Column(name = "IS_ADMIN", nullable = false)
	private boolean isAdmin;

	/**
	 * Flag marking if the user is banned. If true, the user is unable to access
	 * most features of the application.
	 */
	@Column(name = "IS_BANNED", nullable = false)
	private boolean isBanned;

	/**
	 * The slackUrl at which the user can be reached at.
	 */
	@Column(name = "SLACK_URL")
	private String slackUrl;

	public User() {
	}

	public User(long userId, String firstName, String lastName, String fullName, PointOfInterest mainPOI,
			PointOfInterest workPOI, String email, String slackId, boolean isAdmin) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.mainPOI = mainPOI;
		this.workPOI = workPOI;
		this.email = email;
		this.slackId = slackId;
		this.isAdmin = isAdmin;
	}

	public User(long userId, String firstName, String lastName, String fullName, PointOfInterest mainPOI,
			PointOfInterest workPOI, String email, String slackId, boolean isAdmin, boolean isBanned, String slackUrl) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.mainPOI = mainPOI;
		this.workPOI = workPOI;
		this.email = email;
		this.slackId = slackId;
		this.isAdmin = isAdmin;
		this.isBanned = isBanned;
		this.slackUrl = slackUrl;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public PointOfInterest getMainPOI() {
		return mainPOI;
	}

	public void setMainPOI(PointOfInterest mainPOI) {
		this.mainPOI = mainPOI;
	}

	public PointOfInterest getWorkPOI() {
		return workPOI;
	}

	public void setWorkPOI(PointOfInterest workPOI) {
		this.workPOI = workPOI;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSlackId() {
		return slackId;
	}

	public void setSlackId(String slackId) {
		this.slackId = slackId;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isBanned() {
		return isBanned;
	}

	public void setBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}

	public String getSlackUrl() {
		return slackUrl;
	}

	public void setSlackUrl(String slackUrl) {
		this.slackUrl = slackUrl;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", fullName="
				+ fullName + ", mainPOI=" + mainPOI + ", workPOI=" + workPOI + ", email=" + email + ", slackId="
				+ slackId + ", isAdmin=" + isAdmin + ", isBanned=" + isBanned + ", slackUrl=" + slackUrl + "]";
	}

	// Implementations for the methods of the UserDetails interface

	/*
	 * All users will have the role of USER. Administrators will additionally
	 * have the role of ADMIN
	 */
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		if (this.isAdmin) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return authorities;
	}

	/*
	 * UNUSED (a.k.a. credentials) This will either be null or the current slack
	 * api token for the user
	 */
	@JsonIgnore
	@Override
	public String getPassword() {
		return null;
	}

	/*
	 * The slackId of a user will be their username
	 */
	@JsonIgnore
	@Override
	public String getUsername() {
		return slackId;
	}

	/*
	 * Accounts will never expire
	 */
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/*
	 * UNUSED This is probably meant to be used for preventing multiple
	 * simultaneous logins for a single user. Taking advantage of this requires
	 * the addition of another field in this class, although that field may not
	 * need to be persisted to the database.
	 */
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/*
	 * Slack API tokens probably do expire eventually, but until the actual
	 * expiration date can be determined, this will just always return true
	 */
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/*
	 * Enabled will be the same as not banned
	 */
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return !isBanned;
	}

}
