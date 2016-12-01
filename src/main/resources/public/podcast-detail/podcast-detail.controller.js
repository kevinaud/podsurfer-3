(function() {
  'use strict';

    angular.module('app')
      .controller('podcastDetailController', [ '$scope', '$stateParams', '$podcast', '$user', '$http', '$api',
        function($scope, $stateParams, $podcast, $user, $http, $api) {

        let podcastId = $stateParams.podcastId;

        $scope.podcastId = podcastId;
        $scope.responseReceived;
        $scope.ratingReceived = false;

        $scope.podcast;
        $scope.episodes;
        $scope.reviews;

        $scope.numEpisodes;
        $scope.numReviews;

        $scope.$user = $user;

        $scope.review = {
          rating: 1,
          content: ""
        }

        this.rate;
        this.interactive;

        $scope.rate = 1;
        $scope.interactive = true;

        $scope.setRate = function(r){ 

          if(this.interactive === true) {
            this.rate = r;
            $scope.rate = r;
            $scope.review.rating = r;
            console.log(r);
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

        $scope.ratingUpdate = function(rating) {
          console.log('parent', rating);
        }

        this.$onInit = function () {

          $scope.responseReceived = false;

          $podcast.getPodcast($stateParams.podcastId).then(
            function(podcast) {
              $scope.podcast = podcast._source;
              $scope.podcast._id = podcast._id;
              $scope.responseReceived = true;
              $user.updatePreferences(podcast._source.tags);
            },
            function(error) {
              console.log(error);
            }
          );

          $podcast.getEpisodesOfAPodcast($stateParams.podcastId).then(
            function(episodes) {
              $scope.episodes = episodes;
              $scope.numEpisodes = episodes.length;
            },
            function(error) {
              console.log(error);
            }
          );

          $scope.submit = function (){
            console.log($scope.review)
                 var req = {
                  method: 'POST',
                  url: $api.getUrl() + '/podcast/' + podcastId + '/reviews',
                  headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + $user.token
                  },
                  data: $scope.review
                };
                return $http(req).then(
                  (response) => {
                    console.log(response);
                  },
                  (error) => {
                    console.log(error);
                  }
                );
          }

          $podcast.getReviewsOfAPodcast($stateParams.podcastId).then(
            function(reviews) {
              $scope.reviews = reviews.reviews;
              $scope.avgRating = reviews.avgRating;
              $scope.numReviews = reviews.reviews.length;

              console.log("numReviews", $scope.numReviews);

              $scope.ratingReceived = true;
              console.log($scope.avgRating);
            },
            function(error) {
              console.log(error);
            }
          );
      
        };

    }]);

})();
 