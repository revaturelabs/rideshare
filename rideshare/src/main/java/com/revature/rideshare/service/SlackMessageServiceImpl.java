package com.revature.rideshare.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class SlackMessageServiceImpl implements SlackMessageService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	PointOfInterestService poiService;
	
	@Autowired
	RideService rideService;
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.SlackMessageService#setRideService(com.revature.rideshare.service.RideService)
	 */
	@Override
	public void setRideService(RideService rideService) {
		this.rideService = rideService;
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.SlackMessageService#setPoiService(com.revature.rideshare.service.PointOfInterestService)
	 */
	@Override
	public void setPOIService(PointOfInterestService poiService) {
		this.poiService = poiService;
	}

	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.SlackMessageService#createPoiSelectDestinationAttachment(java.lang.String)
	 */
	@Override
	public Attachment createPOISelectDestinationAttachment(String callbackId){
		List<Action> actions = new ArrayList<Action>();
		List<Option> poiOptions = new ArrayList<Option>();
		List<Option> toFromOptions = new ArrayList<Option>();
		Option toOption = new Option("To","To");
		Option fromOption = new Option("From","From");
		toFromOptions.add(toOption);
		toFromOptions.add(fromOption);
		List<PointOfInterest> pois = (ArrayList<PointOfInterest>) poiService.getAll();
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
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.SlackMessageService#createSeatsAttachment(java.lang.String)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.SlackMessageService#convertMessageStringToSlackJSONBuilder(java.lang.String)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.SlackMessageService#convertPayloadToSlackJSONBuilder(org.codehaus.jackson.JsonNode)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.SlackMessageService#getDateFromText(java.lang.String)
	 */
	@Override
	public String getDateFromText(String text){
		return text.split(" ")[text.split(" ").length-1];
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.SlackMessageService#getTextFields(org.codehaus.jackson.JsonNode)
	 */
	@Override
	public List<String> getTextFields(JsonNode payload){
		String message = payload.path("original_message").toString();
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<String> values = new ArrayList<String>();
			SlackJSONBuilder slackMessage = mapper.readValue(message, SlackJSONBuilder.class);
			values = getTextFields(slackMessage);
			return values;
		} catch (IOException e) {
			logger.error("Exception occurred when checking user selections through slack integration.");
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.SlackMessageService#getTextFields(com.revature.rideshare.json.SlackJSONBuilder)
	 */
	@Override
	public List<String> getTextFields(SlackJSONBuilder slackMessage){
		List<Attachment> attachments = slackMessage.getAttachments();
		List<String> strings = new ArrayList<String>();
		String[] dateSplit = slackMessage.getText().split(" ");
		strings.add(dateSplit[dateSplit.length-1]);
		for(Attachment attachment:attachments){
			List<Action> actions = attachment.getActions();
			for(Action action:actions){
				if(action.getType().equals("select")){
					strings.add(action.getText());
				}
			}
		}
		return strings;
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.SlackMessageService#createAvailableRidesAttachment(java.util.Date, java.util.Date, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("deprecation")
	public Attachment createAvailableRidesAttachment(Date starttime, Date endtime,String filter,String poiName,String callbackId){
		List<Action> actions = new ArrayList<Action>();
		List<Option> options = new ArrayList<Option>();
		PointOfInterest poi = poiService.getPOI(poiName);
		String destinationText="";
		String alternateDestinationText="";
		List<AvailableRide> rides = rideService.getAvailableRidesByTime(starttime, endtime);
		if(filter.equals("To")){
			rides=rideService.filterAvailableRidesByDropoffPOI(rides, poi);
			destinationText=poi.getPOIName();
		}else if(filter.equals("From")){
			rides=rideService.filterAvailableRidesByPickupPOI(rides, poi);
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
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.SlackMessageService#createTimeAttachment(java.lang.String)
	 */
	@Override
	public Attachment createTimeAttachment(String callbackId) {
		List<Option> hourOptions = new ArrayList<Option>();
		List<Option> minuteOptions = new ArrayList<Option>();
		List<Option> meridians = new ArrayList<Option>();
		List<Action> actions = new ArrayList<Action>();

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
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.SlackMessageService#createConfirmationButtonsAttachment(java.lang.String)
	 */
	@Override
	public Attachment createConfirmationButtonsAttachment(String callbackId) {
		ArrayList<Action> actions = new ArrayList<Action>();

		Action okayButton = new Action("OKAY", "OKAY", "button", "okay");
		Action cancelButton = new Action("cancel", "CANCEL", "button", "cancel");
		actions.add(okayButton);
		actions.add(cancelButton);

		Attachment buttonAttachment = new Attachment("Unable to display confirmation buttons", callbackId, "#3AA3E3", "default", actions);

		return buttonAttachment;
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.SlackMessageService#createPOIAttachment(java.lang.String, java.lang.String)
	 */
	@Override
	public Attachment createPOIAttachment(String text, String callbackId) {
		List<Action> actions = new ArrayList<Action>();
		List<Option> poiOptions = new ArrayList<Option>();

		List<PointOfInterest> pois = (ArrayList<PointOfInterest>) poiService.getAll();
		for (PointOfInterest poi : pois) {
			Option o = new Option(poi.getPOIName(), poi.getPOIName());
			poiOptions.add(o);
		}

		Action action = new Action("POI", "Pick a destination", "select",poiOptions);
		actions.add(action);

		Attachment attachment = new Attachment(text, "Unable to decide", "newRideMessage", "#3AA3E3", "default", actions);

		return attachment;
	}
	
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.SlackMessageService#getUserId(org.codehaus.jackson.JsonNode)
	 */
	@Override
	public String getUserId(JsonNode payload){
		return payload.path("user").path("id").asText();
	}
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.SlackMessageService#getMessageUrl(org.codehaus.jackson.JsonNode)
	 */
	@Override
	public String getMessageUrl(JsonNode payload){
		return payload.path("response_url").asText();
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.SlackMessageService#createRideDate(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see com.revature.rideshare.service.SlackMessageService#templateCanBeBuiltFromPayload(java.lang.String)
	 */
	@Override
	public boolean templateCanBeBuiltFromPayload(String callbackId){
		return !(callbackId.equals("foundRidesByMessage")||callbackId.equals("foundRequestsByMessage"));
	}
}
