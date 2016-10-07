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

    return exports;

    function login(user){

    }

    function signUp(user){

      let payload = JSON.stringify({
        email: user.email,
        name: user.name,
        password: user.password
      });

      var req = {
        method: 'POST',
        url: 'https://podsurfer3.herokuapp.com/sign-up',
        headers: {
          'Content-Type': 'application/json'
        },
        data: payload 
      };

      $http(req).then(function(response){
        console.log('SUCCESS', response);

        let data = JSON.parse(response.data.message);
        console.log(data.token);

      }, function(response){
        console.log('ERROR', response); ActiveXObject             
      });

    }

  }
})();
