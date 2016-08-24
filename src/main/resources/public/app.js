(function() {
  'use strict';

    angular.module('app', ['ui.router']);

    angular.module('app')
      .config(function($stateProvider, $urlRouterProvider) {

      // For any unmatched url, redirect to /state1
      $urlRouterProvider.otherwise('/home');

      // Now set up the states
      $stateProvider
        .state('home', {
          url: '/home',
          templateUrl: 'home/home.html',
          controller: 'homeController',
          controllerAs: 'home'
        })
        .state('about', {
          url: '/about',
          templateUrl: 'about/about.html',
          controller: 'aboutController',
          controllerAs: 'about'
        })
        .state('contact', {
          url: '/contact',
          templateUrl: 'contact/contact.html',
          controller: 'contactController',
          controllerAs: 'contact'

        });
    });
})();
