app.controller("authorizationController", function ($scope, $http, $location, $rootScope, $cookieStore) {
    $rootScope.showLabelPaySystem = false;
    document.getElementById("loginError").style.display = "none";
    document.getElementById("passwordError").style.display = "none";
    $scope.pay = false;
    $scope.authorization = {};

    var isSuccess = ($location.search()).success;
    if (isSuccess) {
        var response = $http({
            method: "get",
            url: "/EmployeeService/user/userInfo",
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        });
        response.success(function (userInfo) {
            $http({
                method: "get",
                url: "/EmployeeService/company/dateBoundaryRefill",
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json'
            })
                .success(function (date) {
                    var dateBoundaryRefill = new Date(date);
                    var currentDate = new Date();
                    if ((currentDate - dateBoundaryRefill) / (1000 * 60 * 60 * 24) > 7) {
                        $scope.pay = true;
                    } else {
                        if ((currentDate - dateBoundaryRefill) / (1000 * 60 * 60 * 24) <= 7 &&
                            (currentDate - dateBoundaryRefill) / (1000 * 60 * 60 * 24) > 0) {
                            $rootScope.showLabelPaySystem = true;
                        }
                        $cookieStore.put("userInfo", userInfo);
                        $scope.successRedirect(userInfo);
                    }
                });
            $http({
                method: "get",
                url: "/EmployeeService/employee/currentEmployee",
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json'
            })
                .success(function (data){
                    //$('#li-name').text(data.f_name);
                    $cookieStore.put("name", data.f_name);
                    $cookieStore.put("photoURL", "/EmployeeService/files/company/" + data.companyId+ "/photoEmployee/" + data.id + "/" +data.photoURL);
                    $cookieStore.put("role", data.role);
                });
        });
    }

    $scope.processSuccess = function () {
        var success = ($location.search()).success;
        if (success != null) {
            $scope.storeCurrentUserInfo();
        }
    };

    $scope.processError = function () {
        var isError = ($location.search()).error;
        if (isError) {
            return "Ошибка авторизации!";
        }
    };

    $scope.storeCurrentUserInfo = function () {
        $http({
            method: "get",
            url: "/EmployeeService/user/userInfo",
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        })
            .success(function (data) {
                $cookieStore.put("userInfo", data);
                $scope.successRedirect(data);
            })
            .error(function () {
                alert("ALERT");
            });
    };

    $scope.successRedirect = function (data) {
        if (data.role == 'ROLE_HRM') {
            $location.path('/employeeList');
        }
        if (data.role == 'ROLE_SUPERADMIN') {
            $location.path('/companyList');
        }
        if (data.role == 'ROLE_CEO') {
            $location.path('/pay');
        }
        if (data.role == 'ROLE_ADMIN') {
            $location.path('/employeeList');
        }
        $location.replace();
    };

    $scope.authorization = {};
    $scope.authorization.doClick = function () {
        //validateObject.validateAndSubmit('#formID');
        var valid = new VacancyValidator();
        valid.validateAndSubmit('#formID');
    };
});