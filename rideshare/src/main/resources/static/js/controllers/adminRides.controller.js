export let adminRidesController = function($scope, $http, $state) {
	$scope.activeRides = {};
    $scope.rideHistory = {};
    $scope.ride = {}; 
    
    
	// retrieve all active Rides to populate accordion table
	$http.get('admin/activeRides')
	.then((res) => {
		console.log(res);
		$scope.activeRides = res.data;
	})

	
	// retrieve all past Rides to populate accordion table
	$http.get('admin/rideHistory')
	.then((res) => {
		console.log(res);
		$scope.rideHistory = res.data;
	})

}