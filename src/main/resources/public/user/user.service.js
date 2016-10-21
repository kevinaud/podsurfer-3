(function() {
  'use strict';

  angular
    .module('app')
    .service('$user', userService);

  userService.$inject = ['$http'];

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

      $http(makePostRequest(payload)).then(function(response){
        console.log('SUCCESS', response);

        let data = JSON.parse(response.data.message);
        console.log(data.token);

      }, function(response){
        console.log('ERROR', response);
      });

    }

    function makePostRequest(payload) {
      var req = {
        method: 'POST',
        url: 'http://localhost:8080/sign-up',
        headers: { 'Content-Type': 'application/json' },
        data: payload
      }

      return req;
    }

  }
})();
