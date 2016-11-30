(function() {
  'use strict';

  angular.module('app')
    .component('preferences', {
      templateUrl: 'preferences-graph/preferences-graph.html',
      controller: [ '$scope', '$user', preferencesGraphController ]
    });

    function preferencesGraphController($scope, $user) {
      $scope.waiting = false;

      $scope.labels = [];
      $scope.data = [];
      $scope.chartOptions = {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        }
      };

      this.$onInit = function () {
        let token = localStorage.getItem('token');
        if(token !== null) {
          $scope.waiting = true;
          $user.getUserPreferences(token).then((preferences) => {

            var data = [];
            var labels = [];

            for (var tag in preferences) {
              if (preferences.hasOwnProperty(tag)) {

                labels.push(tag);
                data.push(preferences[tag]);

              }
            }

            labels = labels.sort(function(a, b) {
              return preferences[b] - preferences[a];
            });

            data.sort(function(a, b) {
              return b - a
            });

            console.log(labels);
            console.log(data);

            $scope.labels = labels.slice(0,9);
            $scope.data[0] = data.slice(0,9);

            $scope.waiting = false;

          }, (error) => {
            console.log('rec error', error);
          });
        }
      }

    }

})();
