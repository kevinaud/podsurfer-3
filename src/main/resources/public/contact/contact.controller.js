(function() {
  'use strict';

    angular.module('app')
        .controller('contactController', contactController);

    contactController.$inject = [];
    function contactController() {
      var vm = this;
      vm.mentors = [
        {
          name: 'Kelleigh Maroney',
          email: 'kmaroney@credera.com',
          hobbies: [],
          university: 'Baylor Univsersity'
        },{
          name: 'Graeme Scruggs',
          email: 'gscruggs@credera.com',
          hobbies: [],
          university: 'Southern Methodist University'
        },{
          name: 'Christopher Blewett',
          email: 'cblewett@credera.com',
          hobbies: [],
          university: 'Baylor Univsersity'
        },{
          name: 'Trey Sedate',
          email: 'tsedate@credera.com',
          hobbies: [],
          university: 'Baylor Univsersity'
        },{
          name: 'John Lutteringer',
          email: 'jlutteringer@credera.com',
          hobbies: [],
          university: 'Baylor Univsersity'
        },{
          name: 'Ali Momin',
          email: 'amomin@credera.com',
          hobbies: [],
          university: 'Texas A&M Univsersity'
        }

      ]
    }
})();
