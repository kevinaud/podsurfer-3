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
        console.log($scope.user);
        console.log('SUBMIT');

        //if all fields completed
        if($scope.user.email.length > 0 && $scope.user.name.length > 0
          && $scope.user.password.length > 0 && $scope.user.confirmPassword.length > 0) {

          //if password confirmation is same length as password
          if($scope.user.confirmPassword.length === $scope.user.password.length) {

            $scope.error = "";
            var payload = JSON.stringify($scope.user);

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

              var data = JSON.parse(response.data.message);
              console.log(data.token);

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
