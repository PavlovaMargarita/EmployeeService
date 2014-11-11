app.controller("authorizationController", function ($scope, $http, $location, $rootScope, $cookieStore) {
    $rootScope.showLabelPaySystem = false;
    document.getElementById("loginError").style.display = "none";
    document.getElementById("passwordError").style.display = "none";
    $scope.pay = false;
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
        response.success(function (userInfo) {
            $http({
                method: "get",
                url: "/EmployeeService/company/dateBoundaryRefill",
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json'
            })
                .success(function (date){
                    var dateBoundaryRefill = new Date(date);
                    var currentDate = new Date();
                    if ((currentDate - dateBoundaryRefill) / (1000 * 60 * 60 * 24) > 7) {
                        $scope.pay = true;
                    } else {
                        if((currentDate - dateBoundaryRefill) / (1000 * 60 * 60 * 24) <= 7 &&
                            (currentDate - dateBoundaryRefill) / (1000 * 60 * 60 * 24) > 0){
                            $rootScope.showLabelPaySystem = true;
                        }
                        $cookieStore.put("userInfo", userInfo);
                        $scope.successRedirect(userInfo);
                    }
                });
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
        if(data.role == 'ROLE_HRM'){
            $location.path('/employeeList');
        }
        if(data.role == 'ROLE_SUPERADMIN'){
            $location.path('/companyList');
        }
        $location.replace();
    }

    $scope.authorization = {};
    $scope.authorization.doClick = function(){
        validateObject.validateAndSubmit('#formID', '#formID input:not([type="button"])');
    };
//
//    $(function(){
//        $("#form").validate({
//            rules: {
//                j_username: {
//                    required: true,
//                    minlength: 5
//                },
//                j_password: {
//                    required: true,
//                    minlength: 6
//                }
//            },
//            messages: {
//                j_username: {
//                    required: "Введите свой логин",
//                    minlength: "Логин должно содержать не менее 5 символов"
//                },
//                j_password: {
//                    required: "Введите свой пароль",
//                    minlength: "Пароль должно содержать не менее 6 символов"
//                }
//            },
//            submitHandler: function (form) {
//                form.submit();
//            }
//        });
//    })
});