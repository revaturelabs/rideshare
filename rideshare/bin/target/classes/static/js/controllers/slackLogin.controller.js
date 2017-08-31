export let slackLoginController = function($scope, $http, $state, $log, authFactory) {
	$http.get('/auth/process')
		.then(function(res) {
			let token = res.headers('rideshare-token');
			// localStorage.setItem('RideShare_auth_token', token);
			authFactory.setToken(token);
			$state.go('main.passenger');
		})
		.catch(function(reason) {
			$log.error(reason);
			authFactory.clearToken();
		});
}
