package com.revature.rideshare.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.domain.Car;
import com.revature.rideshare.domain.PointOfInterest;
import com.revature.rideshare.domain.Ride;
import com.revature.rideshare.domain.User;
import com.revature.rideshare.service.CarService;
import com.revature.rideshare.service.PointOfInterestService;
import com.revature.rideshare.service.RideService;
import com.revature.rideshare.service.UserService;

@RestController
@RequestMapping("admin")
public class AdminController {

	@Autowired
	private RideService rideService;

	@Autowired
	private UserService userService;

	@Autowired
	private CarService carService;

	@Autowired
	private PointOfInterestService poiService;

	/**
	 * Retrieve all Cars. 
	 * 
	 * @return List of all Car objects 
	 */
	@GetMapping("/cars")
	public List<Car> getAllCars() {
		return carService.getAll();
	}

	/**
	 * Retrieve all Users.  
	 * 
	 * @return List of all User objects
	 */
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userService.getAll();
	}

	/**
	 * Takes the User's ID and a boolean value representing the user's Admin status, and changes the
	 * User's Admin status accordingly. 
	 * 
	 * @param id - number value representing each User
	 * @param isAdmin - true if user is Admin, false otherwise 
	 */
	@PostMapping("/updateStatus/{id}/{isAdmin}")
	public void updateStatus(@PathVariable(value = "id") long id, @PathVariable(value = "isAdmin") boolean isAdmin) {
		User user = userService.getUser(id);
		user.setAdmin(isAdmin);
		userService.updateUser(user);
	}

	/**
	 * Removes a User from the view or the database???? TBD
	 * Currently unused. 
	 * 
	 * @param id - id of the User to be removed 
	 */
	@PostMapping("/removeUser/{id}")
	public void removeUser(@PathVariable(value = "id") long id) {
		User user = userService.getUser(id);
		userService.removeUser(user);
	}

	/** 
	 * Adds a new Point of Interest object. Currently allows duplicates of addresses. 
	 * 
	 * @param poi - the PointofInterest to be added 
	 */
	@PostMapping("/addPOI")
	public void addPoi(@RequestBody PointOfInterest poi) {
		poiService.addPoi(poi);
	}

	/**
	 * Removes an existing Point of Interest from the database. 
	 * 
	 * 
	 * @param poi - the PointofInterst to be removed
	 */
	@PostMapping("/removePOI")
	public void removePoi(@RequestBody PointOfInterest poi) {
		poiService.removePoi(poi);
	}

	/**
	 * Retrieve all Rides that are currently in progress or that are waiting for a driver to accept. 
	 * 
	 * @return List of all Rides considered "active" 
	 */
	@GetMapping("/activeRides")
	public List<Ride> getAllActiveRides() {
		return rideService.getAllActiveRides();
	}

	/**
	 * Retrieve all Rides that no longer considered "active", i.e. Rides that have been completed or Rides
	 * that have gone "stale" - a set amount of time without being accepted. Note: "stale" status has
	 * currently not been implemented. 
	 * 
	 * @return List of all Rides considered "inactive" 
	 */
	@GetMapping("/rideHistory")
	public List<Ride> getAllInactiveRides() {
		return rideService.getAllInactiveRides();
	}
}
