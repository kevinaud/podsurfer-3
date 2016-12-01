'use strict'

angular.module('app')
  .component('loginCard', {
    templateUrl: 'loginCard/loginCard.html',
    controller: ['$scope', '$user', '$state', '$api',
      function($scope, $user, $state, $api){

        this.$onInit = function(){
          if($user.auth)
            $state.go('home');
        }

        $scope.user = {
          email: '',
          password: ''
        };

        $scope.error = false;
        $scope.errorMessage = "";

        $scope.submitForm = function() {
          console.log("logging into: ", $api.getUrl());

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
