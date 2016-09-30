(function() {
  'use strict';

    angular.module('app')
      .controller('signUpController', signUpController);

    function signUpController($scope) {
      
      var user = {
        email: '',
        username: '',
        password: '',
        confirmPassword: ''
      };

      $scope.submitForm = function() {
        console.log(JSON.stringify(this.user));
      };

    }

    function submitForm() {
    }

})();
