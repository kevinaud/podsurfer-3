(function() {
  'use strict';

    angular.module('app')
      .controller('signUpController', ['$scope', '$http', function($scope, $http) {
      
      var user = {
        email: '',
        name: '',
        password: '',
        confirmPassword: ''
      };

      $scope.submitForm = function() {
        
        let payload = JSON.stringify({
          email: this.user.email,
          name: this.user.name,
          password: this.user.password
        });

        var req = {
          method: 'POST',
          url: 'http://localhost:8080/sign-up',
          headers: {
            'Content-Type': 'application/json'
          },
          data: payload 
        }

        $http(req).then(function(response){
          console.log('SUCCESS', response);

          let data = JSON.parse(response.data.message);
          console.log(data.token);

        }, function(response){
          console.log('ERROR', response); ActiveXObject             
        });

      };

    }]);

})();
