package com.revature.rideshare.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Rides")
public class Ride implements Serializable {

	private static final long serialVersionUID = -2957865032918745458L;

	@Id
	@Column(name = "RIDE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "R_ID_SEQUENCE")
	@SequenceGenerator(name = "R_ID_SEQUENCE", sequenceName = "R_ID_SEQUENCE")
	private long rideId;

	@OneToOne(fetch = FetchType.LAZY)
	private AvailableRide availRide;

	@OneToOne(fetch = FetchType.LAZY)
	private RideRequest request;

	@Column(name = "WAS_SUCCESSFUL")
	private Boolean wasSuccessful;

	@Column(name = "COMPLAINT")
	private String complaint;

	public Ride() {
	}

	public Ride(long rideId, AvailableRide availRide, RideRequest request, Boolean wasSuccessful, String complaint) {
		this.rideId = rideId;
		this.availRide = availRide;
		this.request = request;
		this.wasSuccessful = wasSuccessful;
		this.complaint = complaint;
	}

	public long getRideId() {
		return rideId;
	}

	public void setRideId(long rideId) {
		this.rideId = rideId;
	}

	public AvailableRide getAvailRide() {
		return availRide;
	}

	public void setAvailRide(AvailableRide availRide) {
		this.availRide = availRide;
	}

	public RideRequest getRequest() {
		return request;
	}

	public void setRequest(RideRequest request) {
		this.request = request;
	}

	public Boolean getWasSuccessful() {
		return wasSuccessful;
	}

	public void setWasSuccessful(Boolean wasSuccessful) {
		this.wasSuccessful = wasSuccessful;
	}

	public String getComplaint() {
		return complaint;
	}

	public void setComplaint(String complaint) {
		this.complaint = complaint;
	}

	@Override
	public String toString() {
		return "Ride [rideId=" + rideId + ", availRide=" + availRide + ", request=" + request + ", wasSuccessful="
				+ wasSuccessful + ", complaint=" + complaint + "]";
	}

}
