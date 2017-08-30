package com.revature.rideshare.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Class representing a {@link User Driver}'s car<br>
 * <br>
 * <b>Notable Fields:</b><br>
 * {@link #carId}<br>
 * {@link #user}<br>
 * {@link #licensePlate}<br>
 * {@link #brand}<br>
 * {@link #model}<br>
 * {@link #color}<br>
 * {@link #smokeFree}<br>
 * {@link #notes}<br>
 */
@Entity
@Table(name = "CARS")
public class Car implements Serializable {

	private static final long serialVersionUID = -8165384680317463511L;

	/**
	 * The unique ID representing this car in the database.
	 */
	@Id
	@Column(name = "CAR_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAR_ID_SEQUENCE")
	@SequenceGenerator(name = "CAR_ID_SEQUENCE", sequenceName = "CAR_ID_SEQUENCE")
	private long carId;

	/**
	 * The {@link User user} that owns this vehicle.
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;

	/**
	 * The vehicle's license plate number.
	 */

	@Column(name = "LICENSE_PLATE", nullable = false)
	private String licensePlate;

	/**
	 * The car's make or brand.
	 * Examples such as Honda, Ford, Mitsubishi, BMW, 
	 */

	@Column(name = "CAR_BRAND", nullable = false)
	private String brand;

	/**
	 * The car's model.
	 */

	@Column(name = "CAR_MODEL", nullable = false)
	private String model;

	/**
	 * The car's color.
	 */
	@Column(name = "CAR_COLOR", nullable = false)
	private String color;

	/**
	 * A boolean determining weather or not the driver is comfortable with
	 * smoking in their vehicle.
	 */
	@Column(name = "IS_SMOKE_FREE", nullable = false)
	private boolean smokeFree;

	/**
	 * Any notes the driver would like potential riders to know about their
	 * vehicle.
	 */
	@Column(name = "CAR_NOTES")
	private String notes;

	public Car() {
	}

	public Car(String licensePlate, String brand, String model, String color, boolean smokeFree, String notes) {
		super();
		// this.carId = carId;
		// this.user = user;
		this.licensePlate = licensePlate;
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.smokeFree = smokeFree;
		this.notes = notes;
	}

	public long getCarId() {
		return carId;
	}

	public void setCarId(long carId) {
		this.carId = carId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isSmokeFree() {
		return smokeFree;
	}

	public void setSmokeFree(boolean smokeFree) {
		this.smokeFree = smokeFree;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Car [carId=" + carId + ", user=" + user + ", licensePlate=" + licensePlate + ", brand=" + brand
				+ ", model=" + model + ", color=" + color + ", smokeFree=" + smokeFree + ", notes=" + notes + "]";
	}
}
