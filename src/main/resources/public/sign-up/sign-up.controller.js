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
        $user.signUp(this.user);
        console.log(this.user);

      };

    }]);

})();
