(function() {
  'use strict';

  angular
    .module('app')
    .service('$user', userService);

  userService.$inject = ['$http', '$api']

  function userService($http, $api) {

    var ref = this;
    var subscribers = [];

    console.log('initializing user service');
    let storedToken = localStorage.getItem('token');
    if (storedToken !== null && storedToken !== undefined) {
      getUserInfo(storedToken).then(() => {
        notify();
      });
    }

    var exports = {
      signUp: signUp,
      login: login,
      signOut: signOut,
      getUserInfo: getUserInfo,
      getUserPreferences: getUserPreferences,
      updatePreferences: updatePreferences,
      subscribe: subscribe,
      getFavorites: getFavorites,
      removeFavorite: removeFavorite,
      addFavorite: addFavorite,
      preferences: {},
      favorites: [],
      auth: false,
      token: "",
      name: "",
      email: "",
      _id: "",
      authserv: "",
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

        if(response.data.success) {
          var msg = JSON.parse(response.data.message);
          var err = getError(msg);

          if(err === "success"){
            localStorage.setItem('token', msg.token);
            exports.authserv = 'podsurfer';
            getUserInfo(msg.token).then(function(response){
              return response;
            });
            exports.authserv = "podsurfer";
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
            exports.authserv = 'podsurfer';
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
      localStorage.clear();
      exports.auth = false;
      exports.email = "";
      exports.name = "";
      exports.token = "";
      exports._id = "";
      exports.auth = "";
      notify();
    }

    function getUserInfo(token) {

        console.log('get user info');

        if(token) {
          console.log('token exists');
          return $http({
            method: "GET",
            url: $api.getUrl() + '/user',
            headers: {'Authorization': "Bearer " + token}
          })
          .then(
            function(response){

              console.log(response.data.messag);
              try {
                var userInfo = JSON.parse(response.data.message);
                exports.auth = true;
                exports.token = token;
                exports._id = userInfo._id;
                exports.name = userInfo.name;
                exports.email = userInfo.email;
                getFavorites().then(
                  (success) => {
                    console.log('SUCCESS', success);
                    notify();
                  },
                  (error) => {
                    console.log('ERROR', error);
                  }
                );
                notify();
                return "success";
              }
              catch(err){
                console.error(err);
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
    }

    function getUserPreferences(token) {
      return $http({
        method: "GET",
        url: $api.getUrl() + '/user/preferences',
        headers: {'Authorization': "Bearer " + token}
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

    function getUserPreferences(token) {
      return $http({
        method: "GET",
        url: $api.getUrl() + '/user/preferences',
        headers: {'Authorization': "Bearer " + token}
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

    function addFavorite(id) {

      if(!exports.auth) {
        return;
      }

      $http({
        method: 'POST',
        url: '/user/favorite/' + id,
        headers: { 'Authorization': 'Bearer ' + exports.token }
      })
      .then(
        function(response){
          console.log(response);
          exports.favorites.push(id);
          notify();
              
        }, function(error) {
          console.log(error);
        });

    }

    function removeFavorite(id) {

      $http({
        method: 'DELETE',
        url: '/user/favorite/' + id,
        headers: { 'Authorization': 'Bearer ' + exports.token }
      })
      .then(
        function(response){

          let index = exports.favorites.indexOf(id);
          if (index > -1) {
            exports.favorites.splice(index, 1);
          }

          notify();
          console.log(response);
          console.log('delete favorite', response);

        }, function(error) {
          console.log(error);
        });

    }

    function getFavorites() {

      if(exports.token){

        return $http({
          method: "GET",
          url: $api.getUrl() + '/user/favorite',
          headers: { 'Authorization': "Bearer " + exports.token }
        })
        .then(
          function(response){

            if(response.data.favorites) {
              
              console.log(response)
              exports.favorites = response.data.favorites;

              notify();
              return response.data.favorites;
            
            }
            else {
              return {};
            }

          }, function(error) {
            console.log(error);
            return {};
          });

      }

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
