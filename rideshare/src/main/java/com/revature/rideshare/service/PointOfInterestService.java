package com.revature.rideshare.service;

import java.util.List;

import com.revature.rideshare.dao.PointOfInterestRepository;
import com.revature.rideshare.dao.PointOfInterestTypeRepository;
import com.revature.rideshare.domain.PointOfInterest;
import com.revature.rideshare.domain.PointOfInterestType;

public interface PointOfInterestService {

	/**
	 * Set the POI Repo.
	 *
	 * @param PointOfInterestRepository
	 */
	void setPOIRepo(PointOfInterestRepository poiRepo);

	/**
	 * Set the POI Type Repo.
	 *
	 * @param PointOfInterestTypeRepository
	 */
	void setPOITypeRepo(PointOfInterestTypeRepository poiTypeRepo);

	/**
	 * Retrieve a list of all POIs.
	 *
	 * @return List<PointOfInterest>
	 */
	List<PointOfInterest> getAll();

	/**
	 * Retrieve a list of all POI types.
	 *
	 * @return List<PointOfInterestType> a list of all POI types.
	 */
	List<PointOfInterestType> getAllTypes();

	/**
	 * Updates a PointOfInterest.
	 *
	 * @param PointOfInterest
	 *            POI object.
	 *
	 * @return boolean true on success, false on failure.
	 */
	void addPOI(PointOfInterest poi);

	/**
	 * Removes a POI from the database.
	 *
	 * @param PointOfInterest
	 *            POI object.
	 *
	 */
	void removePOI(PointOfInterest poi);

	/**
	 * Updates a PointOfInterest.
	 *
	 * @param PointOfInterest
	 *            POI object.
	 *
	 * @return boolean true on success, false on failure.
	 */
	boolean updatePOI(PointOfInterest poi);

	/**
	 * Retrieves a PointOfInterest object based on the input id.
	 *
	 * @param int
	 *            id Id of the POI
	 *
	 * @return PointOfInterest POI object.
	 */
	PointOfInterest getPOI(int id);

	PointOfInterest getOnePOIByName(String name);

	PointOfInterest getPOIByStreetAddress(String addressLine1);

	PointOfInterest getPOI(String name);

}