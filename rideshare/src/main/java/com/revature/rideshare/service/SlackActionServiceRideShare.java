package com.revature.rideshare.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.rideshare.domain.AvailableRide;
import com.revature.rideshare.domain.PointOfInterest;
import com.revature.rideshare.json.Action;
import com.revature.rideshare.json.Option;

@Component("slackActionService")
@Transactional
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
		List<Option> options = new ArrayList<Option>();
		String destinationText = "";
		String alternateDestinationText = "";
		for (AvailableRide ride : availableRides) {
			if (ride.isOpen()) {
				destinationText = ride.getPickupPOI().getPoiName();
				alternateDestinationText = ride.getDropoffPOI().getPoiName();
				System.out.println(ride);
			}
			Date time = ride.getTime();
			String hours = "" + time.getHours();
			String minutes = "" + time.getMinutes();
			String meridian = "AM";
			if (minutes.equals("0")) {
				minutes = minutes + "0";
			}
			if (time.getHours() >= NUMBER_OF_HOURS) {
				meridian = "PM";
				if (time.getHours() > NUMBER_OF_HOURS) {
					hours = "" + (time.getHours() - NUMBER_OF_HOURS);
				}
			}
			String timeText = hours + ":" + minutes + meridian;
			String text = timeText + " " + destinationText + ">" + alternateDestinationText + " ID:"
					+ ride.getAvailRideId();
			Option o = new Option(text, text);
			options.add(o);
		}
		return new Action(Seats);

	}

	@Override
	public Action getCreateHoursAction() {
		List<Option> hourOptions = new ArrayList<Option>();
		for (int i = 1; i <= NUMBER_OF_HOURS; i++) {
			Option o = new Option(Integer.toString(i), Integer.toString(i));
			hourOptions.add(o);
		}
		Action hourAction = new Action("Hour", "hour", "select", hourOptions);
		return hourAction;
	}

	@Override
	public Action getCreateMinutesAction() {
		List<Option> minuteOptions = new ArrayList<Option>();
		for (int i = 0; i <= MAX_MINUTES; i = i + MINUTES_INCREMENT) {
			Option o;
			if (i == 0)
				o = new Option(Integer.toString(i) + "0", Integer.toString(i) + "0");
			else
				o = new Option(Integer.toString(i), Integer.toString(i));
			minuteOptions.add(o);
		}
		Action minuteAction = new Action("Minute", "minute", "select", minuteOptions);
		return minuteAction;
	}

	@Override
	public Action getCreateMeridianAction() {
		List<Option> meridians = new ArrayList<Option>();

		Option am = new Option("AM", "AM");
		Option pm = new Option("PM", "PM");
		meridians.add(am);
		meridians.add(pm);

		Action meridianAction = new Action("Meridian", "AM/PM", "select", meridians);

		return meridianAction;
	}

	@Override
	public Action getCreateOKAYAction() {
		return new Action("OKAY", "OKAY", "button", "okay");
	}

	@Override
	public Action getCreateCancelAction() {
		return new Action("cancel", "CANCEL", "button", "cancel");
	}

}
