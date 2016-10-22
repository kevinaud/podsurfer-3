(function() {
  'use strict';

    angular.module('app')
      .controller('podcastDetailController', [ '$scope', '$stateParams', '$podcast',
        function($scope, $stateParams, $podcast) {

        let podcastId = $stateParams.podcastId;

        $scope.podcastId = podcastId;
        $scope.responseReceived;

        $scope.podcast;
        $scope.episodes;
        $scope.reviews;

        this.$onInit = function () {

          $scope.responseReceived = false;

          $podcast.getPodcast($stateParams.podcastId).then(
            function(podcast) {
              console.log('PODCAST', podcast);
              $scope.podcast = podcast;
              $scope.responseReceived = true;
            },
            function(error) {
              console.log(error);
            }
          );

          $podcast.getEpisodesOfAPodcast($stateParams.podcastId).then(
            function(episodes) {
              console.log('EPISODES', episodes);
              $scope.episodes = episodes;
            },
            function(error) {
              console.log(error);
            }
          );

          $podcast.getReviewsOfAPodcast($stateParams.podcastId).then(
            function(reviews) {
              console.log('REVIEWS', reviews);
              $scope.reviews = reviews;
            },
            function(error) {
              console.log(error);
            }
          );
      
        };

    }]);

})();
 