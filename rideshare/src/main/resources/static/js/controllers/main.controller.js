export let mainController = function($scope, $http, $state, $location, authFactory){

	
	$scope.isAdmin = authFactory.isAdmin();// View that is the parent of all the main views
	//$scope.isBanned = authFactory.isBanned();

	// view that is the parent of all the main views
	$scope.isAdmin = authFactory.isAdmin();
	$scope.loggedUser = authFactory.getUser();
	console.log($scope.loggedUser);
	$scope.carMain = {};
	$scope.showHide;
	
	/*
	 * Performs a logout by removing the JWT that was stored locally.
	 * This ensures that users must perform a login to access their account again 
	 */
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

	/*
	 *  Retrieve the user's current car by calling the getCar method in CarController.java
	 */
	$http.get("/car/myCar", $scope.carMain)
		.then((response) => {
			$scope.carMain = response.data;
			console.log(response.data);
			
			if ($scope.carMain == null) {
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
