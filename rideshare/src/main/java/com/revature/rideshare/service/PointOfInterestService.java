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

	public void setPOIRepo(PointOfInterestRepository poiRepo) {
		this.poiRepo = poiRepo;
	}


	/**
	 * Set the POI Type Repo.
	 *
	 * @param PointOfInterestTypeRepository
	 */

	public void setPOITypeRepo(PointOfInterestTypeRepository poiTypeRepo) {
		this.poiTypeRepo = poiTypeRepo;
	}


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

	public void addPOI(PointOfInterest poi) {
		poiRepo.saveAndFlush(poi);
	}


	/**
	 * Removes a POI from the database.
	 *
	 * @param PointOfInterest
	 *            POI object.
	 *
	 */

	public void removePOI(PointOfInterest poi) {
		poiRepo.delete(poi);
	}


	/**
	 * Updates a PointOfInterest.
	 *
	 * @param PointOfInterest
	 *            POI object.
	 *
	 * @return boolean true on success, false on failure.
	 */

	public boolean updatePOI(PointOfInterest poi) {
		PointOfInterest temp = poiRepo.saveAndFlush(poi);
		if (temp == null) {
			return false;
		}
		return true;
	}


	/**
	 * Retrieves a PointOfInterest object based on the input id.
	 *
	 * @param int
	 *            id Id of the POI
	 *
	 * @return PointOfInterest POI object.
	 */

	public PointOfInterest getPOI(int id) {
		return poiRepo.findByPOIId(id);
	}
	
	/*
	 * Had to comment it out because same naming convention
	 */
	
	public PointOfInterest getOnePOIByName(String name) {
		List<PointOfInterest> pois = poiRepo.findByPOIName(name);
		if (pois.isEmpty()) {
			return null;
		} else {
			return pois.get(0);
		}
	}

	public PointOfInterest getPOIByStreetAddress(String addressLine1) {
		List<PointOfInterest> pois = poiRepo.findByAddressLine1(addressLine1);
		if (pois.isEmpty()) {
			return null;
		} else {
			return pois.get(0);
		}
	}
	
	public PointOfInterest getPOI(String name){
		List<PointOfInterest> pois = poiRepo.findByPOIName(name);
		if (pois.isEmpty()) {
			return null;
		} else {
			return pois.get(0);
		}
	}
}

