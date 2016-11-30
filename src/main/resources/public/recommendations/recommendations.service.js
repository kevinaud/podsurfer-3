(function() {
  'use strict';

  angular
    .module('app')
    .service('$recommendations', recommendationsService);

  recommendationsService.$inject = ['$http']

  function recommendationsService($http) {

    var exports = {
      recommend: recommend
    };

    return exports;

    function recommend(preferences){

      return $http(makePostRequest(preferences, '/recommendations')).then(function(response) {

        console.log('SUCCESS', response);
        if(response.data.hasOwnProperty('hits')) {

          return response.data.hits.hits;

        }
        else
          return [];
      }, function(response){
        console.log('ERROR', response);
        return [];
      });
    }

    function makePostRequest(payload, endpoint) {
      var req = {
        method: 'POST',
        url: endpoint,
        headers: { 'Content-Type': 'application/json' },
        data: payload
      };

      return req;
    }

  }
})();
