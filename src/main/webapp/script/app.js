var app = angular.module("EmployeeService", ['ngRoute', 'checklist-model', 'ngCookies']);

app.run(function($rootScope, $cookieStore){

    $rootScope.recordsOnPage = 10;

    $rootScope.isAuth = function(){
        var user = $cookieStore.get("userInfo");
        return !(angular.isUndefined && user == null);
    }

    $rootScope.hasAuthority = function(roles){
        var user = $cookieStore.get("userInfo");
        if (typeof(user) == "undefined"){
            return false;
        }
        var userRole = user.role;
        return (roles.indexOf(userRole) > -1);
    }
});

app.config(function($routeProvider){
    $routeProvider
        .when('/login', {
            templateUrl: 'pages/authorization.html',
            controller: 'authorizationController'
        } )
        .when('/employeeList', {
            templateUrl: 'pages/employee_list.html',
            controller: 'employeeListController'
        } )
        .when('/employeeCreate', {
            templateUrl: 'pages/employee_create.html',
            controller: 'employeeCreateController'
        } )

        .when('/employeeCorrect/:id', {
            templateUrl: 'pages/employee_create.html',
            controller: 'employeeCorrectController'
        } )

});

