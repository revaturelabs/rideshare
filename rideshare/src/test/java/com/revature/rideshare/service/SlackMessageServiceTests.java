package com.revature.rideshare.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideshare.json.Action;
import com.revature.rideshare.json.Attachment;

@RunWith(SpringRunner.class)
public class SlackMessageServiceTests {

	@Mock
	PointOfInterestService poiService;

	@Mock
	RideService rideService;

	@InjectMocks
	SlackMessageService slackMessageService;

	@Test
	public void testCreatePoiSelectDestinationAttachment() {

		Attachment testAttachment = slackMessageService.createPoiSelectDestinationAttachment("findRidesMessage");

		List<Action> testActions = testAttachment.getActions();

		assert (testActions.size() == 2);
		
//		assert (testActions.contains())

	}

}
