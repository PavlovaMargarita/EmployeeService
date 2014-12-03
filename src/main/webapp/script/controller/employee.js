app.controller("employeeListController", function ($scope, $rootScope, $http, PagerService) {

    $scope.range = [];
    $scope.currentPage = 1;
    $scope.totalPages = 1;
    $scope.totalRecords = 0;
    $scope.statusForList = 'list';
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
        var employeeCount = $http({
            method: "get",
            url: "/EmployeeService/employee/employeeCount",
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        });
        employeeCount.success(function (data) {
            $scope.totalRecords = Number(data);
            $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
            $scope.range = PagerService.buildRange($scope.totalPages);
        });
    });

    $scope.getClassForEmployee = function (employeeRedRow) {
        if (employeeRedRow) {
            return "red-color";
        }
    };

    $scope.getRecords = {};
    $scope.getRecords.doClick = function (pageNumber) {
        var response;
        if($scope.statusForList == 'list'){
            response = $http({
                method: "get",
                url: "/EmployeeService/employee/employeeList",
                params: {currentPage: pageNumber, pageRecords: $rootScope.recordsOnPage}
            });
            response.success(function (data) {
                $scope.employees = data;
                $scope.employees.forEach(checkDateContractEnd);
                $scope.currentPage = pageNumber;
                var employeeCount = $http({
                    method: "get",
                    url: "/EmployeeService/employee/employeeCount",
                    dataType: 'json',
                    contentType: 'application/json',
                    mimeType: 'application/json'
                });
                employeeCount.success(function (data) {
                    $scope.totalRecords = Number(data);
                    $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
                    $scope.range = PagerService.buildRange($scope.totalPages);
                });
            });
        } else{
            response = $http({
                method: "post",
                url: "/EmployeeService/employee/search",
                data: {
                    value:$scope.searchValue
                },
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json',
                params: {currentPage: pageNumber, pageRecords: $rootScope.recordsOnPage}
            });
            response.success(function(data){
                $scope.employees = data.employeeList;
                $scope.employees.forEach(checkDateContractEnd);
                $scope.totalRecords = Number(data.totalSearchCount);
                $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
                $scope.range = PagerService.buildRange($scope.totalPages);
                $scope.currentPage = pageNumber;
            });
        }

    };

    $scope.search = function () {
        $scope.statusForList = 'search';
        var response = $http({
            method: "post",
            url: "/EmployeeService/employee/search",
            data: {
               value:$scope.searchValue
            },
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            params: {currentPage: 1, pageRecords: $rootScope.recordsOnPage}
        });
        response.success(function (data) {
            $scope.employees = data.employeeList;
            $scope.employees.forEach(checkDateContractEnd);
            $scope.totalRecords = Number(data.totalSearchCount);
            $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
            $scope.range = PagerService.buildRange($scope.totalPages);
            $scope.currentPage = 1;
        });
    }

});

app.controller("employeeCreateController", function ($scope, $rootScope, $http, $location) {
    $scope.employee = {};
    $scope.create = true;
    $scope.showStandardPhotoAndFiredButton = false;
    $scope.editEmployeeInput = true;
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
    };

    $scope.openDateOfBirth = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.openedDateOfBirth = true;
    };
    $scope.openDateContractEnd = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.openedContractEnd = true;
    };


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
        $scope.country = {};
        $scope.country.id = $scope.countries[0].id;
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
        $scope.position = {};
        $scope.position.id = $scope.positions[0].id;
    });

    $scope.sexList = [];
    $scope.sexList[0] = {'sexEnum': 'MALE', sexRussian: 'Мужской'};
    $scope.sexList[1] = {'sexEnum': 'FEMALE', sexRussian: 'Женский'};
    $scope.employee.sex = $scope.sexList[0].sexEnum;

    var role = $http({
        method: "get",
        url: "/EmployeeService/employee/roleList",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json'
    });
    role.success(function (data) {
        $scope.roleList = [];
        data.forEach(addRoleName);
        function addRoleName(element) {
            var value;
            switch (element) {
                case 'ROLE_HRM':
                    value = {roleEnum: element, roleTranslate: 'HRM'};
                    $scope.roleList.push(value);
                    break;
                case 'ROLE_ADMIN':
                    value = {roleEnum: element, roleTranslate: 'Администратор'};
                    $scope.roleList.push(value);
                    break;
                case 'ROLE_EMPLOYEE':
                    value = {roleEnum: element, roleTranslate: 'Сотрудник'};
                    $scope.roleList.push(value);
                    break;
            }
        }

        $scope.employee.role = $scope.roleList[0].roleEnum;
    });

    $scope.save = {};
    $scope.save.doClick = function () {
        var ok = validateObject.validate("#createEmployeeForm");
        if (ok) {
            $scope.address = {};
            $scope.department = {};
            var response = $http({
                method: "post",
                url: "/EmployeeService/employee/saveEmployeeCreate",
                data: {
                    first_name: $scope.employee.first_name,
                    last_name: $scope.employee.last_name,
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
                    companyId: $scope.employee.companyId,
                    email: $scope.employee.email
                },
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json'
            });
            response.success(function (data) {
                if( $scope.photo) {
                    var fd = new FormData();
                    fd.append('idEmployee', data);
                    fd.append("photo", $scope.photo);
                    $http({
                        method: 'POST',
                        url: '/EmployeeService/employee/uploadPhoto',
                        headers: {'Content-Type': undefined},
                        data: fd,
                        transformRequest: angular.identity
                    });
                        //.success(function () {
                        //    $location.path('/employeeList');
                        //    $location.replace();
                        //});
                } else{
                    $location.path('/employeeList');
                    $location.replace();
                }
            });
        }
    };

    $scope.changeDepartment = {};
    $scope.changeDepartment.change = function () {
        loadAddress($scope.department.id, $http, $scope);
    };

    $scope.loadPhotoButton = {};
    $scope.loadPhotoButton.doClick = function () {
        document.getElementById('photoFileID').click();
    };

    $scope.cancel = {};
    $scope.cancel.doClick = function () {
        $location.path('/employeeList');
        $location.replace();
    }
});

app.controller("employeeCorrectController", function ($scope, $http, $routeParams, $location, $rootScope) {
    $scope.create = false;
    $scope.showStandardPhotoAndFiredButton = true;
    if ($rootScope.hasAuthority(['ROLE_CEO', 'ROLE_ADMIN'])) {
        $scope.editEmployeeInput = false;
        $('#saveEmployee').attr('disabled', 'disabled');
    } else {
        $scope.editEmployeeInput = true;
        $('#saveEmployee').removeAttr('disabled');
    }

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
        $('#employeePhotoEdit').attr('src', "/EmployeeService/files/company/" + $scope.company.id + "/photoEmployee/" + $scope.employee.id + "/" + $scope.employee.photoURL);
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

        $scope.sexList = [];
        $scope.sexList[0] = {'sexEnum': 'MALE', sexRussian: 'Мужской'};
        $scope.sexList[1] = {'sexEnum': 'FEMALE', sexRussian: 'Женский'};
        $scope.sexList.forEach(selectSex);
        function selectSex(element, index) {
            if (element.sexEnum == $scope.employee.sex) {
                $scope.employee.sex = $scope.sexList[index].sexEnum;
            }
        }

        var role = $http({
            method: "get",
            url: "/EmployeeService/employee/fullRoleList",
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        });

        role.success(function (data) {
            $scope.roleList = [];
            data.forEach(addRoleName);
            function addRoleName(element) {
        var value;
        switch (element) {
            case 'ROLE_HRM':
                value = {roleEnum: element, roleTranslate: 'HRM'};
                $scope.roleList.push(value);
                break;
            case 'ROLE_ADMIN':
                value = {roleEnum: element, roleTranslate: 'Администратор'};
                $scope.roleList.push(value);
                break;
            case 'ROLE_EMPLOYEE':
                value = {roleEnum: element, roleTranslate: 'Сотрудник'};
                $scope.roleList.push(value);
                break;

        }

    }
    $scope.roleList.forEach(selectRole);
    function selectRole(element, index) {
        if (element.roleEnum == $scope.employee.role) {
            $scope.employee.role = $scope.roleList[index].roleEnum;
        }
    }
});

});


$scope.save = {};
$scope.save.doClick = function () {
    var ok = validateObject.validate("#createEmployeeForm");
        if (ok) {
            var response = $http({
                method: "post",
                url: "/EmployeeService/employee/saveEmployeeUpdate",
                data: {
                    id: $scope.employee.id,
                    first_name: $scope.employee.first_name,
                    last_name: $scope.employee.last_name,
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
                    companyId: $scope.employee.companyId,
                    email: $scope.employee.email
                },
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json'
            });
            response.success(function (data) {
                if ($scope.photo) {
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
                        .success(function () {
                            $location.path('/employeeList');
                            $location.replace();
                        });
                }
                $location.path('/employeeList');
                $location.replace();
            });
        }
    };

    $scope.delete = {};
    $scope.delete.doClick = function () {
        $('#modal-fired-comment').modal('hide');
        var ok = validateObject.validate("#createEmployeeForm");
        if (ok) {
            var response = $http({
                method: "post",
                url: "/EmployeeService/employee/saveEmployeeUpdate",
                data: {
                    id: $scope.employee.id,
                    first_name: $scope.employee.first_name,
                    last_name: $scope.employee.last_name,
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
                    companyId: $scope.employee.companyId,
                    email: $scope.employee.email
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
    };

    $scope.loadPhotoButton = {};
    $scope.loadPhotoButton.doClick = function () {
        document.getElementById('photoFileID').click();
        alert("ok");
    };

    $scope.loadPhotoButton = {};
    $scope.loadPhotoButton.doClick = function () {
        document.getElementById('photoFileID').click();
    };
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

    };
    $scope.changeDepartment = {};
    $scope.changeDepartment.change = function () {
        loadAddress($scope.department.id, $http, $scope);
    };
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
function checkDateContractEnd(element) {
    var employeeDateContractEnd = new Date(element.dateContractEnd);
    var currentDate = new Date();
    if ((employeeDateContractEnd - currentDate) / (1000 * 60 * 60 * 24) <= 10) {
        element.redRow = true;
    } else {
        element.redRow = false;
    }
}