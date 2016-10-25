(function() {
  'use strict';

  angular
    .module('app')
    .service('$search', searchService);

  searchService.$inject = ['$http', '$state', '$api'];

  function searchService($http, $state, $api) {

    var exports = {
      search: search
    };

    return exports;

    function search(query){

      var payload = {
        query: query.toLowerCase()
      }
      
      var req = {
        method: 'POST',
        url: $api.getUrl() + '/search',
        headers: { 'Content-Type': 'application/json' },
        data: payload
      }

      return $http(req).then(function(response) {

        let hits = response.data.hits.hits;
        
        return hits;

      }, function(response){
        console.log('ERROR', response);
      });

    }

  }
})();
