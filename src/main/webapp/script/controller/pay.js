app.controller("payController", function ($scope, $rootScope, $http) {
    var response = $http({
        method: "get",
        url: "/EmployeeService/company/currentCompany",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
    });
    response.success(function (data) {
        $scope.company = data;
    });
//    $scope.pay = {};
//    $scope.pay.doClick = function(){
    $scope.pay = function(){
        var ok = validateObject.validate('#payForm');
        if(ok) {
            var response = $http({
                method: "post",
                url: "/EmployeeService/company/pay",
                params: {
                    accountNumber: $scope.accountNumber
                }
            });
            response.success(function () {

            });
        }
    }
});
