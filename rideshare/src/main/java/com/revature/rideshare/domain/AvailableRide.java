package com.revature.rideshare.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Check;

/**
 * Represents an offer for a ride by a driver.<br>
 * <br>
 * <b>Notable Fields:</b><br>
 * {@link #availRideId}<br>
 * {@link #car}<br>
 * {@link #pickupPOI}<br>
 * {@link #dropoffPOI}<br>
 * {@link #seatsAvailable}<br>
 * {@link #time}<br>
 * {@link #notes}<br>
 * {@link #isOpen}<br>
 *
 */
@Entity
@Table(name = "AVAILABLE_RIDES")
@Check(constraints = "AVAILABLE_SEATS >= 0")
public class AvailableRide implements Serializable, Comparable<AvailableRide> {

	private static final long serialVersionUID = -5753230302496991697L;
	/**
	 * The ID that uniquely identifies this available ride in the database.
	 */
	@Id
	@Column(name = "AVAILABLE_RIDE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AR_ID_SEQUENCE")
	@SequenceGenerator(name = "AR_ID_SEQUENCE", sequenceName = "AR_ID_SEQUENCE")
	private long availRideId;

	/**
	 * The {@link Car} associated with the {@link User Driver} offering this
	 * ride.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private Car car;

	/**
	 * The {@link PointOfInterest Point of Interest} this ride is to begin from.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private PointOfInterest pickupPOI;

	/**
	 * The {@link PointOfInterest Point of Interest} this ride is to end at.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private PointOfInterest dropoffPOI;

	/**
	 * The number of seats available for additional riders.
	 */
	@Column(name = "AVAILABLE_SEATS", nullable = false)
	private short seatsAvailable;
	/**
	 * The time at which this ride is set to leave.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIME", nullable = false)
	private Date time;

	/**
	 * Any notes the driver has left for potential riders.
	 */
	@Column(name = "NOTES")
	private String notes;

	/**
	 * boolean indicating weather or not this ride is still available for new
	 * riders.
	 */
	@Column(name = "IS_OPEN", nullable = false)
	private boolean isOpen;

	public AvailableRide() {
	}

	public AvailableRide(long availRideId, Car car, PointOfInterest pickupPOI, PointOfInterest dropoffPOI,
			short seatsAvailable, Date time, String notes, boolean isOpen) {
		super();
		this.availRideId = availRideId;
		this.car = car;
		this.pickupPOI = pickupPOI;
		this.dropoffPOI = dropoffPOI;
		this.seatsAvailable = seatsAvailable;
		this.time = time;
		this.notes = notes;
		this.isOpen = isOpen;
	}

	public long getAvailRideId() {
		return availRideId;
	}

	public void setAvailRideId(long availRideId) {
		this.availRideId = availRideId;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public PointOfInterest getPickupPOI() {
		return pickupPOI;
	}

	public void setPickupPOI(PointOfInterest pickupPOI) {
		this.pickupPOI = pickupPOI;
	}

	public PointOfInterest getDropoffPOI() {
		return dropoffPOI;
	}

	public void setDropoffPOI(PointOfInterest dropoffPOI) {
		this.dropoffPOI = dropoffPOI;
	}

	public short getSeatsAvailable() {
		return seatsAvailable;
	}

	public void setSeatsAvailable(short seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
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

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	@Override
	public int compareTo(AvailableRide ar) {
		return this.getTime().compareTo(ar.getTime());
	}

	@Override
	public String toString() {
		return "AvailableRide [availRideId=" + availRideId + ", car=" + car + ", pickupPOI=" + pickupPOI
				+ ", dropoffPOI=" + dropoffPOI + ", seatsAvailable=" + seatsAvailable + ", time=" + time + ", notes="
				+ notes + "]";
	}

}
