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
<<<<<<< HEAD
      let apiUrl = "http://localhost:8080";
      return apiUrl;
=======
      return "http://localhost:8080";
>>>>>>> f045051ff15f01863d94bf04e64ce2de263ddc47
    }
  }
})();
