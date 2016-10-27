(function() {
  'use strict';

  angular
    .module('app')
    .service('$user', userService);

  userService.$inject = ['$http', '$api', '$requestFactory'];

  function userService($http, $api, $requestFactory) {

    var exports = {
      signUp: signUp,
      login: login,
      signOut: signOut,
      passwordRecovery: passwordRecovery,
      auth: false,
      token: "",
      name: "",
      email: "",
      _id: ""
    };

    return exports;

    function passwordRecovery(email){
      return $http($requestFactory.makePostRequest(email, '/forgot-password')).then(function(respons) {
        console.log('SUCCESS', response);
        return response.message;

      }, function(response){
        console.log('ERROR', response);
        return "An unexpected error occurred";
      });
    }

    function login(creds){

      return $http($requestFactory.makePostRequest(creds, '/login')).then(function(response) {

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

      return $http($requestFactory.makePostRequest(creds, '/sign-up')).then(function(response){
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


  }
})();
