export let adminUsersController = function($scope, $http, $state) {
	$scope.users ={};
    $scope.user = {}; 
 
    // retrieves data for all Users when page loads
    $http.get('admin/users')
    .then((response) => {
        console.log(response);
        $scope.users = response.data;
    })  
    
    // changes a User's status to admin or not from the checkbox
    $scope.changeAdmin = function(index){
        $scope.user = $scope.users[index];
        $scope.isAdmin = document.getElementById("isAdmin").value;
        $scope.isBanned = document.getElementById("isBanned").value;
        
        console.log($scope.isAdmin);
        console.log($scope.isBanned);

        if ($scope.isAdmin === undefined)   
            $scope.isAdmin = false; 
        
        if ($scope.isBanned === undefined)   
            $scope.isBanned = false; 
        
        var url = "admin/updateStatus/" + $scope.user.userId + "/" + $scope.isAdmin + "/" + $scope.isBanned; 
        
        $http.post(url, $scope.user)
        .then((formResponse) => {
        	console.log(formResponse.data)
            $state.reload('main.adminUsers');
        },
        (failedResponse) => {
            alert('failure'); 
        })
    }
}