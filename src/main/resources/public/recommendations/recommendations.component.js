(function() {
  'use strict';

  angular.module('app')
    .component('recommendations', {
      templateUrl: 'recommendations/recommendations.html',
      controller: [ '$scope', '$user', '$recommendations', recommendationsController ]
    });

    function recommendationsController($scope, $user, $recommendations) {
      $scope.rows = [];
      $scope.waiting = false;

      this.$onInit = function () {

        $user.subscribe(this);

        let token = localStorage.getItem('token');
        if(token !== null) {
          initialize(token);
        }
      }

      this.update = function() {

        let token = $user.token;
        initialize(token);

      }

      function initialize(token) {
        $scope.waiting = true;
          $user.getUserPreferences(token).then((preferences) => {
            
            $recommendations.recommend(preferences).then(
              (podcasts) => {
                $scope.waiting = false;
                $scope.rows = splitIntoRows(podcasts.slice(0,6));
              },
              (error) => {
                $scope.waiting = false;
                console.log('error', error);
              }
            );

          }, (error) => {
            console.log('rec error', error);
          });
      }

      function splitIntoRows(podcasts){
        let rows = [], size = 3;

        while (podcasts.length > 0)
            rows.push(podcasts.splice(0, size));

        return rows;
      }

    }

})();
