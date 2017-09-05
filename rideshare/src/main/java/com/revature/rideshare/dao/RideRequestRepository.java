package com.revature.rideshare.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.rideshare.domain.RideRequest;
import com.revature.rideshare.domain.RideRequest.RequestStatus;
import com.revature.rideshare.domain.User;

public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {
	
	/**
	 * Finds a list of RideRequests by User.
	 * 
	 * @param u the User with RideRequests we are searching for
	 * @return the list of RideRequests we were searching for
	 * @see com.revature.rideshare.domain.RideRequest The RideRequest class
	 */
	List<RideRequest> findByUser(User u);

	/**
	 * Finds a list of RideRequests by their RequestStatus.
	 * 
	 * @param s the RequestStatus of the RideRequests we are searching for
	 * @return the list of RideRequests we were searching for
	 * @see com.revature.rideshare.domain.RideRequest The RideRequest class
	 */
	List<RideRequest> findByStatus(RequestStatus s);
}
