(function() {
  'use strict';

  angular.module('app')
    .controller('addEpisodeController', ['$scope', '$podcast', '$state', 
      function($scope, $podcast, $state) {

    $scope.podcast = {
      name: "",
      description: "",
      link: "",
      producer: ""
    };

    /*let hoursArray = Array.apply(null, {length: 24}).map(Number.call, Number).map(function(num){
      return { number: num.toString() };
    });*/

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
    }

    $scope.tag = "";
    $scope.tags = [ ];

    $scope.addTag = function() {

      let exists = $scope.tags.find(function(tag) {
        return tag.name === $scope.tag;
      });

      if ($scope.tag !== "" && !exists){
        $scope.tags.push({ name: this.tag });
      }
      $scope.tag = "";
    };

    $scope.removeTag = function(tagName) {

      $scope.tags = $scope.tags.filter(function(tag){
        return tag.name != tagName;
      });

    }

    $scope.submit = function() {

      console.log('submitting podcast');

      let tags = $scope.tags.map(function(tag) {
        return tag.name;
      });
      
      let podcast = {
        name: $scope.podcast.name,
        description: $scope.podcast.description,
        producer: $scope.podcast.producer,
        link: $scope.podcast.link,
        tags: tags
      }

      $podcast.addPodcast(podcast).then(
        function(id) {
          console.log(id);
          $state.go('podcasts', { podcastId: id });
        },
        function(error) {
          console.log(error);
        }
      );

    }

  }]);

})();