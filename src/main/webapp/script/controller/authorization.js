app.controller("authorizationController", function ($scope, $http, $location, $rootScope, $cookieStore) {
    document.getElementById("loginError").style.display = "none";
    document.getElementById("passwordError").style.display = "none";
    $scope.authorization = {};

    var isSuccess = ($location.search()).success;
    if(isSuccess){
        var response = $http({
            method: "get",
            url: "/EmployeeService/user/userInfo",
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        });
        response.success(function (data) {
            $cookieStore.put("userInfo", data);
            $scope.successRedirect(data);
        });
    }

    $scope.processSuccess = function(){
        var success = ($location.search()).success;
        if(success != null){
            $scope.storeCurrentUserInfo();
        }
    }

    $scope.processError = function(){
        var isError = ($location.search()).error;
        if(isError){
            return "Ошибка авторизации!";
        }
    }

    $scope.storeCurrentUserInfo = function(){
        var response = $http({
            method: "get",
            url: "/EmployeeService/user/userInfo",
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        })
            .success(function (data) {
                $cookieStore.put("userInfo", data);
                $scope.successRedirect(data);})
            .error(function (data) {
                alert("ALERT");
            });
    }

    $scope.successRedirect = function(data){
//        if (data.role == 'ROLE_SUPERVISOR' || data.role == 'ROLE_SERVICE_DELIVERY_MANAGER' || data.role == 'ROLE_PROCESSING_ORDERS_SPECIALIST' || data.role == 'ROLE_ADMIN'){
//            $location.path('/orderList');
//        } else {
//            $location.path('/contactList');
//        }
//        $location.replace();
        if(data.role == 'ROLE_HRM'){
            $location.path('/employeeList');
        }
        $location.replace();
    }

    $scope.authorization = {};
    $scope.authorization.doClick = function(){
        var formValidator = new FormValidator(document.getElementById("form"));
        formValidator.validateLoginAndPassword();
    }
});