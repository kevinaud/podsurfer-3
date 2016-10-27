(function(){
  'use strict';

  angular
    .module('app')
    .service('$requestFactory', requestFactory);

  requestFactory.$inject = ['$api'];

  function requestFactory($api){

    var exports = {
      getError: getError,
      makePostRequest: makePostRequest,
      makeAuthorizedPostRequest:makeAuthorizedPostRequest
    };
    return exports;

    function getError(message) {
      if(message.hasOwnProperty('token'))
        return "success";

      else if (message.hasOwnProperty('message'))
        return message.message;

      else if(message.hasOwnProperty('errors')) {
        if(message.errors.hasOwnProperty('email'))
          return message.errors.email.message;
      } else
        return "An unexpected error occurred";
    }

    function makePostRequest(payload, endpoint) {
      var req = {
        method: 'POST',
        url: $api.getUrl() + endpoint,
        headers: { 'Content-Type': 'application/json' },
        data: payload
      };

      return req;
    }

    function makeAuthorizedPostRequest(payload, endpoint, token) {
      var req = {
        method: 'POST',
        url: $api.getUrl() + endpoint,
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + token
        },
        data: payload
      };
      return req;
    }
  }
})();