function ToolbarController($scope, $user, $element, $attrs, $state) {
  var ctrl = this;

  $scope.searchQuery = "";

  this.$onInit = function(){
    $scope.user = $user;
  }

  $scope.signOut = function(){
    $user.signOut();
  }
  
  $scope.search = function(event) {
    
    if(event.key === "Enter"){
      $state.go('search', { query: $scope.searchQuery });
    }
  }
}

angular.module('app').component('toolbar', {
  templateUrl: 'components/toolbar.html',
  controller: ToolbarController
});