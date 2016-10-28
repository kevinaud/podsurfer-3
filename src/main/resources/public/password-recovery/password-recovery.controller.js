(function(){
  'use strict';

  angular.module('app')
    .controller('passwordRecoveryController',
      ['$scope', '$requestFactory', '$user', '$http',
      function($scope,$requestFactory, $user, $http){

        $scope.email = "";
        $scope.error = false;
        $scope.errorMesage = "";

        this.$onInit = function(){
          if($user.auth)
            $state.go('home');
        }

        $scope.submitForm = function() {
          return $http($requestFactory.makePostRequest($scope.email, '/password-recovery'))
            .then(function(response){

              console.log("SUCCESS", response);
              $scope.error = !response.data.success;
              $scope.errorMesage = response.data.message;

            }, function(response){
              console.log('Error',response);
              $scope.error = true;
              $scope.errorMessage = "An unexpected error occurred";
            });
          }
        }
    ]);
})();
