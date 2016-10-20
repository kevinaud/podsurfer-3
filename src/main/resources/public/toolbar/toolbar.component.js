function ToolbarController($scope, $element, $attrs) {
  var ctrl = this;

  $scope.searchQuery = "";
  
  $scope.search = function(event) {
    
    if(event.key === "Enter"){
      console.log("Enter Pressed")
    }
    
  }

}

angular.module('app').component('toolbar', {
  templateUrl: 'toolbar/toolbar.html',
  controller: ToolbarController
});