(function() {
  'use strict';

    angular.module('app')
      .controller('homeController', homeController);

    function homeController() {
      var vm = this;

      vm.signupPrompt = 'New to PodSurfer? Create an account!'

    }

})();
