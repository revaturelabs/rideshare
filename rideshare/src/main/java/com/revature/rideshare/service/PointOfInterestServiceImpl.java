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
public class PointOfInterestServiceImpl implements PointOfInterestService {

	@Autowired
	private PointOfInterestRepository poiRepo;
	@Autowired
	private PointOfInterestTypeRepository poiTypeRepo;

	public PointOfInterestServiceImpl() {
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.PointOfInterestService#setPoiRepo(com.revature.rideshare.dao.PointOfInterestRepository)
	 */
	@Override
	public void setPOIRepo(PointOfInterestRepository poiRepo) {
		this.poiRepo = poiRepo;
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.PointOfInterestService#setPoiTypeRepo(com.revature.rideshare.dao.PointOfInterestTypeRepository)
	 */
	@Override
	public void setPOITypeRepo(PointOfInterestTypeRepository poiTypeRepo) {
		this.poiTypeRepo = poiTypeRepo;
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.PointOfInterestService#getAll()
	 */
	@Override
	public List<PointOfInterest> getAll() {
		return poiRepo.findAll();
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.PointOfInterestService#getAllTypes()
	 */
	@Override
	public List<PointOfInterestType> getAllTypes() {
		return poiTypeRepo.findAll();
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.PointOfInterestService#addPoi(com.revature.rideshare.domain.PointOfInterest)
	 */
	@Override
	public void addPOI(PointOfInterest poi) {
		poiRepo.saveAndFlush(poi);
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.PointOfInterestService#removePoi(com.revature.rideshare.domain.PointOfInterest)
	 */
	@Override
	public void removePOI(PointOfInterest poi) {
		poiRepo.delete(poi);
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.PointOfInterestService#updatePoi(com.revature.rideshare.domain.PointOfInterest)
	 */
	@Override
	public boolean updatePOI(PointOfInterest poi) {
		PointOfInterest temp = poiRepo.saveAndFlush(poi);
		if (temp == null) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.PointOfInterestService#getPOI(int)
	 */
	@Override
	public PointOfInterest getPOI(int id) {
		return poiRepo.findBypoiId(id);
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.PointOfInterestService#getOnePoiByName(java.lang.String)
	 */
	@Override
	public PointOfInterest getOnePOIByName(String name) {
		List<PointOfInterest> pois = poiRepo.findBypoiName(name);
		if (pois.isEmpty()) {
			return null;
		} else {
			return pois.get(0);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.PointOfInterestService#getPoiByStreetAddress(java.lang.String)
	 */
	@Override
	public PointOfInterest getPOIByStreetAddress(String addressLine1) {
		List<PointOfInterest> pois = poiRepo.findByAddressLine1(addressLine1);
		if (pois.isEmpty()) {
			return null;
		} else {
			return pois.get(0);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.PointOfInterestService#getPoi(java.lang.String)
	 */
	@Override
	public PointOfInterest getPOI(String name){
		return poiRepo.findByPoiName(name);
	}
}
