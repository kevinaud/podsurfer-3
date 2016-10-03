(function() {
  'use strict';

  angular.module('app')
    .controller('loginController', ['$scope','$http', function($scope, $http)
    {
      $scope.user = {
        username: '',
        password: ''
      };

      $scope.error = "";

      $scope.submitForm = function() {

        console.log($scope.user);
        //if required fields not empty
        if($scope.user.username.length > 0 && $scope.user.password.length > 0) {
          console.log('valid form');
          var payload = JSON.stringify($scope.user);

          var req = {
            method: 'POST',
            url: 'http://localhost:8080/login',
            headers: { 'Content-Type': 'application/json' },
            data: payload
          };

          $http(req).then(

            function(response) {
              console.log('SUCCESS', response);

              var data = JSON.parse(response.data.message);
              console.log(data.token);
            },
            function(response) {
              console.log('ERROR', response);
            }
          );
        }
        else {
          console.log('invalid');
          $scope.error = "Required fields not completed"
        }
      }
    }]);
})();
