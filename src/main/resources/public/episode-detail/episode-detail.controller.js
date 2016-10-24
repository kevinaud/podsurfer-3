(function() {
  'use strict';

    angular.module('app')
      .controller('episodeDetailController', [ '$scope', '$stateParams', '$podcast', '$user',
        function($scope, $stateParams, $podcast, $user) {

        let episodeId = $stateParams.episodeId;

        $scope.episodeId = episodeId;

        $scope.responseReceived;
        $scope.ratingReceived = false;

        $scope.podcast;
        $scope.episodes;

        $scope.formattedLength;

        $scope.$user = $user;

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
                $scope.formattedLength = formatLength($scope.episode.length);

                $scope.responseReceived = true;

            });

          });

          function formatLength(length) {

            console.log('length', length);
            let hours = Math.floor(length / 3600);
            console.log('hours', hours);
            length = length % 3600;
            console.log('length', length);

            let minutes = Math.floor(length / 60);
            console.log('minutes', minutes);
            length = length % 60;
            console.log('length', length);

            let seconds = length;

            let lengthString = "";
            
            lengthString += hours.toString();
          
            if(hours === 1){
              lengthString += " hour, ";
            }
            else {
              lengthString += " hours, ";
            }
          
            lengthString += minutes.toString();
          
            if(minutes === 1){
              lengthString += " minute, ";
            }
            else {
              lengthString += " minutes, ";
            }
            
            lengthString += seconds.toString();
          
            if(seconds === 1){
              lengthString += " second";
            }
            else {
              lengthString += " seconds";
            }

            return lengthString;

          }
      
        };

    }]);

})();
 