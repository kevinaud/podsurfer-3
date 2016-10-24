(function() {
  'use strict';

    angular.module('app')
      .controller('signUpController', ['$scope', '$user', '$state',
        function($scope, $user, $state) {
      
      $scope.user = {
        email: '',
        name: '',
        password: '',
        password_verify: ''
      };

      $scope.error = false;
      $scope.errorMessage = "";

      $scope.submitForm = function() {

        var response = $user.signUp($scope.user);
        console.log('LOGINRESPONSE', response);
        response.then(function(message) {

          $scope.errorMessage = message;
          $scope.error = !($scope.errorMessage === 'success');
          console.log('Errormsg: ', $scope.errorMessage);
          console.log('error', $scope.error);

          if (!$scope.error) {
            console.log("redirecting to profile");
            //TODO: change redirect to profile
            $state.go('home');
          }
        }, function(msg) {
          $scope.error = true;
          $scope.errorMessage = "An unexpected error occurred";
        });
      };

    }]);

})();
