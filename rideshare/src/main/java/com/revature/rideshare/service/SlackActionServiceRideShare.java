package com.revature.rideshare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.domain.AvailableRide;
import com.revature.rideshare.domain.PointOfInterest;
import com.revature.rideshare.json.Action;

@Service("slackActionService")
public class SlackActionServiceRideShare implements SlackActionService {

	@Autowired
	PointOfInterestService poiService;

	@Override
	public Action getAllPOIAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action getPOIListAction(List<PointOfInterest> POIs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action getToFromAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action getCreateSeatsAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action getCreateAvailableRidesAction(List<AvailableRide> availableRides) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action getCreateHoursAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action getCreateMinutesAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action getCreateMeridianAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action getCreateOKAYAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action getCreateCancelAction() {
		// TODO Auto-generated method stub
		return null;
	}

}
