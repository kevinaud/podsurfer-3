(function() {
  'use strict';

  angular.module('app')
    .controller('loginController', ['$scope','$user', function($scope, $user) {

    $scope.user = {
      email: '',
      password: ''
    };

    $scope.responseError = false;

    $scope.submitForm = function() {
      $scope.responseError = true;
      $user.login($scope.user);
    }
  }]);

})();
