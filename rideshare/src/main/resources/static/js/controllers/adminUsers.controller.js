export let adminUsersController = function($scope, $http, $state) {
	$scope.users ={};
    $scope.user = {}; 
    
    
    
    /*
     * Retrieves data for all Users when page loads by calling users method 
     * in AdminController.java
     */
    $http.get('admin/users')
    .then((response) => {
        console.log(response);
        $scope.users = response.data;
    })  
    
    
    
    /*
     * Changes a User's status to admin or not from the checkbox by calling updateStatus method 
     * in AdminController.java
     */ 
    $scope.changeAdmin = function(index){
    	
        $scope.user = $scope.users[index];//Finds current user
        
        $scope.isAdmin = document.getElementById("isAdmin" + index).value;//Checks if user is an admin
        
        var isBanned = document.getElementById("isBanned" + index).value;//Checks if user is banned

        if ($scope.isAdmin === undefined)//Makes sure that user can't accidentally gain admin rights.   
            $scope.isAdmin = false;
        
        /*
         * Calls UpdateStatus method with the form '/updateStatus/{id}/{isAdmin}/{isBanned}'
         */
        var url = "/admin/updateStatus/" 
        	  + $scope.user.userId + "/" 
        	  + $scope.isAdmin + "/" 
        	  + isBanned;
        
        console.log("URL IS " + url);
        
        $http.post(url, $scope.user)
        .then((formResponse) => {
        	console.log(formResponse.data);
            $state.reload('main.adminUsers');
        },
        (failedResponse) => {
            alert('failure'); 
        })
    }
}
