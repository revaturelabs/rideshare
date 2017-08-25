package com.revature.rideshare.util;

public class EnviromentalVariables {

	public final static String  JWTSecret = "#{systemEnvironment['RIDESHARE_JWT_SECRET']}";
	public final static String  slackID = "#{systemEnvironment['RIDESHARE_SLACK_ID']}";
	public final static String  slackSecret = "#{systemEnvironment['RIDESHARE_SLACK_SECRET']}";
	public final static String  slackVerify = "#{systemEnvironment['RIDESHARE_SLACK_VERIFICATION']}";
	public final static String  slackTeam = "#{systemEnvironment['RIDESHARE_SLACK_TEAM']}";
}
