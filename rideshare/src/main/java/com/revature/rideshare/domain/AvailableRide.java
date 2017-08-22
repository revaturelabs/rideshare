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

@Entity
@Table(name = "AVAILABLE_RIDES")
@Check(constraints = "AVAILABLE_SEATS >= 0")
public class AvailableRide implements Serializable, Comparable<AvailableRide> {

	private static final long serialVersionUID = -5753230302496991697L;

	@Id
	@Column(name = "AVAILABLE_RIDE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AR_ID_SEQUENCE")
	@SequenceGenerator(name = "AR_ID_SEQUENCE", sequenceName = "AR_ID_SEQUENCE")
	private long availRideId;

	@ManyToOne(fetch = FetchType.EAGER)
	private Car car;

	@ManyToOne(fetch = FetchType.EAGER)
	private PointOfInterest pickupPOI;

	@ManyToOne(fetch = FetchType.EAGER)
	private PointOfInterest dropoffPOI;

	@Column(name = "AVAILABLE_SEATS", nullable = false)
	private short seatsAvailable;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIME", nullable = false)
	private Date time;

	@Column(name = "NOTES")
	private String notes;

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
