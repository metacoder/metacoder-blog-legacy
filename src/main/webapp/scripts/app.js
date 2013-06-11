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

    loadList = function() {

        $http.get('/services/blogEntryService/numRows').success(function(data, status, headers, config) {

            $scope.numrows = data;

            if(data > 0){
                $http.get('/services/blogEntryService/entries?pageSize='+data+'&pageNumber=0').success(function(data){
                    $scope.entryList = data;
                }).error(genericAjaxErrorHandler);
            }
        }).error(genericAjaxErrorHandler);

    };

    loadList();

    $scope.deletePost = function(number){
        if(confirm("Beitrag " + number + " wirklich loeschen?")){
            $http.delete("/services/blogEntryService/delete?id="+number).success(loadList).error(genericAjaxErrorHandler);
        }
    }

}