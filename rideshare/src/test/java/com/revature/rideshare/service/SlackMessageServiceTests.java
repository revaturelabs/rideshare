package com.revature.rideshare.service;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideshare.domain.AvailableRide;
import com.revature.rideshare.domain.PointOfInterest;
import com.revature.rideshare.json.Action;
import com.revature.rideshare.json.Attachment;
import com.revature.rideshare.json.Option;
import com.revature.rideshare.json.SlackJSONBuilder;

@RunWith(SpringRunner.class)
public class SlackMessageServiceTests {

	@Mock
	PointOfInterestService poiService;

	@Mock
	RideService rideService;

	@Mock
	SlackActionServiceRideShare slackActionService;

	@InjectMocks
	SlackMessageService slackMessageService = new SlackMessageServiceImpl();

	List<Option> getToFromOptions() {
		List<Option> toFromOptions = new ArrayList<Option>();

		Option toOption = new Option("To", "To");

		Option fromOption = new Option("From", "From");

		toFromOptions.add(toOption);

		toFromOptions.add(fromOption);

		return toFromOptions;

	}

	List<PointOfInterest> getMockPoiList() {
		List<PointOfInterest> poiList = new ArrayList<PointOfInterest>();
		return poiList;
	}

	List<Option> getPoiOptions() {
		List<PointOfInterest> pois = getMockPoiList();
		List<Option> poiOptions = new ArrayList<Option>();
		for (PointOfInterest poi : pois) {
			Option o = new Option(poi.getPoiName(), poi.getPoiName());
			poiOptions.add(o);
		}

		return poiOptions;

	}

	String getSlackJson(String channel, String text, List<Attachment> attachments)
			throws JsonGenerationException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		String attachmentJSON = null;
		attachmentJSON = mapper.writeValueAsString(attachments);
		attachmentJSON = "\"attachments\" : " + attachmentJSON;
		String message = "{ \"channel\" : \"" + channel + "\", \"text\" : \"" + text + "\", " + attachmentJSON + " }";
		return message;
	}

	JsonNode getSlackJsonNode(String channel, String text, List<Attachment> attachments)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String message = getSlackJson(channel, text, attachments);
		String messagepayload = "{ \"original_message\": " + message + " }";
		return mapper.readValue(messagepayload, ObjectNode.class);
	}

	SlackJSONBuilder getSlackJsonBuilder(String channel, String text, List<Attachment> attachments)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String message = getSlackJson(channel, text, attachments);
		return mapper.readValue(message, SlackJSONBuilder.class);
	}

	Option getDummyOption(String textvalue) {

		Option out = new Option();

		out.setText(textvalue);
		out.setValue(textvalue);

		return out;

	}

	List<Option> getDummyOptionList() {
		List<Option> out = new ArrayList<Option>();

		out.add(getDummyOption("Red"));
		out.add(getDummyOption("Green"));

		return out;
	}

	Action getDummyAction() {
		Action out = new Action();

		out.setText("Green");

		out.setType("select");

		out.setName("Laser Color");

		out.setOptions(getDummyOptionList());

		return out;
	}

	List<Action> getDummyActionList() {
		List<Action> out = new ArrayList<Action>();

		out.add(getDummyAction());

		return out;
	}

	Attachment getDummyAttachment(String text) {
		Attachment out = new Attachment();
		out.setText(text);
		out.setActions(getDummyActionList());
		return out;
	}

	List<Attachment> getDummyAttachmentList() {
		List<Attachment> attachments = new ArrayList<Attachment>();

		attachments.add(getDummyAttachment("Blaster"));

		return attachments;
	}

	@Test
	public void testCreatePoiSelectDestinationAttachment() {

		// String callbackID = "findRidesMessage";
		String callbackID = "SomeCallbackID";

		List<PointOfInterest> poiList = getMockPoiList();

		when(slackActionService.getPOIListAction(Matchers.any())).thenCallRealMethod();

		when(slackActionService.getToFromAction()).thenCallRealMethod();

		when(poiService.getAll()).thenReturn(poiList);

		Attachment testAttachment = slackMessageService.createPoiSelectDestinationAttachment(callbackID);

		// Fails if the attachment's callbackID is not properly set based off
		// input.
		assert (testAttachment.getCallback_id().equals(callbackID));

		List<Action> testActions = testAttachment.getActions();

		// Fails if the number of actions in the attachment is not exactly two.
		assert (testActions.size() == 2);

		List<Option> toFromOptions = getToFromOptions();

		Action toFromAction = new Action("To/From", "To/From", "select", toFromOptions);

		List<Option> poiOptions = getPoiOptions();

		Action poiAction = new Action("POI", "Pick a destination", "select", poiOptions);

		// Fails if the list does not contain the To/From action;
		assert (testActions.contains(toFromAction));

		// Fails if the list does not contain the Action containing the list of
		// POIs
		assert (testActions.contains(poiAction));

		// Fails if
		// slackMessageService.createPoiSelectDestinationAttachment(String) does
		// not query the POI service for points of interest.
		verify(poiService, atLeastOnce()).getAll();
	}

	@Test
	public void testCreateSeatsAttachment() {

		String callbackId = "SomeOtherCallbackID";

		when(slackActionService.getCreateSeatsAction()).thenCallRealMethod();

		Attachment testAttachment = slackMessageService.createSeatsAttachment(callbackId);

		// Fails if the attachment's callbackID is not properly set based off of
		// input.
		assert (testAttachment.getCallback_id().equals(callbackId));

		// Fails if the attachment lacks the correct number of actions
		assert (testAttachment.getActions().size() == 1);

		// Fails if the only action does not contain the correct number of
		// options.
		assert (testAttachment.getActions().get(0).getOptions().size() == SlackMessageServiceImpl.MAX_NUMBER_SEATS);

	}

	@Test
	public void testConvertMessageStringToSlackJSONBuilder() {

		ObjectMapper mapper = new ObjectMapper();

		String currentMessage = "{ \"channel\":\"Testing Channel\" }";

		SlackJSONBuilder jsonBuilder = slackMessageService.convertMessageStringToSlackJSONBuilder(currentMessage);

		SlackJSONBuilder cMessage = null;

		try {
			cMessage = mapper.readValue(currentMessage, SlackJSONBuilder.class);
		} catch (IOException e) {
			// Failed because ObjectMapper failed to operate on
			// SlackJSONBuilder.class.
			fail();
		}

		assert (jsonBuilder.equals(cMessage));

	}

	@Test
	public void testConvertPayloadToSlackJSONBuilder() {
		JsonNode TestNode = null;
		SlackJSONBuilder exampleJSONBuilder = null;
		try {
			TestNode = getSlackJsonNode("Test Channel", "Test Text", new ArrayList<Attachment>());
		} catch (IOException e) {
			fail();
		}

		try {
			exampleJSONBuilder = getSlackJsonBuilder("Test Channel", "Test Text", new ArrayList<Attachment>());
		} catch (IOException e) {
			fail();
		}

		SlackJSONBuilder slackJSONBuilder = slackMessageService.convertPayloadToSlackJSONBuilder(TestNode);

		assert (exampleJSONBuilder.equals(slackJSONBuilder));

	}

	@Test
	public void testStringToDate() {
		String date;

		date = slackMessageService.getDateFromText("The date I am testing is: 11/11");

		if (!date.equals("11/11")) {
			// Can't parse a date?
			fail();
		}

		date = slackMessageService
				.getDateFromText("Alright let's say stuff is gonna go down on 08/09 in the year 20xx");
		if (!date.equals("08/09")) {
			// Can only parse a date at the end of a string?
			// TODO: Make this fail
		}
	}

	@Test
	public void testgetTextFieldsSlackJsonBuilder() {
		List<String> TestString = null;
		List<Attachment> attachments = getDummyAttachmentList();
		try {
			TestString = slackMessageService
					.getTextFields(getSlackJsonBuilder("Test Channel", "Test 8/29", attachments));
		} catch (IOException e) {
			fail();
		}
		List<String> comparisonString = new ArrayList<String>();

		Action dummyAction = getDummyAction();

		comparisonString.add("8/29");

		comparisonString.add(dummyAction.getText());

		assert (TestString.size() == comparisonString.size());

		for (int i = 0; i < comparisonString.size(); i++) {
			assert (TestString.get(i).equals(comparisonString.get(i)));
		}

	}

	@Test
	public void testgetTextFieldsJsonNode() {
		// NOTE: THIS TEST RELIES ON THE FUNCTION TESTED BY THE ABOVE FUNCTION.
		// IF THE ABOVE TEST FAILS THIS TEST WILL FAIL BY EXTENSION.
		List<String> TestString = null;
		List<Attachment> attachments = getDummyAttachmentList();
		try {
			TestString = slackMessageService.getTextFields(getSlackJsonNode("Test Channel", "Test 8/29", attachments));
		} catch (IOException e) {
			// Fails if an exception is thrown.
			fail();
		}
		List<String> comparisonString = new ArrayList<String>();

		Action dummyAction = getDummyAction();

		comparisonString.add("8/29");

		comparisonString.add(dummyAction.getText());

		// Fails if the wrong number of results is found.

		assert (TestString.size() == comparisonString.size());

		for (int i = 0; i < comparisonString.size(); i++) {
			// Fails if any result does not match the expected result.
			assert (TestString.get(i).equals(comparisonString.get(i)));
		}

	}

	Attachment createDummyAvailableRidesAttachment(String callbackID) {
		Attachment comparisonAttachment = new Attachment();

		comparisonAttachment.setText("Available Rides");

		comparisonAttachment.setFallback("Unable to display available rides");

		comparisonAttachment.setCallback_id(callbackID);

		comparisonAttachment.setColor("#3AA3E3");

		comparisonAttachment.setAttachment_type("default");

		Action comparisonAction = new Action();

		comparisonAction.setName("AvailableRides");

		comparisonAction.setText("Select from the following rides");

		comparisonAction.setType("select");

		comparisonAction.setValue(null);

		Option comparisonOption = new Option();

		comparisonOption.setText("10:45AM > ID:0");

		comparisonOption.setValue("10:45AM > ID:0");

		List<Action> comparisonActionList = new ArrayList<Action>();

		List<Option> comparisonOptionList = new ArrayList<Option>();

		comparisonActionList.add(comparisonAction);

		comparisonOptionList.add(comparisonOption);

		comparisonAttachment.setActions(comparisonActionList);

		comparisonAction.setOptions(comparisonOptionList);

		return comparisonAttachment;

	}

	@Test
	@SuppressWarnings("deprecation")
	public void testCreateAvailableRidesAttachment() {

		Date starttime = new Date(11, 11, 11, 10, 45);
		Date endtime = new Date(11, 11, 11, 11, 30);

		String filter = "";

		String poiName = "Ivory Tower";

		String callbackId = "Call Me Back Please";

		PointOfInterest testPoi = new PointOfInterest();

		AvailableRide testRide = new AvailableRide();

		testPoi.setPoiName(poiName);

		testRide.setDropoffPOI(testPoi);

		testRide.setPickupPOI(testPoi);

		testRide.setOpen(true);

		testRide.setTime(starttime);

		List<AvailableRide> testRides = new ArrayList<AvailableRide>();

		testRides.add(testRide);

		when(poiService.getPoi(Matchers.matches(poiName))).thenReturn(testPoi);

		when(rideService.getAvailableRidesByTime(Matchers.same(starttime), Matchers.same(endtime)))
				.thenReturn(testRides);

		when(slackActionService.getCreateAvailableRidesAction(Matchers.anyList())).thenCallRealMethod();

		Attachment Output = slackMessageService.createAvailableRidesAttachment(starttime, endtime, filter, poiName,
				callbackId);

		assert (Output != null);

		Attachment comparisonAttachment = createDummyAvailableRidesAttachment(callbackId);

		System.out.println(rideService.getAvailableRidesByTime(starttime, endtime));

		assert (Output.equals(comparisonAttachment));

		comparisonAttachment = createDummyAvailableRidesAttachment("This is not the Callback ID");

		assert (!Output.equals(comparisonAttachment));

	}

	Attachment createDummyTimeAttachment(String callbackID) {
		Attachment comparisonAttachment = new Attachment();

		comparisonAttachment.setText("Available Rides");

		comparisonAttachment.setFallback("Unable to display available rides");

		comparisonAttachment.setCallback_id(callbackID);

		comparisonAttachment.setColor("#3AA3E3");

		comparisonAttachment.setAttachment_type("default");

		Action comparisonAction = new Action();

		comparisonAction.setName("AvailableRides");

		comparisonAction.setText("Select from the following rides");

		comparisonAction.setType("select");

		comparisonAction.setValue(null);

		Option comparisonOption = new Option();

		comparisonOption.setText("10:45AM > ID:0");

		comparisonOption.setValue("10:45AM > ID:0");

		List<Action> comparisonActionList = new ArrayList<Action>();

		List<Option> comparisonOptionList = new ArrayList<Option>();

		comparisonActionList.add(comparisonAction);

		comparisonOptionList.add(comparisonOption);

		comparisonAttachment.setActions(comparisonActionList);

		comparisonAction.setOptions(comparisonOptionList);

		return comparisonAttachment;

	}

	@Test
	public void testCreateTimeAttachment() {

		String CallbackID = "Ring Ring Ring Ring...";

		when(slackActionService.getCreateHoursAction()).thenCallRealMethod();

		when(slackActionService.getCreateMinutesAction()).thenCallRealMethod();

		when(slackActionService.getCreateMeridianAction()).thenCallRealMethod();

		Attachment timeAttachment = slackMessageService.createTimeAttachment(CallbackID);

		// First option should be hours, twelve options..
		assert (timeAttachment.getActions().get(0).getOptions().size() == 12);
		// Second option should be minudes in divisions of 15, so four options.
		assert (timeAttachment.getActions().get(1).getOptions().size() == 4);
		// Third option should be AM/PM, two options.
		assert (timeAttachment.getActions().get(2).getOptions().size() == 2);

		assert (timeAttachment.getCallback_id().equals(CallbackID));

	}

	@Test
	public void testCreateConfirmationButtonsAttachment() {

		String CallbackID = "Please Confirm!";

		Attachment confirmAttachment = slackMessageService.createConfirmationButtonsAttachment(CallbackID);

		assert (confirmAttachment.getCallback_id().equals(CallbackID));

		assert (confirmAttachment.getActions().get(0).getName().equals("OKAY"));

		assert (confirmAttachment.getActions().get(0).getType().equals("button"));

		assert (confirmAttachment.getActions().get(1).getName().equals("cancel"));

		assert (confirmAttachment.getActions().get(1).getType().equals("button"));

	}

	@Test
	public void testCreatePOIAttachment() {

		String callbackID = "eight six seven five three oh nine";

		String text = "gimme da pois";

		List<PointOfInterest> poiList = getMockPoiList();

		when(poiService.getAll()).thenReturn(poiList);

		Attachment poiAttachment = slackMessageService.createPOIAttachment(text, callbackID);

		assert (poiAttachment.getText().equals(text));

		// TODO: Make this assert active.

		// assert (poiAttachment.getCallback_id().equals(callbackID));

		assert (poiAttachment.getActions().get(0).getOptions().size() == poiList.size());

		verify(poiService, atLeastOnce()).getAll();

	}

	@Test
	public void testGetUserID() {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode testnode = null;
		try {
			testnode = mapper.readValue("{ \"user\" : { \"id\" : \"0\" } } ", ObjectNode.class);
		} catch (IOException e) {
			fail();
		}
		String response = slackMessageService.getUserId(testnode);
		assert (response.equals("0"));
	}

	@Test
	public void testGetMessageURL() {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode testnode = null;
		try {
			testnode = mapper.readValue("{ \"response_url\" : \"some_url\" } ", ObjectNode.class);
		} catch (IOException e) {
			fail();
		}
		String response = slackMessageService.getMessageUrl(testnode);
		assert (response.equals("some_url"));

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testCreateRideData() {

		Date testDate = slackMessageService.createRideDate("08/09", "11", "45", "AM");

		assert (testDate.getDate() == 9);

		// The month is offset by 1 because January is 0
		assert (testDate.getMonth() == 7);

		assert (testDate.getHours() == 11);

		assert (testDate.getMinutes() == 45);
	}

	public void testTemplateCanBeBuiltFromPayload() {
		assert (!slackMessageService.templateCanBeBuiltFromPayload("failme"));
		assert (slackMessageService.templateCanBeBuiltFromPayload("foundRidesByMessage"));
		assert (slackMessageService.templateCanBeBuiltFromPayload("foundRequestsByMessage"));
	}

}