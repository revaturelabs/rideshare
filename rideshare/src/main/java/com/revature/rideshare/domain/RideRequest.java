package com.revature.rideshare.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Represents a User's request for a ride.<br>
 * <br>
 * <b>Notable Fields:</b><br>
 * {@link #requestId}<br>
 * {@link #user}<br>
 * {@link #pickupLocation}<br>
 * {@link #dropOffLocation}<br>
 * {@link #time}<br>
 * {@link #notes}<br>
 * {@link #status}<br>
 */
@Entity
@Table(name = "RIDE_REQUEST")
public class RideRequest implements Serializable, Comparable<RideRequest> {

	private static final long serialVersionUID = 7337880503973485600L;
	/**
	 * The unique ID identifying this ride request in the database.
	 */
	@Id
	@Column(name = "RIDE_REQUEST_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RR_ID_SEQUENCE")
	@SequenceGenerator(name = "RR_ID_SEQUENCE", sequenceName = "RR_ID_SEQUENCE")
	private long requestId;

	/**
	 * The user making the request.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	/**
	 * The {@link PointOfInterest Point of Interest} at which the user is
	 * requesting the ride begin from.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private PointOfInterest pickupLocation;

	/**
	 * The {@link PointOfInterest Point of Interest} at which the user is
	 * requesting the ride begin from.
	 */

	@ManyToOne(fetch = FetchType.EAGER)
	private PointOfInterest dropOffLocation;

	/**
	 * The {@link Date Date/Time} at which the user would like the ride to
	 * commence.
	 */

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIME", nullable = false)
	private Date time;

	/**
	 * Notes the user left for potential drivers looking to accept this ride.
	 */
	@Column(name = "NOTES")
	private String notes;
	/**
	 * The {@link RequestStatus status code} of the user's ride request.<br>
	 * <br>
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "STATUS", nullable = false)
	private RequestStatus status;

	/**
	 * Contains various request statuses for rides.<br>
	 * <b>Possible Values:</b><br>
	 * <b>OPEN</b> User is currently looking for a ride.<br>
	 * <b>SATISFIED</b> User has found a ride.<br>
	 * <b>STALE</b> User has not found a ride in a reasonable amount of
	 * time.<br>
	 */
	public enum RequestStatus {
		OPEN, SATISFIED, STALE
	}

	public RideRequest() {
	}

	public RideRequest(long requestId, User user, PointOfInterest pickupLocation, PointOfInterest dropOffLocation,
			Date time, String notes, RequestStatus status) {
		super();
		this.requestId = requestId;
		this.user = user;
		this.pickupLocation = pickupLocation;
		this.dropOffLocation = dropOffLocation;
		this.time = time;
		this.notes = notes;
		this.status = status;
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PointOfInterest getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(PointOfInterest pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public PointOfInterest getDropOffLocation() {
		return dropOffLocation;
	}

	public void setDropOffLocation(PointOfInterest dropOffLocation) {
		this.dropOffLocation = dropOffLocation;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public int compareTo(RideRequest ar) {
		return this.getTime().compareTo(ar.getTime());
	}

	@Override
	public String toString() {
		return "RideRequest [requestId=" + requestId + ", user=" + user + ", pickupLocation=" + pickupLocation
				+ ", dropOffLocation=" + dropOffLocation + ", time=" + time + ", notes=" + notes + "]";
	}

}
