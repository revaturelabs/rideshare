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

/**
 * Appears to be a record of a ride that is either in progress or has already
 * taken place from a rider's perspective.<br>
 * <br>
 * <b>Notable Fields:</b><br>
 * {@link #rideId}<br>
 * {@link #availRide}<br>
 * {@link #request}<br>
 * {@link #wasSuccessful}<br>
 * {@link #complaint}<br>
 *
 */
@Entity
@Table(name = "Rides")
public class Ride implements Serializable {

	private static final long serialVersionUID = -2957865032918745458L;

	/**
	 * the Unique ID representing this ride in the database
	 */
	@Id
	@Column(name = "RIDE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "R_ID_SEQUENCE")
	@SequenceGenerator(name = "R_ID_SEQUENCE", sequenceName = "R_ID_SEQUENCE")
	private long rideId;

	/**
	 * The {@link AvailableRide Available Ride} offered by another user that the
	 * user placing the {@link #request} accepted.
	 */
	@OneToOne(fetch = FetchType.LAZY)
	private AvailableRide availRide;

	/**
	 * The {@link RideRequest Ride Request} that was paired with the
	 * {@link availRide Available Ride} this Ride is a long-term record of.
	 */
	@OneToOne(fetch = FetchType.LAZY)
	private RideRequest request;

	/**
	 * A boolean representing if this ride was successfully finished or not.}
	 */
	@Column(name = "WAS_SUCCESSFUL")
	private Boolean wasSuccessful;

	/**
	 * A String containing any and all complaints made by the rider against the driver.
	 */
	
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
