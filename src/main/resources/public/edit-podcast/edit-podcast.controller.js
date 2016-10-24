(function() {
  'use strict';

  angular.module('app')
    .controller('editPodcastController', ['$scope', '$podcast', '$stateParams', 
      function($scope, $podcast, $stateParams) {

    $scope.podcast = {
      name: "",
      description: "",
      link: "",
      producer: ""
    };

    $scope.tag = "";
    $scope.tags = [ ];

    this.$onInit = function() {

      $podcast.getPodcast($stateParams.podcastId).then(
        function(podcast) {
          console.log(podcast);

          $scope.podcast.name = podcast._source.name;
          $scope.podcast.description = podcast._source.description;
          $scope.podcast.link = podcast._source.link;
          $scope.podcast.producer = podcast._source.producer;

          podcast._source.tags.map(function(tag){
            $scope.tags.push({ name:tag });
          });

        },
        function(error) {
          console.log(error);
        }
      );

    };

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