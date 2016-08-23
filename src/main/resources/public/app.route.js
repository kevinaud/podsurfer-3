(function() {
    'use strict';

    angular
        .module('app')
        .config(config)
        .run(run)
        .controller('AppCtrl', AppCtrl);

    config.$inject = ['$stateProvider', '$urlRouterProvider', '$httpProvider'];
    AppCtrl.$inject = ['$rootScope', '$scope', '$uibModal'];

    function config($stateProvider, $urlRouterProvider, $httpProvider) {

        $stateProvider
            .state('root', {
                url: '/',
                abstract: true,
            })
            .state('root.feature', {
                url: 'feature',
                abstract: true,
                views: {
                    'content@root': {
                      templateUrl: 'public/feature/feature.html',
                      controller: 'FeatureController',
                      controllerAs: 'feature'
                    }
                }
            });

        $urlRouterProvider.rule(function($injector, $location) {
            var path = $location.path(),
                search = $location.search();
            if (path !== '/' && path[path.length - 1] === '/') {
                if (search === {}) {
                    return path.substr(0, path.length - 1);
                } else {
                    var params = [];
                    angular.forEach(search, function(v, k) {
                        params.push(k + '=' + v);
                    });
                    return path.substr(0, path.length - 1) + '?' + params.join('&');
                }
            }
        }).otherwise('/feature');

    }



    function run($rootScope, $state) {
        /*
         *  Implemented to handle resolve errors for page refresh events
         */
        $rootScope.$on('$stateChangeError', function(event, toState, toParams, fromState, fromParams) {
            $state.go('root.content');
        });
    }

    function AppCtrl($rootScope, $scope, $uibModal) {
      var myApp = this;

    }
})();
