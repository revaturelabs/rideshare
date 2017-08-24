package com.revature.rideshare.web;


import java.io.UnsupportedEncodingException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.revature.rideshare.service.PointOfInterestService;
import com.revature.rideshare.service.SlackFormatService;
import com.revature.rideshare.service.SlackService;

@RestController
@RequestMapping("slack")
public class SlackController {

	/**
	 * creates an instance of the poiService
	 */
	@Autowired
	private PointOfInterestService poiService;

	/**
	 * Uses a factory and Reflection to build a logger aspect for logging what happens with our database.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * Creates an instance of the slackService
	 */
	@Autowired
	private SlackService slackService;

	@Autowired
	private SlackFormatService slackFormatService;
	
	//sets the slack format service (checks dates/users)
	public void setSlackFormatService(SlackFormatService slackFormatService) {
		this.slackFormatService = slackFormatService;
	}

	//set the slack service
	public void setSlackService(SlackService slackService) {
		this.slackService = slackService;
	}

	//set the poi service
	public void setPoiService(PointOfInterestService poiService) {
		this.poiService = poiService;
	}
	/**
	 * Allows passengers to find rides they can join which fit their criteria.
	 * @param String userId
	 * @param String responseUrl
	 * @param String text
	 * @param String request
	 * @throws UnsupportedEncodingException
	 */
	@PostMapping("/findRides")
	public void sendFindRidesMessage(@RequestParam(name = "user_id") String userId, @RequestParam(name = "response_url") String responseUrl, @RequestParam String text, @RequestBody String request){
		RestTemplate restTemplate = new RestTemplate();
		String confirmationMessage = slackFormatService.isValidUserAndDate(userId,text);
		if(!confirmationMessage.equals("ok")){
			restTemplate.postForLocation(responseUrl,"{\"replace_original\":\"true\",\"text\":\""+confirmationMessage+"\"}");
		}else{
			String requestMessage = slackService.findRidesMessage(userId, text);
			restTemplate.postForLocation(responseUrl, requestMessage);
		}
	}
	/**
	 * --Not Implemented, endpoint is placed here for clarity of naming convention for future iterations.--
	 * Allows user to search for passengers going to/from a desired POI.
	 * @param userId
	 * @param responseUrl
	 * @param text
	 * @param request
	 * @throws UnsupportedEncodingException
	 */
	@PostMapping("/findRequests")
	public void sendFindRequestsMessage(@RequestParam(name = "user_id") String userId, @RequestParam(name = "response_url") String responseUrl, @RequestParam String text, @RequestBody String request) throws UnsupportedEncodingException{
	}
	
	/**
	 * This method will decode a request to make a ride, construct a ride message with the necessary fields
	 * to make a ride, and send back the options to the user on slack.
	 * @param userId
	 * @param responseUrl
	 * @param text
	 * @param request
	 * @throws UnsupportedEncodingException
	 */
	@PostMapping("/newride")
	public void sendRideMessage(@RequestParam(name = "user_id") String userId, @RequestParam(name = "response_url") String responseUrl, @RequestParam String text, @RequestBody String request) throws UnsupportedEncodingException{
		RestTemplate restTemplate = new RestTemplate();
		String confirmationMessage = slackFormatService.isValidUserAndDate(userId,text);
		if(!confirmationMessage.equals("ok")){
			restTemplate.postForLocation(responseUrl,"{\"replace_original\":\"true\",\"text\":\""+confirmationMessage+"\"}");
		}else{
			String requestMessage = slackService.newRideMessage(userId, text);
			restTemplate.postForLocation(responseUrl, requestMessage);
		}
	}

	/**
	 * This method will decode a sent request to ask for a ride, construct  a request message with
	 * the necessary fields to construct a ride, and send those options back to the user on slack.
	 *
	 * @param String userId
	 * @param String responseUrl
	 * @param String text
	 * @param String request
	 * @throws UnsupportedEncodingException
	 */
	@PostMapping("/newrequest")
	public void sendRequestMessage(@RequestParam(name = "user_id") String userId, @RequestParam(name = "response_url") String responseUrl, @RequestParam String text, @RequestBody String request) throws UnsupportedEncodingException{
		RestTemplate restTemplate = new RestTemplate();
		String confirmationMessage = slackFormatService.isValidUserAndDate(userId,text);
		if(!confirmationMessage.equals("ok")){
			restTemplate.postForLocation(responseUrl,"{\"replace_original\":\"true\",\"text\":\""+confirmationMessage+"\"}");
		}else{
			String requestMessage = slackService.newRequestMessage(userId, text);
			restTemplate.postForLocation(responseUrl, requestMessage);
		}
	}
	/**
	 * --Not currently in use.--<br>
	 * Checks to see when slack controller is being accessed.
	 */
	@GetMapping("/check")
	public void getCheck(){
		System.out.println("in slack controller");
	}


	/**
	 * All interactive messages send post request to this endpoint.
	 * @param String request
	 * @throws UnsupportedEncodingException
	 */
	@PostMapping("/postcheck")
	public void postCheck(@RequestBody String request){
		JsonNode payload = slackService.convertMessageRequestToPayload(request);
		RestTemplate restTemplate = new RestTemplate();
		int attachId = payload.path("attachment_id").asInt() - 1;
		String type = payload.path("actions").path(0).path("type").asText();
		String callbackId = payload.path("callback_id").asText();
		//tracks when a user selects a dropdown option.
		if (type.equals("select")) {
			//pulls non-delimited text of value
			String selectedValue = payload.path("actions").path(0).path("selected_options").path(0).path("value").asText();
			//separates value into action position and actual value
			String[] positionValue = selectedValue.split("-");
			//action position
			int position = Integer.parseInt(positionValue[0]);
			//actual value
			String value = positionValue[1];
			
			JsonNode originalMessage = payload.path("original_message");
			((ObjectNode)originalMessage.path("attachments").path(attachId).path("actions").path(position)).put("text", value);
	        String messageurl = payload.path("response_url").asText();
	        restTemplate.postForLocation(messageurl, originalMessage.toString());
		}
		//tracks when a user selects a button.
		else if (type.equals("button")){
			String value = payload.path("actions").path(0).path("value").asText();
			String messageurl = payload.path("response_url").asText();
			if (value.equals("okay")) {
				boolean acceptRequest = slackService.isMessageActionable(payload);

				//if request is accepted, send the user the confirmation message. Else, do nothing
				if (acceptRequest){
					if (slackFormatService.isPreviousTime(payload)) {
						String error = "The time you have entered has already passed";
						restTemplate.postForLocation(messageurl,"{\"replace_original\":\"true\",\"text\":\"" + error + "\"}");
					}
					else {
						boolean isMessageEndOfBranch =slackService.isMessageEndOfBranch(callbackId);
						String confirmationMessage=slackService.handleMessage(payload);
						System.out.println(isMessageEndOfBranch);
						if(isMessageEndOfBranch){
							restTemplate.postForLocation(messageurl,"{\"replace_original\":\"true\",\"text\":\""+confirmationMessage+"\"}");
						}else{
							restTemplate.postForLocation(messageurl,confirmationMessage);
						}
					}
				}
				else {
					logger.debug("User did not completely fill out fields. Response was not sent.");;
				}
			}
			//if cancel is clicked, message sent saying the ride was canceled.
			else if (value.equals("cancel")) {
				restTemplate.postForLocation(messageurl, "{\"replace_original\":\"true\",\"text\":\"Your ride request has been cancelled\"}");
			}
		}
	}

}
