angular // jshint ignore:line
    .module('podsurfer.feature')
    .controller('FeatureController', FeatureController);

FeatureController.$inject = ['$scope'];

// jshint maxstatements:50
function FeatureController($scope) {
    var feature = this;
    feature.message = 'Hello! You made it to the feature content!!';
}
