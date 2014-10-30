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
        $scope.companies.forEach(checkAccountSum);
        function checkAccountSum(element, index) {
            if (element.accountSum < 0) {
                element.redRow = true;
            } else {
                element.redRow = false;
            }
        }

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
    }

    $scope.getRecords = {};
    $scope.getRecords.doClick = function (pageNumber) {
        var response = $http({
            method: "get",
            url: "/EmployeeService/company/companyList",
            params: {currentPage: pageNumber, pageRecords: $rootScope.recordsOnPage}
        });
        response.success(function (data) {
            $scope.companies = data;
            $scope.companies.forEach(checkAccountSum);
            function checkAccountSum(element, index) {
                if (element.accountSum < 0) {
                    element.redRow = true;
                } else {
                    element.redRow = false;
                }
            }

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
    $scope.showStatus = false;
    $scope.showAddSumInput = false;
    $scope.company = {};
    $scope.company.accountSum = 0;
    $scope.company.addSum = 0;
    var currentDate = new Date;
    currentDate.setMonth(currentDate.getMonth() + 2);
    $scope.company.dateBoundaryRefill = currentDate.getFullYear() + "-" + currentDate.getMonth() + "-" + currentDate.getDate();
    $scope.addMoney = {};
    $scope.addMoney.doClick = function(){
        $scope.showAddSumInput = true;
        $scope.company.addSum = $scope.company.addSum - 0;
        $scope.company.addMoney = $scope.company.addMoney - 0;
        $scope.company.addSum += $scope.company.addMoney;
        $scope.company.addMoney = "";
        $('#modal-add-sum').modal('hide');
    }
    $scope.save = {};
    $scope.save.doClick = function () {
        var response = $http({
            method: "post",
            url: "/EmployeeService/company/saveCompanyCreate",
            data: {
                companyName: $scope.company.companyName,
                dateBoundaryRefill: $scope.company.dateBoundaryRefill,
                addSum: $scope.company.addSum,
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


    $scope.cancel = {};
    $scope.cancel.doClick = function () {
        $location.path('/companyList');
        $location.replace();
    }
});

app.controller("companyCorrectController", function ($scope, $http, $routeParams, $location) {
    $scope.showAddSumInput = false;
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

        var companyStatus = $http({
            method: "get",
            url: "/EmployeeService/company/companyStatusList",
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json'
        });
        companyStatus.success(function (data) {
            $scope.statusList = data;
            data.forEach(selectStatus);
            function selectStatus(element, index) {
                if (element.companyStatusEnum == $scope.company.companyStatus) {
                    $scope.company.companyStatus = $scope.statusList[index].companyStatusEnum;
                }
            }
        });
    });

    $scope.addMoney = {};
    $scope.addMoney.doClick = function(){
        $scope.showAddSumInput = true;
        $scope.company.addSum = $scope.company.addSum - 0;
        $scope.company.addMoney = $scope.company.addMoney - 0;
        $scope.company.addSum += $scope.company.addMoney;
        $scope.company.addMoney = "";
        $('#modal-add-sum').modal('hide');
    }

    $scope.save = {};
    $scope.save.doClick = function () {
        var response = $http({
            method: "post",
            url: "/EmployeeService/company/saveCompanyUpdate",
            data: {
                id: $scope.company.id,
                companyName: $scope.company.companyName,
                companyStatus: $scope.company.companyStatus,
                accountSum: $scope.company.accountSum,
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


    $scope.cancel = {};
    $scope.cancel.doClick = function () {
        $location.path('/companyList');
        $location.replace();
    }
});


function showModalAddSum() {
    $('#modal-add-sum').modal('show');
}

function hideModalAddSum() {
    $('#modal-add-sum').modal('hide');
}
