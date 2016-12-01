(function() {
  'use strict';

    angular.module('app')
        .controller('userProfileController', [ '$scope', '$user', '$podcast',
            function($scope, $user, $podcast) {

                var ref = this;

                $scope.user= $user;
                $user.subscribe(this);
                $scope.favorites = $user.favorites;
                $scope.numFavorites = 0;

                $scope.capitalize=function(string){
                    string = string.toLowerCase();
                    return string.substring(0,1).toUpperCase()+string.substring(1);
                }

                this.$onInit = function () {

                    if(!$user.auth) {
                        $state.go('home');
                    }

                    ref.update();

                }

                this.update = function() {
                    
                    $scope.favorites = $user.favorites;
                    console.log('FAVORITES', $scope.favorites);
                    $scope.podcasts = [];

                    $scope.favorites.forEach((id) => {

                        $podcast.getPodcast(id).then(
                            (response) => {

                                if(response.found === true) {
                                    console.log(response._source);
                                    $scope.podcasts.push(response);
                                    $scope.numFavorites++;
                                }

                            },
                            (error) => {
                                console.log(error);
                            }
                        );

                    });
                    
                    console.log('', $scope.podcasts);

                }

        angular.module('userProfileDynamicHeight', ['ngMaterial']);

      }]);
})();
