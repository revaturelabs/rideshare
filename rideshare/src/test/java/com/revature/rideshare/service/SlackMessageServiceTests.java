package com.revature.rideshare.service;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideshare.domain.PointOfInterest;
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
	SlackMessageService slackMessageService = new SlackMessageServiceImpl();

	public List<Option> getToFromOptions() {
		List<Option> toFromOptions = new ArrayList<Option>();

		Option toOption = new Option("To", "To");

		Option fromOption = new Option("From", "From");

		toFromOptions.add(toOption);

		toFromOptions.add(fromOption);

		return toFromOptions;

	}

	public List<PointOfInterest> getMockPoiList() {
		List<PointOfInterest> poiList = new ArrayList<PointOfInterest>();
		return poiList;
	}

	List<Option> getPoiOptions()
	{		
		List<PointOfInterest> pois = getMockPoiList();
		List<Option> poiOptions = new ArrayList<Option>();
		for (PointOfInterest poi : pois) {
			Option o = new Option(poi.getPOIName(), poi.getPOIName());
			poiOptions.add(o);
		}
		
		return poiOptions;
		
	}
	
	@Test
	public void testCreatePoiSelectDestinationAttachment() {

//		String callbackID = "findRidesMessage";
		String callbackID = "SomeCallbackID";
		
		List<PointOfInterest> poiList = getMockPoiList();
		
		when(poiService.getAll()).thenReturn(poiList);

		Attachment testAttachment = slackMessageService.createPOISelectDestinationAttachment(callbackID);

		//Fails if the attachment's callbackID is not properly set based off input.
		assert(testAttachment.getCallback_id().equals(callbackID));
		
		List<Action> testActions = testAttachment.getActions();

		//Fails if the number of actions in the attachment is not exactly two.
		assert (testActions.size() == 2);

		List<Option> toFromOptions = getToFromOptions();

		Action toFromAction = new Action("To/From", "To/From", "select", toFromOptions);

		List<Option> poiOptions = getPoiOptions();
		
		Action poiAction = new Action("POI", "Pick a destination", "select", poiOptions);
		
		//Fails if the list does not contain the To/From action;
		assert (testActions.contains(toFromAction));

		//Fails if the list does not contain the Action containing the list of POIs
		assert (testActions.contains(poiAction));
		
		//Fails if slackMessageService.createPoiSelectDestinationAttachment(String) does not query the POI service for points of interest.
		verify(poiService, atLeastOnce()).getAll();
	}
}