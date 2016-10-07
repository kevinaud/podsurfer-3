(function() {
  'use strict';

    angular.module('app')
      .controller('loginController', ['$scope','$user', function($scope, $user) {


      $scope.user = {
        username: '',
        password: ''
      };

      $scope.responseError = false;

      $scope.submitForm = function() {
        $user.login(user);
      }
    }]);

})();
