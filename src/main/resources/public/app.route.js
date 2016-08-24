(function() {
    'use strict';

    angular
        .module('podsurfer')
        .config(config)
        .run(run);

    config.$inject = ['$stateProvider', '$urlRouterProvider', '$httpProvider'];

    function config($stateProvider, $urlRouterProvider, $httpProvider) {

      $stateProvider
        .state('root', {
            url: '/',
            abstract: true,
            views: {
                'main': {
                    templateUrl: 'layout/shell.html'
                },
                'header@root': {
                    templateUrl: 'layout/header.html',
                    // controller: 'HeaderController',
                    // controllerAs: 'header'
                }
            }
        })
        .state('root.feature', {
            url: 'feature',
            views: {
                'tab-content@root': {
                  templateUrl: 'feature/feature.html',
                  controller: 'FeatureController',
                  controllerAs: 'feature'
                }
            }
        })

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
})();
