package com.revature.rideshare.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.revature.rideshare.domain.AvailableRide;
import com.revature.rideshare.domain.PointOfInterest;
import com.revature.rideshare.domain.Ride;
import com.revature.rideshare.domain.RideRequest;
import com.revature.rideshare.domain.User;

public interface RideService {

	/**
	 * Persists a RideRequest to the database.
	 *
	 * @param RideRequest  req a RideRequest object to be persisted.
	 *
	 * @return true on success and false on failure.
	 */
	boolean addRequest(RideRequest req);

	/**
	 * Returns a list of all (active and inactive) rides.
	 *
	 * @return A list of Rides.
	 */
	List<Ride> getAll();

	/**
	 * Returns a list of all active(not complete) rides.
	 *
	 * @return A list of active Rides.
	 */
	List<Ride> getAllActiveRides();

	/**
	 * Returns a list of all inactive(completed) rides.
	 *
	 * @return A list of inactive Rides.
	 */
	List<Ride> getAllInactiveRides();

	/**
	 * Takes in the RideRequest id and creates a Ride to associate said
	 * RideRequest with. If the user does not have an open AvailableRide to link
	 * with the Ride, one will be created with a default of 1 available seat.
	 *
	 * @param long  id the id of the AvailableRide to assign the Request to.
	 * 
	 * @param User  u the active user.
	 * 
	 * @return true on success, false on failure.
	 */
	boolean acceptRequest(long id, User u);

	/**
	 * Takes in a Ride ID. Closes the open RideRequest and deletes the Ride.
	 *
	 * @param long  id The id of the request to cancel.
	 * @return true on success, false on failure.
	 */
	boolean cancelRequest(long id, User u);
	
	/**
	 * Takes in a RideRequest ID. Deletes the open RideRequest.
	 *
	 * @param long  id The id of the request to cancel.
	 * @return true on success, false on failure.
	 */
	boolean cancelActiveRequest(long id, User u);
	
	/**
	 * Takes in a Ride ID. Deletes the open Ride and RideRequest, reopens the
	 * AvailableRide.
	 *
	 * @param long  id The id of the request to cancel.
	 * @return true on success, false on failure.
	 */
	boolean cancelRideReopenAvailRide(long id, User u);
	
	/**
	 * Takes in a Ride ID. Completes the open RideRequest and Ride.
	 *
	 * @param long  id The id of the request to cancel.
	 * @return true on success, false on failure.
	 */
	boolean completeRequest(long id);

	/**
	 * Takes in the main poi's id and returns all open requests starting at said
	 * id. List is ordered by closest to farthest POI and within each of those,
	 * by date.
	 *
	 * @param int  id The id of the main POI(Point of Interest).
	 * @return A list of Ride Requests.
	 */
	List<RideRequest> getOpenRequests(int poiId);

	/**
	 * Takes in a User and returns a list of all RideRequests associated with
	 * the User.
	 *
	 * @param User  u the active user.
	 * 
	 * @return A list of all requests associated with the
	 *         User.
	 */
	List<RideRequest> getRequestsForUser(User u);

	/**
	 * Returns a list of RideRequest objects that are active but not currently associated 
	 * with an AvailableRide/Ride for the input User.
	 *
	 * @param User u  user object to associate list with.
	 * 
	 * @return List of Ride objects.
	 */
	List<RideRequest> getOpenRequestsForUser(User u);
	
	/**
	 * Takes in a User and returns a list of completed Rides associated with the
	 * User.
	 *
	 * @param User  u the active user.
	 * 
	 * @return A list of completed Rides.
	 */
	List<Ride> getActiveRequestsForUser(User u);

	/**
	 * Takes in a User and returns a list of completed Rides associated with the
	 * User.
	 *
	 * @param User u the active user.
	 * 
	 * @return A list of completed Rides.
	 */
	List<Ride> getRequestHistoryForUser(User u);

	/**
	 * Takes in a User object and uses said object to retrieve a list of all
	 * AvailableRide objects.
	 *
	 * @param User  u the active user.
	 * @return A list of all AvailableRide objects
	 *         associated with the User.
	 */
	List<AvailableRide> getOffersForUser(User u);

	/**
	 * Takes in an AvailableRide object and persists it to the database.
	 *
	 * @param AvailableRide  offer the AvailableRide to persist.
	 * @return true on success, false on failure.
	 */
	boolean addOffer(AvailableRide offer);

	/**
	 * Takes in the AvailableRide id and User to create a Ride assigned to the
	 * Request and Offer.
	 *
	 * @param long  id the id of the AvailableRide to assign the Request to.
	 * 
	 * @param User  u the active user.
	 * 
	 * @return True on success, false on failure.
	 */
	boolean acceptOffer(long id, User u);

	/**
	 * Takes in an AvailableRide id and deletes ALL Rides associated with it.
	 * Sets the RequestStatus of ALL RideRequest objects associated to 'OPEN'
	 * and deletes the AvailableRide object.
	 *
	 * @param long  id The id of the Ride to cancel.
	 * 
	 * @return true on success, false on failure.
	 */
	boolean cancelOffer(long id, User u);
	
	/**
	 * Takes in an AvailableRide id and removes it from the database.
	 *
	 * @param long  id The id of the AvailableRide to cancel.
	 * 
	 * @return true on success, false on failure.
	 */
	boolean cancelActiveOffer(long id, User u);

	/**
	 * Takes in the main poi's id and returns all open requests starting at said
	 * id. List is ordered by closest to farthest POI and within each of those,
	 * by date.
	 *
	 * @param int
	 *            id The id of the main POI(Point of Interest).
	 * @return A list of Available Rides.
	 */
	List<AvailableRide> getOpenOffers(int poiId);

	/**
	 * Takes in a User object and uses said object to retrieve a list of
	 * Available Ride objects.
	 *
	 * @param User
	 *            u the active user.
	 * @return A list of Available Rides.
	 */
	List<AvailableRide> getOpenOffersForUser(User u);

	/**
	 * Takes in a User object and uses said object to retrieve a list of
	 * non-completed Ride objects.
	 *
	 * @param User
	 *            u the active user.
	 * @return A list of Rides.
	 */
	List<Ride> getActiveOffersForUser(User u);

	/**
	 * Takes in a User object and uses said object to retrieve a list of
	 * completed Ride objects.
	 *
	 * @param User
	 *            u the active user.
	 * @return A list of Rides.
	 */
	List<Ride> getOfferHistoryForUser(User u);

	/**
	 * Returns a list of RideRequest Objects in order from closest destination
	 * point to farthest away.
	 *
	 * @param List<RideRequest>
	 *            reqs a list of all open RideRequests
	 * @param PointOfInterest
	 *            mpoi the user's main POI, used as a starting(pickup) point for
	 *            all calculations.
	 * @return List of PointOfInterest objects.
	 */
	List<RideRequest> sortRequestsByPOI(List<RideRequest> reqs, PointOfInterest mpoi);

	/**
	 * Returns a list of AvailableRide Objects in order from closest destination
	 * point to farthest away.
	 *
	 * @param List<AvailableRide>
	 *            reqs a list of all open AvailableRide
	 * @param PointOfInterest
	 *            mpoi the user's main POI, used as a starting(pickup) point for
	 *            all calculations.
	 * @return List of PointOfInterest objects.
	 */
	List<AvailableRide> sortAvailableByPOI(List<AvailableRide> reqs, PointOfInterest poi);

	List<AvailableRide> getOpenOffersByDestination(int poiId);

	ArrayList<AvailableRide> getAvailableRidesByTime(Date starttime, Date endtime);

	ArrayList<AvailableRide> filterAvailableRidesByDropoffPoi(ArrayList<AvailableRide> rides,
			PointOfInterest dropoffPoi);

	ArrayList<AvailableRide> filterAvailableRidesByPickupPoi(ArrayList<AvailableRide> rides, PointOfInterest pickupPoi);

	AvailableRide getRideById(long availableRideId);

}