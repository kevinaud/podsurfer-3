(function() {
  'use strict';

    angular.module('app')
      .controller('homeController', ['$scope','$user', function($scope, $user) {

        $scope.signupPrompt = 'New to PodSurfer? Create an account';
        this.$onInit = function() {
          $scope.user = $user;
        }
    }]);
})();