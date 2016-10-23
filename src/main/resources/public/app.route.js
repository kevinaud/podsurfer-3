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
        .state('podcasts', {
          url: '/podcasts/:podcastId',
          templateUrl: 'podcast-detail/podcast-detail.html',
          controller: 'podcastDetailController',
          controllerAs: 'podcast-detail'
        })
        .state('add-podcast', {
          url: '/add-podcast',
          templateUrl: 'add-podcast/add-podcast.html',
          controller: 'addPodcastController',
          controllerAs: 'add-podcast'
        })
        .state('episodes', {
          url: '/episodes/:podcastId/:episodeNumber',
          templateUrl: 'episode-detail/episode-detail.html',
          controller: 'episodeDetailController',
          controllerAs: 'episode-detail'
        })
        .state('search', {
          url: '/search?query',
          templateUrl: 'search/search.html',
          controller: 'searchController',
          controllerAs: 'search'
        });

    };

})();
