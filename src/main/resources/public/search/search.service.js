(function() {
  'use strict';

  angular
    .module('app')
    .service('$search', searchService);

  searchService.$inject = ['$http', '$state'];

  function searchService($http, $state) {

    var exports = {
      search: search
    };

    return exports;

    function search(query){

      var payload = {
        query: query
      }
      
      var req = {
        method: 'POST',
        url: 'http://localhost:8080/search',
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
