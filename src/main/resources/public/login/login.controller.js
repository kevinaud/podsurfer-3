(function() {
  'use strict';

  angular.module('app')
    .controller('loginController', ['$scope','$user','$state',
      function($scope, $user, $state) {

      this.$onInit = function(){
        if($user.auth)
          $state.go('home');
      }

    }]);

})();

