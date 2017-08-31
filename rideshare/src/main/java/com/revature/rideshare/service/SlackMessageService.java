package com.revature.rideshare.service;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonNode;

import com.revature.rideshare.domain.PointOfInterest;
import com.revature.rideshare.json.Attachment;
import com.revature.rideshare.json.SlackJSONBuilder;

public interface SlackMessageService {

	void setRideService(RideService rideService);

	void setPoiService(PointOfInterestService poiService);

	void setSlackActionService(SlackActionService slackActionService);

	/**
	 * Creates attachment for POI selection.
	 * 
	 * <p>
	 * {@link Attachment} is formatted as follows:
	 * <ul>
	 * <li>{@link com.revature.rideshare.json.Action Action}:
	 * <ul>
	 * <li>{@link com.revature.rideshare.json.Option Option}: To
	 * <li>{@link com.revature.rideshare.json.Option Option}: From
	 * </ul>
	 * <li>{@link com.revature.rideshare.json.Action Action}: A list of
	 * {@link PointOfInterest Points Of Interest} that are acceptable
	 * destinations as {@link com.revature.rideshare.json.Option Options}.
	 * </ul>
	 * 
	 * @param String
	 *            callbackId
	 * @return {@link Attachment} Which lets user select their
	 *         destination/origin (to/from option with POI.)
	 */
	Attachment createPoiSelectDestinationAttachment(String callbackId);

	/**
	 * Creates the Attachment that contains a drop down menu with the number of
	 * seats a ride can have.
	 * 
	 * @param String
	 *            callbackId
	 * @return Attachment Seats attachment.
	 */
	Attachment createSeatsAttachment(String callbackId);

	/**
	 * Converts slack message into Java object (Refer to
	 * {@link com.revature.rideshare.json.SlackJSONBuilder SlackJSONBuilder})
	 * 
	 * @param JsonNode
	 *            message
	 * @return SlackJSONBuilder Usable Java object mapped to slack message.
	 */
	SlackJSONBuilder convertMessageStringToSlackJSONBuilder(String currentMessage);

	/**
	 * Converts slack payload into Java object (Refer to
	 * {@link com.revature.rideshare.json.SlackJSONBuilder SlackJSONBuilder})
	 * 
	 * @param JsonNode
	 *            payload
	 * @return SlackJSONBuilder Usable Java object mapped to slack message.
	 */
	SlackJSONBuilder convertPayloadToSlackJSONBuilder(JsonNode payload);

	/**
	 * Pulls date from user slash command text.
	 * 
	 * @param String
	 *            text
	 * @return String date (mm/dd format)
	 */
	String getDateFromText(String text);

	/**
	 * Gets the text fields from each drop down menu in the message<br>
	 * <br>
	 * Relies on {@link #getTextFields(SlackJSONBuilder)
	 * getTextFields(SlackJSONBuilder)}
	 * 
	 * @param slackMessage
	 * @return List of strings containing user selection strings(positions
	 *         1-end) and selected date(position 0.)
	 */
	List<String> getTextFields(JsonNode payload);

	/**
	 * Retrieves message selections a user has made in a message.
	 * 
	 * @param slackMessage
	 *            a {@link com.revature.rideshare.json.SlackJSONBuilder
	 *            SlackJSONBuilder} containing the slack message.
	 * @return List of strings containing user selection strings(positions
	 *         1-end) and selected date(position 0.)
	 */
	List<String> getTextFields(SlackJSONBuilder slackMessage);

	/**
	 * Creates attachment for passengers to select rides.
	 * 
	 * @param Date
	 *            starttime
	 * @param Date
	 *            endtime
	 * @param String
	 *            filter
	 * @param String
	 *            poiName
	 * @param String
	 *            callbackId
	 * @return {@link com.revature.rideshare.json.Attachment Attachment}Which
	 *         lets user select from rides matching their criteria.
	 */
	Attachment createAvailableRidesAttachment(Date starttime, Date endtime, String filter, String poiName,
			String callbackId);

	/**
	 * Creates an Attachment that contains three drop down menus: hour, minutes,
	 * and meridian
	 * 
	 * @param callbackId
	 * @return {@link com.revature.rideshare.json.Attachment Attachment}
	 *         Containing time selection drop downs.
	 */
	Attachment createTimeAttachment(String callbackId);

	/**
	 * Creates an Attachment that contains an "OKAY" and a "CANCEL" button
	 * 
	 * @param String
	 *            callbackId
	 * @return {@link com.revature.rideshare.json.Attachment Attachment}
	 *         containing Confirm/Cancel selections
	 */
	Attachment createConfirmationButtonsAttachment(String callbackId);

	/**
	 * Creates an Attachment that contains a drop down menu that is populated
	 * with all of the POIs
	 * 
	 * @param String
	 *            text This is displayed above the drop down menu.
	 * @param String
	 *            callbackId
	 * @return {@link com.revature.rideshare.json.Attachment Attachment}
	 *         Contains POI drop downs.
	 */
	Attachment createPOIAttachment(String text, String callbackId);

	/**
	 * Extracts userId from a payload
	 * 
	 * @param payload
	 *            containing { "user" : { "id" : "something" } }
	 * @return String Url for response to a slack message.
	 */
	String getUserId(JsonNode payload);

	/**
	 * Extracts responseUrl from a payload (used to send slack message to slack
	 * user.)
	 * 
	 * @param payload
	 * @return String Url for response to a slack message.
	 */
	String getMessageUrl(JsonNode payload);

	/**
	 * Creates a date object constructed with the values from the parameters.
	 * 
	 * @param dateString,
	 *            a string that has the following format: "MM/DD"
	 * @param String
	 *            hour (1-12)
	 * @param String
	 *            minute (00-59)
	 * @param String
	 *            meridian (AM/PM)
	 * @return Date Representing combination of input parameters.
	 */
	Date createRideDate(String dateString, String hour, String minute, String meridian);

	/**
	 * Checks to see if the template can be built for a particular message from
	 * its payload.
	 * 
	 * @param String
	 *            callbackId
	 * @return boolean True if message has a template that can be built off its
	 *         current message.
	 */
	boolean templateCanBeBuiltFromPayload(String callbackId);

}