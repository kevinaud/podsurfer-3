(function() {
  'use strict';

  angular
    .module('app')
    .service('$user', userService);

  userService.$inject = ['$http', '$window', '$state'];

  function userService($http, $window, $state){

    var ref = this;
    var subscribers = [];

    let storedToken = localStorage.getItem('token');
    if (storedToken !== null) {
      getUserInfo(storedToken);
    }

    var exports = {
      signUp: signUp,
      login: login,
      signOut: signOut,
      getUserInfo: getUserInfo,
      getUserPreferences: getUserPreferences,
      updatePreferences: updatePreferences,
      subscribe: subscribe,
      preferences: {},
      auth: false,
      token: "",
      name: "",
      email: "",
      _id: ""
    };

    return exports;

    function subscribe(subscriber) {
      subscribers.push(subscriber)
    }

    function notify(){
      subscribers.forEach(function(subscriber) {
        subscriber.update();
      });
    }

    function login(creds){

      return $http(makePostRequest(creds, '/login')).then(function(response) {

        console.log('SUCCESS', response);
        if(response.data.success) {
          var msg = JSON.parse(response.data.message);
          var err = getError(msg);

          if(err === "success"){
            localStorage.setItem('token', msg.token);
            localStorage.setItem('authserv','podsurfer');
            getUserInfo(msg.token).then(function(response){
              console.log('user info', response);
              return response;
            });
            notify();
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
            localStorage.setItem('token', msg.token);
            localStorage.setItem('authserv','podsurfer');
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
      var authserv = localStorage.getItem('authserv');
      if(localStorage.getItem('authserv') === 'facebook') {
        $window.fbLogout(function () {
          localStorage.clear();
          exports.auth = false;
          exports.email = "";
          exports.name = "";
          exports.token = "";
          exports._id = "";
          $state.go('home');
        });
      }
      else {
        localStorage.clear();
        exports.auth = false;
        exports.email = "";
        exports.name = "";
        exports.token = "";
        exports._id = "";
        $state.go('home');
      }
    }

    function getUserInfo(token) {
      var authserv = localStorage.getItem('authserv');
      console.log('authserv',authserv);

      return $http({
        method: "GET",
        url: '/user',
        headers: {
          'Authorization': "Bearer " + token,
          'Server': authserv ? authserv : "podsurfer"
        }
      })
        .then(
          function(response){

            console.log(response.data.message);
            try {
              var userInfo = JSON.parse(response.data.message);
              exports.auth = true;
              exports.token = token;
              exports._id = userInfo._id;
              exports.name = userInfo.name;
              exports.email = userInfo.email;
              return "success";
            }
            catch(err){
              if (response === "Not Found" || response === "Unauthorized")
              {
                exports.auth = false;
                exports.token = "";
                localStorage.clear();
                return response;
              }
            }
          }, function(response) {
            return "An unexpected error occurred";
          });
    }

    function getUserPreferences(token) {
      return $http({
        method: "GET",
        url: '/user/preferences',
        headers: {
          'Authorization': "Bearer " + token,
          'Server': localStorage.getItem('authserv')
        }
      })
        .then(
          function(response){

          if(response.data.success) {
            
            exports.preferences = response.data.preferences;
            return response.data.preferences;
          }
          else {
            return {};
          }

        }, function(error) {
          console.log(error);
          return {};
        });
    }

    function updatePreferences(tags) {

      if(!exports.auth) {
        return;
      }

      tags.forEach(function(tag) {

        if(exports.preferences.hasOwnProperty(tag)) {
          exports.preferences[tag]++;
        } else {
          exports.preferences[tag] = 1;
        }
  
      });

      $http(makeAuthorizedPostRequest(exports.preferences, '/user/preferences', exports.token))
      .then(
        function(response){

          console.log(response);

        }, function(error) {
          console.log(error);
        });

    }

    function getError(message) {
      if (message.hasOwnProperty('token'))
        return "success";

      else if (message.hasOwnProperty('message'))
        return message.message;

      else if (message.hasOwnProperty('errors')) {
        if (message.errors.hasOwnProperty('email'))
          return message.errors.email.message;
      }
      else {
        return "An unexpected error occurred";
      }
    }

    function makePostRequest(payload, endpoint) {
      var req = {
        method: 'POST',
        url: endpoint,
        headers: { 'Content-Type': 'application/json' },
        data: payload
      };

      return req;
    }

    function makeAuthorizedPostRequest(payload, endpoint, token) {
      var req = {
        method: 'POST',
        url: endpoint,
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + token,
          'Server': localStorage.getItem('authserv')
        },
        data: payload
      };
      return req;
    }

  }
})();
