package com.revature.rideshare.service;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.revature.rideshare.dao.AvailableRideRepository;
import com.revature.rideshare.dao.CarRepository;
import com.revature.rideshare.dao.RideRepository;
import com.revature.rideshare.dao.RideRequestRepository;

public class RideServiceTest {
	
	@Mock
	RideRepository rideRepository;

	@Mock
	RideRequestRepository rideRequestRepository;

	@Mock
	AvailableRideRepository availableRideRepository;

	@Mock
	CarRepository carRepository;

	@Mock
	PointOfInterestService poiService;
	
	@InjectMocks
	RideService rideService = new RideServiceImpl();
	
	@Test
	public void testAddRequest() {
		
	}
	
	@Test
	public void testGetAll() {
		
	}
	
	@Test
	public void testGetAllActiveRides() {
		
	}
	
	@Test
	public void testGetAllInactiveRides() {
		
	}
	
	@Test
	public void testAcceptRequest() {
		
	}
	
	@Test
	public void testIgnoreRequest() {
		
	}
	
	@Test
	public void testCancelRequest() {
		
	}
	
	@Test
	public void testCancelActiveRequest() {
		
	}
	
	@Test
	public void testCancelRideReopenAvailRide() {
		
	}
	
	@Test
	public void testCompleteRequest() {
		
	}
	
	@Test
	public void testGetOpenRequests() {
		
	}
	
	@Test
	public void testGetRequestsForUser() {
		
	}
	
	@Test
	public void testGetOpenRequestsForUser() {
		
	}
	
	@Test
	public void testGetActiveRequestsForUser() {
		
	}
	
	@Test
	public void testGetRequestHistoryForUser() {
		
	}
	
	@Test
	public void testGetOffersForUser() {
		
	}
	
	@Test
	public void testAddOffer() {
		
	}
	
	@Test
	public void testAcceptOffer() {
		
	}
	
	@Test
	public void testCancelOffer() {
		
	}
	
	@Test
	public void testCancelActiveOffer() {
		
	}
	
	@Test
	public void testGetOpenOffers() {
		
	}
	
	@Test
	public void testGetOpenOffersByDestination() {
		
	}
	
	@Test
	public void testGetOpenOffersForUser() {
		
	}
	
	@Test
	public void testGetActiveOffersForUser() {
		
	}
	
	@Test
	public void testGetOfferHistoryForUser() {
		
	}
	
	@Test
	public void testSortRequestsByPOI() {
		
	}
	
	@Test
	public void testSortAvailableByPOI() {
		
	}
	
	@Test
	public void testGetAvailableRidesByTime() {
		
	}
	
	@Test
	public void testFilterAvailableRidesByDropoffPoi() {
		
	}
	
	@Test
	public void testFilterAvailableRidesByPickupPoi() {
		
	}
	
	@Test
	public void testGetRideById() {
		
	}
	
}
