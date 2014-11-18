app.controller("companyListController", function ($scope, $rootScope, $http, PagerService) {
    $scope.range = [];
    $scope.currentPage = 1;
    $scope.totalPages = 1;
    $scope.totalRecords = 0;

    var response = $http({
        method: "get",
        url: "/EmployeeService/company/companyList",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        params: {currentPage: 1, pageRecords: $rootScope.recordsOnPage}
    });
    response.success(function (data) {
        $scope.companies = data;

        var companyCount = $http({
            method: "get",
            url: "/EmployeeService/company/companyCount",
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        });
        companyCount.success(function (data) {
            $scope.totalRecords = data - 0;
            $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
            $scope.range = PagerService.buildRange($scope.totalPages);
        });
    });
    $scope.setColor = function (value) {
        if (value) {
            return "alert alert-danger";
        }
    };

    $scope.getRecords = {};
    $scope.getRecords.doClick = function (pageNumber) {
        var response = $http({
            method: "get",
            url: "/EmployeeService/company/companyList",
            params: {currentPage: pageNumber, pageRecords: $rootScope.recordsOnPage}
        });
        response.success(function (data) {
            $scope.companies = data;
            $scope.currentPage = pageNumber;
            var companyCount = $http({
                method: "get",
                url: "/EmployeeService/company/companyCount",
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json'
            });
            companyCount.success(function (data) {
                $scope.totalRecords = data - 0;
                $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
                $scope.range = PagerService.buildRange($scope.totalPages);
            });
        });
    }

});

app.controller("companyCreateController", function ($scope, $rootScope, $http, $location) {
    $scope.readonly = false;
    $scope.showStatus = false;
    $scope.company = {};
    $scope.employee = {};
    var currentDate = new Date();
    currentDate.setMonth(currentDate.getMonth() + 1);
    var newMonth = currentDate.getMonth() + 1;
    $scope.company.dateBoundaryRefill = currentDate.getFullYear() + "-" + newMonth + "-" + currentDate.getDate();
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
    $scope.sexList = [];
    $scope.sexList[0] = {'sexEnum': 'MALE', sexRussian: 'Мужской'};
    $scope.sexList[1] = {'sexEnum': 'FEMALE', sexRussian: 'Женский'};


    $scope.roleList =[];
    $scope.roleList[0] = {'roleEnum': 'ROLE_CEO', roleRussian: 'CEO'};
    $scope.employee.role = $scope.roleList[0].roleEnum;


    $scope.save = {};
    $scope.save.doClick = function () {
        var ok = validateObject.validate('#createCompanyForm');
        if(ok) {
            var response = $http({
                method: "post",
                url: "/EmployeeService/company/saveCompanyCreate",
                data: {
                    companyName: $scope.company.companyName,
                    dateBoundaryRefill: $scope.company.dateBoundaryRefill,
                    companyPlan: $scope.company.companyPlan,
                    programCost: $scope.company.programCost
                },
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json'
            });
            response.success(function (data) {
                var employee = $http({
                    method: "post",
                    url: "/EmployeeService/employee/saveEmployeeCreateCEO",
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
                        login: $scope.employee.login,
                        password: $scope.employee.password,
                        role: $scope.employee.role,
                        companyId: data,
                        email: $scope.employee.email
                    },
                    dataType: 'json',
                    contentType: 'application/json',
                    mimeType: 'application/json'

                }).success(function () {
                    $location.path('/companyList');
                    $location.replace();
                })
            });
        }
    };

    $scope.cancel = {};
    $scope.cancel.doClick = function () {
        $location.path('/companyList');
        $location.replace();
    }
});

app.controller("companyCorrectController", function ($scope, $http, $routeParams, $location) {
    $scope.readonly = true;
    $scope.showStatus = true;
    var id = $routeParams.id;
    var response = $http({
        method: "get",
        url: "/EmployeeService/company/companyById",
        params: {
            id: id
        }
    });
    response.success(function (data) {
        $scope.company = data;
        $scope.statusList = [];
        $scope.statusList[0] = {companyStatusEnum: 'CONTINUE_FUNCTIONING', companyStatusEnumRussian: 'Активное'};
        $scope.statusList[1] = {companyStatusEnum: 'SUSPEND_FUNCTIONING', companyStatusEnumRussian: 'Приостановленное'};
        $scope.statusList[2] = {companyStatusEnum: 'FINISH_FUNCTIONING', companyStatusEnumRussian: 'Завершенное'};
        $scope.statusList.forEach(selectStatus);
        function selectStatus(element, index) {
            if (element.companyStatusEnum == $scope.company.companyStatus) {
                $scope.company.companyStatus = $scope.statusList[index].companyStatusEnum;
            }
        }
    });
    $scope.sexList = [];
    $scope.sexList[0] = {'sexEnum': 'MALE', sexRussian: 'Мужской'};
    $scope.sexList[1] = {'sexEnum': 'FEMALE', sexRussian: 'Женский'};

    $scope.roleList = [];
    $scope.roleList[0] = {'roleEnum': 'ROLE_CEO', roleRussian: 'CEO'};


    $http({
        method: "get",
        url: "/EmployeeService/employee/employeeCeoByCompanyId",
        params: {
            companyId: id
        }
    }).success(function(data){
        $scope.employee = data;
        $scope.employee.role = $scope.roleList[0].roleEnum;
        $scope.sexList.forEach(selectSex);
        function selectSex(element, index) {
            if (element.sexEnum == $scope.employee.sex) {
                $scope.employee.sex = $scope.sexList[index].sexEnum;
            }
        }
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
    });
    $scope.save = {};
    $scope.save.doClick = function () {
        var ok = validateObject.validate('#createCompanyForm');
        if(ok) {
            var response = $http({
                method: "post",
                url: "/EmployeeService/company/saveCompanyUpdate",
                data: {
                    id: $scope.company.id,
                    companyName: $scope.company.companyName,
                    companyStatus: $scope.company.companyStatus,
                    dateBoundaryRefill: $scope.company.dateBoundaryRefill,
                    addSum: $scope.company.addSum,
                    canLogin: $scope.company.canLogin

                },
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json'
            });
            response.success(function () {
                $location.path('/companyList');
                $location.replace();
            });
        }
    };

    $scope.cancel = {};
    $scope.cancel.doClick = function () {
        $location.path('/companyList');
        $location.replace();
    }
});

function showModalAddSum() {
    $('#modal-add-sum').modal('showStandardPhotoAndFiredButton');
}

function hideModalAddSum() {
    $('#modal-add-sum').modal('hide');
}
