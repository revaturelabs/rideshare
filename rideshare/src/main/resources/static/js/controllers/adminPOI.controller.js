export let adminPoiController = function ($scope, $http, $state) {
	$scope.poi = {};
	$scope.allpois = {};
	$scope.newPoi = {};
	$scope.dummyPoi = {};

	// retrieve all pois
	$http.get("/poiController")
		.then(function (response) {
			$scope.allpois = response.data;
		});

	// retrieve the poiType objects
	$scope.types = {};
	$http.get("/poiController/type")
		.then(function (response) {
			$scope.types = response.data;
		})  // end of poiType retrieval

	// Used to bypass Same Origin Policy
	$scope.createCORSRequest = function (method, url) {
		var xhr = new XMLHttpRequest();

		if ("withCredentials" in xhr) {
			// XHR for Chrome/Firefox/Opera/Safari.
			xhr.open(method, url, true);
		}
		else if (typeof XDomainRequest != "undefined") {
			// XDomainRequest for IE.
			xhr = new XDomainRequest();
			xhr.open(method, url);
		}
		else {
			// CORS not supported.
			xhr = null;
		}

		return xhr;
	}

	// addPoi function()
	$scope.addPoi = function () {
		// must first get lat/lng THEN sumbit data to backend
		// prevent undefined response
		if ($scope.poi.addressLine2 === undefined)
			$scope.poi.addressLine2 = "";
		// get address and format it for google maps response
		var address = "" + $scope.poi.addressLine1 + " "
			+ $scope.poi.addressLine2 + ", " +
			$scope.poi.city +
			", " + $scope.poi.state;
		address = address.replace(/\s/g, '+'); // replace white space with +

		// store url to retrieve response from google maps api
		var url = "https://maps.googleapis.com/maps/api/geocode/" +
			"json?address=" + address +
			"&key=AIzaSyB_mhIdxsdRfwiAHVm8qPufCklQ0iMOt6A";

		var xhr = $scope.createCORSRequest('GET', url)

		// extract latitude and longitude with google maps api
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

	// removePoi()
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

	$scope.openModal = function (index) {
		$scope.dummyPoi = $scope.allpois[index];
		$scope.newPoi = angular.copy($scope.dummyPoi);
		$scope.newPoi.zipCode = parseInt($scope.dummyPoi.zipCode, 10);
	}

	// edit/updatePoi()
	$scope.updatePoi = function () {

		if ($scope.newPoi.addressLine2 === null)
			$scope.newPoi.addressLine2 = "";
		// get address and format it for google maps response
		var address = "" + $scope.newPoi.addressLine1 + " "
			+ $scope.newPoi.addressLine2 + ", " +
			$scope.newPoi.city +
			", " + $scope.newPoi.state;
		address = address.replace(/\s/g, '+'); // replace white space with +

		// store url to retrieve response from google maps api
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