app.controller("contactListController", function ($scope, $rootScope, $http) {

    var response = $http({
        method: "get",
        url: "/EmployeeService/employee/employeeList",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
    });
    response.success(function (data) {
        $scope.contacts = data;
    });
});

app.controller("contactListController", function ($scope, $rootScope, $http, $location, $route) {

    var response = $http({
        method: "get",
        url: "/EmployeeService/employee/employeeList",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
    });
    response.success(function (data) {
        $scope.contacts = data;
    });
});