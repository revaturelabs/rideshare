export let passengerController = function($scope, $http, $state, $location){
	$scope.activeRequests = [];
	// global variables
	let user;
	let poiLimit = 0;
	
	$scope.updateSort = function (item){
		$http.get("/ride/offer/open/"+(item.poiId))
		.then(function(response) {
			$scope.openOffer = response.data;
		});
	}

	$http.get("user/me").then(function(response){
		// get current user
		user = response.data;
		$scope.user = response.data;
	})
	.then(function(){
		$http.get('poiController').then(function(response){
			let allPOI = response.data;
			let userMainPOI;
			$scope.allMainPOI = allPOI;

			if($scope.user.mainPOI != null) {
				$scope.selectedItem = allPOI[$scope.user.mainPOI.poiId-1];
				$scope.updateSort($scope.user.mainPOI);
			} else {
				$scope.selectedItem = $scope.allPoi[0];
				$scope.updateSort($scope.allPoi[0]);
			}
			
			// check if the user main POI is null
			if($scope.user.mainPOI == null){
				// if null set the default coordinates to 1st address in the
				// database
				userMainPOI = {lat: allPOI[0].latitude, lng: allPOI[0].longitude};
			}else{
				// get the current user main POI
				userMainPOI = {lat: $scope.user.mainPOI.latitude, lng: $scope.user.mainPOI.longitude};
			}

			// create markers for all the current POI
			let locations = [];
			for(let x = 0; x < response.data.length; x++){
				let temp = {lat: allPOI[x].latitude, lng: allPOI[x].longitude};
				locations.push(temp);
			};
			
			// used to initialize the google map
			function initMap() {
				let directionsDisplay = new google.maps.DirectionsRenderer();
				let directionsService = new google.maps.DirectionsService();
				
				var map = new google.maps.Map(document.getElementById('map'), {
					zoom: 15,
					center: userMainPOI,
					disableDefaultUI: true
				});

				// Add some markers to the map.
				// Note: The code uses the JavaScript Array.prototype.map()
				// method to create an array of markers based on a given
				// "locations" array. The map() method here has nothing
				// to do with the Google Maps API.
				var markers = locations.map(function(location, i) {
					return new google.maps.Marker({
						position: location
					});
				});
				
				
				
				for(let x = 0; x < markers.length; x++){
					let id = x+1;
					document.getElementById("mapText").innerHTML = 'Choose Start Point';
					
					// add event listener to each marker on the map
					markers[x].addListener('click',function(){
						// set each ng-selected value to false
						for(let x = 0; x<markers.length; x++){
							let temp1 = 'start' + id;
							let temp2 = 'destination' + id;
							
							$scope[temp1] = false;
							$scope[temp2] = false;
						}
						
						if(poiLimit === 1){
							markers[x].setIcon('http://earth.google.com/images/kml-icons/track-directional/track-8.png');
							
							// Remove blue markers and text once route shown
							$scope.clearMapMarkers();
							document.getElementById("mapText").innerHTML = '';
							
							let temp2 = 'destination' + id;
							$scope[temp2] = true;
							$scope.$apply();
							poiLimit = 2;

							$scope.showDirections();
						}
						
						if(poiLimit === 0){
							markers[x].setIcon('http://earth.google.com/images/kml-icons/track-directional/track-8.png');
							document.getElementById("mapText").innerHTML = 'Choose Destination';
							
							let temp1 = 'start' + id;
							$scope[temp1] = true;
							$scope.$apply();
							poiLimit = 1;
						}
						
					}, false);

				}
				
				// remove push pins from map, by setting the markers to default
				$scope.clearMapMarkers = function() {
					poiLimit = 0;
					directionsDisplay.setMap(null);
					document.getElementById("mapText").innerHTML = 'Choose Start Point';
				
					for(let x = 0; x < markers.length; x++){
						markers[x].setIcon();
					}
				};
				
				//show the current route from start to destination
				$scope.showDirections = function() {
					
					//get the current drop down options id
					let select1 = document.getElementById("fromPOI");
					let start = select1.options[select1.selectedIndex].id;
					
					let select2 = document.getElementById("toPOI");
					let destination = select2.options[select2.selectedIndex].id;
					
					directionsDisplay.setMap(map);

					let request = {
							//get the longitude and latitude to match the selected poi
							origin: {lat: allPOI[start].latitude, lng: allPOI[start].longitude},
							destination: {lat: allPOI[destination].latitude, lng: allPOI[destination].longitude},
							travelMode: 'DRIVING'
					}
					
					//use google map api to show the current route
					directionsService.route(request, function(result, status){
						directionsDisplay.setDirections(result);
					});
				};

				// Add a marker cluster to manage the markers.
				var markerCluster = new MarkerClusterer(map, markers,
						{imagePath: '../js/googleMapAPI/m'});

			}

			// initialize the google map
			initMap();
		});
		
	});

	
	
	// scope and function used to pass ride data to front end
	$scope.isArray = angular.isArray;
	$scope.rides = {};
	
	
	
	
	$http.get("/ride")
	.then(function(response) {
		$scope.rides = response.data;
		return $http.get("/poiController");
	})
	.then(function(response) {
		$scope.allPoi = response.data;
		return $http.get("/user/me");
	})
	.then(function(response){
		if(response.data.mainPOI != null) {
			$scope.selectedItem = $scope.allPoi[response.data.mainPOI.poiId-1];
		} else {
			$scope.selectedItem = $scope.allPoi[0];
		}
	});
		

	// Setting mPOI in case a user does not have a mPOI.
	$scope.poiId = {poiId : 1};

	// Setting to empty arrays for correct ng-repeat processing.
	$scope.openOffer = [];
	$scope.activeRides = [];
	$scope.pastRides = [];
	
	// show open requests from a poi
	$http.get("/ride/offer/open/"+1)
	.then(function(response) {
		$scope.openOffer = response.data;
	});

	$http.get("/ride/request/active")
	.then(function(response){
		$scope.activeRides = response.data;
		console.log(response.data);
	});

	$http.get("/ride/request/history")
	.then(function(response){
		$scope.pastRides = response.data;
	});

	// accept open offers
	$scope.acceptOffer = function(id){
		$http.get("/ride/offer/accept/"+id)
		.then(function(response) {
		});

		setTimeout(function(){$state.reload();}, 500);
	}

	$scope.cancelRide = function(rideId) {
		$http.get('/ride/request/cancelRide/' + rideId).then(
			(response) => {
				for(let i = 0; i < $scope.activeRides.length; i++){
					if($scope.activeRides[i].availRide.availRideId == rideId) {
						$scope.activeRides.splice(i, 1);
						$scope.$apply;
					}
				}
				setTimeout(function(){$state.reload();}, 500);
			}
		);
	};
	$scope.date = new Date().getTime();
	$scope.completeRide = function(rideId) {
		$http.post('/ride/request/complete/' + rideId).then((response) => {
			for(let i = 0; i < $scope.activeRides.length; i++){
				if($scope.activeRides[i].rideId == rideId) {
					$scope.activeRides.splice(i, 1);
					$scope.$apply;
				}
			}
			setTimeout(function(){$state.reload();}, 500);
		});
	};
	
	$scope.addRequest = function(pickup,dropoff,notes,time) {

		$scope.newRequest = {};

		let select1 = document.getElementById("fromPOI");
		let start = $scope.allMainPOI[select1.options[select1.selectedIndex].id];

		let select2 = document.getElementById("toPOI");
		let destination = $scope.allMainPOI[select2.options[select2.selectedIndex].id];

		$scope.newRequest.pickupLocation = start;
		$scope.newRequest.dropOffLocation = destination;

		if(notes == undefined || notes == "") {
			notes = "N/A";
		}

		$scope.newRequest.notes = notes;
		$scope.newRequest.time = new Date(time);
		$scope.newRequest.status = 'OPEN';
		$scope.newRequest.user = user;
		if(pickup == dropoff){
			$scope.$parent.sameStartEnd = true;
			console.log("PASSENGER: You chose the same two points WHYYYY");
		}else{
			$http.post('/ride/request/add', $scope.newRequest).then(
				(formResponse) => {
					setTimeout(function(){$state.reload();}, 500);
				},
				(failedResponse) => {
					alert('Failure');
				}
			)
		}
	};
	
	
	function compare(a,b) {
		if (a.request.requestId < b.request.requestId)
			return -1;
		if (a.request.requestId > b.request.requestId)
			return 1;
		return 0;
	}
	
	$http.get("/ride/request/active")
	.then(function(res){
		$http.get("/ride/request/open")
		.then(function(response){
			$scope.activeRequests = response.data;
			organizeData(res, "active");
			});
		});
	
	/*
	 * Organizes Ride list data by combining RideRequests with matching AvailableRide objects.
	 */
	function organizeData(response, reqString){
		if(response.data.length == 0){
			let temp = [];
			$scope.activeRides = temp;
			return;
		}
		let list = response.data;
		let listReq = [];
		let temp = [];
		let counter = 0;
		let currentAvailId = list[0].request.requestId;
		list.sort(compare); 
		listReq = [list[0]];
		for(let i = 0; i < list.length; i++){
			if((currentAvailId != list[i].request.requestId) && i == list.length-1){
				listReq[counter++].request = temp;
				temp = [];
				temp.push(list[i].request);
				listReq[counter] = list[i];
				listReq[counter].request = temp;
				
			}
			else if ((currentAvailId == list[i].request.requestId) && i == list.length-1){
				temp.push(list[i].request);
				listReq[counter].request = temp;
			}
			else if((currentAvailId != list[i].request.requestId)){
				currentAvailId = list[i].request.requestId;
				if(temp.length > 0){
					listReq[counter++].request = temp;
					listReq[counter] = list[i];
					temp = [];
				}
			} 
			if(i != list.length-1) temp.push(list[i].request);
		}
		if(reqString == "active") {
			$scope.activeRides = listReq;
			for(let i = 0; i < $scope.activeRequests.length; i++) {
				for(let k = 0; k < $scope.activeRides.length; k++) {
					if($scope.activeRequests[i].requestId == $scope.activeRides[k].request.requestId){
						$scope.activeRequests.splice(i, 1);
						i--;
						break;
					}
				}
			}
		}
		else if (reqString == "history") {
			$scope.pastRides = listReq;
		}
	}
	
	$scope.cancelActiveRequest = function(activeReqId){
		$http.get('/ride/request/cancelActive/' + activeReqId).then(
				(response) => {
					for(let i = 0; i < $scope.activeRequests.length; i++){
						if($scope.activeRequests[i].requestId == activeReqId) {
							$scope.activeRequests.splice(i, 1);
							$scope.$apply;
						}
					}
					
					setTimeout(function(){$state.reload();}, 500);
				}
		);
	}
	
	
	//stops past dates from being selected in date/time picker
	$scope.startDateBeforeRender = function($dates) {
		  const todaySinceMidnight = new Date();
		    todaySinceMidnight.setUTCHours(0,0,0,0);
		    $dates.filter(function (date) {
		      return date.utcDateValue < todaySinceMidnight.getTime();
		    }).forEach(function (date) {
		      date.selectable = false;
		    });
		};

};
