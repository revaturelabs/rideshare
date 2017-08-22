package com.revature.rideshare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.revature.rideshare.dao.PointOfInterestRepository;
import com.revature.rideshare.dao.PointOfInterestTypeRepository;
import com.revature.rideshare.domain.PointOfInterest;
import com.revature.rideshare.domain.PointOfInterestType;

@Component("poiService")
@Transactional // need??
public class PointOfInterestService {

	@Autowired
	private PointOfInterestRepository poiRepo;
	@Autowired
	private PointOfInterestTypeRepository poiTypeRepo;

	public PointOfInterestService() {
	}

	/**
	 * Set the POI Repo.
	 *
	 * @param PointOfInterestRepository
	 */
	public void setPoiRepo(PointOfInterestRepository poiRepo) {
		this.poiRepo = poiRepo;
	}

	/**
	 * Set the POI Type Repo.
	 *
	 * @param PointOfInterestTypeRepository
	 */
	public void setPoiTypeRepo(PointOfInterestTypeRepository poiTypeRepo) {
		this.poiTypeRepo = poiTypeRepo;
	}

	/**
	 * Retrieve a list of all POIs.
	 *
	 * @return List<PointOfInterest>
	 */
	public List<PointOfInterest> getAll() {
		return poiRepo.findAll();
	}

	/**
	 * Retrieve a list of all POI types.
	 *
	 * @return List<PointOfInterestType> a list of all POI types.
	 */
	public List<PointOfInterestType> getAllTypes() {
		return poiTypeRepo.findAll();
	}

	/**
	 * Updates a PointOfInterest.
	 *
	 * @param PointOfInterest
	 *            POI object.
	 *
	 * @return boolean true on success, false on failure.
	 */
	public void addPoi(PointOfInterest poi) {
		poiRepo.saveAndFlush(poi);
	}

	/**
	 * Removes a POI from the database.
	 *
	 * @param PointOfInterest
	 *            POI object.
	 *
	 */
	public void removePoi(PointOfInterest poi) {
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
	public boolean updatePoi(PointOfInterest poi) {
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
	public PointOfInterest getPoi(int id) {
		return poiRepo.findBypoiId(id);
	}
	
	public PointOfInterest getOnePoiByName(String name) {
		List<PointOfInterest> pois = poiRepo.findByPoiName(name);
		if (pois.isEmpty()) {
			return null;
		} else {
			return pois.get(0);
		}
	}
	
	public PointOfInterest getPoiByStreetAddress(String addressLine1) {
		List<PointOfInterest> pois = poiRepo.findByAddressLine1(addressLine1);
		if (pois.isEmpty()) {
			return null;
		} else {
			return pois.get(0);
		}
	}
	
	public PointOfInterest getPoi(String name){
		return poiRepo.findBypoiName(name);
	}
}
