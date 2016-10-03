(function() {
  'use strict';

    angular.module('app')
      .controller('signUpController', ['$scope', '$http', function($scope, $http) {
      
      $scope.user = {
        email: '',
        name: '',
        password: '',
        confirmPassword: ''
      };

      $scope.error = "";

      $scope.submitForm = function() {

        //if all fields completed
        if($scope.user.email.length > 0 && $scope.user.name.length > 0
          && $scope.user.password.length > 0 && $scope.user.confirmPassword.length > 0) {

          //if password confirmation is same length as password
          if($scope.user.confirmPassword.length === $scope.user.password.length) {

            $scope.error = "";
            var payload = JSON.stringify($scope.user);
            console.log('PAYLOAD', payload);

            var req = {
              method: 'POST',
              url: 'http://localhost:8080/sign-up',
              headers: {
                'Content-Type': 'application/json'
              },
              data: payload
            };

            $http(req).then(function (response) {
              console.log('SUCCESS', response);

              var message = JSON.parse(response.data.message);
              console.log('DATA', response.data);
              console.log('MESSAGE', message)

              if(response.data.success === false || message.name === "ValidationError") {
                console.log('ERRORS', message.errors);
                console.log('PASSWORD', message.errors.password);
                console.log('EMAIL', message.errors.email);
                $scope.error = message.message;
              }


            }, function (response) {
              console.log('ERROR', response);
              ActiveXObject
            });

          }
          else {
            $scope.error = "Passwords must be the same length";
          }

        }
        else{
          $scope.error = "Require fields not complete";
        }

      };

    }]);

})();
