(function() {
  'use strict';

  angular.module('app')
    .component('rating', {
      templateUrl: 'rating/rating.html',
      controller: function($scope) {
        $scope.rate = 0;
        $scope.favorite = false;
        $scope.favoriteIcon = "star_border";
        $scope.favoriteLeft = false;

        $scope.starClick = function() {
          $scope.favorite = !$scope.favorite;
          $scope.favoriteIcon = ($scope.favorite ? "star" : "star_border");
        }

        $scope.setFavorite = function(fav){ $scope.favorite = fav; }
        $scope.getFavorite = function(){ return $scope.favorite; }
        $scope.setRate = function(r){ $scope.rate = r; }
        $scope.getRate = function(){ return $scope.rate; }
        $scope.setFavoriteLeft = function(){ $scope.favoriteLeft= true; }
        $scope.setFavoriteRight = function(){ $scope.favoriteRight = false; }
      }
    });
})();
