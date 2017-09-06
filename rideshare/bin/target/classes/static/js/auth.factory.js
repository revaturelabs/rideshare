export let authFactory = function($window, $log, jwtHelper) {
	return {
		getToken: function() {
			return $window.localStorage.getItem('RideShare_auth_token');
		},
		setToken: function(token) {
			$window.localStorage.setItem('RideShare_auth_token', token);
		},
		clearToken: function() {
			$window.localStorage.removeItem('RideShare_auth_token');
		},
		decodeToken: function() {
			let result = null;
			let token = $window.localStorage.getItem('RideShare_auth_token');
			if (token) {
				try {
					result = jwtHelper.decodeToken(token);
				} catch (err) {
					$log.error('Failed to decode JSON web token: ' + err);
				}
			}
			return result;
		},
		getUser: function() {
			let result = null;
			let token = $window.localStorage.getItem('RideShare_auth_token');
			if (token) {
				try {
					let payload = jwtHelper.decodeToken(token);
					result = JSON.parse(payload.user);
				} catch (err) {
					$log.error('Failed to retrieve user from JSON web token: ' + err);
				}
			}
			return result;
		},
		isAdmin: function() {
			let result = false;
			let token = $window.localStorage.getItem('RideShare_auth_token');
			if (token) {
				try {
					let payload = jwtHelper.decodeToken(token);
					let user = JSON.parse(payload.user);
					result = user.admin;
				} catch (err) {
					$log.error('Failed to determine if the current user is an admin: ' + err);
				}
			}
			return result;
		}
	};
};