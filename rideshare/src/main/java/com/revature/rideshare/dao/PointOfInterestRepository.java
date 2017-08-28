package com.revature.rideshare.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.rideshare.domain.PointOfInterest;

public interface PointOfInterestRepository extends JpaRepository<PointOfInterest, Long> {
	
	List<PointOfInterest> findByPoiName(String name);
	
	List<PointOfInterest> findByAddressLine1(String addressLine1);

	PointOfInterest findBypoiId(int id);
	
	PointOfInterest findBypoiName(String name);
	
}
