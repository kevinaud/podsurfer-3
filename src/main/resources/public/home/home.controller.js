(function() {
  'use strict';

    angular.module('app')
      .controller('homeController', homeController);

    homeController.$inject = ['homeEndpoints'];
    function homeController(homeEndpoints) {
      var vm = this;

      vm.welcome = 'Welcome to PodSurfer';
      vm.signupPrompt = 'New to PodSurfer? Create an account!'
      vm.outOfZen = false;
      vm.getZen = getZen;

      function getZen() {
        var success = function(response) {
          vm.outOfZen = false;
          console.log('Got Zen');
          vm.zen = response.data;
        };

        var error = function(response) {
          vm.outOfZen = true;
          console.error('Failed to get Zen');
          vm.zen = response.data.message;
        };

        return homeEndpoints.getZen().then(success, error);
      }
    }

})();
