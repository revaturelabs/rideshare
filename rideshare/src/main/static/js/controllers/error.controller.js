export let errorController = function($scope, $http, $state, $location) {
	$scope.query = $location.search();
	$scope.banned = $scope.query.reason === 'ban';
}