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

app.controller("contactCreateController", function ($scope, $rootScope, $http, $location, $route) {

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

    $scope.save.doClick = function(){
        var response = $http({
            method: "post",
            url: "/EmployeeService/employee/saveEmployeeCreate",
            data: {
                surname: $scope.contact.surname,
                name: $scope.contact.name,
                patronymic: $scope.contact.patronymic,
                dateOfBirth: $scope.contact.dateOfBirth,
                email: $scope.contact.email,
                city: $scope.contact.city,
                street: $scope.contact.street,
                home: $scope.contact.home,
                flat: $scope.contact.flat,
                phones: phones
            }
        });
        response.success(function () {
            $location.path('/contactList');
            $location.replace();
        });
    }
});