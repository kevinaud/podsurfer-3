(function() {
  'use strict';

    angular.module('app')
      .controller('searchController', ['$scope', '$stateParams', '$search',
        function($scope, $stateParams, $search) {

        $scope.query = $stateParams.query;

        $scope.numEpisodeHits = 0;
        $scope.numPodcastHits = 0;

        $scope.podcastHits;
        $scope.episodeHits;

        this.$onInit = function () {

          $scope.responseReceived = false;

          $search.search($stateParams.query).then(
            function(hits){

              console.log(hits);

              hits.map(function(hit){
                let highlights = {};

                if (hit.highlight.hasOwnProperty('name')) {
                  highlights.name = hit.highlight.name[0];
                }

                if (hit.highlight.hasOwnProperty('description')) {
                  highlights.description = hit.highlight.description[0];
                }

                let source = Object.assign( hit._source, highlights );

                console.log(source);

              });

              $scope.podcastHits = hits.filter(function(hit){
                return hit._type === "podcast";
              });

              $scope.episodeHits = hits.filter(function(hit){
                return hit._type === "episode";
              });

              $scope.numPodcastHits = $scope.podcastHits.length;
              $scope.numEpisodeHits = $scope.episodeHits.length;

              $scope.responseReceived = true;
            },
            function(){
              console.log("an unexpected error occured");
            }
          );
        

      
        };

    }]);

})();
 