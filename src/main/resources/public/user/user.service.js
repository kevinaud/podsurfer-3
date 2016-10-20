(function() {
  'use strict';

  angular
    .module('app')
    .service('$user', userService);

  userService.$inject = ['$http']

  function userService($http) {

    var exports = {
      signUp: signUp,
      login: login
    };

    var auth = false;

    return exports;

    function login(user){
      let payload = JSON.stringify({
        username: user.username,
        password: user.password
      });

      $http(makePostRequest(payload)).then(function(response) {

        console.log('SUCCESS', response);
        let msg = JSON.parse(response.data.message);
        console.log(msg.token);

      }, function(response){
        console.log('ERROR', response);
      });
    }

    function signUp(user){

      let payload = JSON.stringify({
        email: user.email,
        name: user.name,
        password: user.password
      });

      return $http(makePostRequest(payload, '/sign-up')).then(function(response){
        console.log('SUCCESS', response);

        let msg = JSON.parse(response.data.message);
        console.log(msg.token);
        console.log('MESSAGE', msg);
        if(msg.hasOwnProperty('token'))
          return "success";
        else
          return msg.message;

      }, function(response){
        console.log('ERROR', response);
        return "An unexpected error occurred";
      });
    }

    function makePostRequest(payload, endpoint) {
      var req = {
        method: 'POST',
        url: 'http://localhost:8080' + endpoint, //https://podsurfer3.herokuapp.com/sign-up',
        headers: { 'Content-Type': 'application/json' },
        data: payload
      }

      return req;
    }

    function makeAuthorizedPostRequest(payload, endpoint, token) {
      var req = {
        method: 'POST',
        url: 'http://localhost:8080' + endpoint, //https://podsurfer3.herokuapp.com/sign-up',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + token
        },
        data: payload
      }

      return req;

    }

  }
})();
