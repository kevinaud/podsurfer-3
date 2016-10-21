function ToolbarController($scope, $element, $attrs, $state) {
  var ctrl = this;

  $scope.searchQuery = "";
  
  $scope.search = function(event) {
    
    if(event.key === "Enter"){
      console.log("Enter Pressed")

      $state.go('search', { query: $scope.searchQuery });
    }
    
  }

}

angular.module('app').component('toolbar', {
  templateUrl: 'toolbar/toolbar.html',
  controller: ToolbarController
});