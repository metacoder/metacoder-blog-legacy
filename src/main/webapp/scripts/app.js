/**
 * Created with IntelliJ IDEA.
 * UserBO: becker
 * Date: 6/11/13
 * Time: 12:42 AM
 * To change this template use File | Settings | File Templates.
 */

function genericAjaxErrorHandler(data, status, headers, config){
    alert('error!' + data + " " + status + " " + headers + " " + config);
}
function OverviewCtrl($scope, $http){
    $scope.greeting = "Hello Felix!";

    $http.get('/services/numRows').success(function(data, status, headers, config) {

        $scope.numrows = data;


        $http.get('/services/entries?pageSize='+data+'&pageNumber=0').success(function(data){
            $scope.entryList = data;
        }).error(genericAjaxErrorHandler);

    }).error(genericAjaxErrorHandler);




}