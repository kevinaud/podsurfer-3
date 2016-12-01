/**
 * Created by QB on 11/29/2016.
 */
(function() {
    'use strict';

    angular.module('app')
        .controller('userProfileController', [ '$scope', '$user',
            function($scope, $user) {

                $scope.user= $user;

                angular.module('userProfileDynamicHeight', ['ngMaterial']);

                $scope.capitalize=function(string){
                    string = string.toLowerCase();
                    return string.substring(0,1).toUpperCase()+string.substring(1);

                }

            }] );



})();