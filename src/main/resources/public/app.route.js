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
    };

})();
