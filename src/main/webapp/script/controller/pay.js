app.controller("payController", function ($scope, $rootScope, $http) {
    $scope.pay = {};
    $scope.pay.doClick = function(){
        var ok = validateObject.validate('#payForm');
        if(ok) {
            var response = $http({
                method: "post",
                url: "/EmployeeService/company/pay",
                params: {
                    accountNumber: $scope.accountNumber
                }
            });
            response.success(function (data) {
                alert("ok");
            });
        }
    }
});
