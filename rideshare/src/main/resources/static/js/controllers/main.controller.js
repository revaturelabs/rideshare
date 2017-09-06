export let mainController = function($scope, $http, $state, $location, authFactory){

	
	$scope.isAdmin = authFactory.isAdmin();// View that is the parent of all the main views

	// view that is the parent of all the main views
	$scope.isAdmin = authFactory.isAdmin();
	$scope.loggedUser = authFactory.getUser();
	$scope.carMain = {};
	$scope.showHide;
	$scope.sameStartEnd = false;

	console.log("The logged User...");
	console.log($scope.loggedUser);
	console.log("is this user undefined? " + $scope.loggedUser == undefined);

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
			self.authenticated = false;
		});
	};

	/*
	 *  Retrieve the user's current car by calling the getCar method in CarController.java
	 */
	$http.get("/car/myCar", $scope.carMain)
		.then((response) => {
			$scope.carMain = response.data;
			if ($scope.carMain == null) {
				$scope.showHide = false;
			}
			else {
				$scope.showHide = true;
			}
		},
		(failedResponse) => {
			alert('failure');
		})

		/*
		* When a user selects two points start and end that are the same
		* an alert will appear to inform them of their mistake this resets
		* the show/hide flag for that alert when they hit 'x' to dismiss it
		*/

		$scope.revertAlert = function () {
			$scope.sameStartEnd = false;
		}
}
