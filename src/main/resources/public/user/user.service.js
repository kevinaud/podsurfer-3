(function() {
  'use strict';

  angular
    .module('app')
    .service('$user', userService);

  userService.$inject = ['$http']

  function userService($http) {

    var exports = {
      signUp: signUp,
      login: login,
      user: user
    };

    var user = {
      auth: false,
      token: "",
      name: "",
      email: "",
      _id: ""
    };

    return exports;

    function login(user){
      let payload = JSON.stringify({
        username: user.username,
        password: user.password
      });

      return $http(makePostRequest(payload, '/login')).then(function(response) {

        console.log('SUCCESS', response);
        let msg = JSON.parse(response.data.message);
        console.log('TOKEN', msg.token);
        console.log('MESSAGE', msg);

        var err = getError(msg);

        return err;
      }, function(response){
        console.log('ERROR', response);
        return "An unexpected error occurred";
      });
    }

    function signUp(user){

      var payload = JSON.stringify({
        email: user.email,
        name: user.name,
        password: user.password
      });

      return $http(makePostRequest(payload, '/sign-up')).then(function(response){
        console.log('SUCCESS', response);

        var msg = JSON.parse(response.data.message);
        console.log(msg.token);
        console.log('MESSAGE', msg);
        console.log('ERROR', getError(msg));
        var err = getError(msg);

      }, function(response){
        console.log('ERROR', response);
        return "An unexpected error occurred";
      });
    }

    function getError(message) {
      if(message.hasOwnProperty('token'))
        return "success";

      if(message.hasOwnProperty('errors')) {
        if(message.errors.hasOwnProperty('email'))
          return message.errors.email.message;
      }
      else
        return "An unexpected error occurred";
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
