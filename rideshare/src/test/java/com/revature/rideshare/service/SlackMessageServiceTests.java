package com.revature.rideshare.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideshare.json.Action;
import com.revature.rideshare.json.Attachment;
import com.revature.rideshare.json.Option;

@RunWith(SpringRunner.class)
public class SlackMessageServiceTests {

	@Mock
	PointOfInterestService poiService;

	@Mock
	RideService rideService;

	@InjectMocks
	SlackMessageService slackMessageService;

	public List<Option> getToFromOptions() {
		List<Option> toFromOptions = new ArrayList<Option>();

		Option toOption = new Option("To", "To");

		Option fromOption = new Option("From", "From");

		toFromOptions.add(toOption);

		toFromOptions.add(fromOption);

		return toFromOptions;

	}

	@Test
	public void testCreatePoiSelectDestinationAttachment() {

		String callbackID = "findRidesMessage";

		Attachment testAttachment = slackMessageService.createPoiSelectDestinationAttachment(callbackID);

		List<Action> testActions = testAttachment.getActions();

		assert (testActions.size() == 2);

		List<Option> toFromOptions = getToFromOptions();

		Action ToAction = new Action("To/From","To/From","select",toFromOptions);

		assert (testActions.contains(ToAction));

	}

}
