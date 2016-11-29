(function() {
  'use strict';

  angular.module('app')
    .component('recommendations', {
      templateUrl: 'recommendations/recommendations.html',
      controller: [ '$scope', '$user', recommendationsController]
    });

    function recommendationsController($scope, $user) {
      $scope.podcasts = [];
      $scope.waiting = true;

      this.$onInit = function () {
        let token = localStorage.getItem('token');
        if(token !== null) {
          $user.getUserInfo(token).then((success) => {
            console.log('rec success', success);
          }, (error) => {
            console.log('rec error', error);
          });
        }
      }

    }

})();
