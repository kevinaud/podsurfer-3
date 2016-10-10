(function() {
  'use strict';

  angular.module('app')
    .controller('ratingController', ['$scope', function($scope) {

    $scope.rate = 0;
    $scope.setRate = function(r) {
      $scope.rate = r;
    }

  }]);
})();
