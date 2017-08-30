package com.revature.rideshare.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.rideshare.domain.RideRequest;
import com.revature.rideshare.domain.RideRequest.RequestStatus;
import com.revature.rideshare.domain.User;

public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {
	List<RideRequest> findByUser(User u);

	List<RideRequest> findByStatus(RequestStatus s);
}
