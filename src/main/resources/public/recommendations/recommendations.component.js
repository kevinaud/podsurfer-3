(function() {
  'use strict';

  angular.module('app')
    .component('recommendations', {
      templateUrl: 'recommendations/recommendations.html',
      controller: [ '$scope', '$user', '$recommendations', recommendationsController ]
    });

    function recommendationsController($scope, $user, $recommendations) {
      $scope.podcasts = [];
      $scope.waiting = false;

      this.$onInit = function () {
        let token = localStorage.getItem('token');
        if(token !== null) {
          $scope.waiting = true;
          $user.getUserPreferences(token).then((preferences) => {
            
            $recommendations.recommend(preferences).then(
              (podcasts) => {
                $scope.waiting = false;
                $scope.podcasts = podcasts;
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
      }

      function getRecommendations(email) {
        $scope.waiting = true;



      }

    }

})();
