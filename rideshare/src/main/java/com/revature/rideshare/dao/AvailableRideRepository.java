package com.revature.rideshare.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.rideshare.domain.AvailableRide;
import com.revature.rideshare.domain.PointOfInterest;
import com.revature.rideshare.domain.User;

public interface AvailableRideRepository extends JpaRepository<AvailableRide, Long> {
	List<AvailableRide> findByCarUser(User u);

	List<AvailableRide> findByIsOpenFalse();

	List<AvailableRide> findAllByIsOpenTrue();

	List<AvailableRide> findByTimeBetween(Date starttime,Date endtime);
	
	List<AvailableRide> findByPickupPOI(PointOfInterest p);
	
	List<AvailableRide> findByDropoffPOI(PointOfInterest p);
	
	AvailableRide findByAvailRideId(long id);
	
}
