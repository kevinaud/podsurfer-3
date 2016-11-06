(function() {
    'use strict';

    angular.module('app')
        .controller('userProfileController', [ '$scope', '$user',
            function($scope, $user) {

             $scope.user= $user;

            angular.module('userProfileDynamicHeight', ['ngMaterial']);



            }]);

})();
 