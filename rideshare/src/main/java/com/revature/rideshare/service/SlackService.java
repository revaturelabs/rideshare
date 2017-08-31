package com.revature.rideshare.service;

import java.io.UnsupportedEncodingException;

import org.codehaus.jackson.JsonNode;

/**
 * Top-level service used for all slack integration.<br>
 * <b>Relies upon</b><br>
 * {@link SlackMessageService}<br>
 * {@link CarService}<br>
 * {@link UserService}<br>
 * {@link RideService}<br>
 * {@link PointOfInterestService}<br>
 * <br>
 * <b>Methods:</b><br>
 * {@link #newRideMessage(String, String) newRideMessage}<br>
 * {@link #newRideMessage(String, String) newRequestMessage}<br>
 * {@link #findRidesMessage(String, String) findRidesMessage}<br>
 * {@link #findRequestsMessage(String, String) findRequestsMessage}<br>
 * {@link #foundRidesByMessage(String) foundRidesByMessage(String)}<br>
 * {@link #foundRidesByMessage(JsonNode) foundRidesByMessage(JsonNode)}<br>
 * {@link #foundRequestsByMessage(JsonNode)}<br>
 * {@link #convertMessageRequestToPayload(String)}<br>
 * {@link #handleMessage(JsonNode)}<br>
 * {@link #isMessageActionable(JsonNode)}<br>
 * {@link #compareMessages(String, String)}<br>
 * {@link #isMessageEndOfBranch(String)}<br>
 */

public interface SlackService {

	void setSlackMessageService(SlackMessageService slackMessageService);

	void setCarService(CarService carService);

	void setUserService(UserService userService);

	void setRideService(RideService rideService);

	void setPoiService(PointOfInterestService poiService);

	/**
	 * Creates an interactive message that will be sent to the user.<br>
	 * The message contains five attachments.<br>
	 * Attachment 1: contains three drop down menus for:
	 * <ul>
	 * <li>hour(1 - 12)</li>
	 * <li>minutes(00, 15, 30, 45)</li>
	 * <li>meridian(AM, PM)</li>
	 * </ul>
	 * Attachment 2: contains a drop down menu for origin POI.<br>
	 * Attachment 3: contains a drop down menu for destination POI.<br>
	 * Attachment 4: contains a drop down menu for number of seats.<br>
	 * Attachment 5: contains two buttons: OKAY and CANCEL<br>
	 * 
	 * @param userId
	 * @param text
	 * @return a JSON string that contains the interactive message for a new
	 *         ride
	 */
	String newRideMessage(String userId, String text);

	/**
	 * Creates an interactive message that will be sent to the user.<br>
	 * The message contains four attachments.<br>
	 * Attachment 1: contains three drop down menus for:
	 * <ul>
	 * <li>hour(1 - 12)</li>
	 * <li>minutes(00, 15, 30, 45)</li>
	 * <li>meridian(AM, PM)</li>
	 * </ul>
	 * Attachment 2: contains a drop down menu for origin POI.<br>
	 * Attachment 3: contains a drop down menu for destination POI.<br>
	 * Attachment 4: contains two buttons: OKAY and CANCEL<br>
	 * 
	 * @param userId
	 * @param text
	 * @return String New slack message.
	 */
	String newRequestMessage(String userId, String text);

	/**
	 * Creates an interactive message that will be sent to the user.<br>
	 * The message contains four attachments.<br>
	 * Attachment 1: contains two drop down menus for:
	 * <ul>
	 * <li>Destination/origin selection</li>
	 * <li>Destination/origin POI</li>
	 * </ul>
	 * Attachment 2: contains a three drop down menus for start time selection
	 * with:<br>
	 * <ul>
	 * <li>hour(1 - 12)</li>
	 * <li>minutes(00, 15, 30, 45)</li>
	 * <li>meridian(AM, PM)</li>
	 * </ul>
	 * Attachment 3: contains a drop down menu for end time selection with:<br>
	 * <ul>
	 * <li>hour(1 - 12)</li>
	 * <li>minutes(00, 15, 30, 45)</li>
	 * <li>meridian(AM, PM)</li>
	 * </ul>
	 * Attachment 4: contains two buttons: OKAY and CANCEL<br>
	 * 
	 * @param userId
	 * @param text
	 * @return String New slack message.
	 */
	String findRidesMessage(String userId, String text);

	/**
	 * --Not Implemented, placed here for naming convention clarity in future
	 * iterations.-- <br>
	 * Lets a driver find request matching their parameters.
	 * 
	 * @param userId
	 * @param date
	 * @return String New slack message.
	 */
	String findRequestsMessage(String userId, String date);

	/**
	 * Creates a ride in the database using the values that that the user
	 * inputted from slack
	 * 
	 * @param payload
	 * @return String Confirmation message.
	 */
	String createRideByMessage(JsonNode payload);

	/**
	 * Creates a request confirmation message that contains the values that the
	 * user selected and creates a ride request in the application.
	 * 
	 * @param payload
	 * @return String Confirmation message.
	 */
	String createRequestByMessage(JsonNode payload);

	/**
	 * Returns a boolean to the {@link SlackServiceImpl#isMessageActionable}
	 * method to determine if the fields of the message have been filled by the
	 * user.
	 * 
	 * @param message
	 * @return boolean Determines if all fields have been filled.
	 */
	boolean foundRidesByMessage(String message);

	/**
	 * Returns a message which lets a user select rides matching their criteria.
	 * 
	 * @param payload
	 * @return String Confirmation message to be propagated as a message to
	 *         slack user.
	 */
	String foundRidesByMessage(JsonNode payload);

	/**
	 * --Not Implemented, placed here for naming convention clarity in future
	 * iterations.-- <br>
	 * Follows the flow after findRequestByMessage.
	 * 
	 * @param payload
	 * @return String Confirmation message to be propagated as a message to
	 *         slack user.
	 */
	String foundRequestsByMessage(JsonNode payload);

	/**
	 * --Only for interactive messages--<br>
	 * Convert http request into a usable JsonNode.
	 * 
	 * @param request
	 * @return JsonNode Payload which can be parsed for message values.
	 * @throws UnsupportedEncodingException
	 */
	JsonNode convertMessageRequestToPayload(String request);

	/**
	 * Process the values that the user submitted in the interactive message
	 * 
	 * @param payload
	 * @return String Confirmation message to be propagated as a message to
	 *         slack user.
	 */
	String handleMessage(JsonNode payload);

	/**
	 * Checks to see if a message is ready to be processed<br>
	 * If the template for the message can be built from its payload, this will
	 * automatically check.<br>
	 * <br>
	 * If not, the message must have a method matching its name, but the
	 * argument must be the current message in String format.
	 * 
	 * @param payload
	 * @return boolean True if all fields in the message are filled, false
	 *         otherwise.
	 */
	boolean isMessageActionable(JsonNode payload);

	/**
	 * Compares the user's message with the original message template to see if
	 * all fields have been filled.
	 * 
	 * @param currentMessage
	 * @param template
	 * @return boolean True if all fields filled, false otherwise
	 */
	boolean compareMessages(String currentMessage, String template);

	/**
	 * Checks to see if a message is at the end of its message chain for
	 * propagating confirmation messages to slack user.
	 * 
	 * @param callbackId
	 * @return boolean True if message is able to send a confirmation message
	 *         back to slack user.
	 */
	boolean isMessageEndOfBranch(String callbackId);

}