(function() {
  'use strict';

    angular.module('app')
      .controller('signUpController', ['$scope', '$user', function($scope, $user) {
      
      $scope.user = {
        email: '',
        name: '',
        password: '',
        password_verify: ''
      };

      $scope.responseError = false;

      $scope.submitForm = function() {
        $scope.responseError = true;
        $user.signUp(this.user);

      };

    }]);

})();
