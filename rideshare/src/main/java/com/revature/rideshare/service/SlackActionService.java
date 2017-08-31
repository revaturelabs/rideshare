package com.revature.rideshare.service;

import java.util.List;

import com.revature.rideshare.domain.AvailableRide;
import com.revature.rideshare.domain.PointOfInterest;
import com.revature.rideshare.json.Action;

/**
 * Acts as the lowest-level service for the creation of a variety of
 * commonly-required Slack {@link Action actions}.
 * <p>
 * Primarily used by {@link SlackMessageService} to create the {@link Action
 * actions} used by {@link com.revature.rideshare.json.Attachment attachments}.
 *
 */

public interface SlackActionService {

	/**
	 * Gets an {@link Action action} allowing the user to select if the
	 * {@link PointOfInterest PoI} they are choosing will be the starting point
	 * or ending point of a {@link com.revature.rideshare.domain.RideRequest
	 * ride}.
	 * 
	 * @return Action
	 *         <ul>
	 *         <li>name: "To/From"</li>
	 *         <li>text: "To/From"</li>
	 *         <li>type: "select"</li>
	 *         <li>options:
	 *         <ul>
	 *         <li>option:
	 *         <ul>
	 *         <li>text: "To"
	 *         <li>value: "To"
	 *         </ul>
	 *         <li>option:
	 *         <ul>
	 *         <li>text: "From"
	 *         <li>value: "From"
	 *         </ul>
	 *         </ul>
	 *         </li>
	 *         </ul>
	 */

	Action getToFromAction();

	/**
	 * Gets a list of all {@link PointOfInterest POIs} as an {@link Action
	 * action}. Makes use of {@link SlackActionService#getPOIListAction(List)}
	 * 
	 * @return Action
	 *         <ul>
	 *         <li>name: "POI"</li>
	 *         <li>text: "Pick a destination"</li>
	 *         <li>type: "select"</li>
	 *         <li>options: A list containing all {@link PointOfInterest Points
	 *         of Interest}</li>
	 *         </ul>
	 */
	Action getAllPOIAction();

	/**
	 * Returns an {@link Action action} containing the list of
	 * {@link PointOfInterest POIs}.
	 * 
	 * @param POIs
	 *            A list of {@link PointOfInterest POIs} to convert into the
	 *            options for the {@code Action}.
	 * 
	 * @return Action
	 *         <ul>
	 *         <li>name: "POI"</li>
	 *         <li>text: "Pick a destination"</li>
	 *         <li>type: "select"</li>
	 *         <li>options: A list containing {@link PointOfInterest Points of
	 *         Interest} that were passed in.</li>
	 *         </ul>
	 */
	Action getPOIListAction(List<PointOfInterest> POIs);

	/**
	 * return an action containing a list of numbers of possible seats.
	 * 
	 * @return Action
	 *         <ul>
	 *         <li>name: "Seats"</li>
	 *         <li>text: "# of seats"</li>
	 *         <li>type: "select"</li>
	 *         <li>options: the number between 1 and the maximum number of
	 *         allowed seats</li>
	 *         </ul>
	 */

	Action getCreateSeatsAction();

	/**
	 * Returns an {@link Action action} containing options corresponding to a
	 * list of {@link AvailableRide available rides}.
	 * 
	 * @param availableRides
	 *            A list of {@link AvailableRide Available Rides} to create
	 *            options from.
	 * @return Action
	 *         <ul>
	 *         <li>name: "Seats"</li>
	 *         <li>text: "# of seats"</li>
	 *         <li>type: "select"</li>
	 *         <li>options: a list of {@link AvailableRide available rides}</li>
	 *         </ul>
	 */

	Action getCreateAvailableRidesAction(List<AvailableRide> availableRides);

	/**
	 * returns an {@link Action action} containing hours representing the 12
	 * hours of a U.S. Standard clock.
	 * 
	 * @return Action
	 *         <ul>
	 *         <li>name: "Hour"</li>
	 *         <li>text: "hour"</li>
	 *         <li>type: "select"</li>
	 *         <li>options: a list from 1 to 12</li>
	 *         </ul>
	 */

	Action getCreateHoursAction();

	/**
	 * returns an {@link Action action} containing minutes in 15 minute
	 * intervals.
	 * 
	 * @return Action
	 *         <ul>
	 *         <li>name: "Minute"</li>
	 *         <li>text: "minute"</li>
	 *         <li>type: "select"</li>
	 *         <li>options:
	 * 
	 *         <ul>
	 * 
	 *         <li>option:
	 *         <ul>
	 *         <li>text: "00"
	 *         <li>value: "00"
	 *         </ul>
	 * 
	 * 
	 *         <li>option:
	 *         <ul>
	 *         <li>text: "15"
	 *         <li>value: "15"
	 *         </ul>
	 * 
	 *         <li>option:
	 *         <ul>
	 *         <li>text: "30"
	 *         <li>value: "30"
	 *         </ul>
	 * 
	 *         <li>option:
	 *         <ul>
	 *         <li>text: "45"
	 *         <li>value: "45"
	 *         </ul>
	 * 
	 *         </ul>
	 *         </li>
	 * 
	 *         </li>
	 *         </ul>
	 */

	Action getCreateMinutesAction();

	/**
	 * 
	 * Returns an {@link Action action} containing AM/PM options
	 * 
	 * 
	 * @return Action
	 *         <ul>
	 *         <li>name: "Meridian"</li>
	 *         <li>text: "AM/PM"</li>
	 *         <li>type: "select"</li>
	 *         <li>options:
	 * 
	 *         <ul>
	 * 
	 *         <li>option:
	 *         <ul>
	 *         <li>text: "AM"
	 *         <li>value: "AM"
	 *         </ul>
	 * 
	 * 
	 *         <li>option:
	 *         <ul>
	 *         <li>text: "PM"
	 *         <li>value: "PM"
	 *         </ul>
	 * 
	 *         </ul>
	 *         </li>
	 * 
	 *         </li>
	 *         </ul>
	 */
	Action getCreateMeridianAction();

	/**
	 * Returns an {@link Action action} containing an OKAY button.
	 * 
	 * @return Action
	 *         <ul>
	 *         <li>name: "OKAY"</li>
	 *         <li>text: "OKAY"</li>
	 *         <li>type: "button"</li>
	 *         <li>value: "okay"</li>
	 *         </ul>
	 */

	Action getCreateOKAYAction();

	/**
	 * Returns an {@link Action action} containing a CANCEL button.
	 * 
	 * @return Action
	 *         <ul>
	 *         <li>name: "cancel"</li>
	 *         <li>text: "CANCEL"</li>
	 *         <li>type: "button"</li>
	 *         <li>value: "cancel"</li>
	 *         </ul>
	 */

	Action getCreateCancelAction();

}
