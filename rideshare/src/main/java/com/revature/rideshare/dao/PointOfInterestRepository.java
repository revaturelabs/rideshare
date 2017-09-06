package com.revature.rideshare.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.rideshare.domain.PointOfInterest;

public interface PointOfInterestRepository extends JpaRepository<PointOfInterest, Long> {
	
	/**
	 * Finds a List of PointOfInterest(s) by the name of the POI.
	 * 
	 * @param name the String name of the POI we are searching for
	 * @return the PointOfInterest List we were searching for
	 * @see com.revature.rideshare.domain.PointOfInterest The PointOfInterest class
	 */
	List<PointOfInterest> findByPoiName(String name);
	
	/**
	 * Finds a List of PointOfInterest(s) by their addressLine1.
	 * 
	 * @param addressLine1 the addressLine1 String of the POI we are searching for
	 * @return the PointOfInterest List we were searching for
	 * @see com.revature.rideshare.domain.PointOfInterest The PointOfInterest class
	 */
	List<PointOfInterest> findByAddressLine1(String addressLine1);

	/**
	 * Finds a PointOfInterest by the ID of the POI.
	 * 
	 * @param id the id of the POI we are searching for
	 * @return the PointOfInterest we were searching for
	 * @see com.revature.rideshare.domain.PointOfInterest The PointOfInterest class
	 */
	PointOfInterest findBypoiId(int id);
	
	/**
	 * Finds a PointOfInterest by the Name of the POI.
	 * 
	 * @param name the name String of the User we are searching for
	 * @return the PointOfInterest we were searching for
	 * @see com.revature.rideshare.domain.PointOfInterest The PointOfInterest class
	 */
	PointOfInterest findBypoiName(String name);
	
}
