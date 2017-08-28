package com.revature.rideshare.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideshare.domain.User;

@RunWith(SpringRunner.class)
public class SlackFormatServiceTest {
	
	@Mock
	SlackMessageService slackMessageService;
	
	@Mock
	UserService userService;
	
	@InjectMocks
	SlackFormatService slackFormatService = new SlackFormatServiceImpl();
	
	@Test
	public void testDenyDateBefore() {
		testAcceptDate(getPreviousMonth(), false);
	}
	
	@Test
	public void testAcceptDateToday() {
		testAcceptDate(getThisMonth(), true);
	}
	
	@Test
	public void testAcceptDateAfter() {
		testAcceptDate(getNextMonth(), true);
	}
	
	@Test
	public void testIsValidUser() {
		String mockId = "42";
		User mockUser = new User();
		whenGetUserBySlackId(mockId, mockUser);
		User user = slackFormatService.isValidUser(mockId);
		verifyGetUserBySlackId(mockId, mockUser);
		assertTrue(user == mockUser);
	}
	
	@Test
	public void testIsValidUserNotNullAndDateBefore() {
		testIsValidUserAndDate(getPreviousMonth(), new User(), "That date has passed. Please select a date of today or later.");
	}
	
	@Test
	public void testIsValidUserNotNullAndDateToday() {
		testIsValidUserAndDate(getThisMonth(), new User(), "ok");
	}
	
	@Test
	public void testIsValidUserNotNullAndDateAfter() {
		testIsValidUserAndDate(getNextMonth(), new User(), "ok");
	}
	
	@Test
	public void testIsValidUserNullAndDateBefore() {
		testIsValidUserAndDate(getPreviousMonth(), null, "You have not permitted the slack application in slack or don't exist in our database."
				+ " Please log in to the application and permit our application to use slash commands.");
	}
	
	@Test
	public void testIsValidUserNullAndDateToday() {
		testIsValidUserAndDate(getThisMonth(), null, "You have not permitted the slack application in slack or don't exist in our database."
				+ " Please log in to the application and permit our application to use slash commands.");
	}
	
	@Test
	public void testIsValidUserNullAndDateAfter() {
		testIsValidUserAndDate(getNextMonth(), null, "You have not permitted the slack application in slack or don't exist in our database."
				+ " Please log in to the application and permit our application to use slash commands.");
	}
	
	private Calendar getNextMonth() {
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.MONTH, 1);
		return calendar;
	}
	
	private Calendar getThisMonth() {
		return new GregorianCalendar();
	}
	
	private Calendar getPreviousMonth() {
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.MONTH, -1);
		return calendar;
	}
	
	private String getDateString(Calendar calendar) {
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return month + "/" + day;
	}
	
	@SuppressWarnings("deprecation")
	private void whenCreateRideDate(Calendar calendar, String mockDate) {
		when(slackMessageService.createRideDate(Matchers.contains(mockDate),
				Matchers.contains("11"), Matchers.contains("59"), Matchers.contains("PM")))
				.thenReturn(new Date(calendar.get(Calendar.YEAR) - 1900,
						calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH),
						23, 59));
	}
	
	private void verifyCreateRideDate(Calendar calendar, String mockDate) {
		verify(slackMessageService, atLeastOnce()).createRideDate(Matchers.contains(mockDate),
				Matchers.contains("11"), Matchers.contains("59"), Matchers.contains("PM"));
	}
	
	private void whenGetUserBySlackId(String mockId, User mockUser) {
		when(userService.getUserBySlackId(Matchers.same(mockId))).thenReturn(mockUser);
	}
	
	private void verifyGetUserBySlackId(String mockId, User mockUser) {
		verify(userService, atLeastOnce()).getUserBySlackId(Matchers.same(mockId));
	}
	
	private void testAcceptDate(Calendar calendar, boolean validity) {
		String mockDate = getDateString(calendar);
		whenCreateRideDate(calendar, mockDate);
		boolean valid = slackFormatService.acceptDate(mockDate);
		verifyCreateRideDate(calendar, mockDate);
		
		if (validity) {
			assertTrue(valid);
		} else {
			assertFalse(valid);
		}
	}
	
	private void testIsValidUserAndDate(Calendar calendar, User mockUser, String validMessage) {
		String mockDate = getDateString(calendar);
		String mockId = "42";
		
		whenCreateRideDate(calendar, mockDate);
		whenGetUserBySlackId(mockId, mockUser);
		String message = slackFormatService.isValidUserAndDate(mockId, mockDate);
		
		assertTrue(message.equals(validMessage));
	}
}
