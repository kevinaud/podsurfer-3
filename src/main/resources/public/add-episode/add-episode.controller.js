(function() {
  'use strict';

  angular.module('app')
    .controller('addEpisodeController', ['$scope', '$podcast', '$stateParams', 
      function($scope, $podcast, $stateParams) {

    console.log('PODCAST ID', $stateParams.podcastId);

    $scope.episode = {
      name: "",
      description: "",
      link: "",
      producer: ""
    };

    let hoursArray = Array.apply(null, {length: 24}).map(Number.call, Number).map(function(num){
      return num.toString();
    }).map(function(state) {
      return state;
    });

    let minutesArray = Array.apply(null, {length: 60}).map(Number.call, Number).map(function(num){
      return num.toString();
    }).map(function(state) {
      return state;
    });

    let secondsArray = Array.apply(null, {length: 60}).map(Number.call, Number).map(function(num){
      return num.toString();
    }).map(function(state) {
      return state;
    });

    $scope.length = {
      hours: hoursArray,
      minutes: minutesArray,
      seconds: secondsArray,
      selectedHours: 0,
      selectedMinutes: 0,
      selectedSeconds: 0,
    };

    $scope.submit = function() {

      console.log('submitting episode');
      
      convertToSeconds($scope.length);

      $podcast.getNumberOfEpisodes(1).then(
        function(numEpisodes) {

          let currentDate = getCurrentDate();

          let episode = {
            name: $scope.episode.name,
            description: $scope.episode.description,
            length: convertToSeconds($scope.length),
            upload_date: currentDate,
            number: numEpisodes + 1
          }

          $podcast.addEpisode($stateParams.podcastId, episode);

        },
        function(error) {
          console.log(error);
        }
      );

      /*$podcast.addPodcast(podcast).then(

        function(id) {
          console.log(id);
          $state.go('podcasts', { podcastId: id });
        },
        function(error) {
          console.log(error);
        }
      );*/

    }

    function convertToSeconds(lengthObj){

      let seconds = 0;

      seconds += parseInt(lengthObj.selectedSeconds);
      seconds += parseInt(lengthObj.selectedMinutes) * 60;
      seconds += parseInt(lengthObj.selectedHours) * 60 * 60;

      return seconds;

    }

    function getCurrentDate() {

      var today = new Date();
      var dd = today.getDate();
      var mm = today.getMonth()+1; //January is 0!
      var yyyy = today.getFullYear();

      if(dd<10) {
          dd='0'+dd
      } 

      if(mm<10) {
          mm='0'+mm
      } 

      return yyyy + '-' + mm + '-' + dd;
    
    }

  }]);

})();