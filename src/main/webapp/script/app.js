var app = angular.module("EmployeeService", ['ngRoute', 'checklist-model', 'ngCookies', 'angularFileUpload']);

app.run(function ($rootScope, $cookieStore) {

    $rootScope.recordsOnPage = 5;

    $rootScope.isAuth = function () {
        var user = $cookieStore.get("userInfo");
        $rootScope.name = $cookieStore.get("name");
        var role = $cookieStore.get("role");
        switch (role) {
            case 'ROLE_HRM':
                $rootScope.role = 'HRM';
                break;
            case 'ROLE_ADMIN':
                $rootScope.role = 'Администратор';
                break;
            case 'ROLE_EMPLOYEE':
                $rootScope.role = 'Сотрудник';
                break;
            case 'ROLE_CEO':
                $rootScope.role = 'Руководитель компании';
                break;
            case 'ROLE_SUPERADMIN':
                $rootScope.role = 'Владелец сервиса';
                break;
        }
        var photoURL = $cookieStore.get("photoURL");
        $('#photoEmployeeNavbar').attr('src', photoURL);
        return !(angular.isUndefined && user == null);
    }

    $rootScope.hasAuthority = function (roles) {
        var user = $cookieStore.get("userInfo");
        if (typeof(user) == "undefined") {
            return false;
        }
        var userRole = user.role;
        return (roles.indexOf(userRole) > -1);
    }
});

app.config(function ($routeProvider) {
    $routeProvider
        .when('/login', {
            templateUrl: 'pages/authorization.html',
            controller: 'authorizationController'
        })
        .when('/employeeList', {
            templateUrl: 'pages/employee_list.html',
            controller: 'employeeListController'
        })
        .when('/employeeCreate', {
            templateUrl: 'pages/employee_create.html',
            controller: 'employeeCreateController'
        })
        .when('/employeeCorrect/:id', {
            templateUrl: 'pages/employee_create.html',
            controller: 'employeeCorrectController'
        })
        .when('/companyList', {
            templateUrl: 'pages/company_list.html',
            controller: 'companyListController'
        })
        .when('/companyCreate', {
            templateUrl: 'pages/company_create.html',
            controller: 'companyCreateController'
        })
        .when('/companyCorrect/:id', {
            templateUrl: 'pages/company_create.html',
            controller: 'companyCorrectController'
        })
        .when('/pay', {
            templateUrl: 'pages/pay.html',
            controller: 'payController'
        })
});

app.service('PagerService', function () {
    this.totalPageNumber = function (pageRecords, totalRecords) {
        var totalPageNumber = 1;
        if (typeof totalRecords != 'undefined') {
            totalPageNumber = Math.floor((totalRecords + pageRecords - 1) / pageRecords);
        }
        return (totalPageNumber == 0) ? 1 : totalPageNumber;
    }

    this.buildRange = function (totalPageNumber) {
        var pages = [];
        for (var i = 1; i <= totalPageNumber; i++) {
            pages.push(i);
        }
        return pages;
    }

    this.isPrevDisabled = function (currentPage) {
        return currentPage === 1 ? "disabled" : "";
    }

    this.isNextDisabled = function (currentPage, totalPageCountt) {
        return currentPage === totalPageCountt ? "disabled" : "";
    }

    this.isFirstDisabled = function (currentPage) {
        return currentPage === 1 ? "disabled" : "";
    }

    this.isLastDisabled = function (currentPage, totalPageCount) {
        return currentPage === totalPageCount ? "disabled" : "";
    }
});