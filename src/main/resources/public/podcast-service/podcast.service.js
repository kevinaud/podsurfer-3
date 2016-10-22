(function() {
  'use strict';

  angular
    .module('app')
    .service('$podcast', podcastService);

  podcastService.$inject = ['$http', '$api'];

  function podcastService($http, $api) {

    var exports = {
      addPodcast: addPodcast,
      getPodcast: getPodcast,
      getEpisodesOfAPodcast: getEpisodesOfAPodcast,
      getReviewsOfAPodcast: getReviewsOfAPodcast
    };

    return exports;

    function addPodcast(podcast) {

      var req = {
        method: 'POST',
        url: $api.getUrl(),
        headers: { 'Content-Type': 'application/json' },
        data: podcast
      }

      console.log(req);

      return $http(req).then(function(response) {

        console.log('SUCCESS', response);

        return response.data._id;

      }, function(response){
        console.log('ERROR', response);
      });
    }

    function getPodcast(id) {

      var req = {
        method: 'GET',
        url: $api.getUrl() + '/podcast/' + id,
        headers: { 'Content-Type': 'application/json' },
      };

      return $http(req).then(
        function(response) {
          console.log(response);

          return response.data._source;
        },
        function(error) {
          console.log(error);
        }
      );

    }

    function getEpisodesOfAPodcast(podcastId) {

      var req = {
        method: 'GET',
        url: $api.getUrl() + '/podcast/' + podcastId + '/episodes',
        headers: { 'Content-Type': 'application/json' },
      };

      return $http(req).then(
        function(response) {
          console.log(response);

          return response.data.hits.hits;
        },
        function(error) {
          console.log(error);
        }
      );

    }

    function getReviewsOfAPodcast(podcastId) {

      var req = {
        method: 'GET',
        url: $api.getUrl() + '/podcast/' + podcastId + '/reviews',
        headers: { 'Content-Type': 'application/json' },
      };

      return $http(req).then(
        function(response) {
          console.log(response);

          return response.data.hits.hits;
        },
        function(error) {
          console.log(error);
        }
      );

    }

  }
})();
