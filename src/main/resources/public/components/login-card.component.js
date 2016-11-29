function LoginCardController($scope, $user, $state) {

    this.$onInit = function(){

        if($user.auth)
            $state.go('home');
    }

    $scope.user = {
        email: '',
        password: ''
    };

    $scope.error = false;
    $scope.errorMessage = "";

    $scope.submitForm = function() {

        $user.login($scope.user).then(function(message) {

            $scope.errorMessage = message;
            $scope.error = !($scope.errorMessage === 'success');

            if(!$scope.error) {
                console.log("redirecting to home");
                $state.go('home');
            }
        }, function(msg) {
            $scope.error = true;
            $scope.errorMessage = "An unexpected error ocurred";
        });
    };
}

angular.module('app').component('login-card', {
    templateUrl: 'components/login-card.html',
    controller: [ '$scope', '$user', '$state', LoginCardController ]
});