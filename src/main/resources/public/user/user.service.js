(function() {
  'use strict';

  angular
    .module('app')
    .service('$user', userService);

  userService.$inject = ['$http', '$api']

  function userService($http, $api) {

    var exports = {
      signUp: signUp,
      login: login,
      signOut: signOut,
      auth: false,
      token: "",
      name: "",
      email: "",
      _id: ""
    };

    return exports;


    function login(creds){

      return $http(makePostRequest(creds, '/login')).then(function(response) {

        console.log('SUCCESS', response);
        if(response.data.success) {
          var msg = JSON.parse(response.data.message);

          var err = getError(msg);

          if(err === "success"){
            getUserInfo(msg.token).then(function(response){
              return response;
            });
          }
          return err;
        }
        else
          return response.data.message;
      }, function(response){
        console.log('ERROR', response);
        return "An unexpected error occurred";
      });
    }

    function signUp(creds){

      return $http(makePostRequest(creds, '/sign-up')).then(function(response){
        console.log('SUCCESS', response);
        if(response.data.success) {

          var msg = JSON.parse(response.data.message);

          var err = getError(msg);

          if(err === "success"){
            getUserInfo(msg.token).then(function(response){
              return response;
            });
          }
          return err;
        }
        else
          return response.data.message;
      }, function(response){
        console.log('ERROR', response);
        return "An unexpected error occurred";
      });

    }

    function signOut(){
      exports.auth = false;
      exports.email = "";
      exports.name = "";
      exports.token = "";
      exports._id = "";
    }

    function getUserInfo(token) {
      return $http({
        method: "GET",
        url: $api.getUrl() + '/user',
        headers: {'Authorization': "Bearer " + token}
      })
      .then(
        function(response){

              var userInfo = JSON.parse(response.data.message);
              if(response === "Not Found" || response === "Unauthorized") {
                exports.auth = false;
                exports.token = "";
                return response;
              }
              else {
                exports.auth = true;
                exports.token = token;
                exports._id = userInfo._id;
                exports.name = userInfo.name;
                exports.email = userInfo.email;
                return "success";
              }
              return response;
        }, function(response) {
          return "An unexpected error occurred";
        });
    }

    function getError(message) {
      if(message.hasOwnProperty('token'))
        return "success";

      else if (message.hasOwnProperty('message'))
        return message.message;

      else if(message.hasOwnProperty('errors')) {
        if(message.errors.hasOwnProperty('email'))
          return message.errors.email.message;
      }
      else
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
