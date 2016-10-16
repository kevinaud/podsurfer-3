(function() {
  'use strict';

  angular.module('app')
    .component('rating', {
      templateUrl: 'rating/rating.html',
      controller: function($scope) {
        $scope.rate = 0;
        $scope.favorite = false;
        $scope.favoriteIcon = "star_border";

        $scope.starClick = function() {
          $scope.favorite = !$scope.favorite;
          $scope.favoriteIcon = ($scope.favorite ? "star" : "star_border");
          console.log("click");
          console.log($scope.favorite);
          console.log($scope.favoriteIcon);
        }
      },
      bindings:{
        /*
        rate: '=',
        favorite: '=',
        favoriteIcon: '@',
        starClick: '&'
        */
      }
    });
})();
