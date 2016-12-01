(function() {
  'use strict';

  angular.module('app')
    .controller('addPodcastController', ['$scope', '$podcast', '$state', 
      function($scope, $podcast, $state) {

    $scope.podcast = {
      name: "",
      description: "",
      link: "",
      producer: ""
    };

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