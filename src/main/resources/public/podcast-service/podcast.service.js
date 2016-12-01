(function() {
  'use strict';

  angular
    .module('app')
    .service('$podcast', podcastService);

  podcastService.$inject = ['$http'];

  function podcastService($http) {

    var exports = {
      addPodcast: addPodcast,
      getPodcast: getPodcast,
      getPodcastEpisodeByNumber: getPodcastEpisodeByNumber,
      getEpisodesOfAPodcast: getEpisodesOfAPodcast,
      getReviewsOfAPodcast: getReviewsOfAPodcast,
      getPodcastByEpisodeId: getPodcastByEpisodeId,
      getEpisodeById: getEpisodeById,
      getNumberOfEpisodes: getNumberOfEpisodes,
      addEpisode: addEpisode
    };

    return exports;

    function addPodcast(podcast) {

      var req = {
        method: 'POST',
        url: '/podcast',
        headers: { 'Content-Type': 'application/json' },
        data: podcast
      };

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
        url: '/podcast/' + id,
        headers: { 'Content-Type': 'application/json' },
      };

      return $http(req).then(
        function(response) {
          console.log(response);

          return response.data;
        },
        function(error) {
          console.log(error);
        }
      );

    }

    function getPodcastEpisodeByNumber(podcastId, episodeNumber) {

      var req = {
        method: 'GET',
        url: '/podcast/' + podcastId + '/episodes/' + episodeNumber,
        headers: { 'Content-Type': 'application/json' },
      };

      return $http(req).then(
        function(response) {
          console.log(response);
          if (response.data.hasOwnProperty('hits') && (response.data.hits.total > 0)){
            return response.data.hits.hits[0];
          }
          else {
            return null;
          }
        },
        function(error) {
          console.log(error);
        }
      );

    }

    function getPopular() {

      var req = {
        method: 'GET',
        url: $api.getUrl() + '/podcast/popular',
        headers: { 'Content-Type': 'application/json' },
      };

      return $http(req).then(
        function(response) {
          console.log(response);
        },
        function(error) {
          console.log(error);
        }
      );

    }

    function getPodcastByEpisodeId(episodeId) {

      var req = {
        method: 'GET',
        url: '/episodes/' + episodeId + '/podcast'
      };

      return $http(req).then(
        function(response) {
          if (response.data.hasOwnProperty('hits') && (response.data.hits.total > 0)){
            return response.data.hits.hits[0];
          }
          else {
            return null;
          }
        },
        function(error) {
          console.log(error);
        }
      );

    }

    function getEpisodeById(podcastId, episodeId) {

      var req = {
        method: 'GET',
        url: '/episodes/' + podcastId + '/' + episodeId
      };

      return $http(req).then(
        function(response) {
          return response.data;
        },
        function(error) {
          console.log(error);
        }
      );

    }

    function getEpisodesOfAPodcast(podcastId) {

      var req = {
        method: 'GET',
        url: '/podcast/' + podcastId + '/episodes',
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
        url: '/podcast/' + podcastId + '/reviews',
        headers: { 'Content-Type': 'application/json' },
      };

      return $http(req).then(
        function(response) {
          console.log(response);

          var data = {
            reviews: response.data.hits.hits,
            avgRating: response.data.aggregations.avg_rating.value
          }

          return data;
        },
        function(error) {
          console.log(error);
        }
      );

    }

    function getNumberOfEpisodes(podcastId) {

      var req = {
        method: 'GET',
        url: '/podcast/' + podcastId + '/episodes',
        headers: { 'Content-Type': 'application/json' },
      };

      return $http(req).then(
        function(response) {
          console.log(response);

          return response.data.hits.hits.length;
        },
        function(error) {
          console.log(error);
        }
      );

    }

    function addEpisode(podcastId, episode) {

      var req = {
        method: 'POST',
        url: '/podcast/' + podcastId + '/episodes',
        headers: { 'Content-Type': 'application/json' },
        data: episode
      };

      return $http(req).then(
        function(response) {
          console.log(response);
        },
        function(error) {
          console.log(error);
        }
      );

    }

  }
})();
