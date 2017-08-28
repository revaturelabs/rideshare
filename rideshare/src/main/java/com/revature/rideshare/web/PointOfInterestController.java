package com.revature.rideshare.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.rideshare.domain.PointOfInterest;
import com.revature.rideshare.domain.PointOfInterestType;
import com.revature.rideshare.service.PointOfInterestService;

@RestController
@RequestMapping("poiController")
public class PointOfInterestController {

	@Autowired
	private PointOfInterestService poiService;

	/**
	 * Retrieve all Points of Interest. 
	 * 
	 * @return List of all PointOfInterest Objects
	 */
	@GetMapping
	public List<PointOfInterest> getAll() {
		return poiService.getAll();
	}

	/**
	 * Retrieve all possible types a Point of Interest can be categorized into. 
	 * 
	 * @return List of all PointOfInterestType Objects 
	 */
	@GetMapping("/type")
	public List<PointOfInterestType> getAllTypes() {
		return poiService.getAllTypes();
	}

	/**
	 * Adds a new PointOfInterest based on the JSON representation sent in the request. 
	 * 
	 * @param jsonPoi - String representation of a PointOfInterest object in JSON format
	 */
	@PostMapping("/addPoi")
	public void addPoi(@RequestBody String jsonPoi) {
		poiService.addPoi(getPoi(jsonPoi));
	}

	/**
	 * Removes the PointOfInterest based on the JSON representation sent in the request. 
	 * 
	 * @param jsonPoi - String representation of a PointOfInterest object in JSON format
	 */
	@PostMapping("/removePoi")
	public void removePoi(@RequestBody String jsonPoi) {
		poiService.removePoi(getPoi(jsonPoi));
	}

	/**
	 * Updates an existing Point of Interest's information. 
	 * 
	 * @param poi - the PointOfInterest object to be updated
	 */
	@PostMapping("/updatePoi")
	public void updatePoi(@RequestBody PointOfInterest poi) {
		poiService.updatePoi(poi);
	}

	/**
	 * Unmarshalls the JSON string to a PointofInterest Object. 
	 * 
	 * @param jsonString - JSON representation of a PointOfInterest Object
	 * @return the PointofInterest Object represented by the JSON string parameter
	 */
	public PointOfInterest getPoi(String jsonString) {
		try {
			return (PointOfInterest) new ObjectMapper().readValue(jsonString, PointOfInterest.class);
		} catch (Exception e) {
			return null;
		}
	}
}
