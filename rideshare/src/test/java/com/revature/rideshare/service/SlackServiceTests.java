package com.revature.rideshare.service;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SlackServiceTests {

	@Mock
	private SlackMessageService slackMessageService;

	@Mock
	private RideService rideService;

	@Mock
	private CarService carService;

	@Mock
	private UserService userService;

	@Mock
	private PointOfInterestService poiService;

	@InjectMocks
	private SlackService slackService = new SlackServiceImpl();
	
}
