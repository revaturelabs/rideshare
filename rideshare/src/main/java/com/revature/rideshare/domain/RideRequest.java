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


@Entity
@Table(name = "RIDE_REQUEST")
public class RideRequest implements Serializable, Comparable<RideRequest> {

	private static final long serialVersionUID = 7337880503973485600L;

	@Id
	@Column(name = "RIDE_REQUEST_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RR_ID_SEQUENCE")
	@SequenceGenerator(name = "RR_ID_SEQUENCE", sequenceName = "RR_ID_SEQUENCE")
	private long requestId;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	private PointOfInterest pickupLocation;

	@ManyToOne(fetch = FetchType.EAGER)
	private PointOfInterest dropOffLocation;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIME", nullable = false)
	private Date time;

	@Column(name = "NOTES")
	private String notes;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "STATUS", nullable = false)
	private RequestStatus status;

	public enum RequestStatus {
		OPEN, // User currently looking for ride
		SATISFIED, // User has found a ride
		STALE // User has not found a ride in a reasonable amount of time
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
