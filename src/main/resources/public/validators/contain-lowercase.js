(function() {
  'use strict';

  angular
    .module('app')
    .directive("containLowercase", function(){
    
      return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, ele, attrs, ctrl){

          ctrl.$parsers.unshift(function(value) {

            if(value){

              var regex = /(?=.*[a-z])/
              var valid = regex.test(value);

              ctrl.$setValidity('containLowercase', valid);

            }

            return valid ? value : undefined;
          });

        }
      }
  });

})();