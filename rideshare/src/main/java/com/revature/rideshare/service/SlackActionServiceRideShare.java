package com.revature.rideshare.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.domain.AvailableRide;
import com.revature.rideshare.domain.PointOfInterest;
import com.revature.rideshare.json.Action;
import com.revature.rideshare.json.Option;

@Service("slackActionService")
public class SlackActionServiceRideShare implements SlackActionService {

	private static final Integer MAX_NUMBER_SEATS = 4;

	public static final Integer NUMBER_OF_HOURS = 12;

	public static final Integer MAX_MINUTES = 45;

	public static final Integer MINUTES_INCREMENT = 15;

	@Autowired
	PointOfInterestService poiService;

	@Override
	public Action getAllPOIAction() {
		List<PointOfInterest> POIs = poiService.getAll();
		return getPOIListAction(POIs);
	}

	@Override
	public Action getPOIListAction(List<PointOfInterest> POIs) {

		List<Option> poiOptions = new ArrayList<Option>();
		for (PointOfInterest poi : POIs) {
			Option o = new Option(poi.getPoiName(), poi.getPoiName());
			poiOptions.add(o);
		}
		Action poiAction = new Action("POI", "Pick a destination", "select", poiOptions);

		return poiAction;
	}

	@Override
	public Action getToFromAction() {
		List<Option> toFromOptions = new ArrayList<Option>();
		Option toOption = new Option("To", "To");
		Option fromOption = new Option("From", "From");
		toFromOptions.add(toOption);
		toFromOptions.add(fromOption);
		Action toFromAction = new Action("To/From", "To/From", "select", toFromOptions);
		return toFromAction;
	}

	@Override
	public Action getCreateSeatsAction() {
		List<Option> seatOptions = new ArrayList<Option>();
		for (int i = 1; i <= MAX_NUMBER_SEATS; i++) {
			Option o = new Option(Integer.toString(i), Integer.toString(i));
			seatOptions.add(o);
		}
		Action seatsAction = new Action("Seats", "# of seats", "select", seatOptions);
		return seatsAction;
	}

	@Override
	public Action getCreateAvailableRidesAction(List<AvailableRide> availableRides) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action getCreateHoursAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action getCreateMinutesAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action getCreateMeridianAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action getCreateOKAYAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action getCreateCancelAction() {
		// TODO Auto-generated method stub
		return null;
	}

}
