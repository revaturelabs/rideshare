package com.revature.rideshare.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.rideshare.dao.AvailableRideRepository;
import com.revature.rideshare.dao.CarRepository;
import com.revature.rideshare.dao.RideRepository;
import com.revature.rideshare.dao.RideRequestRepository;
import com.revature.rideshare.domain.AvailableRide;
import com.revature.rideshare.domain.Car;
import com.revature.rideshare.domain.PointOfInterest;
import com.revature.rideshare.domain.Ride;
import com.revature.rideshare.domain.RideRequest;
import com.revature.rideshare.domain.RideRequest.RequestStatus;
import com.revature.rideshare.domain.User;

@Component("rideService")
public class RideServiceImpl implements RideService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RideRepository rideRepo;

	@Autowired
	private RideRequestRepository rideReqRepo;

	@Autowired
	private AvailableRideRepository availRideRepo;

	@Autowired
	private CarRepository carRepo;

	@Autowired
	private PointOfInterestService poiService;

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#addRequest(com.revature.rideshare.domain.RideRequest)
	 */
	@Override
	public boolean addRequest(RideRequest req) {
		RideRequest temp = rideReqRepo.saveAndFlush(req);
		if (temp == null) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#getAll()
	 */
	@Override
	public List<Ride> getAll() {
		return rideRepo.findAll();
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#getAllActiveRides()
	 */
	@Override
	public List<Ride> getAllActiveRides() {
		return rideRepo.findByWasSuccessfulNull();
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#getAllInactiveRides()
	 */
	@Override
	public List<Ride> getAllInactiveRides() {
		return rideRepo.findByWasSuccessfulNotNull();
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#acceptRequest(long, com.revature.rideshare.domain.User)
	 */
	@Override
	public boolean acceptRequest(long id, User u) {
		// get request from id and satisfy it
		RideRequest req = rideReqRepo.getOne(id);
		req.setStatus(RideRequest.RequestStatus.SATISFIED);
		rideReqRepo.saveAndFlush(req);

		// TODO: Optimize the creation of AvailableRides. There is currently no
		// checks
		// for if this driver already has an offer opened for these POIs.
		AvailableRide offer = new AvailableRide();
		Car car = carRepo.findByUser(u);
		offer.setCar(car);
		offer.setSeatsAvailable((short) 1);
		offer.setPickupPOI(req.getPickupLocation());
		offer.setDropoffPOI(req.getDropOffLocation());
		offer.setOpen(false);
		offer.setTime(req.getTime());
		availRideRepo.saveAndFlush(offer);

		// create ride obj from req and avail
		Ride ride = new Ride();
		ride.setAvailRide(offer);
		ride.setRequest(req);

		try {
			rideRepo.saveAndFlush(ride);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#cancelRequest(long, com.revature.rideshare.domain.User)
	 */
	@Override
	public boolean cancelRequest(long id, User u) {
		try {
			Ride ride = rideRepo.findOne(id);
			RideRequest req = ride.getRequest();

			AvailableRide availRide = ride.getAvailRide();
			if (!availRide.isOpen()) {
				// reopen if closed (because a seat is now available)
				availRide.setOpen(true);
			}

			rideRepo.delete(ride);
			rideReqRepo.delete(req);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#cancelActiveRequest(long, com.revature.rideshare.domain.User)
	 */
	@Override
	public boolean cancelActiveRequest(long id, User u) {
		try {
			RideRequest req = rideReqRepo.findOne(id);
			rideReqRepo.delete(req);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#cancelActiveRequest(long, com.revature.rideshare.domain.User)
	 */
	@Override
	public boolean cancelRideReopenAvailRide(long id, User u) {
		try {
			Ride temp = rideRepo.findOne(id);
			RideRequest req = temp.getRequest();
			AvailableRide avail = temp.getAvailRide();
			
			rideRepo.delete(temp);
			rideReqRepo.delete(req);
			
			avail.setOpen(true);
			availRideRepo.saveAndFlush(avail);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	
	
	
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#completeRequest(long, com.revature.rideshare.domain.User)
	 */
	@Override
	public boolean completeRequest(long id) {
		Ride ride = rideRepo.findOne(id);
		RideRequest req = ride.getRequest();
		
		req.setStatus(RequestStatus.SATISFIED);
		ride.setWasSuccessful(true);
		
		RideRequest temp = rideReqRepo.saveAndFlush(req);
		Ride tempRide = rideRepo.saveAndFlush(ride);
		if(temp == null || tempRide == null) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#getOpenRequests(int)
	 */
	@Override
	public List<RideRequest> getOpenRequests(int poiId) {
		List<RideRequest> openReqs = rideReqRepo.findByStatus(RequestStatus.OPEN);

		Collections.sort(openReqs); // sorting by date.

		// Sorting by closest to farthest POI
		PointOfInterest temp = poiService.getPoi(poiId);
		openReqs = sortRequestsByPOI(openReqs, temp);

		return openReqs;
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#getRequestsForUser(com.revature.rideshare.domain.User)
	 */
	@Override
	public List<RideRequest> getRequestsForUser(User u) {
		return rideReqRepo.findByUser(u);
	}

	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#getActiveRequestsForUser(com.revature.rideshare.domain.User)
	 */
	@Override
	public List<RideRequest> getOpenRequestsForUser(User u) {
		List<RideRequest> allReqs = rideReqRepo.findByUser(u);
		List<RideRequest> temp = new ArrayList<RideRequest>();

		for (RideRequest r : allReqs) {
			if (r.getStatus() == RequestStatus.OPEN) {
				temp.add(r);
			} else {
				logger.debug("NOT ADDED\n\n");
			}
		}

		return temp;
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#getActiveRequestsForUser(com.revature.rideshare.domain.User)
	 */
	@Override
	public List<Ride> getActiveRequestsForUser(User u) {
		List<Ride> allRides = rideRepo.findByRequestUser(u);
		List<Ride> activeRides = new ArrayList<Ride>();

		for (Ride r : allRides) {
			if (r.getWasSuccessful() == null) {
				activeRides.add(r);
			}
		}

		return activeRides;
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#getRequestHistoryForUser(com.revature.rideshare.domain.User)
	 */
	@Override
	public List<Ride> getRequestHistoryForUser(User u) {
		List<Ride> allRides = rideRepo.findByRequestUser(u);
		List<Ride> completedRides = new ArrayList<Ride>();

		for (Ride r : allRides) {
			if (r.getWasSuccessful() != null) {
				completedRides.add(r);
			}
		}
		return completedRides;
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#getOffersForUser(com.revature.rideshare.domain.User)
	 */
	@Override
	public List<AvailableRide> getOffersForUser(User u) {
		return availRideRepo.findByCarUser(u);
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#addOffer(com.revature.rideshare.domain.AvailableRide)
	 */
	@Override
	public boolean addOffer(AvailableRide offer) {
		AvailableRide temp = availRideRepo.saveAndFlush(offer);
		if (temp == null) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#acceptOffer(long, com.revature.rideshare.domain.User)
	 */
	@Override
	public boolean acceptOffer(long id, User u) {
		// get request from id and satisfy it
		AvailableRide offer = availRideRepo.getOne(id);

		// if car is full set open to false
		Long inRide = rideRepo.countByAvailRide(offer) + 1;
		if (offer.getSeatsAvailable() <= inRide) {
			offer.setOpen(false);
		}

		availRideRepo.saveAndFlush(offer);

		// duplicate offer as request
		RideRequest req = new RideRequest();
		req.setUser(u);
		req.setPickupLocation(offer.getPickupPOI());
		req.setDropOffLocation(offer.getDropoffPOI());
		req.setTime(offer.getTime());
		req.setStatus(RequestStatus.SATISFIED);
		rideReqRepo.saveAndFlush(req);

		// create ride obj from req and avail
		Ride ride = new Ride();
		ride.setAvailRide(offer);
		ride.setRequest(req);

		try {
			rideRepo.saveAndFlush(ride);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#cancelOffer(long, com.revature.rideshare.domain.User)
	 */
	@Override
	public boolean cancelOffer(long id, User u) {
		try {
			List<Ride> rides = rideRepo.findAllByAvailRideAvailRideId(id);
			AvailableRide availRide = rides.get(0).getAvailRide();

			for (Ride r : rides) {
				RideRequest temp = r.getRequest();
				temp.setStatus(RequestStatus.OPEN); // reopen request
				rideReqRepo.save(temp); // update Request

				rideRepo.delete(r);
			}

			availRideRepo.delete(availRide);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#cancelOffer(long, com.revature.rideshare.domain.User)
	 */
	@Override
	public boolean cancelActiveOffer(long id, User u) {
		try {
			AvailableRide availRide = availRideRepo.findByAvailRideId(id);

			availRideRepo.delete(availRide);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#getOpenOffers(int)
	 */
	@Override
	public List<AvailableRide> getOpenOffers(int poiId) {
		List<AvailableRide> openOffers = availRideRepo.findAllByIsOpenTrue();
		
		Collections.sort(openOffers); // Sorting by date.

		// Sorting by closest to farthest POI
		PointOfInterest temp = poiService.getPoi(poiId);
		openOffers = sortAvailableByPOI(openOffers, temp);
		
		return openOffers;
	}

	@Override
	public List<AvailableRide> getOpenOffersByDestination(int poiId){
		return availRideRepo.findByDropoffPOI(poiService.getPoi(poiId));
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#getOpenOffersForUser(com.revature.rideshare.domain.User)
	 */
	@Override
	public List<AvailableRide> getOpenOffersForUser(User u) {

		List<AvailableRide> allOpenOffers = availRideRepo.findAllByIsOpenTrue();
		List<AvailableRide> openOffers = new ArrayList<AvailableRide>();

		// filter rides to get ride for user
		for (AvailableRide a : allOpenOffers) {
			if (a.getCar().getUser().getUserId() == u.getUserId()) {
				openOffers.add(a);
			}
		}
		
		Collections.sort(openOffers);  // Sorting by date
		
		return openOffers;
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#getActiveOffersForUser(com.revature.rideshare.domain.User)
	 */
	@Override
	public List<Ride> getActiveOffersForUser(User u) {
		List<Ride> allRides = rideRepo.findByAvailRideCarUser(u);
		List<Ride> activeRides = new ArrayList<Ride>();

		for (Ride r : allRides) {
			if (r.getWasSuccessful() == null) {
				activeRides.add(r);
			}
		}

		return activeRides;
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#getOfferHistoryForUser(com.revature.rideshare.domain.User)
	 */
	@Override
	public List<Ride> getOfferHistoryForUser(User u) {
		List<Ride> allRides = rideRepo.findByAvailRideCarUser(u);
		List<Ride> completedRides = new ArrayList<Ride>();
		for (Ride r : allRides) {
			if (r.getWasSuccessful() != null) {
				completedRides.add(r);
			}
		}

		return completedRides;
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#sortRequestsByPOI(java.util.List, com.revature.rideshare.domain.PointOfInterest)
	 */
	@Override
	public List<RideRequest> sortRequestsByPOI(List<RideRequest> reqs, PointOfInterest mpoi) {
		List<RideRequest> temp = new ArrayList<RideRequest>();
		List<PointOfInterest> pois = poiService.getAll();

		int[] poisByDistance = calculateDistance(pois, mpoi);
		for (int i : poisByDistance) {
			for (int k = 0; k < reqs.size(); k++) {
				if (reqs.get(k).getDropOffLocation().getPoiId() == i + 1
						&& mpoi.getPoiId() == reqs.get(k).getPickupLocation().getPoiId()) {
					temp.add(reqs.get(k));
					reqs.remove(k--);
				}
			}
		}

		return temp;
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.RideService#sortAvailableByPOI(java.util.List, com.revature.rideshare.domain.PointOfInterest)
	 */
	@Override
	public List<AvailableRide> sortAvailableByPOI(List<AvailableRide> reqs, PointOfInterest mpoi) {
		List<AvailableRide> temp = new ArrayList<AvailableRide>();
		List<PointOfInterest> pois = poiService.getAll();

		int[] poisByDistance = calculateDistance(pois, mpoi);
		for (int i : poisByDistance) {
			for (int k = 0; k < reqs.size(); k++) {
				if (reqs.get(k).getDropoffPOI().getPoiId() == i + 1
						&& mpoi.getPoiId() == reqs.get(k).getPickupPOI().getPoiId()) {
					temp.add(reqs.get(k));
					reqs.remove(k--);
				}
			}
		}
		return temp;
	}

	/**
	 * Returns a list of PointOfInterest Objects in order from closest to
	 * farthest away, excluding the main PointOfInterest.
	 *
	 * @param List<PointOfInterest>
	 *            pois a list of all available POIs
	 * @param PointOfInterest
	 *            mpoi the user's main POI
	 * @return list of PointOfInterest objects.
	 */
	private int[] calculateDistance(List<PointOfInterest> pois, PointOfInterest mpoi) {
		double mLat = Math.abs(mpoi.getLatitude());
		double mLong = Math.abs(mpoi.getLongitude());
		Map<Double, Integer> map = new TreeMap();

		// Calculating distance: sqrt( (|x1|-|x2|) + (|y1|-|y2|)^2 )
		// distance is then stored in a Treemap which naturally orders.
		for (int i = 0; i < pois.size(); i++) {
			// skipping the main POI.
			if (mpoi.getPoiId() == pois.get(i).getPoiId()) {
				continue;
			}
			double poiLat = Math.abs(pois.get(i).getLatitude());
			double poiLong = Math.abs(pois.get(i).getLongitude());

			double x = mLong - poiLong;
			double y = mLat - poiLat;

			double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
			map.put(distance, i);
		}

		Set<?> set = map.entrySet();
		Iterator<?> iter = set.iterator();

		// -1 because it does not include the current poi
		int[] poiByDistance = new int[pois.size() - 1];

		int counter = 0;

		// creates an array of POI ids in order from closest to farthest away.
		while (iter.hasNext()) {
			Map.Entry me = (Map.Entry) iter.next();
			poiByDistance[counter++] = (Integer) me.getValue();
		}

		return poiByDistance;
	}
	@Override
	public ArrayList<AvailableRide> getAvailableRidesByTime(Date starttime, Date endtime){
		return (ArrayList<AvailableRide>)availRideRepo.findByTimeBetween(starttime, endtime);
	}
	@Override
	public ArrayList<AvailableRide> filterAvailableRidesByDropoffPoi(ArrayList<AvailableRide> rides,PointOfInterest dropoffPoi){
		ArrayList<AvailableRide> returnList = new ArrayList<AvailableRide>();
		for(AvailableRide ride:rides){
			if(ride.getDropoffPOI().getPoiName().equals(dropoffPoi.getPoiName())){
				returnList.add(ride);
			}
		}
		return returnList;
	}
	@Override
	public ArrayList<AvailableRide> filterAvailableRidesByPickupPoi(ArrayList<AvailableRide> rides,PointOfInterest pickupPoi){
		ArrayList<AvailableRide> returnList = new ArrayList<AvailableRide>();
		for(AvailableRide ride:rides){
			if(ride.getPickupPOI().getPoiName().equals(pickupPoi.getPoiName())){
				returnList.add(ride);
			}
		}
		return returnList;
	}
	@Override
	public AvailableRide getRideById(long availableRideId){
		return availRideRepo.findByAvailRideId(availableRideId);
	}
	
}
