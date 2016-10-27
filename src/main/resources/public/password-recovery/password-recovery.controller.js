(function(){
  'use strict';

  angular.module('app')
    .controller('passwordRecoveryController', ['$scope', '$requestFactory',
      function($scope,$requestFactory){

        $scope.email = "";
        $scope.error = false;
        $scope.errorMesage = "";

        $scope.submitForm = function() {
          return $http($requestFactory.makePostRequest($scope.email, 'password-recovery'))
            .then(function(response){
              console.log("SUCCESS", response);
              $scope.error = response.success;
              $scope.errorMesage = response.message;
            }, function(response){
              console.log('Error',response);
              $scope.error = true;
              $scope.errorMessage = "An unexpected error occurred";
            });
        }
      }
    ]);
})();
