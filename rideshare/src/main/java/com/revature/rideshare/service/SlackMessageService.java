package com.revature.rideshare.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.rideshare.domain.AvailableRide;
import com.revature.rideshare.domain.PointOfInterest;
import com.revature.rideshare.json.Action;
import com.revature.rideshare.json.Attachment;
import com.revature.rideshare.json.Option;
import com.revature.rideshare.json.SlackJSONBuilder;

@Component("slackMessageService")
@Transactional
public class SlackMessageService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	PointOfInterestService poiService;
	
	@Autowired
	RideService rideService;
	
	public void setRideService(RideService rideService) {
		this.rideService = rideService;
	}

	public void setPoiService(PointOfInterestService poiService) {
		this.poiService = poiService;
	}

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
	public Attachment createSeatsAttachment(String callbackId){
		ArrayList<Option> seatOptions = new ArrayList<Option>();
		ArrayList<Action> actions = new ArrayList<Action>();
		for(int i=1;i<5;i++){
			Option o = new Option(Integer.toString(i),Integer.toString(i));
			seatOptions.add(o);
		}
		Action seatsAction = new Action("Seats","# of seats","select",seatOptions);
		actions.add(seatsAction);
		Attachment seatsAttachment = new Attachment("Select # of Seats", "Unable to decide", callbackId, "#3AA3E3", "default", actions);
		return seatsAttachment;
	}
	
	/**
	 * Converts slack message into Java object
	 * (Refer to {@link com.revature.rideshare.json.SlackJSONBuilder})
	 * @param JsonNode message
	 * @return SlackJSONBuilder Usable Java object mapped to slack message.
	 */
	public SlackJSONBuilder convertMessageStringToSlackJSONBuilder(String currentMessage){
		ObjectMapper mapper = new ObjectMapper();
		try {
			SlackJSONBuilder cMessage = mapper.readValue(currentMessage, SlackJSONBuilder.class);
			return cMessage;
		} catch (IOException e) {
			logger.error("Message extraction exception");
			return null;
		}
	}
	
	/**
	 * Converts slack payload into Java object
	 * (Refer to {@link com.revature.rideshare.json.SlackJSONBuilder})
	 * @param JsonNode payload
	 * @return SlackJSONBuilder Usable Java object mapped to slack message.
	 */
	public SlackJSONBuilder convertPayloadToSlackJSONBuilder(JsonNode payload){
		ObjectMapper mapper = new ObjectMapper();
		String currentMessage = payload.path("original_message").toString();
		try {
			SlackJSONBuilder cMessage = mapper.readValue(currentMessage, SlackJSONBuilder.class);
			return cMessage;
		} catch (IOException e) {
			logger.error("Message extraction exception");
			return null;
		}
	}
	
	/**
	 * Pulls date from user slash command text.
	 * @param String text
	 * @return String date (mm/dd format)
	 */
	public String getDateFromText(String text){
		return text.split(" ")[text.split(" ").length-1];
	}
	
	/**
	 * Gets the text fields from each drop down menu in the message
	 * @param slackMessage
	 * @return ArrayList<String> of user selection strings(positions 1-end) and selected date(position 0.)
	 */
	public ArrayList<String> getTextFields(JsonNode payload){
		String message = payload.path("original_message").toString();
		ObjectMapper mapper = new ObjectMapper();
		try {
			ArrayList<String> values = new ArrayList<String>();
			SlackJSONBuilder slackMessage = mapper.readValue(message, SlackJSONBuilder.class);
			values = getTextFields(slackMessage);
			return values;
		} catch (IOException e) {
			logger.error("Exception occurred when checking user selections through slack integration.");
		}
		return null;
	}
	
	/**
	 * Retrieves message selections a user has made in a message.
	 * @param slackMessage
	 * @return ArrayList<String> of user selection strings(positions 1-end) and selected date(position 0.)
	 */
	public ArrayList<String> getTextFields(SlackJSONBuilder slackMessage){
		ArrayList<Attachment> attachments = slackMessage.getAttachments();
		ArrayList<String> strings = new ArrayList<String>();
		String[] dateSplit = slackMessage.getText().split(" ");
		strings.add(dateSplit[dateSplit.length-1]);
		for(Attachment attachment:attachments){
			ArrayList<Action> actions = attachment.getActions();
			for(Action action:actions){
				if(action.getType().equals("select")){
					strings.add(action.getText());
				}
			}
		}
		return strings;
	}
	
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
	public Attachment createTimeAttachment(String callbackId) {
		ArrayList<Option> hourOptions = new ArrayList<Option>();
		ArrayList<Option> minuteOptions = new ArrayList<Option>();
		ArrayList<Option> meridians = new ArrayList<Option>();
		ArrayList<Action> actions = new ArrayList<Action>();

		for (int i = 1; i <= 12; i++) {
			Option o = new Option(Integer.toString(i), Integer.toString(i));
			hourOptions.add(o);
		}

		for (int i = 0; i <= 45; i = i + 15) {
			Option o;
			if (i == 0)
				o = new Option(Integer.toString(i) + "0", Integer.toString(i) + "0");
			else
				o = new Option(Integer.toString(i), Integer.toString(i));
			minuteOptions.add(o);
		}

		Option am = new Option("AM", "AM");
		Option pm = new Option("PM", "PM");
		meridians.add(am);
		meridians.add(pm);

		Action hourAction = new Action("Hour", "hour", "select", hourOptions);
		Action minuteAction = new Action("Minute", "minute", "select", minuteOptions);
		Action meridianAction = new Action("Meridian", "AM/PM", "select", meridians);
		actions.add(hourAction);
		actions.add(minuteAction);
		actions.add(meridianAction);

		Attachment timeAttachment = new Attachment("Select a Time", "Unable to decide", callbackId, "#3AA3E3", "default", actions);

		return timeAttachment;
	}
	
	/**
	 * Creates an Attachment that contains an "OKAY" and a "CANCEL" button
	 * @param String callbackId
	 * @return Attachment Confirm/Cancel
	 */
	public Attachment createConfirmationButtonsAttachment(String callbackId) {
		ArrayList<Action> actions = new ArrayList<Action>();

		Action okayButton = new Action("OKAY", "OKAY", "button", "okay");
		Action cancelButton = new Action("cancel", "CANCEL", "button", "cancel");
		actions.add(okayButton);
		actions.add(cancelButton);

		Attachment buttonAttachment = new Attachment("Unable to display confirmation buttons", callbackId, "#3AA3E3", "default", actions);

		return buttonAttachment;
	}
	
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

		Attachment attachment = new Attachment(text, "Unable to decide", "newRideMessage", "#3AA3E3", "default", actions);

		return attachment;
	}
	
	
	/**
	 * Extracts userId from a payload
	 * @param payload
	 * @return String Url for response to a slack message.
	 */
	public String getUserId(JsonNode payload){
		return payload.path("user").path("id").asText();
	}
	/**
	 * Extracts responseUrl from a payload (used to send slack message to slack user.)
	 * @param payload
	 * @return String Url for response to a slack message.
	 */
	public String getMessageUrl(JsonNode payload){
		return payload.path("response_url").asText();
	}
	
	/**
	 * Creates a date object constructed with the values from the parameters.
	 * @param dateString, a string that has the following format: "MM/DD"
	 * @param String hour (1-12)
	 * @param String minute (00-59)
	 * @param String meridian (AM/PM)
	 * @return Date Representing combination of input parameters.
	 */
	@SuppressWarnings("deprecation")
	public Date createRideDate(String dateString,String hour,String minute,String meridian){
		int currentYear=new Date().getYear();
		int month = Integer.parseInt(dateString.split("/")[0]) - 1;
		int day = Integer.parseInt(dateString.split("/")[1]);
		int startHour = Integer.parseInt(hour);
		int startMinute = Integer.parseInt(minute);
		if(meridian.equals("AM")){
			if(startHour==12){
				startHour=0;
			}
		}else if(meridian.equals("PM")){
			if(startHour<12){
				startHour=startHour+12;
			}
		}
		Date time = new Date(currentYear,month,day,startHour,startMinute);
		return time;
	}
	
	/**
	 * Checks to see if the template can be built for a particular message from its payload.
	 * @param String callbackId
	 * @return boolean True if message has a template that can be built off its current message.
	 */
	public boolean templateCanBeBuiltFromPayload(String callbackId){
		return !(callbackId.equals("foundRidesByMessage")||callbackId.equals("foundRequestsByMessage"));
	}
}
