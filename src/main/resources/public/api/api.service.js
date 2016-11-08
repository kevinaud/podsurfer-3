(function() {
  'use strict';

  angular
    .module('app')
    .service('$api', apiService);

  function apiService($http) {

    var exports = {
      getUrl: getUrl
    };

    return exports;

    function getUrl() {
      return "http://localhost:8080";
    }
  }
})();
