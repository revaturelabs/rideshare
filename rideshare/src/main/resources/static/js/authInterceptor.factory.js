export let authInterceptor = function($q, authService) {
	return {
		'response': function(res) {
			let rideshareToken = res.headers('rideshare-token');
			if (rideshareToken) {
				authService.setToken(rideshareToken);
			}
		}//,
		// 'responseError': function(rejection) {
		// 	console.log('you got an error')
		// }
	};
}