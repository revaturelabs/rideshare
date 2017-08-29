package com.revature.rideshare.service;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

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
		System.out.println(TestString);

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
			//Fails if an exception is thrown.
			fail();
		}
		List<String> comparisonString = new ArrayList<String>();
		System.out.println(TestString);

		Action dummyAction = getDummyAction();

		comparisonString.add("8/29");

		comparisonString.add(dummyAction.getText());

		//Fails if the wrong number of results is found.
		
		assert (TestString.size() == comparisonString.size());

		for (int i = 0; i < comparisonString.size(); i++) {
			//Fails if any result does not match the expected result.
			assert (TestString.get(i).equals(comparisonString.get(i)));
		}

	}

}