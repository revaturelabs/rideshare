// import 'angular';
// import '@uirouter/angularjs';
import 'moment';
import './js/dateTimePicker/datetimepicker.js';
import './js/dateTimePicker/datetimepicker.templates.js';
import { angularJwt } from 'angular-jwt';
import { mainController } from './js/controllers/main.controller.js';
import { passengerController } from './js/controllers/passenger.controller.js';
import { driverController } from './js/controllers/driver.controller.js';
import { historyController } from './js/controllers/history.controller.js';
import { slackLoginController } from './js/controllers/slackLogin.controller.js';
import { errorController } from './js/controllers/error.controller.js';
import { adminRidesController } from './js/controllers/adminRides.controller.js';
import { adminUsersController } from './js/controllers/adminUsers.controller.js';
import { adminPoiController } from './js/controllers/adminPOI.controller.js';
import { userProfileController } from './js/controllers/userProfile.controller.js';
import { authFactory } from './js/auth.factory.js';

//var = function scope
//const and let = block scope 

//Main module 
const app = angular.module('app', ['ui.router', 'angular-jwt', 'ui.bootstrap.datetimepicker'])//;
	.factory('authFactory', ['$window', '$log', 'jwtHelper', authFactory]);

/*
 *  'Run' function contains the code needed to kickstart the program. 
 * 
 * Is equivalent to the main method in a Java application.
 * 
 * Gets executed after the 'config' function.
 * 
 * The authManager from angular-jwt checks JWTs for authentication purposes such as logging in or preventing users 
 * from accessing pages they shouldn't be allowed to access.  
 * 
 * checkAuthoOnRefresh checks if token is still valid on page refresh.
 * 
 * redirectWhenUnauthenticated will redirect users if they don't have valid credentials.
 * This is what prevents a user from accessing restricted content by simply typing the URL in the search bar.  
**/ 
app.run(function(authManager, $http) {
	authManager.checkAuthOnRefresh();
	authManager.redirectWhenUnauthenticated();
});

/*
 *  'Config' function gets executed first, before 'run'. It can only contain providers to declare.
 */
app.config(function($stateProvider, $urlRouterProvider, $httpProvider, jwtOptionsProvider){
	
	/*
	 * angular-jwt provides the jwtOptionsProvider to define your own scheme for JWT.
	 * 
	 * Whitelist applications that aren't on the same domain as the application.
	 */
	jwtOptionsProvider.config({
		loginPath: '/#/login',
		unauthenticatedRedirectPath: '/',
		authPrefix: '',
		authHeader: 'X-JWT-RIDESHARE',
		tokenGetter: ['authFactory', function(authFactory) {
			return authFactory.getToken();
		}],
		whiteListedDomains: ['maps.googleapis.com']
	});
	
	/* 
	 * Interceptors are service factories that are registered with the $httpProvider by adding to the interceptors array. 
	 * 
	 * jwtInterceptor takes care of sending the JWT in every request.
	 */
	$httpProvider.interceptors.push('jwtInterceptor');
	
	/*
	 * otherwise() is used for invalid routes.
	 */
	$urlRouterProvider.otherwise('/login');

	
	/*
	 * The state machine for routing between different views in the application.
	 * Allows for the linking of controllers to html pages.
	 */
	$stateProvider
	
		/*
		 * Returns main.html
		 */
		.state('main', {
			url: '/main',
			templateUrl: 'partials/main.html',
			controller: mainController,
			data: { requiresLogin: true }
		})
		
		/*
		 * Returns stackLogin.html
		 */
		.state('slackLogin', {
			url: '/login',
			templateUrl: 'partials/slackLogin.html',
			controller: slackLoginController,
			data: { requiresLogin: false }
		})
		
		/*
		 * Returns passenger.html
		 */
		.state('main.passenger',{
			url: '/passenger',
			templateUrl : 'partials/passenger.html',
			controller : passengerController,
			data: { requiresLogin: true }
		})
		
		/*
		 * Returns driver.html
		 */
		.state('main.driver',{
			url: '/driver',
			templateUrl : 'partials/driver.html',
			controller : driverController,
			data: { requiresLogin: true }
		})
		
		/*
		 * Returns adminRides.html
		 */
		.state('main.adminRides' , {
			url: '/adminRides', 
			templateUrl : 'partials/adminRides.html',
			controller : adminRidesController,
			data: { requiresLogin: true },
			// resolve: {
			// 	adminAccess: function() {
			// 		return new Promise((resolve, reject) => {
			// 			console.log('in resolver');
			// 			if (authFactory.isAdmin()) {
			// 				console.log('You are an admin');
			// 				resolve(true);
			// 			} else {
			// 				console.log('You are not an admin');
			// 				reject(false);
			// 			}
			// 		});
			// 	}
			// }
		})
		
		/*
		 * Returns adminUsers.html
		 */
		.state('main.adminUsers', {
			url: '/adminUsers',
			templateUrl: 'partials/adminUsers.html',
			controller : adminUsersController,
			data: { requiresLogin: true },
			// resolve: {
			// 	adminAccess: function() {
			// 		return new Promise((resolve, reject) => {
			// 			if (authFactory.isAdmin()) {
			// 				console.log('You are an admin')
			// 				resolve(true);
			// 			} else {
			// 				console.log('You are not an admin')
			// 				reject(false);
			// 			}
			// 		});
			// 	}
			// }
		})
		
		/*
		 * Returns adminPOI.html
		 */
		.state('main.adminPoi',{
			url: '/adminPoi',
			templateUrl : 'partials/adminPOI.html',
			controller : adminPoiController,
			data: { requiresLogin: true },
			// resolve: {
			// 	adminAccess: function() {
			// 		return new Promise((resolve, reject) => {
			// 			if (authFactory.isAdmin()) {
			// 				console.log('You are an admin')
			// 				resolve(true);
			// 			} else {
			// 				console.log('You are not an admin')
			// 				reject(false);
			// 			}
			// 		});
			// 	}
			// }
		})
    
		/*
		 * Returns userProfile.html
		 */
		.state('main.userProfile', {
			url: '/userProfile',
			templateUrl : 'partials/userProfile.html',
			controller : userProfileController,
			data: { requiresLogin: true }
		})

		/*
		 * Returns error.html
		 */
		.state('error', {
			url: '/error',
			templateUrl: 'partials/error.html',
			controller: errorController,
			data: { requiresLogin: false }
		})
});
