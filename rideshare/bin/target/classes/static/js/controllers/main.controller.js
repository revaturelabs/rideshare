export let mainController = function($scope, $http, $state, $location, authFactory){
	// view that is the parent of all the main views
	$scope.isAdmin = authFactory.isAdmin();

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

}
