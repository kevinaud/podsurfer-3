(function() {
  'use strict';

  angular
    .module('app')
    .service('$podcast', podcastService);

  podcastService.$inject = ['$http'];

  function podcastService($http) {

    var exports = {
      addPodcast: addPodcast,
      getPodcast: getPodcast
    };

    return exports;

    function addPodcast(podcast) {

      var req = {
        method: 'POST',
        url: 'http://localhost:8080/podcast',
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
        url: 'http://localhost:8080/podcast/' + id,
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

  }
})();
