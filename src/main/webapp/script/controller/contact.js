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
    countries.success(function (data) {
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

    var sex = $http({
        method: "get",
        url: "/EmployeeService/sex/sexList",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
    });
    sex.success(function (data) {
        $scope.sexList = data;
    });


    $scope.save = {};
    $scope.save.doClick = function () {
        var response = $http({
            method: "post",
            url: "/EmployeeService/employee/saveEmployeeCreate",
            data: {
                f_name: $scope.employee.f_name,
                s_name: $scope.employee.s_name,
                sex: $scope.employee.sex,
                dateOfBirth: $scope.employee.dateOfBirth,
                countryId: $scope.country.id,
                city: $scope.employee.city,
                street: $scope.employee.street,
                house: $scope.employee.house,
                flat: $scope.employee.flat,
                addressId: $scope.address.id,
                departmentId: $scope.department.id,
                positionInCompanyId: $scope.position.id,
                dateContractEnd: $scope.employee.dateContractEnd,
                fired: false,
                firedComment: ''
            },
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        });
        response.success(function () {
            $location.path('/employeeList');
            $location.replace();
        });
    }
});

app.controller("employeeCorrectController", function ($scope, $http, $routeParams, $location) {
    var id = $routeParams.id;
    var response = $http({
        method: "get",
        url: "/EmployeeService/employee/employeeById",
        params: {
            id: id
        }
    });
    response.success(function (data) {
        $scope.employee = data;
        var departments = $http({
            method: "get",
            url: "/EmployeeService/department/departmentList",
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        });
        departments.success(function (data) {
            $scope.departments = data;
            if ($scope.employee != undefined) {
                data.forEach(selectDepartment);
                function selectDepartment(element, index) {
                    if (element.id == $scope.employee.departmentId) {
                        $scope.department = $scope.departments[index];
                    }
                }
            }
        });

        var countries = $http({
            method: "get",
            url: "/EmployeeService/country/countryList",
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        });
        countries.success(function (data) {
            $scope.countries = data;
            data.forEach(selectCountry);
            if ($scope.employee != undefined) {
                function selectCountry(element, index) {
                    if (element.id == $scope.employee.countryId) {
                        $scope.country = $scope.countries[index];
                    }
                }
            }
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
            data.forEach(selectAddress);
            if ($scope.employee != undefined) {
                function selectAddress(element, index) {
                    if (element.id == $scope.employee.addressId) {
                        $scope.address = $scope.addresses[index];
                    }
                }
            }
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
            data.forEach(selectPosition);
            if ($scope.employee != undefined) {
                function selectPosition(element, index) {
                    if (element.id == $scope.employee.positionInCompanyId) {
                        $scope.position = $scope.positions[index];
                    }
                }
            }
        });


        var sex = $http({
            method: "get",
            url: "/EmployeeService/sex/sexList",
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        });
        sex.success(function (data) {
            $scope.sexList = data;
            data.forEach(selectSex);
            function selectSex(element, index){
                if(element.sexEnum == $scope.employee.sex){
                    $scope.employee.sex = $scope.sexList[index].sexEnum;
                }
            }
        });
    });


    $scope.save = {};
    $scope.save.doClick = function () {
        var response = $http({
            method: "post",
            url: "/EmployeeService/employee/saveEmployeeUpdate",
            data: {
                id: $scope.employee.id,
                f_name: $scope.employee.f_name,
                s_name: $scope.employee.s_name,
                dateOfBirth: $scope.employee.dateOfBirth,
                sex: $scope.employee.sex,
                countryId: $scope.country.id,
                city: $scope.employee.city,
                street: $scope.employee.street,
                house: $scope.employee.house,
                flat: $scope.employee.flat,
                addressId: $scope.address.id,
                departmentId: $scope.department.id,
                positionInCompanyId: $scope.position.id,
                dateContractEnd: $scope.employee.dateContractEnd,
                fired: false,
                firedComment: ''
            },
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        });
        response.success(function () {
            $location.path('/employeeList');
            $location.replace();
        });
    }

    $scope.delete = {};
    $scope.delete.doClick = function () {
        $('#modal-fired-comment').modal('hide');
        var response = $http({
            method: "post",
            url: "/EmployeeService/employee/saveEmployeeUpdate",
            data: {
                id: $scope.employee.id,
                f_name: $scope.employee.f_name,
                s_name: $scope.employee.s_name,
                dateOfBirth: $scope.employee.dateOfBirth,
                sex: $scope.employee.sex,
                countryId: $scope.country.id,
                city: $scope.employee.city,
                street: $scope.employee.street,
                house: $scope.employee.house,
                flat: $scope.employee.flat,
                addressId: $scope.address.id,
                departmentId: $scope.department.id,
                positionInCompanyId: $scope.position.id,
                dateContractEnd: $scope.employee.dateContractEnd,
                fired: true,
                firedComment: $scope.employee.firedComment
            },
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        });
        response.success(function () {
            $location.path('/employeeList');
            $location.replace();
        });
    }
});

function showModalFiredComment() {
    $('#modal-fired-comment').modal('show');
}

function hideModalFiredComment() {
    document.getElementById('fired-comment').value = "";
    $('#modal-fired-comment').modal('hide');
}