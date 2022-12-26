var App = angular.module('paymentApp', []);
App.controller('paymentCtrl',['$scope','$http','$q', function($scope, $http, $q) {

    $scope.showSubmitButton = false;
    $scope.productinfo = 'Online Course';
    $scope.firstname = '';
    $scope.email = '';
    $scope.phone = '';
    $scope.amount = '';
    $scope.surl = '';
    $scope.furl = '';
    $scope.key = '';
    $scope.hash = '';
    $scope.txnid = '';

    $scope.confirmPayment = function() {
        var url = 'http://localhost:8080/payment/payment-details';
        var data = {productInfo: $scope.productinfo, email: $scope.email, name: $scope.firstname, phone: $scope.phone, amount:$scope.amount};
        $http.post(url, data)
            .then(function (response) {
                    console.log(response.data);
                    $scope.txnid = response.data.txnId;
                    $scope.surl = response.data.sUrl;
                    $scope.furl = response.data.fUrl;
                    $scope.key = response.data.key;
                    $scope.hash = response.data.hash;
                    $scope.showSubmitButton = true;
                },
                function (errResponse) {
                    if (errResponse.status == -1) {
                        errResponse.status = 408;
                        errResponse.statusText = 'Server Timeout.';
                    }
                    alert(errResponse.status + ':' + errResponse.statusText);
                });
    }
}]);
