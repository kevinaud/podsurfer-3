(function() {
  'use strict';

  angular
    .module('app')
    .service('homeEndpoints', homeEndpoints);

  homeEndpoints.$inject = ['$http'];

  function homeEndpoints($http) {
    var exports = {
      getZen: getZen
    };

    return exports;

    function getZen() {
      return $http.get('https://api.github.com/zen');
    }
  }
})();
