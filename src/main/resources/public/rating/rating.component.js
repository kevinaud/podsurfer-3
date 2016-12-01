(function() {
  'use strict';

  angular.module('app')
    .component('rating', {
      templateUrl: 'rating/rating.html',
      controller: function($scope) {
        
        this.rate;
        this.interactive;

        $scope.rate = this.rate;
        $scope.interactive = this.interactive;

        $scope.setRate = function(r){ 

          if(this.interactive === true) {
            this.rate = r;
            $scope.rate = this.rate;
          }

        }

        $scope.getRate = function(){ 
          return this.rate; 
        }

        this.$onChanges = function (changesObj) {

          if (changesObj.rate) {
            this.rate = changesObj.rate.currentValue;
            $scope.rate = changesObj.rate.currentValue;
            onUpdate(this.rate);
          }
          
        };

        this.update = function(rating) {

        }

        /*$scope.favorite = false;
        $scope.favoriteIcon = "star_border";
        $scope.favoriteLeft = false;

        $scope.starClick = function() {
          $scope.favorite = !$scope.favorite;
          $scope.favoriteIcon = ($scope.favorite ? "star" : "star_border");
        }

        $scope.setFavorite = function(fav){ $scope.favorite = fav; }
        $scope.getFavorite = function(){ return $scope.favorite; }
        $scope.setFavoriteLeft = function(){ $scope.favoriteLeft= true; }
        $scope.setFavoriteRight = function(){ $scope.favoriteRight = false; }*/
      },
      bindings: {
        rate: '=',
        interactive: '<',
        onUpdate: '&'
      }
    });
})();
