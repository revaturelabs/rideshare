export let authFactory = function($window, $log, jwtHelper) {
	return {
		
		/*
		 * Finds the token in local storage.
		 */
		getToken: function() {
			return $window.localStorage.getItem('RideShare_auth_token');
		},
		
		/*
		 * Stores an object of type 'token' with the name 'Rideshare_auth_token'.
		 */
		setToken: function(token) {
			$window.localStorage.setItem('RideShare_auth_token', token);
		},
		
		/*
		 * Removes the token from local storage
		 */
		clearToken: function() {
			$window.localStorage.removeItem('RideShare_auth_token');
		},
		
		/*
		 * Decodes the token in localstorage and returns the value.
		 */
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
		
		/*
		 * Parses through the token for find user information
		 */
		getUser: function() {
			let result = null;
			let token = $window.localStorage.getItem('RideShare_auth_token');
			if (token) {
				try {
					let payload = jwtHelper.decodeToken(token);
					result = JSON.parse(payload.user);
					console.log(result);
				} catch (err) {
					$log.error('Failed to retrieve user from JSON web token: ' + err);
				}
			}
			return result;
		},
		
		/*
		 * Parses through token to determine if user is an admin.
		 */
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
		/*isBanned: function() {
			let result = false;
			let token = $window.localStorage.getItem('RideShare_auth_token');
			if (token) {
				try {
					let payload = jwtHelper.decodeToken(token);
					let user = JSON.parse(payload.user);
					result = user.banned;
				} catch (err) {
					$log.error('Failed to determine if the current user is an banned: ' + err);
				}
			}
			return result;
		}*/
	};
};