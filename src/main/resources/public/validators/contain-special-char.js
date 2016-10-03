(function() {
  'use strict';

  angular
    .module('app')
    .directive("containSpecialChar", function(){
    
      return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, ele, attrs, ctrl){

          ctrl.$parsers.unshift(function(value) {

            if(value){

              var regex = /(?=.*[@#$%^&+=])/
              var valid = regex.test(value);

              ctrl.$setValidity('containSpecialChar', valid);

            }

            return valid ? value : undefined;
          });

        }
      }
  });

})();