(function() {
  'use strict';

  angular.module('app')
    .component('popular', {
      templateUrl: 'popular/popular.html',
      controller: [ '$scope', '$podcast', popularController ]
    });

    function popularController($scope, $podcast) {
      $scope.rows = [];
      $scope.waiting = false;

      this.$onInit = function () {
            
        $podcast.popular().then(
          (podcasts) => {
            $scope.waiting = false;
            $scope.rows = splitIntoRows(podcasts.slice(0,5));
          },
          (error) => {
            $scope.waiting = false;
            console.log('error', error);
          }
        );

      }

      function splitIntoRows(podcasts){
        let rows = [], size = 3;

        while (podcasts.length > 0)
            rows.push(podcasts.splice(0, size));

        return rows;
      }

    }

})();
