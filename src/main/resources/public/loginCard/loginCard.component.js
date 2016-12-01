'use strict'

angular.module('app')
  .component('loginCard', {
    templateUrl: 'loginCard/loginCard.html',
    controller: ['$scope', '$user', '$state',
      function($scope, $user, $state){

        $scope.user = {
          email: '',
          password: ''
        };

        $scope.error = false;
        $scope.errorMessage = "";

        $scope.submitForm = function() {

          $user.login($scope.user).then(function(message) {

            $scope.errorMessage = message;
            $scope.error = !($scope.errorMessage === 'success');

            if(!$scope.error) {
              console.log("redirecting to home");
              $state.go('home');
            }
          }, function(msg) {
            $scope.error = true;
            $scope.errorMessage = "An unexpected error ocurred";
          });
        };
      }]
  });
