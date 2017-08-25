package com.revature.rideshare.service;

import java.io.UnsupportedEncodingException;

import org.codehaus.jackson.JsonNode;

public interface SlackService {

	void setSlackMessageService(SlackMessageService slackMessageService);

	void setCarService(CarService carService);

	void setUserService(UserService userService);

	void setRideService(RideService rideService);

	void setPoiService(PointOfInterestService poiService);

	/**
	  * Creates an interactive message that will be sent to the user.<br>
	 * The message contains five attachments.<br>
	 * 		Attachment 1: contains three drop down menus for:
	 * 		<ul>	
	 * 			<li>hour(1 - 12)</li>  
	 * 			<li>minutes(00, 15, 30, 45)</li>
	 * 			<li>meridian(AM, PM)</li>
	 * 		</ul>
	 * 		Attachment 2: contains a drop down menu for origin POI.<br>
	 * 		Attachment 3: contains a drop down menu for destination POI.<br>
	 * 		Attachment 4: contains a drop down menu for number of seats.<br>
	 * 		Attachment 5: contains two buttons: OKAY and CANCEL<br>
	 * @param String userId
	 * @param String text
	 * @return a JSON string that contains the interactive message for a new ride
	 */
	String newRideMessage(String userId, String text);

	/**
	 * Creates an interactive message that will be sent to the user.<br>
	 * The message contains four attachments.<br>
	 * 		Attachment 1: contains three drop down menus for:
	 * 		<ul>	
	 * 			<li>hour(1 - 12)</li>  
	 * 			<li>minutes(00, 15, 30, 45)</li>
	 * 			<li>meridian(AM, PM)</li>
	 * 		</ul>
	 * 		Attachment 2: contains a drop down menu for origin POI.<br>
	 * 		Attachment 3: contains a drop down menu for destination POI.<br>
	 * 		Attachment 4: contains two buttons: OKAY and CANCEL<br>
	 * @param String userId
	 * @param String text
	 * @return String New slack message.
	 */
	String newRequestMessage(String userId, String text);

	/**
	 * Creates an interactive message that will be sent to the user.<br>
	 * The message contains four attachments.<br>
	 * 		Attachment 1: contains two drop down menus for:
	 * 		<ul>	
	 * 			<li>Destination/origin selection</li>  
	 * 			<li>Destination/origin POI</li>
	 * 		</ul>
	 * 		Attachment 2: contains a three drop down menus for start time selection with:<br>
	 * 		<ul>	
	 * 			<li>hour(1 - 12)</li>  
	 * 			<li>minutes(00, 15, 30, 45)</li>
	 * 			<li>meridian(AM, PM)</li>
	 * 		</ul>
	 * 		Attachment 3: contains a drop down menu for end time selection with:<br>
	 * 		<ul>	
	 * 			<li>hour(1 - 12)</li>  
	 * 			<li>minutes(00, 15, 30, 45)</li>
	 * 			<li>meridian(AM, PM)</li>
	 * 		</ul>
	 * 		Attachment 4: contains two buttons: OKAY and CANCEL<br>
	 * @param String userId
	 * @param String text
	 * @return String New slack message.
	 */
	String findRidesMessage(String userId, String text);

	/**
	 * --Not Implemented, placed here for naming convention clarity in future iterations.--
	 * <br>Lets a driver find request matching their parameters.
	 * @param String userId
	 * @param String date
	 * @return String New slack message.
	 */
	String findRequestsMessage(String userId, String date);

	/**
	 * Creates a ride in the database using the values that that the user inputted from slack
	 * @param JsonNode payload
	 * @return String Confirmation message.
	 */

	public String createRideByMessage(JsonNode payload){
		String userId = payload.path("user").path("id").asText();
		User user = userService.getUserBySlackId(userId);
		Car userCar = carService.getCarForUser(user);
		if(userCar!=null){
			SlackJSONBuilder slackMessage = slackMessageService.convertPayloadToSlackJSONBuilder(payload);
			ArrayList<String> strings=new ArrayList<String>();
			strings=slackMessageService.getTextFields(slackMessage);
			String dateString=strings.get(0);
			String hour = strings.get(1);
			String minute = strings.get(2);
			String meridian = strings.get(3);
			String pickupName = strings.get(4);
			String dropoffName = strings.get(5);
			Date time = slackMessageService.createRideDate(dateString,hour,minute,meridian);
			short seatsAvailable = Short.parseShort(strings.get(6));
			PointOfInterest pickupPOI = poiService.getPOI(pickupName);
			PointOfInterest dropoffPOI = poiService.getPOI(dropoffName);
			AvailableRide availableRide = new AvailableRide();
			availableRide.setCar(userCar);
			availableRide.setPickupPOI(pickupPOI);
			availableRide.setDropoffPOI(dropoffPOI);
			availableRide.setSeatsAvailable(seatsAvailable);
			availableRide.setOpen(true);
			availableRide.setTime(time);
			availableRide.setNotes("");
			rideService.addOffer(availableRide);
			String confirmationMessage = "Your ride for " + time.toString()
				+ " from " + pickupName + " to " + dropoffName +" with "+seatsAvailable+" seats  has been created";
			return confirmationMessage;
		}
		return null;
	}


	/**
	 * Creates a request confirmation message that contains the values that the user selected
	 * and creates a ride request in the application.
	 * @param JsonNode payload
	 * @return String Confirmation message.
	 */

	public String createRequestByMessage(JsonNode payload) {
		String userId = payload.path("user").path("id").asText();
		User user = userService.getUserBySlackId(userId);
		SlackJSONBuilder slackMessage = slackMessageService.convertPayloadToSlackJSONBuilder(payload);
		ArrayList<String> values = new ArrayList<String>();
		values = slackMessageService.getTextFields(slackMessage);
		values.forEach(v -> System.out.println("Value: " + v));
		String date = values.get(0);
		String hour = values.get(1);
		String minutes = values.get(2);
		String meridian = values.get(3);
		Date time = slackMessageService.createRideDate(date, hour, minutes, meridian);
		String fromPOI = values.get(4);
		String toPOI = values.get(5);
		RideRequest rideRequest = new RideRequest();
		rideRequest.setUser(user);
		rideRequest.setStatus(RequestStatus.OPEN);
		rideRequest.setPickupLocation(poiService.getPOI(fromPOI));
		rideRequest.setDropOffLocation(poiService.getPOI(toPOI));
		rideRequest.setTime(time);
		boolean success = rideService.addRequest(rideRequest);
		if(success){
			String confirmationMessage = "Your ride request for " + time.toString()
			+ " from " + fromPOI + " to " + toPOI + " has been created";
			return confirmationMessage;
		}else{
			return null;
		}
	}
	

	/**
	 * Returns a boolean to the {@link SlackServiceImpl#isMessageActionable} method
	 * to determine if the fields of the message have been filled by the user.
	 * @param String message
	 * @return boolean Determines if all fields have been filled.
	 */
	boolean foundRidesByMessage(String message);

	/**
	 * Returns a message which lets a user select rides matching their criteria.
	 * @param JsonNode payload
	 * @return String Confirmation message to be propagated as a message to slack user.
	 */
	String foundRidesByMessage(JsonNode payload);

	/**
	 *--Not Implemented, placed here for naming convention clarity in future iterations.--
	 *<br>Follows the flow after findRequestByMessage.
	 * @param payload
	 * @return String Confirmation message to be propagated as a message to slack user.
	 */
	String foundRequestsByMessage(JsonNode payload);

	/**
	 * --Only for interactive messages--<br>
	 * Convert http request into a usable JsonNode.
	 * @param request
	 * @return JsonNode Payload which can be parsed for message values.
	 * @throws UnsupportedEncodingException
	 */
	JsonNode convertMessageRequestToPayload(String request);

	/**
	 * Process the values that the user submitted in the interactive message
	 * @param JsonNode payload
	 * @return String Confirmation message to be propagated as a message to slack user.
	 */

	public String handleMessage(JsonNode payload){
		String callbackId=payload.path("callback_id").asText();
		ObjectMapper mapper = new ObjectMapper();
		String currentMessage = payload.path("original_message").toString();
		SlackJSONBuilder cMessage;
		try {
			cMessage = mapper.readValue(currentMessage, SlackJSONBuilder.class);
			ArrayList<String> strings= slackMessageService.getTextFields(cMessage);
			boolean isNewRequestOrRide=(callbackId.equals("newRideMessage")||callbackId.equals("newRequestMessage"));
			if(isNewRequestOrRide&&strings.get(4).equals(strings.get(5))){
				return ("Invalid Selection: Cannot use matching origin and destination.");
			}
			boolean isFindRequestOrRide=(callbackId.equals("findRidesMessage")||callbackId.equals("findRequestsMessage"));
			if(isFindRequestOrRide&&strings.get(6).equals(strings.get(7))){
				return("Invalid Selection: Cannot use matching origin and destination");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
		switch(callbackId){
		case("newRideMessage"):
			return createRideByMessage(payload);
		case("newRequestMessage"):
			return createRequestByMessage(payload);		
		case("findRidesMessage"):
			return foundRidesByMessage(payload);
		case("findRequestsMessage"):
			return foundRequestsByMessage(payload);
		case("foundRequestsByMessage"):
			return addPassengersToRideByMessage(payload);
		case("foundRidesByMessage"):
			return addUserToRideByMessage(payload);
		default:
			return "Message does not match any known callbackid, callbackId is "+callbackId;
		}
	}
	/**
	 * --Not implemented, only here for naming clarity--
	 * Should be used for a driver to add passengers to their ride.
	 * @param JsonNode payload
	 * @return Confirmation message to be propagated as a message to slack user.
	 */
	private String addPassengersToRideByMessage(JsonNode payload) {
		return "Message not implemented";
	}
	/**
	 * Adds a user to a ride selected through slack integration messages.
	 * @param JsonNode payload
	 * @return String Confirmation message to be propagated as a message to slack user.
	 */
	private String addUserToRideByMessage(JsonNode payload) {
		SlackJSONBuilder message = slackMessageService.convertPayloadToSlackJSONBuilder(payload);
		String userId = slackMessageService.getUserId(payload);
		String rideInfo = message.getAttachments().get(0).getActions().get(0).getText();
		long rideId = Long.parseLong(rideInfo.split(":")[rideInfo.split(":").length-1]);
		User u = userService.getUserBySlackId(userId);
		AvailableRide ride = rideService.getRideById(rideId);
		Date time = ride.getTime();
		String fromPOI = ride.getPickupPOI().getPOIName();
		String toPOI = ride.getDropoffPOI().getPOIName();
		boolean addedUser = rideService.acceptOffer(rideId, u);
		String confirmationMessage;
		if(addedUser){
			confirmationMessage = "Your ride request for " + time.toString()
			+ " from " + fromPOI + " to " + toPOI + " has been created";
		}else{
			confirmationMessage = "There was a problem with your request. Please try again.";
		}
		return confirmationMessage;
	}


	/**
	 * Checks to see if a message is ready to be processed<br>
	 * If the template for the message can be built from its payload, this will automatically check.<br><br>
	 * If not, the message must have a method matching its name, but 
	 * the argument must be the current message in String format.
	 * @param JsonNode payload
	 * @return boolean True if all fields in the message are filled, false otherwise.
	 */
	boolean isMessageActionable(JsonNode payload);

	/**
	 * Compares the user's message with the original message template to see if all fields have been filled.
	 * @param String currentMessage
	 * @param String template
	 * @return boolean True if all fields filled, false otherwise
	 */
	boolean compareMessages(String currentMessage, String template);

	/**
	 * Checks to see if a message is at the end of its message chain for propagating confirmation messages to slack user.
	 * @param String callbackId
	 * @return boolean True if message is able to send a confirmation message back to slack user. 
	 */
	boolean isMessageEndOfBranch(String callbackId);

}