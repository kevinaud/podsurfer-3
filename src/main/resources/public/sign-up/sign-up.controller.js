(function() {
  'use strict';

    angular.module('app')
      .controller('signUpController', ['$scope', '$user', '$state',
        function($scope, $user, $state) {

      this.$onInit = function(){
        if($user.auth)
          $state.go('home');
      }

      $scope.user = {
        email: '',
        name: '',
        password: '',
        password_verify: ''
      };

      $scope.error = false;
      $scope.errorMessage = "";

      $scope.submitForm = function() {

        $user.signUp($scope.user).then(function(message) {

          $scope.errorMessage = message;
          $scope.error = !($scope.errorMessage === 'success');

          if (!$scope.error) {
            console.log("redirecting to profile");
            //TODO: change redirect to profile
            $state.go('userProfile');
          }
        }, function(msg) {
          $scope.error = true;
          $scope.errorMessage = "An unexpected error occurred";
        });
      };

    }]);

})();
