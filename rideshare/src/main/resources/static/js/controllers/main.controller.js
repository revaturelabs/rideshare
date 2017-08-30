export let mainController = function($scope, $http, $state, $location, authFactory){
	// view that is the parent of all the main views
	$scope.isAdmin = authFactory.isAdmin();
	//$scope.isBanned = authFactory.isBanned();

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
}
