(function(){
  'use strict';

  angular.module('app')
    .component('forgot-password', {
      tempaletUrl: 'forgot-password/forgot-password.html',
      controller: function($scope, $user){

        $scope.click = function(){
          $user.email;
        }
      }
    });
})();
