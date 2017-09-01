export let mainController = function($scope, $http, $state, $location, authFactory){
	// view that is the parent of all the main views
	$scope.isAdmin = authFactory.isAdmin();
	$scope.loggedUser = authFactory.getUser();
	console.log($scope.loggedUser);

	$scope.logout = function() {
		localStorage.removeItem('RideShare_auth_token');
		$http.post('/logout', {})
		.then(function() {
			$location.path("/");
		})
		.catch(function(data) {
			console.log("Logout failed");
			self.authenticated = false;
		});
	};

	// retrieve the user's current car
	$http.get("/car/myCar", $scope.car)
		.then((response) => {
			$scope.car = response.data;
			console.log($scope.car);
			$scope.carCopy = angular.copy($scope.car);
			
			if ($scope.car == null) {
				console.log("no car");
				$scope.showHide = false;
			}
			else {
				console.log("car")
				$scope.showHide = true;
			}
		},
		(failedResponse) => {
			alert('failure');
		})
}
