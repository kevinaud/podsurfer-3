(function() {
  'use strict';

    angular.module('app')
      .controller('episodeDetailController', [ '$scope', '$stateParams', '$podcast',
        function($scope, $stateParams, $podcast) {

        let episodeId = $stateParams.episodeId;

        $scope.episodeId = episodeId;

        $scope.responseReceived;
        $scope.ratingReceived = false;

        $scope.podcast;
        $scope.episodes;

        this.$onInit = function () {

          $podcast.getPodcastByEpisodeId($stateParams.episodeId)
            .then(function(response){
              
            console.log('PODCAST', response);
            $scope.podcast = response._source;

            return response;

          }).then(function(podcast) {

            $podcast.getEpisodeById(podcast._Id, $scope.episodeId)
              .then(function(response) {

                console.log('EPISODE', response);
                $scope.episode = response._source;
                $scope.responseReceived = true;

            });

          });

          /*$scope.responseReceived = false;

          $podcast.getPodcast($stateParams.podcastId).then(
            function(podcast) {
              $scope.podcast = podcast._source;
              console.log(podcast)
              $scope.responseReceived = true;
            },
            function(error) {
              console.log(error);
            }
          );

          $podcast.getPodcastEpisodeByNumber($stateParams.podcastId, $stateParams.episodeNumber).then(
            function(episode) {
              $scope.episode = episode._source;
              console.log(episode);
            },
            function(error) {
              console.log(error);
            }
          );*/
      
        };

    }]);

})();
 