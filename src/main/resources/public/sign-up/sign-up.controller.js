(function() {
  'use strict';

    angular.module('app')
      .controller('signUpController', signUpController);

    function signUpController() {
      
      var user = {
        email: '',
        username: '',
        password: '',
        confirmPassword: ''
      };

    }

})();
