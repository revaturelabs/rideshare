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
	void setPoiRepo(PointOfInterestRepository poiRepo);

	/**
	 * Set the POI Type Repo.
	 *
	 * @param PointOfInterestTypeRepository
	 */
	void setPoiTypeRepo(PointOfInterestTypeRepository poiTypeRepo);

	/**
	 * Retrieve a list of all {@link PointOfInterest POI}s.
	 *
	 * @return List<PointOfInterest>
	 */
	List<PointOfInterest> getAll();

	/**
	 * Retrieve a list of all {@link PointOfInterestType POI types}.
	 *
	 * @return List<PointOfInterestType> a list of all POI types.
	 */
	List<PointOfInterestType> getAllTypes();

	/**
	 * Updates a {@link PointOfInterest Point Of Interest}.
	 *
	 * @param PointOfInterest
	 *            POI object.
	 *
	 * @return boolean true on success, false on failure.
	 */
	void addPoi(PointOfInterest poi);

	/**
	 * Removes a {@link PointOfInterest Point Of Interest} from the database.
	 *
	 * @param PointOfInterest
	 *            POI object.
	 *
	 */
	void removePoi(PointOfInterest poi);

	/**
	 * Updates a {@link PointOfInterest Point Of Interest}.
	 *
	 * @param PointOfInterest
	 *            {@link PointOfInterest Point Of Interest} object.
	 *
	 * @return boolean true on success, false on failure.
	 */
	boolean updatePoi(PointOfInterest poi);

	/**
	 * Retrieves a {@link PointOfInterest Point Of Interest} object based on the input id.
	 *
	 * @param int
	 *            id Id of the POI
	 *
	 * @return {@link PointOfInterest Point Of Interest} POI object.
	 */
	PointOfInterest getPoi(int id);

	PointOfInterest getOnePoiByName(String name);

	PointOfInterest getPoiByStreetAddress(String addressLine1);

	PointOfInterest getPoi(String name);

}