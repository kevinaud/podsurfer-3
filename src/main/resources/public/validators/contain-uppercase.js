(function() {
  'use strict';

  angular
    .module('app')
    .directive("containUppercase", function(){
    
      return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, ele, attrs, ctrl){

          ctrl.$parsers.unshift(function(value) {

            if(value){

              var regex = /(?=.*[A-Z])/
              var valid = regex.test(value);

              ctrl.$setValidity('containUppercase', valid);

            }

            return valid ? value : undefined;
          });

        }
      }
  });

})();