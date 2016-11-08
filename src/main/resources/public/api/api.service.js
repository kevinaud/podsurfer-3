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
      let apiUrl = "http://localhost:8080";
      return apiUrl;
    }
  }
})();
