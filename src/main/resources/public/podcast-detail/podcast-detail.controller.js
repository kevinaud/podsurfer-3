(function() {
  'use strict';

    angular.module('app')
      .controller('podcastDetailController', [ '$scope', '$stateParams', '$podcast', '$user',
        function($scope, $stateParams, $podcast, $user) {

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

        $scope.favorited = false;

        this.$onInit = function () {

          $scope.responseReceived = false;
          $user.subscribe(this);

          $podcast.getPodcast($stateParams.podcastId).then(
            function(podcast) {
              $scope.podcast = podcast._source;
              $scope.podcast._id = podcast._id;
              $scope.responseReceived = true;
              if($user.auth){
                $user.updatePreferences(podcast._source.tags);
              }
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

        this.update = function() {
          
          if ($user.favorites) {
            $user.favorites.forEach(function(id) {

              if(id === $scope.podcastId) {
                $scope.favorited = true;
              }

            });
          }

        }

        $scope.toggleFavorited = function() {
          
          $podcast.addFavorite(exports.id);

        }

    }]);

})();
 