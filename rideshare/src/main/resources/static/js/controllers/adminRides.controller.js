export let adminRidesController = function($scope, $http, $state) {
	$scope.activeRides = {};
    $scope.rideHistory = {};
    $scope.ride = {}; 
    
    
	/* 
	 * Retrieve all active Rides to populate accordion table by calling activeRides method
	 * in AdminController.java
	 */
	$http.get('admin/activeRides')
	.then((res) => {
		console.log(res);
		$scope.activeRides = res.data;
	})

	
	
	/*
	 * Retrieve all past Rides to populate accordion table by calling rideHistory method 
	 * in AdminController.java
	 */
	$http.get('admin/rideHistory')
	.then((res) => {
		console.log(res);
		$scope.rideHistory = res.data;
	})

}