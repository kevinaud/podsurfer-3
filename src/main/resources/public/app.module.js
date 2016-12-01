(function() {
    'use strict';

    angular.module('app', [
        'ui.router',
        'ngMaterial',
        'ngSanitize',
        'chart.js'
    ]).config(function($mdThemingProvider) {

        $mdThemingProvider.theme('default')
            .primaryPalette('grey', {
                'hue-1': '600',
                'hue-2': '300',
                'hue-3': '100'
            })
            .warnPalette('red')
            .foregroundPalette['3'] = 'rgba(0,0,0,1)';
    });

})();
