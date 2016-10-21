(function() {
  'use strict';

    angular.module('app')
      .config(config)

    config.$inject = ['$stateProvider', '$urlRouterProvider'];
    function config($stateProvider, $urlRouterProvider) {

      $urlRouterProvider.otherwise('/home');

      $stateProvider
        .state('home', {
          url: '/home',
          templateUrl: 'home/home.html',
          controller: 'homeController',
          controllerAs: 'home'
        })
        .state('login', {
          url: '/login',
          templateUrl: 'login/login.html',
          controller: 'loginController',
          controllerAs: 'login'
        })
        .state('sign-up', {
          url: '/sign-up',
          templateUrl: 'sign-up/sign-up.html',
          controller: 'signUpController',
          controllerAs: 'sign-up'
        })
        .state('sign-up', {
          url: '/sign-up',
          templateUrl: 'sign-up/sign-up.html',
          controller: 'signUpController',
          controllerAs: 'sign-up'
        });
    };

})();
