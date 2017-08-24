package com.revature.rideshare.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.revature.rideshare.domain.User;
import com.revature.rideshare.json.SlackJSONBuilder;

@Component("slackFormatService")
@Transactional
public class SlackFormatService {
	@Autowired
	SlackMessageService slackMessageService;
	
	@Autowired
	UserService userService;
	
	private static Set<String> dates = new HashSet<String>();
	
	public void setSlackMessageService(SlackMessageService slackMessageService) {
		this.slackMessageService = slackMessageService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * This static block fills a set of dates with valid Strings in the format [yyyymmdd]
	 * for the next two years.
	 */
	static {
		@SuppressWarnings("deprecation")
		int currentYear = (new Date()).getYear() + 1900;
		for (int year = currentYear; year < currentYear + 2; year++) {
			for (int month = 1; month <= 12; month++) {
				for (int day = 1; day <= daysInMonth(year, month); day++) {
					StringBuilder date = new StringBuilder();
					date.append(String.format("%04d", year));
					date.append(String.format("%02d", month));
					date.append(String.format("%02d", day));
					dates.add(date.toString());
				}
			}
		}
	}
	
	/**
	 * Checks if a date is a valid date.  A valid date is one that is not prior to today's date
	 * @param String date which is being validated
	 * @return boolean True if the date is valid, false otherwise
	 */
	public boolean acceptDate(String date) {
		Date checkedDate = slackMessageService.createRideDate(date, "11", "59", "PM");
		Date today = new Date();
		boolean isBeforeToday = today.before(checkedDate);
		return isBeforeToday;
	}
	/**
	 * Checks if the user is in the database.
	 * @param String slackId
	 * @return User If user exists with slackId, otherwise null.
	 */
	public User isValidUser(String slackId) {
		return userService.getUserBySlackId(slackId);
	}
	public String isValidUserAndDate(String slackId,String text){
		//split the text parameters by space.
		String[] params = text.split(" ");
		String date = params[0];
		if(isValidUser(slackId)!=null){
			if(acceptDate(date)){
				return "ok";
			}else{
			return "That date has passed. Please select a date of today or later.";
			}
		}else{
			return "You have not permitted the slack application in slack or don't exist in our database."
					+ " Please log in to the application and permit our application to use slash commands.";
		}
	}
	
	/**
	 * Checks if the time the user chose has already passed.
	 * @param JsonNode payload
	 * @return boolean True if time has passed, false otherwise
	 */
	public boolean isPreviousTime(JsonNode payload) {
		SlackJSONBuilder message = slackMessageService.convertPayloadToSlackJSONBuilder(payload);
		ArrayList<String> strings = slackMessageService.getTextFields(message);
		String callbackId = payload.path("callback_id").asText();
		System.out.println(callbackId);
		Date today = new Date();
		boolean isFoundRequestOrRide=(callbackId.equals("foundRidesByMessage")||callbackId.equals("foundRequestsByMessage"));
		if(isFoundRequestOrRide){
			//bypass the check for messages which have already undergone validation of time fields
			return false;
		}
		// This string array contains six elements:
		// 		date (mm/dd), hour, minute, meridian, from POI, and to POI
		boolean isFindRequestOrRide=(callbackId.equals("findRidesMessage")||callbackId.equals("findRequestsMessage"));
		
		if(!isFindRequestOrRide){
				Date userDate = slackMessageService.createRideDate(strings.get(0), strings.get(1), strings.get(2), strings.get(3));
				if(userDate.before(today)){
					return true;
				}else{
					return false;
				}
		}else{
			Date startDate = slackMessageService.createRideDate(strings.get(0),strings.get(3),strings.get(4),strings.get(5));
			Date endDate = slackMessageService.createRideDate(strings.get(0),strings.get(6),strings.get(7),strings.get(8));
			System.out.println("Start\n"+startDate+"\nEnd\n"+endDate+"\nNow\n"+today);
			System.out.println(startDate.before(today));
			System.out.println(endDate.before(today));
			System.out.println(endDate.before(startDate));
			if(startDate.before(today)||endDate.before(today)||endDate.before(startDate)){
				return true;
			}else{
				return false;
			}	
		}
	}
	
	/**
	 * Check to see if the date that the user inputed is a valid date
	 * @param String dateString
	 * @return boolean True if the date is valid, false otherwise
	 */
	public static boolean isValidDate(String dateString) {
		return dates.contains(dateString);
	}
	
	/**
	 * Returns the number of days in the specified month.
	 * @param int year
	 * @param int month
	 * @return int Number of days in specified month.
	 */
	private static int daysInMonth(int year, int month) {
		int daysInMonth;
		switch (month) {
		case 1: // fall through
		case 3: // fall through
		case 5: // fall through
		case 7: // fall through
		case 8: // fall through
		case 10: // fall through
		case 12:
			daysInMonth = 31;
			break;
		case 2:
			if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
				daysInMonth = 29;
			} else {
				daysInMonth = 28;
			}
			break;
		default:
			// returns 30 even for nonexistent months 
			daysInMonth = 30;
		}
		return daysInMonth;
	}
}
