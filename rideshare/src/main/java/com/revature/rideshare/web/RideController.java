package com.revature.rideshare.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.domain.AvailableRide;
import com.revature.rideshare.domain.Ride;
import com.revature.rideshare.domain.RideRequest;
import com.revature.rideshare.domain.User;
import com.revature.rideshare.service.AuthService;
import com.revature.rideshare.service.RideService;

@RestController
@RequestMapping("ride")
public class RideController {

	@Autowired
	private RideService rideService;

	public void setRideService(RideService rideService) {
		this.rideService = rideService;
	}

	@Autowired
	private AuthService authService;

	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}

	// ALL RIDES
	@GetMapping
	public List<Ride> getAllRides() {
		return rideService.getAll();
	}
	
	// REQUESTS
	@GetMapping("/request")
	public List<RideRequest> getRequestsForCurrentUser(@RequestHeader(name = "X-JWT-RIDESHARE") String token) {
		User u = authService.getUserFromToken(token);
		return rideService.getRequestsForUser(u);
	}

	@GetMapping("/request/accept/{id}")
	public boolean acceptRequest(@PathVariable(value = "id") long id,
			@RequestHeader(name = "X-JWT-RIDESHARE") String token) {
		User u = authService.getUserFromToken(token);
		return rideService.acceptRequest(id, u);
	}

	/**
	 * Takes in a rideID, cancels the RideRequest and reopens the AvailableRide.
	 */
	@GetMapping("/request/cancelRide/{id}")
	public boolean cancelRide(@PathVariable(value = "id") long id,
			@RequestHeader(name = "X-JWT-RIDESHARE") String token) {
		User u = authService.getUserFromToken(token);
		return rideService.cancelRideReopenAvailRide(id, u);
	}
	
	/**
	 * Takes in a Ride ID and deleted the Ride and RideRequest objects
	 * associated.
	 */
	@GetMapping("/request/cancel/{id}")
	public boolean cancelRequest(@PathVariable(value = "id") long id,
			@RequestHeader(name = "X-JWT-RIDESHARE") String token) {
		User u = authService.getUserFromToken(token);
		return rideService.cancelRequest(id, u);
	}
	
	/**
	 * Takes in a RideRequest ID and deleted the RideRequest objects.
	 */
	@GetMapping("/request/cancelActive/{id}")
	public boolean cancelActiveRequest(@PathVariable(value = "id") long id,
			@RequestHeader(name = "X-JWT-RIDESHARE") String token) {
		User u = authService.getUserFromToken(token);
		return rideService.cancelActiveRequest(id, u);
	}
	
	/**
	 * Takes in a Ride ID and marks the Ride and RideRequest objects
	 * associated as complete.
	 */
	@PostMapping("/request/complete/{id}")
	public boolean completeRequest(@PathVariable(value = "id") long id) {
		return rideService.completeRequest(id);
	}

	@PostMapping("/request/add")
	public void addRequest(@RequestBody RideRequest req) {
		rideService.addRequest(req);
	}

	/**
	 * Takes in a User and retrieves all active RideRequests for said User.
	 */
	@GetMapping("/request/open")
	public List<RideRequest> getOpenRequest(@RequestHeader(name = "X-JWT-RIDESHARE") String token) {
		User u = authService.getUserFromToken(token);
		return rideService.getOpenRequestsForUser(u);
	}
	
	@GetMapping("/request/open/{id}")
	public List<RideRequest> getOpenRequests(@PathVariable(value = "id") int id) {
		return rideService.getOpenRequests(id);
	}

	@GetMapping("/request/active")
	public List<Ride> getActiveRequestsForCurrentUser(@RequestHeader(name = "X-JWT-RIDESHARE") String token) {
		User u = authService.getUserFromToken(token);
		return rideService.getActiveRequestsForUser(u);
	}

	@GetMapping("/request/history")
	public List<Ride> getRequestHistoryForCurrentUser(@RequestHeader(name = "X-JWT-RIDESHARE") String token) {
		User u = authService.getUserFromToken(token);
		return rideService.getRequestHistoryForUser(u);
	}

	// OFFERS
	@GetMapping("/offer")
	public List<AvailableRide> getOffersForCurrentUser(@RequestHeader(name = "X-JWT-RIDESHARE") String token) {
		User u = authService.getUserFromToken(token);
		return rideService.getOffersForUser(u);
	}

	@PostMapping("/offer/add")
	public void addOffer(@RequestBody AvailableRide offer) {
		rideService.addOffer(offer);
	}

	@GetMapping("/offer/accept/{id}")
	public boolean acceptOffer(@PathVariable(value = "id") long id,
			@RequestHeader(name = "X-JWT-RIDESHARE") String token) {
		User u = authService.getUserFromToken(token);
		return rideService.acceptOffer(id, u);
	}

	/**
	 * Takes in an AvailableRide ID, deletes all associated Rides and reopens
	 * all associated RideRequests.
	 */
	@GetMapping("/offer/cancel/{id}")
	public boolean cancelOffer(@PathVariable(value = "id") long id,
			@RequestHeader(name = "X-JWT-RIDESHARE") String token) {
		User u = authService.getUserFromToken(token);
		return rideService.cancelOffer(id, u);
	}
	
	/**
	 * Takes in an AvailableRide ID and deletes said AvailableRide.
	 */
	@GetMapping("/offer/cancelActive/{id}")
	public boolean cancelActiveOffer(@PathVariable(value = "id") long id,
			@RequestHeader(name = "X-JWT-RIDESHARE") String token) {
		User u = authService.getUserFromToken(token);
		return rideService.cancelActiveOffer(id, u);
	}

	@GetMapping("/offer/open/{id}")
	public List<AvailableRide> getOpenOffers(@PathVariable(value = "id") int id) {
		return rideService.getOpenOffers(id);
	}

	@GetMapping("/offer/open")
	public List<AvailableRide> getOpenOffers(@RequestHeader(name = "X-JWT-RIDESHARE") String token) {
		User u = authService.getUserFromToken(token);
		return rideService.getOpenOffersForUser(u);
	}

	@GetMapping("/offer/active")
	public List<Ride> getActiveOffersForCurrentUser(@RequestHeader(name = "X-JWT-RIDESHARE") String token) {
		User u = authService.getUserFromToken(token);
		return rideService.getActiveOffersForUser(u);
	}

	@GetMapping("/offer/history")
	public List<Ride> getOfferHistoryForCurrentUser(@RequestHeader(name = "X-JWT-RIDESHARE") String token) {
		User u = authService.getUserFromToken(token);
		return rideService.getOfferHistoryForUser(u);
	}
}
