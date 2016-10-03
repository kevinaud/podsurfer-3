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

        //if required fields not empty
        if($scope.user.username.length > 0 && $scope.user.password.length > 0) {
          $scope.error = "";
          var payload = JSON.stringify($scope.user);
          console.log('PAYLOAD', payload);

          var req = {
            method: 'POST',
            url: 'https://podsurfer3.herokuapp.com/login',
            headers: { 'Content-Type': 'application/json' },
            data: payload
          };

          $http(req).then(

            function(response) {
              console.log('SUCCESS', response);
              console.log('DATA',response.data);

              //var message = JSON.parse(response.data);
              //console.log('MESSAGE', message);

              if(response.data.success === false) {
                $scope.error = response.data.message;
              }
            },
            function(response) {
              console.log('ERROR', response);
            }
          );
        }
        else {
          $scope.error = "Required fields not completed"
        }
      }
    }]);
})();
