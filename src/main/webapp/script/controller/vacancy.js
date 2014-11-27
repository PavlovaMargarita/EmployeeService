app.controller("vacancyListController", function ($scope, $rootScope, $http, PagerService) {
    $scope.range = [];
    $scope.currentPage = 1;
    $scope.totalPages = 1;
    $scope.totalRecords = 0;

    var response = $http({
        method: "get",
        url: "/EmployeeService/vacancy/vacancyList",
        dataType: 'json',
        contentType: 'application/json',
        mimeType: 'application/json',
        params: {currentPage: 1, pageRecords: $rootScope.recordsOnPage}
    });
    response.success(function (data) {
        $scope.vacancyList = data;

        var vacancyCount = $http({
            method: "get",
            url: "/EmployeeService/vacancy/vacancyCount",
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        });
        vacancyCount.success(function (data) {
            $scope.totalRecords = Number(data);
            $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
            $scope.range = PagerService.buildRange($scope.totalPages);
        });
    });


    $scope.getRecords = {};
    $scope.getRecords.doClick = function (pageNumber) {
        var response = $http({
            method: "get",
            url: "/EmployeeService/vacancy/vacancyList",
            params: {currentPage: pageNumber, pageRecords: $rootScope.recordsOnPage}
        });
        response.success(function (data) {
            $scope.vacancyList = data;
            $scope.currentPage = pageNumber;
            var vacancyCount = $http({
                method: "get",
                url: "/EmployeeService/vacancy/vacancyCount",
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json'
            });
            vacancyCount.success(function (data) {
                $scope.totalRecords = Number(data);
                $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
                $scope.range = PagerService.buildRange($scope.totalPages);
            });
        });
    }
});

app.controller("vacancyCreateController", function ($scope, $rootScope, $http, $location) {
    $scope.showStatus = false;
    $scope.vacancy = {};

    $scope.save = function () {
        var vacancyValidateObject = new VacancyValidator();
        var ok = vacancyValidateObject.validate('#createVacancyForm');
        if (ok) {
            var response = $http({
                method: "post",
                url: "/EmployeeService/vacancy/saveVacancyCreate",
                data: {
                    description: $scope.vacancy.description
                },
                dataType: 'json',
                contentType: 'application/json',
                mimeType: 'application/json'
            });
            response.success(function () {

                $location.path('/vacancyList');
                $location.replace();
            });
        }
    };

    $scope.cancel = {};
    $scope.cancel.doClick = function () {
        $location.path('/vacancyList');
        $location.replace();
    }
});

app.controller("vacancyCorrectController", function ($scope, $http, $routeParams, $location) {
    $scope.readonly = true;
    $scope.showStatus = true;
    var id = $routeParams.id;
    var response = $http({
        method: "get",
        url: "/EmployeeService/vacancy/readVacancyById",
        params: {
            id: id
        }
    });
    response.success(function (data) {
        $scope.vacancy = data;
        $scope.statusList = [];
        $scope.statusList[0] = {statusEnum: 'false', statusEnumRussian: 'Открытое'};
        $scope.statusList[1] = {statusEnum: 'true', statusEnumRussian: 'Закрытое'};
        $scope.vacancy.status = $scope.statusList[0].statusEnum;
    });

    $scope.save = function () {
        var vacancyValidateObject = new VacancyValidator();
        var ok = vacancyValidateObject.validate('#createVacancyForm');
        if (ok) {
            if ($scope.vacancy.status == 'false') {
                var response = $http({
                    method: "post",
                    url: "/EmployeeService/vacancy/saveVacancyUpdate",
                    data: {
                        id: $scope.vacancy.id,
                        description: $scope.vacancy.description,
                        close: $scope.vacancy.status
                    },
                    dataType: 'json',
                    contentType: 'application/json',
                    mimeType: 'application/json'
                });
                response.success(function () {
                    $location.path('/vacancyList');
                    $location.replace();
                });
            } else {
                var response = $http({
                    method: "post",
                    url: "/EmployeeService/vacancy/deleteVacancyById",
                    data: {
                        id: $scope.vacancy.id
                    },
                    dataType: 'json',
                    contentType: 'application/json',
                    mimeType: 'application/json'
                });
                response.success(function () {
                    $location.path('/vacancyList');
                    $location.replace();
                });
            }
        }
    };

    $scope.cancel = {};
    $scope.cancel.doClick = function () {
        $location.path('/vacancyList');
        $location.replace();
    }
});