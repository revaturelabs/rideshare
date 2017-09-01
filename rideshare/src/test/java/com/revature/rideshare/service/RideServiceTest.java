package com.revature.rideshare.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideshare.dao.AvailableRideRepository;
import com.revature.rideshare.dao.CarRepository;
import com.revature.rideshare.dao.RideRepository;
import com.revature.rideshare.dao.RideRequestRepository;
import com.revature.rideshare.domain.Ride;
import com.revature.rideshare.domain.RideRequest;

@RunWith(SpringRunner.class)
public class RideServiceTest {
	
	@Mock
	RideRepository rideRepository;

	@Mock
	RideRequestRepository rideReqRepo;

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
		RideRequest mockRequest = new RideRequest();
		
		when(rideReqRepo.saveAndFlush(Matchers.same(mockRequest))).thenReturn(mockRequest);
		
		rideService.addRequest(mockRequest);
		
		verify(rideReqRepo, atLeastOnce()).saveAndFlush(Matchers.same(mockRequest));
	}
	
	@Test
	public void testGetAll() {
		int mockRideListSize = 5;
		
		List<Ride> mockRideList = new ArrayList<>();
		for (int i = 0; i < mockRideListSize; i++) {
			mockRideList.add(new Ride());
		}
		
		when(rideRepository.findAll()).thenReturn(mockRideList);
		
		List<Ride> rideList = rideService.getAll();
		
		verify(rideRepository, atLeastOnce()).findAll();
		
		assertNotNull(rideList);
		assertTrue(rideList.size() == mockRideListSize);
	}
	
	@Test
	public void testGetAllActiveRides() {
		int mockRideListSize = 5;
		
		List<Ride> mockRideList = new ArrayList<>();
		for (int i = 0; i < mockRideListSize; i++) {
			mockRideList.add(new Ride());
		}
		
		when(rideRepository.findByWasSuccessfulNull()).thenReturn(mockRideList);
		
		List<Ride> rideList = rideService.getAllActiveRides();
		
		verify(rideRepository, atLeastOnce()).findByWasSuccessfulNull();
		
		assertNotNull(rideList);
		assertTrue(rideList.size() == mockRideListSize);
	}
	
	@Test
	public void testGetAllInactiveRides() {
		int mockRideListSize = 5;
		
		List<Ride> mockRideList = new ArrayList<>();
		for (int i = 0; i < mockRideListSize; i++) {
			mockRideList.add(new Ride());
		}
		
		when(rideRepository.findByWasSuccessfulNotNull()).thenReturn(mockRideList);
		
		List<Ride> rideList = rideService.getAllInactiveRides();
		
		verify(rideRepository, atLeastOnce()).findByWasSuccessfulNotNull();
		
		assertNotNull(rideList);
		assertTrue(rideList.size() == mockRideListSize);
	}
	
	@Test
	public void testAcceptRequest() {
		// TODO - Waiting on method to be complete
	}
	
	@Test
	public void testIgnoreRequest() {
		// TODO - Waiting on method to be complete
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
