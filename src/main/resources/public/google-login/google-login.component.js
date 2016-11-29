'use strict'

angular.module('app')
  .component('google-login', {
    templateUrl: 'google-login/google-login.html',
    controller: [ '$scope', function($scope){
      $scope.onSignIn = function(user){
        console.log("success");
        var profile = googleUser.getBasicProfile();
        console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
        console.log('Name: ' + profile.getName());
        console.log('Image URL: ' + profile.getImageUrl());
        console.log('Email: ' + profile.getEmail());
      }

      $scope.signOut = function (){
        var auth2 = gapi.auth2.getAuthInstance();
        auth2.signOuth().then(function() {
          console.log("signed out");
        });

      }
    } ]
  });
