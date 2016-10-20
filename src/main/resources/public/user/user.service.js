(function() {
  'use strict';

  angular
    .module('app')
    .service('$user', userService);

  userService.$inject = ['$http', '$state'];

  function userService($http, $state) {

    var exports = {
      signUp: signUp,
      login: login,
      hasAuth: hasAuth,
      getName: getName,
      getToken: getToken,
      getEmail: getEmail,
      setAuth: setAuth,
      setName: setName,
      setToken: setToken,
      setEmail: setEmail
    };

    return exports;

    var user = {
      name: null,
      email: null,
      password: null,
      token:null,
      auth: false
    };

    function hasAuth(){ return user.auth; }
    function getName(){ return user.name; }
    function getToken(){ return user.token; }
    function getEmail(){ return user.email; }
    function setAuth(a){ user.auth = a; }
    function setName(n){ user.name = n; }
    function setToken(t){ user.token = t; }
    function setEmail(e){ user.email = e; }

    function login(user){
      let payload = JSON.stringify({
        username: user.username,
        password: user.password
      });

      $http(makePostRequest(payload)).then(function(response) {

        console.log('SUCCESS', response);
        let msg = JSON.parse(response.data.message);
        console.log(msg.token);
        console.log(response);
        console.log("rerouting to home");
        $state.go('home');

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

      $http(makePostRequest(payload, '/sign-up')).then(function(response){
        console.log('SUCCESS', response);

        let data = JSON.parse(response.data.message);
        setToken(data.token);
        console.log(getToken());
        console.log(data);

        console.log("rerouting to profile");
        //change to profile page
        $state.go('home');

      }, function(response){
        console.log('ERROR', response); ActiveXObject             
      });

    }

    function makePostRequest(payload, endpoint) {
      var req = {
        method: 'POST',
        url: 'http://localhost:8080' + endpoint,//'https://podsurfer3.herokuapp.com/sign-up',
        headers: { 'Content-Type': 'application/json' },
        data: payload
      };

      return req;
    }

  }
})();
