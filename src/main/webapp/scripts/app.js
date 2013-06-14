angular.module('metacoderAdmin', ['ui.bootstrap'], function($routeProvider, $locationProvider) {
    $routeProvider.when('/admin/entries', {
        templateUrl: '/static/overview.html',
        controller: OverviewCtrl
    }).when('/admin/entries/newentry', {
        templateUrl: '/static/newOrEditEntry.html',
        controller: NewOrEditEntryCtrl
    }).when('/admin/entries/editentry/:entryId', {
        templateUrl: '/static/newOrEditEntry.html',
        controller: NewOrEditEntryCtrl
    }).when('/admin/users', {
        templateUrl: '/static/userOverview.html',
        controller: UserOverviewCtrl
    }).when('/admin/users/edit/:username', {
        templateUrl: '/static/newOrEditUser.html',
        controller: EditUserCtrl
    }).when('/admin/users/create', {
            templateUrl: '/static/newOrEditUser.html',
            controller: EditUserCtrl
    }).otherwise({'templateUrl': '/static/welcome.html'});

    $locationProvider.html5Mode(true);

});

function genericAjaxErrorHandler(data, status, headers, config){
    alert('error!' + data + " " + status + " " + headers + " " + config);
}

function AdminCtrl($scope, $route, $routeParams, $location){
    $scope.name = "AdminCtrl";
    $scope.$route = $route;
    $scope.$location = $location;
    $scope.$routeParams = $routeParams;
}

function NewOrEditEntryCtrl($scope, $http, $routeParams, $location){
    entryId = $routeParams.entryId;


    $scope.blogEntry = {};

    if(entryId === undefined){
        $scope.blogEntry.title = '';
        $scope.blogEntry.content = '';
    } else {
        $http.get('/services/entries/'+entryId).success(function($data){
            $scope.blogEntry.title = $data.title;
            $scope.blogEntry.content = $data.content;
            $scope.blogEntry.id = $data.id;
        }).error(genericAjaxErrorHandler);
    }

    $scope.save = function(){
        $http.post("/services/entries", $scope.blogEntry).success(function(){
            $location.path("/admin/entries");
        }).error(genericAjaxErrorHandler);
    }

}

function UserOverviewCtrl($scope, $http){

    $http.get("/services/users").success(function($data){
        $scope.userList = $data;
    }).error(genericAjaxErrorHandler);

}

function EditUserCtrl($scope, $http, $routeParams){
    $scope.username = $routeParams.username;
    $scope.password = "";

    $scope.savePassword = function(){
        password = $scope.password;
        $http.put("/services/users/"+$scope.username+"/password", password).success(function(){
            alert('Neues passwort gesetzt!');
            $scope.password = '';
        }).error(genericAjaxErrorHandler);
    }

    $scope.createNewUser = function(){
        newUser = {};
        newUser.username = $scope.newusername;
        newUser.password = $scope.newuserpassword;
        $http.put("/services/users", newUser).success(function(){alert('user created');}).error(genericAjaxErrorHandler);
    }
}

function OverviewCtrl($scope, $http){

    loadList = function() {
        $http.get('/services/entries/numRows').success(function(data, status, headers, config) {

            $scope.numrows = data;

            if(data > 0){
                $http.get('/services/entries?pageSize='+data+'&pageNumber=0').success(function(data){
                    $scope.entryList = data;
                }).error(genericAjaxErrorHandler);
            } else {
                    $scope.entryList = [];
            }
        }).error(genericAjaxErrorHandler);

    };

    loadList();

    $scope.deletePost = function(number){
        if(confirm("Beitrag " + number + " wirklich loeschen?")){
            $http.delete("/services/entries/"+number).success(loadList).error(genericAjaxErrorHandler);
        }
    }

}