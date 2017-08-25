package com.revature.rideshare.service;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonNode;

import com.revature.rideshare.json.Attachment;
import com.revature.rideshare.json.SlackJSONBuilder;

public interface SlackMessageService {

	void setRideService(RideService rideService);

	void setPoiService(PointOfInterestService poiService);

	/**
	 * Creates attachment for POI selection.
	 * @param String callbackId
	 * @return Attachment Which lets user select their destination/origin (to/from option with POI.)
	 */

	public Attachment createPoiSelectDestinationAttachment(String callbackId){
		ArrayList<Action> actions = new ArrayList<Action>();
		ArrayList<Option> poiOptions = new ArrayList<Option>();
		ArrayList<Option> toFromOptions = new ArrayList<Option>();
		Option toOption = new Option("To","To");
		Option fromOption = new Option("From","From");
		toFromOptions.add(toOption);
		toFromOptions.add(fromOption);
		ArrayList<PointOfInterest> pois = (ArrayList<PointOfInterest>) poiService.getAll();
		for (PointOfInterest poi : pois) {
			Option o = new Option(poi.getPOIName(), poi.getPOIName());
			poiOptions.add(o);
		}
		Action toFromAction = new Action("To/From","To/From","select",toFromOptions);
		Action poiAction = new Action("POI", "Pick a destination", "select",poiOptions);
		actions.add(toFromAction);
		actions.add(poiAction);
		Attachment attachment = new Attachment("Select a destination or origin", "Unable to view destinations", "newRideMessage", "#3AA3E3", "default", actions);
		return attachment;
	}
	

	/**
	 * Creates the Attachment that contains a drop down menu with the number of seats a ride can have.
	 * @param String callbackId
	 * @return Attachment Seats attachment.
	 */
	Attachment createSeatsAttachment(String callbackId);

	/**
	 * Converts slack message into Java object
	 * (Refer to {@link com.revature.rideshare.json.SlackJSONBuilder})
	 * @param JsonNode message
	 * @return SlackJSONBuilder Usable Java object mapped to slack message.
	 */
	SlackJSONBuilder convertMessageStringToSlackJSONBuilder(String currentMessage);

	/**
	 * Converts slack payload into Java object
	 * (Refer to {@link com.revature.rideshare.json.SlackJSONBuilder})
	 * @param JsonNode payload
	 * @return SlackJSONBuilder Usable Java object mapped to slack message.
	 */
	SlackJSONBuilder convertPayloadToSlackJSONBuilder(JsonNode payload);

	/**
	 * Pulls date from user slash command text.
	 * @param String text
	 * @return String date (mm/dd format)
	 */
	String getDateFromText(String text);

	/**
	 * Gets the text fields from each drop down menu in the message
	 * @param slackMessage
	 * @return ArrayList<String> of user selection strings(positions 1-end) and selected date(position 0.)
	 */
	List<String> getTextFields(JsonNode payload);

	/**
	 * Retrieves message selections a user has made in a message.
	 * @param slackMessage
	 * @return ArrayList<String> of user selection strings(positions 1-end) and selected date(position 0.)
	 */
	List<String> getTextFields(SlackJSONBuilder slackMessage);

	/**
	 * Creates attachment for passengers to select rides.
	 * @param Date starttime
	 * @param Date endtime
	 * @param String filter
	 * @param String poiName
	 * @param String callbackId
	 * @return Attachment Which lets user select from rides matching their criteria.
	 */

	@SuppressWarnings("deprecation")
	public Attachment createAvailableRidesAttachment(Date starttime, Date endtime,String filter,String poiName,String callbackId){
		ArrayList<Action> actions = new ArrayList<Action>();
		ArrayList<Option> options = new ArrayList<Option>();
		PointOfInterest poi = poiService.getPOI(poiName);
		String destinationText="";
		String alternateDestinationText="";
		ArrayList<AvailableRide> rides = rideService.getAvailableRidesByTime(starttime, endtime);
		if(filter.equals("To")){
			rides=rideService.filterAvailableRidesByDropoffPoi(rides, poi);
			destinationText=poi.getPOIName();
		}else if(filter.equals("From")){
			rides=rideService.filterAvailableRidesByPickupPoi(rides, poi);
			destinationText=poi.getPOIName();
		}
		for(AvailableRide ride:rides){
			if(ride.isOpen()){
				if(filter.equals("To")){
					alternateDestinationText=ride.getPickupPOI().getPOIName();
				}else if(filter.equals("From")){
					alternateDestinationText=ride.getDropoffPOI().getPOIName();
				}
					Date time = ride.getTime();
					String hours = ""+time.getHours();
					String minutes = ""+time.getMinutes();
					String meridian = "AM";
					if(minutes.equals("0")){
						minutes = minutes+"0";
					}
					if(time.getHours()>=12){
						meridian = "PM";
						if(time.getHours()>12){
							hours = ""+(time.getHours()-12);
						}
					}
					String timeText = hours + ":" + minutes + meridian;
					String text = timeText+" "+destinationText+">"+alternateDestinationText+" ID:"+ride.getAvailRideId();
					Option o = new Option(text,text);
					options.add(o);
			}
		}
		Action action = new Action("AvailableRides","Select from the following rides","select",options);
		actions.add(action);
		Attachment availableRidesAttachment = new Attachment("AvailableRides","Available Rides","Unable to display available rides", callbackId, "#3AA3E3", "default", actions);
		return availableRidesAttachment;
	}
	

	/**
	 * Creates an Attachment that contains three drop down menus: hour, minutes, and meridian
	 * @param callbackId
	 * @return Attachment Contains time selection drop downs.
	 */
	Attachment createTimeAttachment(String callbackId);

	/**
	 * Creates an Attachment that contains an "OKAY" and a "CANCEL" button
	 * @param String callbackId
	 * @return Attachment Confirm/Cancel
	 */
	Attachment createConfirmationButtonsAttachment(String callbackId);

	/**
	 * Creates an Attachment that contains a drop down menu that is populated with all of the POIs
	 * @param String text This is displayed above the drop down menu.
	 * @param String callbackId
	 * @return Attachment Contains POI drop downs.
	 */

	public Attachment createPOIAttachment(String text, String callbackId) {
		ArrayList<Action> actions = new ArrayList<Action>();
		ArrayList<Option> poiOptions = new ArrayList<Option>();

		ArrayList<PointOfInterest> pois = (ArrayList<PointOfInterest>) poiService.getAll();
		for (PointOfInterest poi : pois) {
			Option o = new Option(poi.getPOIName(), poi.getPOIName());
			poiOptions.add(o);
		}

		Action action = new Action("POI", "Pick a destination", "select",poiOptions);
		actions.add(action);


	/**
	 * Extracts userId from a payload
	 * @param payload
	 * @return String Url for response to a slack message.
	 */
	String getUserId(JsonNode payload);

	/**
	 * Extracts responseUrl from a payload (used to send slack message to slack user.)
	 * @param payload
	 * @return String Url for response to a slack message.
	 */
	String getMessageUrl(JsonNode payload);

	/**
	 * Creates a date object constructed with the values from the parameters.
	 * @param dateString, a string that has the following format: "MM/DD"
	 * @param String hour (1-12)
	 * @param String minute (00-59)
	 * @param String meridian (AM/PM)
	 * @return Date Representing combination of input parameters.
	 */
	Date createRideDate(String dateString, String hour, String minute, String meridian);

	/**
	 * Checks to see if the template can be built for a particular message from its payload.
	 * @param String callbackId
	 * @return boolean True if message has a template that can be built off its current message.
	 */
	boolean templateCanBeBuiltFromPayload(String callbackId);

}