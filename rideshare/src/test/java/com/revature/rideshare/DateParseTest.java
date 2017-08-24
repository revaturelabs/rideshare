package com.revature.rideshare;

import java.time.LocalDate;
import java.util.Date;

public class DateParseTest {

	public static void main(String[] args) {
		String dateInput = "07/22 12:00";
		String delim = " ";
		String[] tokens = dateInput.split(delim);
		// Split the variables for the Date Object
		String[] dateTokens = tokens[0].split("/");
		String[] timeTokens = tokens[1].split(":");
		Date rideDate = new Date(LocalDate.now().getYear()-1900, Integer.parseInt(dateTokens[0])-1,
				Integer.parseInt(dateTokens[1]), Integer.parseInt(timeTokens[0]), Integer.parseInt(timeTokens[1]));

		System.out.println(rideDate);

	}

}
