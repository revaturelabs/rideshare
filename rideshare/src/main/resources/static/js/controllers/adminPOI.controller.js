export let adminPoiController = function ($scope, $http, $state) {
	$scope.poi = {};
	$scope.allpois = {};
	$scope.newPoi = {};
	$scope.dummyPoi = {};

	/*
	 * Retrieves all Points of Interests(POIs).
	 */
	$http.get("/poiController")
		.then(function (response) {
			$scope.allpois = response.data;
		});

	
	
	/*
	 *  Retrieves the poiType objects by calling PointOfInterestController.java.
	 */
	$scope.types = {};
	$http.get("/poiController/type")
		.then(function (response) {
			$scope.types = response.data;
		})  // end of poiType retrieval

		
		
	/*
	 *  Used to bypass Same Origin Policy which would prevent pages with different origins 
	 *  from accessing each others scripts.
	 */
	$scope.createCORSRequest = function (method, url) {
		var xhr = new XMLHttpRequest();

		if ("withCredentials" in xhr) {
			xhr.open(method, url, true);// XHR for Chrome/Firefox/Opera/Safari.
		}
		else if (typeof XDomainRequest != "undefined") {
			xhr = new XDomainRequest();// XDomainRequest for IE.
			xhr.open(method, url);
		}
		else {
			xhr = null; // CORS not supported.
		}
		return xhr;
	}

	
	
	/*
	 * Calls the addPoi method defined in PointOfInterestController.java.
	 */
	$scope.addPoi = function () {
		
		/*
		 * Must first get latitude/longitude THEN submit data to the back-end.
		 */ 
		
		if ($scope.poi.addressLine2 === undefined)//Prevents undefined responses.
			$scope.poi.addressLine2 = "";
		
		/*
		 *  Retrieves address and format it for google maps response.
		 */
		var address = "" + $scope.poi.addressLine1 + " "
			+ $scope.poi.addressLine2 + ", " +
			$scope.poi.city +
			", " + $scope.poi.state;
		address = address.replace(/\s/g, '+'); // replace white space with +

		/* 
		 * Store URL to retrieve response from Google Maps API.
		 */
		var url = "https://maps.googleapis.com/maps/api/geocode/" +
			"json?address=" + address +
			"&key=AIzaSyB_mhIdxsdRfwiAHVm8qPufCklQ0iMOt6A";

		var xhr = $scope.createCORSRequest('GET', url)

		/*
		 *  Extract latitude and longitude with Google Maps API.
		 */
		xhr.onload = function (response) {
			var result = JSON.parse(xhr.responseText);
			if (result.length !== 0) {
				
				$scope.poi.latitude = result.results[0].geometry.location.lat;
				$scope.poi.longitude = result.results[0].geometry.location.lng;
				
				$http.post("/poiController/addPoi", $scope.poi)
				
					.then((formResponse) => {
						document.getElementById("addPoi-form").reset();
						$state.reload();
					},
					(failedResponse) => {
						alert('failure');
					})
					
			}
		}
		xhr.send();
	}   // end of addPoi() function

	
	
	/*
	 * Calls the rmovePOI method in the PointOfInterestController.java
	 */
	$scope.removePoi = function (index) {
		// later add modal asks "Are you sure you want to remove this POI?"
		$http.post("/poiController/removePoi", $scope.allpois[index])
			.then((response) => {
				$state.reload();
			},
			(failedResponse) => {
				alert('failure');
			})
	}   // end of removePoi() function
	
	
	
	/*
	 * Creates a dummyPoi
	 */
	$scope.openModal = function (index) {
		$scope.dummyPoi = $scope.allpois[index];
		$scope.newPoi = angular.copy($scope.dummyPoi);
		$scope.newPoi.zipCode = parseInt($scope.dummyPoi.zipCode, 10);
	}

	
	
	/*
	 *  Calls the updatePOI method in PointOfInterestController.java
	 */
	$scope.updatePoi = function () {

		if ($scope.newPoi.addressLine2 === null)
			$scope.newPoi.addressLine2 = "";
		
		/*
		 * Get address and format it for google maps response
		 */
		var address = "" + $scope.newPoi.addressLine1 + " "
			+ $scope.newPoi.addressLine2 + ", " +
			$scope.newPoi.city +
			", " + $scope.newPoi.state;
		
		address = address.replace(/\s/g, '+'); // Replace white space with +

		/*
		 *  Store URL to retrieve response from google maps API
		 */
		var url = "https://maps.googleapis.com/maps/api/geocode/" +
			"json?address=" + address +
			"&key=AIzaSyB_mhIdxsdRfwiAHVm8qPufCklQ0iMOt6A";
		
		var xhr = $scope.createCORSRequest('GET', url);

		xhr.onload = function (response) {
			var result = JSON.parse(xhr.responseText);

			$scope.newPoi.latitude = result.results[0].geometry.location.lat;
			$scope.newPoi.longitude = result.results[0].geometry.location.lng;

			$http.post("/poiController/updatePoi", $scope.newPoi)
			
				.then((formResponse) => {
					$state.reload('main.adminPoi');
				},
				(failedResponse) => {
					alert('failure');
				})
				
		}

		xhr.send();
	}   // end of updatePoi() function
};