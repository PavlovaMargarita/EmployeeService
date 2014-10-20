app.controller("employeeListController", function ($scope, $rootScope, $http) {

    var response = $http({
        method: "get",
        url: "/EmployeeService/employee/employeeList",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
    });
    response.success(function (data) {
        $scope.employees = data;
    });
});

app.controller("employeeCreateController", function ($scope, $rootScope, $http, $location, $route) {

    var departments = $http({
        method: "get",
        url: "/EmployeeService/department/departmentList",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
    });
    departments.success(function (data) {
        $scope.departments = data;
    });

    var countries = $http({
        method: "get",
        url: "/EmployeeService/country/countryList",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
    });
    countries .success(function (data) {
        $scope.countries = data;
    });

    var departmentAddresses = $http({
        method: "get",
        url: "/EmployeeService/address/addressList",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
    });
    departmentAddresses.success(function (data) {
        $scope.addresses = data;
    });

    var position = $http({
        method: "get",
        url: "/EmployeeService/positionInCompany/positionList",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
    });
    position.success(function (data) {
        $scope.positions = data;
    });

    $scope.save = {};
    $scope.save.doClick = function(){
        var response = $http({
            method: "post",
            url: "/EmployeeService/employee/saveEmployeeCreate",
            data: {
                f_name: $scope.employee.f_name,
                s_name: $scope.employee.s_name,
                dateOfBirth: $scope.employee.dateOfBirth,
                countryId: $scope.country.id,
                city: $scope.employee.city,
                street: $scope.employee.street,
                house: $scope.employee.house,
                flat: $scope.employee.flat,
                addressId: $scope.address.id,
                departmentId: $scope.department.id,
                positionInCompany: $scope.position.id
            },
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        });
        response.success(function () {
            $location.path('/contactList');
            $location.replace();
        });
    }
});