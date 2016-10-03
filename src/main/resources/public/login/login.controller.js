(function() {
  'use strict';

  angular.module('app')
    .controller('loginController', ['$scope','$http', function($scope, $http)
    {
      var user = {
        username: '',
        password: ''
      };

      if(user.email.length() > 0 && user.name.length() > 0
        && user.password.length() > 0
        && user.confirmPassword.length() === user.password.length())
      {
        $scope.submitForm = function()
        {
          var payload = JSON.stringify(user);
          var req = {
            method: 'POST',
            url: 'http://localhost:8080/login',
            headers: { 'Content-Type': 'application/json' },
            data: payload
          }

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
      }
    }]);
})();
