app.controller("employeeListController", function ($scope, $rootScope, $http, PagerService) {

    $scope.range = [];
    $scope.currentPage = 1;
    $scope.totalPages = 1;
    $scope.totalRecords = 0;

    var response = $http({
        method: "get",
        url: "/EmployeeService/employee/employeeList",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        params: {currentPage: 1, pageRecords: $rootScope.recordsOnPage}
    });
    response.success(function (data) {
        $scope.employees = data;
        $scope.employees.forEach(checkDateContractEnd);
        function checkDateContractEnd(element, index) {
            var employeeDateContractEnd = new Date(element.dateContractEnd);
            var currentDate = new Date();
            if ((employeeDateContractEnd - currentDate) / (1000 * 60 * 60 * 24) <= 10) {
                element.redRow = true;
            } else {
                element.redRow = false;
            }
        }

        var employeeCount = $http({
            method: "get",
            url: "/EmployeeService/employee/employeeCount",
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        });
        employeeCount.success(function (data) {
            $scope.totalRecords = data - 0;
            $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
            $scope.range = PagerService.buildRange($scope.totalPages);
        });
    });
    $scope.setColor = function (value) {
        if (value) {
            return "alert alert-danger";
        }
    }

    $scope.getRecords = {};
    $scope.getRecords.doClick = function (pageNumber) {
        var response = $http({
            method: "get",
            url: "/EmployeeService/employee/employeeList",
            params: {currentPage: pageNumber, pageRecords: $rootScope.recordsOnPage}
        });
        response.success(function (data) {
            $scope.employees = data;
            $scope.employees.forEach(checkDateContractEnd);
            function checkDateContractEnd(element, index) {
                var employeeDateContractEnd = new Date(element.dateContractEnd);
                var currentDate = new Date();
                if ((employeeDateContractEnd - currentDate) / (1000 * 60 * 60 * 24) <= 10) {
                    element.redRow = true;
                } else {
                    element.redRow = false;
                }
            }

            $scope.currentPage = pageNumber;
            var employeeCount = $http({
                method: "get",
                url: "/EmployeeService/employee/employeeCount",
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json'
            });
            employeeCount.success(function (data) {
                $scope.totalRecords = data - 0;
                $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
                $scope.range = PagerService.buildRange($scope.totalPages);
            });
        });
    }

});

app.controller("employeeCreateController", function ($scope, $rootScope, $http, $location, $route, $upload) {
    $scope.show = false;
    $scope.onFileSelect = function ($files) {
        $scope.photo = $files[0];
        var reader = new FileReader();
        var imgtag = document.getElementById("employeePhotoCreate");
        var photo;
        reader.onload = function (e) {
            photo = e.target.result;
            imgtag.src = photo;
        };
        reader.readAsDataURL($scope.photo);

    }
    var departments = $http({
        method: "get",
        url: "/EmployeeService/company/departmentList",
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

    var position = $http({
        method: "get",
        url: "/EmployeeService/company/positionInCompanyList",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
    });
    position.success(function (data) {
        $scope.positions = data;
    });

    var sex = $http({
        method: "get",
        url: "/EmployeeService/employee/sexList",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
    });
    sex.success(function (data) {
        $scope.sexList = data;
    });

    var role= $http({
        method: "get",
        url: "/EmployeeService/employee/roleList",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
    });
    role.success(function (data) {
        $scope.roleList = data;
    });

    $scope.save = {};
    $scope.save.doClick = function () {
//        var temp = validateObject.validate("#createEmployeeForm");
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
                firedComment: '',
                photoURL: 't',
                login: $scope.employee.login,
                password: $scope.employee.password,
                role: $scope.employee.role,
                companyId: $scope.employee.companyId
            },
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        });
        response.success(function (data) {
            var fd = new FormData();
            fd.append('idEmployee', data);
            fd.append("photo", $scope.photo);
            $http({
                method: 'POST',
                url: '/EmployeeService/employee/uploadPhoto',
                headers: {'Content-Type': undefined},
                data: fd,
                transformRequest: angular.identity
            })
                .success(function (data, status) {
                    alert("success");
                    $location.path('/employeeList');
                    $location.replace();
                });

        });
    }

    $scope.changeDepartment = {};
    $scope.changeDepartment.change = function () {
        loadAddress($scope.department.id, $http, $scope);
    }

    $scope.loadPhotoButton = {};
    $scope.loadPhotoButton.doClick = function () {
        document.getElementById('photoFileID').click();
    }

    $scope.cancel = {};
    $scope.cancel.doClick = function () {
        $location.path('/employeeList');
        $location.replace();
    }
});

app.controller("employeeCorrectController", function ($scope, $http, $routeParams, $location) {
    $scope.show = true;
    var currentCompany = $http({
        method: "get",
        url: "/EmployeeService/company/currentCompanyId",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
    });
    currentCompany.success(function (data) {
        $scope.company = {};
        $scope.company.id = data;

    });

    $scope.showPhoto = false;
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
        //load photo
        $('#employeePhotoEdit').attr('src', "/EmployeeService/files/company/" + $scope.company.id+ "/photoEmployee/" + $scope.employee.id + "/" +$scope.employee.photoURL);
        $scope.showPhoto = true;
        var departments = $http({
            method: "get",
            url: "/EmployeeService/company/departmentList",
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        });
        departments.success(function (data) {
            $scope.departments = data;
            $scope.department = {};
            if ($scope.employee != undefined) {
                data.forEach(selectDepartment);
                function selectDepartment(element, index) {
                    if (element.id == $scope.employee.departmentId) {
                        $scope.department = $scope.departments[index];
                    }
                }
            }
            var departmentAddresses = $http({
                method: "get",
                url: "/EmployeeService/company/addressList",
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json',
                params: {
                    departmentId: $scope.department.id
                }
            });
            departmentAddresses.success(function (data) {
                $scope.addresses = data;
                $scope.address = {};
                data.forEach(selectAddress);
                if ($scope.employee) {
                    function selectAddress(element, index) {
                        if (element.id == $scope.employee.addressId) {
                            $scope.address = $scope.addresses[index];
                        }
                    }
                }
            });
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


        var position = $http({
            method: "get",
            url: "/EmployeeService/company/positionInCompanyList",
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
            url: "/EmployeeService/employee/sexList",
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        });
        sex.success(function (data) {
            $scope.sexList = data;
            data.forEach(selectSex);
            function selectSex(element, index) {
                if (element.sexEnum == $scope.employee.sex) {
                    $scope.employee.sex = $scope.sexList[index].sexEnum;
                }
            }
        });

        var role = $http({
            method: "get",
            url: "/EmployeeService/employee/roleList",
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        });
        role.success(function (data) {
            $scope.roleList = data;
            data.forEach(selectRole);
            function selectRole(element, index) {
                if (element.roleEnum == $scope.employee.role) {
                    $scope.employee.role = $scope.roleList[index].roleEnum;
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
                firedComment: '',
                photoURL: $scope.employee.photoURL,
                login: $scope.employee.login,
                password: $scope.employee.password,
                role: $scope.employee.role,
                companyId: $scope.employee.companyId
            },
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        });
        response.success(function (data) {
            if($scope.photo) {
                var fd = new FormData();
                fd.append('idEmployee', data);
                fd.append("photo", $scope.photo);
                $http({
                    method: 'POST',
                    url: '/EmployeeService/employee/uploadPhoto',
                    headers: {'Content-Type': undefined},
                    data: fd,
                    transformRequest: angular.identity
                })
                    .success(function (data, status) {
                        alert("success");
                        $location.path('/employeeList');
                        $location.replace();
                    });
            }
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
                firedComment: $scope.employee.firedComment,
                dateFired: $scope.employee.dateFired,
                photoURL: "test",
                login: $scope.employee.login,
                password: $scope.employee.password,
                role: $scope.employee.role,
                companyId: $scope.employee.companyId
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

    $scope.loadPhotoButton = {};
    $scope.loadPhotoButton.doClick = function () {
        document.getElementById('photoFileID').click();
        alert("ok");
    }

    $scope.loadPhotoButton = {};
    $scope.loadPhotoButton.doClick = function () {
        document.getElementById('photoFileID').click();
    }
    $scope.onFileSelect = function ($files) {
        $scope.photo = $files[0];
        var reader = new FileReader();
        var imgtag = document.getElementById("employeePhotoEdit");
        var photo;
        reader.onload = function (e) {
            photo = e.target.result;
            imgtag.src = photo;
        };
        reader.readAsDataURL($scope.photo);

    }

    $scope.cancel = {};
    $scope.cancel.doClick = function () {
        $location.path('/employeeList');
        $location.replace();
    }
});

function showModalFiredComment() {
    $('#modal-fired-comment').modal('show');
}

function hideModalFiredComment() {
    document.getElementById('fired-comment').value = "";
    $('#modal-fired-comment').modal('hide');
}

function loadAddress(idDepartment, $http, $scope) {
    alert("load");
    var test;
    var departmentAddresses = $http({
        method: "get",
        url: "/EmployeeService/company/addressList",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        params: {
            departmentId: idDepartment
        }
    });
    departmentAddresses.success(function (data) {
        $scope.addresses = data;
    });
}
