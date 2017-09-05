export let userProfileController = function ($scope, $http, $state, $location) {
	$scope.allpois = {};
	$scope.user = {};
	// Car object which is bound to the car information table
	$scope.car = {};
	// Copy of car that is bound to the modal
	$scope.carCopy = {};
	$scope.buttonText = '';
	$scope.mainPoiOption = {};
	$scope.workPoiOption = {};

	// enable bootstrap popovers on click 
	$(document).ready(function () {
		$('[data-toggle="popover"]').popover();
	});

	// Retrieve user's information
	$http.get("user/me", $scope.user)
		.then((response) => {
			// Possible issue: We may not want all of a user's information
			// floating around on the client side. Should some information
			// be withheld in Java User controller?
			$scope.user = response.data;

			return $scope.getPois($scope, $http);
		}),
		(failedResponse) => {
			alert('Failed to get user\'s information');
		}

	//retrieve all pois
	$scope.getPois = function ($scope, $http) {
		$http.get("/poiController")
			.then(function (response) {
				$scope.allpois = response.data;
			})
			// Set 
			.then(function () {
				for (var i = 0; i < $scope.allpois.length; i++) {
					if ($scope.user.mainPOI.poiId == $scope.allpois[i].poiId) {
						$scope.mainPoiOption = $scope.allpois[i];
					}
					if ($scope.user.workPOI.poiId == $scope.allpois[i].poiId) {
						$scope.workPoiOption = $scope.allpois[i];
					}
				}
			});
	}

	// retrieve the user's current car
	$http.get("/car/myCar", $scope.car)
		.then((response) => {
			$scope.car = response.data;
			$scope.carCopy = angular.copy($scope.car);
			$scope.carMain = angular.copy($scope.car);
			if ($scope.car === '') {
				$scope.buttonText = 'Add Car';
			}
			else {
				$scope.buttonText = 'Edit Car';
			}
		},
		(failedResponse) => {
			alert('failure');
		})

	// how get poi from selected option 
	// set the pois to the user 
	$scope.setPois = function () {
		$scope.user.mainPOI = $scope.mainPoiOption;
		$scope.user.workPOI = $scope.workPoiOption;
		$http.post("/user/updateCurrentUser", $scope.user)
			.then((formResponse) => {
				console.log(formResponse.data);
				console.log($scope.parent.loggedUser);
				$scope.parent.loggedUser.mainPoiOption = formResponse.data.mainPoiOption;
				$scope.parent.loggedUser.workPoiOption = formResponse.data.workPoiOption;
				$state.go('main.userProfile');
			},
			(failedResponse) => {
				alert('failure in setPois');
			})
	}
	
	

	$scope.addCar = function () {
		$http.post('/car', $scope.carCopy).then(
			(formResponse) => {
				$scope.buttonText = 'Edit Car';
				$scope.car = angular.copy($scope.carCopy);
				$scope.carMain = angular.copy($scope.carCopy);
				// Reloading the view stops the user from adding a new car
				// after deleting a car.
				$state.reload('main.userProfile');
			},
			(failedResponse) => {
				alert('Failure in addCar');
			}
		)
	}

	$scope.updateCar = function () {

		$http.post('/car/updateCar', $scope.carCopy).then(
			(formResponse) => {
				$scope.car = angular.copy($scope.carCopy);
				$state.go('main.userProfile');
			},
			(failedResponse) => {
				alert('Failure in updateCar');
			}
		)
	}

	$scope.removeCar = function () {
		$http.post("/car/removeCar", $scope.car)
			.then((response) => {
				$scope.buttonText = 'Add Car';

				$scope.car = {};
				$scope.carCopy = {};
				$scope.carMain = {};
				$state.go("main.userProfile");
			},
			(failedResponse) => {
				alert('failure');
			})
	}
	
	$scope.departUser = function () {
		$http.post("/user/removeUser", $scope.user)
			.then((response) => {

				if($scope.car){
					$http.post("/car/removeCar", $scope.car)
				} //removing a users car

				$http.post('/logout', {})
				.then(function() {
					$location.path("/");
				})
			},
			(failedResponse) => {
				alert('failure');
			})
	}
}