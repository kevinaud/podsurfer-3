(function() {
  'use strict';

  angular.module('app')
    .controller('userProfileController', [ '$scope', '$user', '$state',
      function($scope, $user, $state) {

        this.$onInit = function(){
          if(!$user.auth)
            $state.go('home');
        }

        $scope.user= $user;

        angular.module('userProfileDynamicHeight', ['ngMaterial']);

      }]);
})();